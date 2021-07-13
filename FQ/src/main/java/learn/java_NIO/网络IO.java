package learn.java_NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/7 17:25
 */
public class 网络IO {
    public static class NIOServer {
        /**
         * public abstract class SelectionKey {
         * //读事件
         * public static final int OP_READ = 1 << 0; //2^0=1
         * //写事件
         * public static final int OP_WRITE = 1 << 2; // 2^2=4
         * //连接操作,Client端支持的一种操作
         * public static final int OP_CONNECT = 1 << 3; // 2^3=8
         * //连接可接受操作,仅ServerSocketChannel支持
         * public static final int OP_ACCEPT = 1 << 4; // 2^4=16
         * }
         */
        public static void main(String[] args) throws Exception {
            //打开一个ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel
                .open();
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",
                6666);
            //绑定地址
            serverSocketChannel.bind(address);
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //打开一个选择器
            Selector selector = Selector.open();
            //serverSocketChannel注册到选择器中,监听连接事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //循环等待客户端的连接
            while (true) {
                //等待3秒，（返回0相当于没有事件）如果没有事件，则跳过
                if (selector.select(3000) == 0) {
                    System.out.println("服务器等待3秒，没有连接");
                    continue;
                }
                //如果有事件selector.select(3000)>0的情况,获取事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //获取迭代器遍历
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    //获取到事件
                    SelectionKey selectionKey = it.next();
                    //判断如果是连接事件
                    if (selectionKey.isAcceptable()) {
                        //服务器与客户端建立连接，获取socketChannel
                        SocketChannel socketChannel = serverSocketChannel
                            .accept();
                        //设置成非阻塞
                        socketChannel.configureBlocking(false);
                        //把socketChannel注册到selector中，监听读事件，并绑定一个缓冲区
                        socketChannel.register(selector, SelectionKey.OP_READ,
                            ByteBuffer.allocate(1024));
                    }
                    //如果是读事件
                    if (selectionKey.isReadable()) {
                        //获取通道
                        SocketChannel socketChannel = (SocketChannel) selectionKey
                            .channel();
                        //获取关联的ByteBuffer
                        ByteBuffer buffer = (ByteBuffer) selectionKey
                            .attachment();
                        //打印从客户端获取到的数据
                        socketChannel.read(buffer);
                        System.out
                            .println("from 客户端：" + new String(buffer.array()));
                    }
                    //从事件集合中删除已处理的事件，防止重复处理
                    it.remove();
                }
            }
        }
    }

    public static class NIOClient {
        public static void main(String[] args) throws Exception {
            SocketChannel socketChannel = SocketChannel.open();
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",
                6666);
            socketChannel.configureBlocking(false);
            //连接服务器
            boolean connect = socketChannel.connect(address);
            //判断是否连接成功
            if (!connect) {
                //等待连接的过程中
                while (!socketChannel.finishConnect()) {
                    System.out.println("连接服务器需要时间，期间可以做其他事情...");
                }
            }
            String msg = "hello java技术爱好者！";
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
            //把byteBuffer数据写入到通道中
            socketChannel.write(byteBuffer);
            //让程序卡在这个位置，不关闭连接
            System.in.read();
        }
    }

}
