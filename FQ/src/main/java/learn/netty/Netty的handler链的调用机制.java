package learn.netty;

import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/14 14:51
 */
public class Netty的handler链的调用机制 {

    public static class MyServer {
        public static void main(String[] args) throws Exception {

            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {

                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer()); //自定义一个初始化类

                ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
                channelFuture.channel().closeFuture().sync();

            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        }

        public static class MyServerInitializer
                extends ChannelInitializer<SocketChannel> {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();//一会下断点

                //入站的handler进行解码 MyByteToLongDecoder
                pipeline.addLast(new MyByteToLongDecoder());
                //出站的handler进行编码
                pipeline.addLast(new MyLongToByteEncoder());
                //自定义的handler 处理业务逻辑
                pipeline.addLast(new MyServerHandler());
                System.out.println("MyServerInitializer 完成");
            }
        }

        public static class MyServerHandler
                extends SimpleChannelInboundHandler<Long> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Long msg)
                    throws Exception {

                System.out.println(
                    "从客户端" + ctx.channel().remoteAddress() + " 读取到long " + msg);

                //给客户端发送一个long
                ctx.writeAndFlush(98765L);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx,
                    Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close();
            }
        }

    }

    public static class MyClient {
        public static void main(String[] args) throws Exception {

            EventLoopGroup group = new NioEventLoopGroup();

            try {

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer()); //自定义一个初始化类

                ChannelFuture channelFuture = bootstrap
                    .connect("localhost", 7000).sync();

                channelFuture.channel().closeFuture().sync();

            } finally {
                group.shutdownGracefully();
            }
        }

        public static class MyClientInitializer
                extends ChannelInitializer<SocketChannel> {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ChannelPipeline pipeline = ch.pipeline();

                //加入一个出站的handler 对数据进行一个编码
                pipeline.addLast(new MyLongToByteEncoder());

                //这时一个入站的解码器(入站handler )
                pipeline.addLast(new MyByteToLongDecoder());
                //加入一个自定义的handler ， 处理业务
                pipeline.addLast(new MyClientHandler());

            }
        }

        public static class MyClientHandler
                extends SimpleChannelInboundHandler<Long> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Long msg)
                    throws Exception {

                System.out.println("服务器的ip=" + ctx.channel().remoteAddress());
                System.out.println("收到服务器消息=" + msg);

            }

            //重写channelActive 发送数据

            @Override
            public void channelActive(ChannelHandlerContext ctx)
                    throws Exception {
                System.out.println("MyClientHandler 发送数据");
                //ctx.writeAndFlush(Unpooled.copiedBuffer(""))
                ctx.writeAndFlush(123456L); //发送的是一个long
            }
        }
    }

    public static class MyByteToLongDecoder extends ByteToMessageDecoder {
        /**
         * decode 会根据接收的数据，被调用多次, 直到确定没有新的元素被添加到list
         * , 或者是ByteBuf 没有更多的可读字节为止
         * 如果list out 不为空，就会将list的内容传递给下一个 channelinboundhandler处理,
         * 该处理器的方法也会被调用多次
         *
         * @param ctx
         *        上下文对象
         * @param in
         *        入站的 ByteBuf
         * @param out
         *        List 集合，将解码后的数据传给下一个handler
         * @throws Exception
         */
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                List<Object> out) throws Exception {

            System.out.println("MyByteToLongDecoder 被调用");
            //因为 long 8个字节, 需要判断有8个字节，才能读取一个long
            if (in.readableBytes() >= 8) {
                out.add(in.readLong());
            }
        }
    }

    public static class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
        //编码方法
        @Override
        protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out)
                throws Exception {

            System.out.println("MyLongToByteEncoder encode 被调用");
            System.out.println("msg=" + msg);
            out.writeLong(msg);

        }
    }

}
