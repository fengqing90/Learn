package learn.netty;

/**
 * https://mp.weixin.qq.com/s?__biz=MzU1OTgzNTAzNQ==&mid=2247483892&idx=1&sn=1f568454533c8ba4b1f85d1b3bfc1323&chksm=fc107300cb67fa161a77d828387928809fa398d4a1128b3bb65715a3b88df29274e98b8ea320&scene=178&cur_album_id=1445826382487207937#rd
 * 二、解决方案
 * 总体思路可以分为三种：
 * 在数据的末尾添加特殊的符号标识数据包的边界。通常会加\n\r、\t或者其他的符号。
 * 在数据的头部声明数据的长度，按长度获取数据。
 * 规定报文的长度，不足则补空位。读取时按规定好的长度来读取。
 * 
 * @author fengqing
 * @date 2021/7/8 17:14
 */
public class 粘包和拆包 {
    //首先准备客户端负责发送消息，连续发送5次消息，代码如下：
    // for (int i = 1; i <= 5; i++) {
    //         ByteBuf byteBuf = Unpooled.copiedBuffer("msg No" + i + " ", Charset.forName("utf-8"));
    //         ctx.writeAndFlush(byteBuf);
    //     }

    // 然后服务端作为接收方，接收并且打印结果：
    ////count变量，用于计数
    // private int count = 0;
    // 
    // @Override
    // protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    //     byte[] bytes = new byte[msg.readableBytes()];
    //     //把ByteBuf的数据读到bytes数组中
    //     msg.readBytes(bytes);
    //     String message = new String(bytes, Charset.forName("utf-8"));
    //     System.out.println("服务器接收到数据：" + message);
    //     //打印接收的次数
    //     System.out.println("接收到的数据量是：" + (++this.count));
    // }
}
