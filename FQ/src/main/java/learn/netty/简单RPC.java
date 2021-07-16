package learn.netty;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.cglib.proxy.Proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
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

/**
 * 过程：
 * 1、调用者(Caller)，调用远程API(Remote API)
 * 2、调用远程API会通过一个RPC代理(RpcProxy)
 * 3、RPC代理再去调用RpcInvoker(这个是PRC的调用者)
 * 4、RpcInvoker通过RPC连接器(RpcConnector)
 * 5、RPC连接器用两台机器规定好的PRC协议(RpcProtocol)把数据进行编码
 * 6、接着RPC连接器通过RpcChannel通道发送到对方的PRC接收器(RpcAcceptor)
 * 7、PRC接收器通过PRC协议进行解码拿到数据
 * 8、然后将数据传给RpcProcessor
 * 9、RpcProcessor再传给RpcInvoker
 * 10、RpcInvoker调用Remote API
 * 11、最后推给被调用者(Callee)
 * ————————————————
 * 版权声明：本文为CSDN博主「youthlql」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Youth_lql/article/details/116015820
 *
 * @author fengqing
 * @date 2021/7/14 15:29
 */
public class 简单RPC {

    public static class NettyServer {
        public static void main(String[] args) {

            //代码代填..
            NettyServer.startServer("127.0.0.1", 7000);
        }

        public static void startServer(String hostName, int port) {
            startServer0(hostName, port);
        }

        //编写一个方法，完成对NettyServer的初始化和启动

        private static void startServer0(String hostname, int port) {

            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {

                ServerBootstrap serverBootstrap = new ServerBootstrap();

                serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyServerHandler()); //业务处理器

                        }
                    }

                    );

                ChannelFuture channelFuture = serverBootstrap
                    .bind(hostname, port).sync();
                System.out.println("服务提供方开始提供服务~~");
                channelFuture.channel().closeFuture().sync();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        }

        //服务器这边handler比较简单
        public static class NettyServerHandler
                extends ChannelInboundHandlerAdapter {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg)
                    throws Exception {
                System.out.println("---服务端开始收到来自客户单的消息---");
                //获取客户端发送的消息，并调用服务
                System.out.println("原始消息：" + msg);

                /*
                 * 1.客户端在调用服务器的api 时，我们需要定义一个协议，比如我们要求 每次发消息是都
                 * 必须以某个字符串开头 "HelloService#hello#你好"
                 * 2.Dubbo注册在Zookeeper里时，这种就是类的全路径字符串，你用IDEA的zookeeper插件
                 * 就可以清楚地看到
                 */
                if (msg.toString().startsWith(NettyClient.providerName)) {

                    String result = new HelloServiceImpl().hello(msg.toString()
                        .substring(msg.toString().lastIndexOf("#") + 1));
                    ctx.writeAndFlush(result);
                }
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                ctx.close();
            }
        }

        public static class HelloServiceImpl implements HelloService {

            private static int count = 0;

            //当有消费方调用该方法时， 就返回一个结果
            @Override
            public String hello(String mes) {
                System.out.println("收到客户端消息=" + mes);
                System.out.println();
                //根据mes 返回不同的结果
                if (mes != null) {
                    return "你好客户端, 我已经收到你的消息。消息为：[" + mes + "] ，第" + (++count)
                        + " 次 \n";
                } else {
                    return "你好客户端, 我已经收到你的消息 ";
                }
            }
        }
    }

    public static class NettyClient {
        //这里定义协议头
        public static final String providerName = "HelloService#hello#";

        public static void main(String[] args) throws Exception {

            //创建一个消费者
            NettyClient customer = new NettyClient();

            //创建代理对象
            HelloService service = (HelloService) customer
                .getBean(HelloService.class, providerName);

            for (;;) {
                Thread.sleep(2 * 1000);
                //通过代理对象调用服务提供者的方法(服务)
                String res = service.hello("你好 dubbo~");
                System.out.println("调用的结果 res= " + res);
            }
        }

        //创建线程池
        private static ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        private static NettyClientHandler client;
        private int count = 0;

        //编写方法使用代理模式，获取一个代理对象

        public Object getBean(final Class<?> serivceClass,
                final String providerName) {

            return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] { serivceClass }, (proxy, method, args) -> {

                    System.out.println(
                        "(proxy, method, args) 进入...." + (++count) + " 次");
                    //{}  部分的代码，客户端每调用一次 hello, 就会进入到该代码
                    if (client == null) {
                        initClient();
                    }

                    //设置要发给服务器端的信息
                    //providerName：协议头，args[0]：就是客户端要发送给服务端的数据
                    client.setPara(providerName + args[0]);

                    //
                    return executor.submit(client).get();

                });
        }

        //初始化客户端
        private static void initClient() {
            client = new NettyClientHandler();
            //创建EventLoopGroup
            NioEventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch)
                            throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });

            try {
                bootstrap.connect("127.0.0.1", 7000).sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static class NettyClientHandler
                extends ChannelInboundHandlerAdapter implements Callable {

            private ChannelHandlerContext context;//上下文
            private String result; //返回的结果
            private String para; //客户端调用方法时，传入的参数

            //与服务器的连接创建后，就会被调用, 这个方法是第一个被调用(1)
            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                System.out.println(" channelActive 被调用  ");
                context = ctx; //因为我们在其它方法会使用到 ctx
            }

            //收到服务器的数据后，调用方法 (4)
            //
            @Override
            public synchronized void channelRead(ChannelHandlerContext ctx,
                    Object msg) throws Exception {
                System.out.println(" channelRead 被调用  ");
                result = msg.toString();
                notify(); //唤醒等待的线程
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                ctx.close();
            }

            //被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)-》5
            @Override
            public synchronized Object call() throws Exception {
                System.out.println(" call1 被调用  ");
                context.writeAndFlush(para);
                //进行wait
                wait(); //等待channelRead 方法获取到服务器的结果后，唤醒
                System.out.println(" call2 被调用  ");
                return result; //服务方返回的结果

            }

            //(2)
            void setPara(String para) {
                System.out.println(" setPara  ");
                this.para = para;
            }
        }
    }

    //这个是接口，是服务提供方和 服务消费方都需要
    public interface HelloService {

        String hello(String mes);
    }
}
