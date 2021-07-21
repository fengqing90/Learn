package learn.netty;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳和重连的整个过程：
 * 1）客户端连接服务端
 * 2）在客户端的的ChannelPipeline中加入一个比较特殊的IdleStateHandler，设置一下客户端的写空闲时间，例如5s
 * 3）当客户端的所有ChannelHandler中4s内没有write事件，则会触发userEventTriggered方法（上文介绍过）
 * 4）我们在客户端的userEventTriggered中对应的触发事件下发送一个心跳包给服务端，检测服务端是否还存活，防止服务端已经宕机，客户端还不知道
 * 5）同样，服务端要对心跳包做出响应，其实给客户端最好的回复就是“不回复”，这样可以服务端的压力，假如有10w个空闲Idle的连接，那么服务端光发送心跳回复，则也是费事的事情，那么怎么才能告诉客户端它还活着呢，其实很简单，因为5s服务端都会收到来自客户端的心跳信息，那么如果10秒内收不到，服务端可以认为客户端挂了，可以close链路
 * 6）加入服务端因为什么因素导致宕机的话，就会关闭所有的链路链接，所以作为客户端要做的事情就是短线重连
 * <br>
 * 要写工业级的Netty心跳重连的代码，需要解决一下几个问题：
 * 1）ChannelPipeline中的ChannelHandlers的维护，首次连接和重连都需要对ChannelHandlers进行管理
 * 2）重连对象的管理，也就是bootstrap对象的管理
 * 3）重连机制编写
 * ————————————————
 * 版权声明：本文为CSDN博主「BazingaLyncc」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/linuu/article/details/51509847
 * 
 * @author fengqing
 * @date 2021/7/16 10:17
 */
public class 生产级的心跳和重连机制 {
    /**
     * 客户端的ChannelHandler集合，由子类实现，这样做的好处：
     * 继承这个接口的所有子类可以很方便地获取ChannelPipeline中的Handlers
     * 获取到handlers之后方便ChannelPipeline中的handler的初始化和在重连的时候也能很方便
     * 地获取所有的handlers
     */
    interface ChannelHandlerHolder {
        ChannelHandler[] handlers();
    }

    @Slf4j
    public static class HeartBeatServer {
        public static void main(String[] args) throws Exception {
            int port;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            } else {
                port = 8080;
            }
            new HeartBeatServer(port).start();
        }

        private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();

        private int port;

        public HeartBeatServer(int port) {
            this.port = port;
        }

