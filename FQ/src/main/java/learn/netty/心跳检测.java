package learn.netty;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
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
import io.netty.util.ReferenceCountUtil;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/13 20:04
 */
public class 心跳检测 {

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
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0,
                                TimeUnit.SECONDS));
                            ch.pipeline().addLast("decoder",
                                new StringDecoder());
                            ch.pipeline().addLast("encoder",
                                new StringEncoder());
                            ch.pipeline().addLast(new HeartBeatServerHandler());
                        };

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
                // 绑定端口，开始接收进来的连接
                ChannelFuture future = sbs.bind(port).sync();

                System.out.println("Server start listen at " + port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }

        public class HeartBeatServerHandler
                extends ChannelInboundHandlerAdapter {

            private int loss_connect_time = 0;

            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                System.out.println("channelActive " + new Date());
                super.channelActive(ctx);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg)
                    throws Exception {
                System.out.println("server channelRead..");
                System.out.println(ctx.channel().remoteAddress() + "->Server :"
                    + msg.toString());
            }

            @Override
            public void userEventTriggered(ChannelHandlerContext ctx,
                    Object evt) throws Exception {
                if (evt instanceof IdleStateEvent) {
                    IdleStateEvent event = (IdleStateEvent) evt;
                    if (event.state() == IdleState.READER_IDLE) {
                        loss_connect_time++;
                        System.out.println("5 秒没有接收到客户端的信息了");
                        if (loss_connect_time > 2) {
                            System.out.println("关闭这个不活跃的channel");
                            ctx.channel().close();
                        }
                    }
                } else {
                    super.userEventTriggered(ctx, evt);
                }
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close();
            }

        }
    }

    public static class HeartBeatsClient {
        /**
         * @param args
         * @throws Exception
         */
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

        public void connect(int port, String host) throws Exception {
            // Configure the client.
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("ping", new IdleStateHandler(0, 4, 0,
                                TimeUnit.SECONDS));
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new HeartBeatClientHandler());
                        }
                    });

                ChannelFuture future = b.connect(host, port).sync();
                future.channel().closeFuture().sync();
            } finally {
                group.shutdownGracefully();
            }
        }

        public static class HeartBeatClientHandler
                extends ChannelInboundHandlerAdapter {

            private final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
                .unreleasableBuffer(
                    Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));

            private static final int TRY_TIMES = 3;

            private int currentTime = 0;

            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                System.out.println("激活时间是：" + new Date());
                System.out.println("HeartBeatClientHandler channelActive");
                ctx.fireChannelActive();
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx)
                    throws Exception {
                System.out.println("停止时间是：" + new Date());
                System.out.println("HeartBeatClientHandler channelInactive");
            }

            @Override
            public void userEventTriggered(ChannelHandlerContext ctx,
                    Object evt) throws Exception {
                System.out.println("循环触发时间：" + new Date());
                if (evt instanceof IdleStateEvent) {
                    IdleStateEvent event = (IdleStateEvent) evt;
                    if (event.state() == IdleState.WRITER_IDLE) {
                        if (currentTime <= TRY_TIMES) {
                            System.out.println("currentTime:" + currentTime);
                            currentTime++;
                            ctx.channel()
                                .writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                        }
                    }
                }
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg)
                    throws Exception {
                String message = (String) msg;
                System.out.println(message);
                if (message.equals("Heartbeat")) {
                    ctx.write("has read message from server");
                    ctx.flush();
                }
                ReferenceCountUtil.release(msg);
            }

        }
    }
}
