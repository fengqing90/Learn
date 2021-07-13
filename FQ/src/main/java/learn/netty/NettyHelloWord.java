package learn.netty;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.LineSeparator;
import io.netty.util.CharsetUtil;

/**
 * https://developer.aliyun.com/article/769587?utm_content=g_1000170046
 *
 * @author fengqing
 * @date 2021/7/7 20:23
 */
public class NettyHelloWord {

    public static void main(String[] args) throws Exception {
        //创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // bossGroup 用于监听客户端连接，专门负责与客户端创建连接，并把连接注册到workerGroup的Selector中。
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //workerGroup用于处理每一个连接发生的读写事件。

        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                //设置服务端通道实现类型
                // NioSocketChannel： 异步非阻塞的客户端 TCP Socket 连接。
                // NioServerSocketChannel： 异步非阻塞的服务器端 TCP Socket 连接。
                // 
                // 常用的就是这两个通道类型，因为是异步非阻塞的。所以是首选。
                // 
                //  OioSocketChannel： 同步阻塞的客户端 TCP Socket 连接。
                //  OioServerSocketChannel： 同步阻塞的服务器端 TCP Socket 连接。    
                //  NioSctpChannel： 异步的客户端 Sctp（Stream Control Transmission Protocol，流控制传输协议）连接。
                //  NioSctpServerChannel： 异步的 Sctp 服务器端连接。
                .channel(NioServerSocketChannel.class)
                //设置线程队列得到连接个数    
                .option(ChannelOption.SO_BACKLOG, 128) //SO_BACKLOG Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
                //设置保持活动连接状态    
                // SO_RCVBUF Socket参数，TCP数据接收缓冲区大小。
                // TCP_NODELAY TCP参数，立即发送数据，默认值为Ture。
                // SO_KEEPALIVE Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //使用匿名内部类的形式初始化通道对象    
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel)
                            throws Exception {
                        //*************************************************** 2.1 使用LineBasedFrameDecoder
                        //解码器需要设置数据的最大长度，我这里设置成1024
                        socketChannel.pipeline()
                            .addLast(new LineBasedFrameDecoder(1024));
                        //给pipeline管道设置处理器
                        // ChannelPipeline是Netty处理请求的责任链，ChannelHandler则是具体处理请求的处理器。实际上每一个channel都有一个处理器的流水线。
                        socketChannel.pipeline().addLast(new MyServerHandler());

                    }
                });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("java技术爱好者的服务端已经准备就绪...");
            //绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            //添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                //使用匿名内部类，ChannelFutureListener接口
                //重写operationComplete方法
                @Override
                public void operationComplete(ChannelFuture future)
                        throws Exception {
                    //判断是否操作成功    
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else {
                        System.out.println("连接失败");
                    }
                }
            });
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 自定义的Handler需要继承Netty规定好的HandlerAdapter
     * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
     * 处理器Handler主要分为两种：
     * ChannelInboundHandlerAdapter(入站处理器)：底层java NIO Channel 到 Netty的Channel
     * ChannelOutboundHandler(出站处理器)：Netty的Channel 操作底层 java NIO Channel
     **/
    static class MyServerHandler extends ChannelInboundHandlerAdapter {

        //5.1 taskQueue任务队列
        //5.2 scheduleTaskQueue延时任务队列
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            //获取客户端发送过来的消息
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("收到客户端" + ctx.channel().remoteAddress()
                + "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));

            //*************************************************** 5.1 taskQueue任务队列
            //taskQueue任务队列,如果Handler处理器有一些长时间的业务处理，可以交给taskQueue异步处理。
            //获取到线程池eventLoop，添加线程，执行
            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //长时间操作，不至于长时间的业务操作导致Handler阻塞
                        Thread.sleep(1000);
                        System.out.println("长时间的业务处理");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //*************************************************** 5.2 scheduleTaskQueue延时任务队列
            ctx.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        //长时间操作，不至于长时间的业务操作导致Handler阻塞
                        Thread.sleep(1000);
                        System.out.println("长时间的业务处理");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5, TimeUnit.SECONDS);//5秒后执行
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx)
                throws Exception {
            //发送消息给客户端
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?",
                CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            //发生异常，关闭通道
            ctx.close();
        }
    }

    static class MyClient {

        public static void main(String[] args) throws Exception {
            NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
            try {
                //创建bootstrap对象，配置参数
                Bootstrap bootstrap = new Bootstrap();
                //设置线程组
                bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型    
                    .channel(NioSocketChannel.class)
                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {

                            //*************************************************** 2.1 使用LineBasedFrameDecoder
                            //添加编码器，使用默认的符号\n，字符集是UTF-8
                            ch.pipeline().addLast(new LineEncoder(
                                LineSeparator.DEFAULT, CharsetUtil.UTF_8));
                            //*************************************************** 2.2 使用自定义长度帧解码器
                            //数据包最大长度是1024
                            //长度域的起始索引是0
                            //长度域的数据长度是4
                            //矫正值为0，因为长度域只有 有效数据的长度的值
                            //丢弃数据起始值是4，因为长度域长度为4，我要把长度域丢弃，才能得到有效数据
                            ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024,
                                    0, 4, 0, 4));
                            //添加客户端通道的处理器
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
                System.out.println("客户端准备就绪，随时可以起飞~");
                //连接服务端
                ChannelFuture channelFuture = bootstrap
                    .connect("127.0.0.1", 6666).sync();

                //添加监听器
                channelFuture.addListener(new ChannelFutureListener() {
                    //使用匿名内部类，ChannelFutureListener接口
                    //重写operationComplete方法
                    @Override
                    public void operationComplete(ChannelFuture future)
                            throws Exception {
                        //判断是否操作成功    
                        if (future.isSuccess()) {
                            System.out.println("连接成功");
                        } else {
                            System.out.println("连接失败");
                        }
                    }
                });

                //对通道关闭进行监听
                channelFuture.channel().closeFuture().sync();
            } finally {
                //关闭线程组
                eventExecutors.shutdownGracefully();
            }
        }
    }

    static class MyClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //发送消息到服务端
            ctx.writeAndFlush(Unpooled
                .copiedBuffer("歪比巴卜~茉莉~Are you good~马来西亚~", CharsetUtil.UTF_8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            //接收服务端发送过来的消息
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("收到服务端" + ctx.channel().remoteAddress() + "的消息："
                + byteBuf.toString(CharsetUtil.UTF_8));
        }
    }
}