        public void start() {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap sbs = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(this.port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0,
                                TimeUnit.SECONDS));
                            ch.pipeline()
                                .addLast(HeartBeatServer.this.idleStateTrigger);
                            ch.pipeline().addLast("decoder",
                                new StringDecoder());
                            ch.pipeline().addLast("encoder",
                                new StringEncoder());
                            ch.pipeline().addLast(new HeartBeatServerHandler());
                        }

                    ;

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
                // 绑定端口，开始接收进来的连接
                ChannelFuture future = sbs.bind(this.port).sync();

                log.info("Server start listen at " + this.port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }

        public class HeartBeatServerHandler
                extends ChannelInboundHandlerAdapter {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg)
                    throws Exception {
                log.info("server channelRead..");
                log.info(ctx.channel().remoteAddress() + "->Server :"
                    + msg.toString());
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close();
            }

        }

    }

    /**
     * 重写userEventTriggered方法，因为客户端是write，那么服务端自然是read，设置的状态就是IdleState.READER_IDLE，源码如下：
     */
    @ChannelHandler.Sharable
    public static class AcceptorIdleStateTrigger
            extends ChannelInboundHandlerAdapter {

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
                throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleState state = ((IdleStateEvent) evt).state();
                if (state == IdleState.READER_IDLE) {
                    throw new Exception("idle exception");
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    @Slf4j
    public static class HeartBeatsClient {
        public static void main(String[] args) throws Exception {
            int port = 8080;
            if (args != null && args.length > 0) {
                try {
                    port = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    // 采用默认值
                }
            }
            new HeartBeatsClient().connect(port, "127.0.0.1");
        }

        protected final HashedWheelTimer timer = new HashedWheelTimer();

        private Bootstrap boot;

        private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

        public void connect(int port, String host) throws Exception {

            EventLoopGroup group = new NioEventLoopGroup();

            this.boot = new Bootstrap();
            this.boot.group(group).channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

            final ConnectionWatchdog watchdog = new ConnectionWatchdog(
                this.boot, this.timer, port, host, true) {

                @Override
                public ChannelHandler[] handlers() {
                    return new ChannelHandler[] { this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        HeartBeatsClient.this.idleStateTrigger,
                        new StringDecoder(), new StringEncoder(),
                        new HeartBeatClientHandler() };
                }
            };

            ChannelFuture future;
            //进行连接
            try {
                synchronized (this.boot) {
                    this.boot.handler(new ChannelInitializer<Channel>() {

                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch)
                                throws Exception {
                            ch.pipeline().addLast(watchdog.handlers());
                        }
                    });

                    future = this.boot.connect(host, port);
                }

                // 以下代码在synchronized同步块外面是安全的
                future.sync();
            } catch (Throwable t) {
                throw new Exception("connects to  fails", t);
            }
        }

        @ChannelHandler.Sharable
        public class HeartBeatClientHandler
                extends ChannelInboundHandlerAdapter {

            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                log.info("激活时间是：" + new Date()
                    + " HeartBeatClientHandler channelActive");
                ctx.fireChannelActive();
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx)
                    throws Exception {
                log.info("停止时间是：" + new Date()
                    + " HeartBeatClientHandler channelInactive");
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg)
                    throws Exception {
                String message = (String) msg;
                log.info(message);
                if (message.equals("Heartbeat")) {
                    ctx.write("has read message from server");
                    ctx.flush();
                }
                ReferenceCountUtil.release(msg);
            }
        }
    }

    /**
     * 重连检测狗，当发现当前的链路不稳定关闭之后，进行12次重连
     */
    @ChannelHandler.Sharable
    @Slf4j
    public abstract static class ConnectionWatchdog
            extends ChannelInboundHandlerAdapter
            implements TimerTask, ChannelHandlerHolder {

        private final Bootstrap bootstrap;
        private final Timer timer;
        private final int port;

        private final String host;

        private volatile boolean reconnect = true;
        private int attempts;

        public ConnectionWatchdog(Bootstrap bootstrap, HashedWheelTimer timer,
                int port, String host, boolean reconnect) {
            this.bootstrap = bootstrap;
            this.timer = timer;
            this.port = port;
            this.host = host;
            this.reconnect = reconnect;
        }

        /**
         * channel链路每次active的时候，将其连接的次数重新☞ 0
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            log.info("当前链路已经激活了，重连尝试次数重新置为0");

            this.attempts = 0;
            ctx.fireChannelActive();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx)
                throws Exception {
            log.info("链接关闭");
            if (this.reconnect) {
                log.info("链接关闭，将进行重连");
                if (this.attempts < 12) {
                    this.attempts++;
                    //重连的间隔时间会越来越长
                    int timeout = 2 << this.attempts;
                    log.info("次数小于12,将进行重连。timeout={}", timeout);
                    this.timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
                }
            }
            ctx.fireChannelInactive();
        }

        public void run(Timeout timeout) throws Exception {

            ChannelFuture future;
            //bootstrap已经初始化好了，只需要将handler填入就可以了
            synchronized (this.bootstrap) {
                this.bootstrap.handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {

                        ch.pipeline()
                            .addLast(ConnectionWatchdog.this.handlers());
                    }
                });
                future = this.bootstrap.connect(this.host, this.port);
            }
            //future对象
            future.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture f)
                        throws Exception {
                    boolean succeed = f.isSuccess();

                    //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                    if (!succeed) {
                        log.info("重连失败");
                        f.channel().pipeline().fireChannelInactive();
                    } else {
                        log.info("重连成功");
                    }
                }
            });

        }

    }

    @ChannelHandler.Sharable
    public static class ConnectorIdleStateTrigger
            extends ChannelInboundHandlerAdapter {

        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(
                Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
                throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleState state = ((IdleStateEvent) evt).state();
                if (state == IdleState.WRITER_IDLE) {
                    // write heartbeat to server
                    ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
