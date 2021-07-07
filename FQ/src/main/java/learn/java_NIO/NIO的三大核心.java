package learn.java_NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * https://mp.weixin.qq.com/s/GfV9w2B0mbT7PmeBS45xLw?spm=a2c6h.12873639.0.0.28b24a61xzeqTp
 *
 * @author fengqing
 * @date 2021/7/7 16:53
 */
public class NIO的三大核心 {

    /**
     * Buffer是一个内存块。在NIO中，所有的数据都是用Buffer处理，有读写两种模式。
     * 所以NIO和传统的IO的区别就体现在这里。传统IO是面向Stream流，NIO而是面向缓冲区(Buffer)。
     */
    static class ByteBufferTest {
        public static void main(String[] args) throws Exception {
            String msg = "java技术爱好者，起飞！";
            //创建一个固定大小的buffer(返回的是HeapByteBuffer)
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byte[] bytes = msg.getBytes();
            //写入数据到Buffer中
            byteBuffer.put(bytes);
            //切换成读模式，关键一步
            byteBuffer.flip();
            //创建一个临时数组，用于存储获取到的数据
            byte[] tempByte = new byte[bytes.length];
            int i = 0;
            //如果还有数据，就循环。循环判断条件
            while (byteBuffer.hasRemaining()) {
                //获取byteBuffer中的数据
                byte b = byteBuffer.get();
                //放到临时数组中
                tempByte[i] = b;
                i++;
            }
            //打印结果
            System.out.println(new String(tempByte));//java技术爱好者，起飞！
        }
    }

    /**
     * 常用的Channel有这四种：
     * FileChannel，读写文件中的数据。
     * SocketChannel，通过TCP读写网络中的数据。
     * ServerSockectChannel，监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
     * DatagramChannel，通过UDP读写网络中的数据。
     */
    static class FileChannelTest {
        public static void main(String[] args) throws Exception {
            //获取文件输入流
            File file = new File("1.txt");
            FileInputStream inputStream = new FileInputStream(file);
            //从文件输入流获取通道
            FileChannel inputStreamChannel = inputStream.getChannel();
            //获取文件输出流
            FileOutputStream outputStream = new FileOutputStream(
                new File("2.txt"));
            //从文件输出流获取通道
            FileChannel outputStreamChannel = outputStream.getChannel();
            //创建一个byteBuffer，小文件所以就直接一次读取，不分多次循环了
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            //把输入流通道的数据读取到缓冲区
            inputStreamChannel.read(byteBuffer);
            //切换成读模式
            byteBuffer.flip();
            //把数据从缓冲区写入到输出流通道
            outputStreamChannel.write(byteBuffer);
            //关闭通道
            outputStream.close();
            inputStream.close();
            outputStreamChannel.close();
            inputStreamChannel.close();
        }
    }

    /*
     * 阻塞的
     */
    static class SocketChannelTest {
        /*
         * cmd
         * telnet localhost 6666
         * 输入
         */
        public static void main(String[] args) throws Exception {
            //获取ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel
                .open();
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",
                6666);
            //绑定地址，端口号
            serverSocketChannel.bind(address);
            //创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true) {
                //获取SocketChannel
                SocketChannel socketChannel = serverSocketChannel.accept();
                while (socketChannel.read(byteBuffer) != -1) {
                    //打印结果
                    System.out.println(new String(byteBuffer.array()));
                    //清空缓冲区
                    byteBuffer.clear();
                }
            }
        }
    }

}
