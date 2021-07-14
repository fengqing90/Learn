package learn.netty;

import java.net.URI;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * 快速入门实例 - HTTP服务
 * 1、实例要求：使用 IDEA 创建 Netty 项目
 * 2、Netty 服务器在 6668 端口监听，浏览器发出请求 http://localhost:6668/
 * 3、服务器可以回复消息给客户端"Hello!我是服务器5",并对特定请求资源进行过滤。
 * 4、目的：Netty 可以做 Http 服务开发，并且理解 Handler 实例和客户端及其请求的关系。
 * ————————————————
 * 版权声明：本文为CSDN博主「youthlql」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Youth_lql/article/details/115734142
 *
 * @author fengqing
 * @date 2021/7/13 17:10
 */
public class HttpNetty {

    public static void main(String[] args) throws Exception {
        HttpServer.run();
    }

    static class HttpServer {
        private static void run() throws InterruptedException {
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

                            //向管道加入处理器

                            //得到管道
                            ChannelPipeline pipeline = ch.pipeline();

                            //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
                            //HttpServerCodec 说明
                            //1. HttpServerCodec 是netty 提供的处理http的 编-解码器
                            pipeline.addLast("MyHttpServerCodec",
                                new HttpServerCodec());
                            //2. 增加一个自定义的handler
                            pipeline.addLast("MyTestHttpServerHandler",
                                new HttpServerHandler());

                            System.out.println("ok~~~~");
                        }
                    });

                ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

                channelFuture.channel().closeFuture().sync();

            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }

        /*
         * 也可以HttpProcessHandler
         * 说明
         * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
         * 2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
         */
        static class HttpServerHandler
                extends SimpleChannelInboundHandler<HttpObject> {

            //channelRead0 读取客户端数据
            @Override
            protected void channelRead0(ChannelHandlerContext ctx,
                    HttpObject msg) throws Exception {

                System.out.println("对应的channel=" + ctx.channel() + " pipeline="
                    + ctx.pipeline() + " 通过pipeline获取channel"
                    + ctx.pipeline().channel());

                System.out.println("当前ctx的handler=" + ctx.handler());

                //判断 msg 是不是 httprequest请求
                if (msg instanceof HttpRequest) {

                    System.out.println("ctx 类型=" + ctx.getClass());

                    System.out
                        .println("pipeline hashcode" + ctx.pipeline().hashCode()
                            + " TestHttpServerHandler hash=" + this.hashCode());

                    System.out.println("msg 类型=" + msg.getClass());
                    System.out.println("客户端地址" + ctx.channel().remoteAddress());

                    //获取到
                    HttpRequest httpRequest = (HttpRequest) msg;
                    //获取uri, 过滤指定的资源
                    URI uri = new URI(httpRequest.uri());
                    if ("/favicon.ico".equals(uri.getPath())) {
                        System.out.println("请求了 favicon.ico, 不做响应");
                        return;
                    }
                    //回复信息给浏览器 [http协议]

                    ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器",
                        CharsetUtil.UTF_8);

                    //构造一个http的相应，即 httpresponse
                    FullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

                    response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                        "text/plain");
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                        content.readableBytes());

                    //将构建好 response返回
                    ctx.writeAndFlush(response);

                }
            }

        }
    }

}
