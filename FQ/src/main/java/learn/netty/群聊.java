package learn.netty;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 应用实例-群聊系统
 * 实例要求：
 * 1、编写一个 Netty 群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 2、实现多人群聊
 * 3、服务器端：可以监测用户上线，离线，并实现消息转发功能
 * 4、客户端：通过 channel 可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息（有服务器转发得到）
 * 5、目的：进一步理解 Netty 非阻塞网络编程机制
 * ————————————————
 * 版权声明：本文为CSDN博主「youthlql」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Youth_lql/article/details/115734142
 *
 * @author fengqing
 * @date 2021/7/13 17:30
 */
public class 群聊 {
    @Slf4j
    public static class GroupChatServer {

        public static void main(String[] args) throws Exception {
            new GroupChatServer(7000).run();
        }

        private int port; //监听端口

        public GroupChatServer(int port) {
            this.port = port;
        }

        //编写run方法，处理客户端的请求
        public void run() throws Exception {

            //创建两个线程组
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup(); //8个NioEventLoop

            try {
                ServerBootstrap b = new ServerBootstrap();

                b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {

                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(new GroupChatServerHandler());

                        }
                    });

                System.out.println("netty 服务器启动");
                ChannelFuture channelFuture = b.bind(port).sync();

                //监听关闭
                channelFuture.channel().closeFuture().sync();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        }

        class GroupChatServerHandler
                extends SimpleChannelInboundHandler<String> {

            //这样写还要自己遍历Channel
            //public static List<Channel> channels = new ArrayList<Channel>();

            //使用一个hashmap 管理私聊（私聊本案例并未实现，只是提供个思路）
            //public static Map<String, Channel> channels = new HashMap<String,Channel>();

            //定义一个channle 组，管理所有的channel
            //GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
            private ChannelGroup channelGroup = new DefaultChannelGroup(
                GlobalEventExecutor.INSTANCE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //handlerAdded 表示连接建立，一旦连接，第一个被执行
            //将当前channel 加入到  channelGroup
            @Override
            public void handlerAdded(ChannelHandlerContext ctx)
                    throws Exception {
                Channel channel = ctx.channel();
                //将该客户加入聊天的信息推送给其它在线的客户端

                //该方法会将 channelGroup 中所有的channel 遍历，并发送消息，我们不需要自己遍历

                channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress()
                    + " 加入聊天" + sdf.format(new java.util.Date()) + " \n");
                channelGroup.add(channel);

                //私聊如何实现
//         channels.put（"userid100",channel）;

            }

            //断开连接, 将xx客户离开信息推送给当前在线的客户
            @Override
            public void handlerRemoved(ChannelHandlerContext ctx)
                    throws Exception {

                Channel channel = ctx.channel();
                channelGroup.writeAndFlush(
                    "[客户端]" + channel.remoteAddress() + " 离开了\n");
                System.out.println("channelGroup size" + channelGroup.size());

            }

            //表示channel 处于活动状态, 提示 xx上线
            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                //这个是给服务端看的，客户端上面已经提示xxx加入群聊了
                System.out.println(ctx.channel().remoteAddress() + " 上线了~");
            }

            //表示channel 处于不活动状态, 提示 xx离线了
            @Override
            public void channelInactive(ChannelHandlerContext ctx)
                    throws Exception {

                System.out.println(ctx.channel().remoteAddress() + " 离线了~");
            }

            //读取数据，转发给在线的每一个客户端
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg)
                    throws Exception {

                //获取到当前channel
                Channel channel = ctx.channel();
                //这时我们遍历channelGroup, 根据不同的情况，回送不同的消息

                log.info("【转发消息】remoteAddress=[{}],msg=[{}]",
                    ctx.channel().remoteAddress(), msg);
                channelGroup.forEach(ch -> {
                    if (channel != ch) { //不是当前的channel,转发消息
                        ch.writeAndFlush("[客户]" + channel.remoteAddress()
                            + " 发送了消息" + msg + "\n");
                    } else {//回显自己发送的消息给自己
                        ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
                    }
                });
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                //关闭通道
                ctx.close();
            }
        }

    }

    @Slf4j
    public static class GroupChatClient {
        public static void main(String[] args) throws Exception {
            new GroupChatClient("127.0.0.1", 7000).run();
        }

        //属性
        private final String host;
        private final int port;

        public GroupChatClient(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void run() throws Exception {
            EventLoopGroup group = new NioEventLoopGroup();

            try {

                Bootstrap bootstrap = new Bootstrap().group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {

                            //得到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入相关handler
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自定义的handler
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });

                ChannelFuture channelFuture = bootstrap.connect(host, port)
                    .sync();
                //得到channel
                Channel channel = channelFuture.channel();
                System.out
                    .println("-------" + channel.localAddress() + "--------");
                //客户端需要输入信息，创建一个扫描器
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String msg = scanner.nextLine();
                    //通过channel 发送到服务器端
                    channel.writeAndFlush(msg + "\r\n");
                }
            } finally {
                group.shutdownGracefully();
            }
        }

        public class GroupChatClientHandler
                extends SimpleChannelInboundHandler<String> {

            //从服务器拿到的数据
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, String msg)
                    throws Exception {
                System.out.println(msg.trim());
            }
        }
    }
}
