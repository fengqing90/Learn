package learn.java_NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/9 16:58
 */
public class 群聊 {

    public static class GroupChatClinet {

        private Selector selector;

        private SocketChannel socketChannel;

        private String userName;

        public GroupChatClinet() {
            try {
                //打开选择器
                this.selector = Selector.open();
                //连接服务器
                this.socketChannel = SocketChannel.open(
                    new InetSocketAddress("127.0.0.1", GroupChatServer.PORT));
                //设置为非阻塞
                this.socketChannel.configureBlocking(false);
                //注册到选择器中
                this.socketChannel.register(this.selector,
                    SelectionKey.OP_READ);
                //获取用户名
                this.userName = this.socketChannel.getLocalAddress().toString()
                    .substring(1);
                System.out.println(this.userName + " is ok~");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //发送消息到服务端
        private void sendMsg(String msg) {
            msg = this.userName + "说：" + msg;
            try {
                this.socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //读取服务端发送过来的消息
        private void readMsg() {
            try {
                int count = this.selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = this.selector
                        .selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        //判断是读就绪事件
                        if (selectionKey.isReadable()) {
                            SocketChannel channel = (SocketChannel) selectionKey
                                .channel();
                            //创建一个缓冲区
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            //从服务器的通道中读取数据到缓冲区
                            channel.read(byteBuffer);
                            //缓冲区的数据，转成字符串，并打印
                            System.out.println(new String(byteBuffer.array()));
                        }
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws Exception {
            GroupChatClinet chatClient = new GroupChatClinet();
            //启动线程，读取服务器转发过来的消息
            new Thread(() -> {
                while (true) {
                    chatClient.readMsg();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //主线程发送消息到服务器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                chatClient.sendMsg(msg);
            }
        }
    }

    /**
     * NIO实现多人聊天室
     */
    public static class GroupChatServer {

        private Selector selector;

        private ServerSocketChannel serverSocketChannel;

        public static final int PORT = 6667;

        //构造器初始化成员变量
        public GroupChatServer() {
            try {
                //打开一个选择器
                this.selector = Selector.open();
                //打开serverSocketChannel
                this.serverSocketChannel = ServerSocketChannel.open();
                //绑定地址，端口号
                this.serverSocketChannel
                    .bind(new InetSocketAddress("127.0.0.1", PORT));
                //设置为非阻塞
                this.serverSocketChannel.configureBlocking(false);
                //把通道注册到选择器中
                this.serverSocketChannel.register(this.selector,
                    SelectionKey.OP_ACCEPT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 监听，并且接受客户端消息，转发到其他客户端
         */
        public void listen() {
            try {
                while (true) {
                    //获取监听的事件总数
                    int count = this.selector.select(2000);
                    if (count > 0) {
                        Set<SelectionKey> selectionKeys = this.selector
                            .selectedKeys();
                        //获取SelectionKey集合
                        Iterator<SelectionKey> it = selectionKeys.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            //如果是获取连接事件
                            if (key.isAcceptable()) {
                                SocketChannel socketChannel = this.serverSocketChannel
                                    .accept();
                                //设置为非阻塞
                                socketChannel.configureBlocking(false);
                                //注册到选择器中
                                socketChannel.register(this.selector,
                                    SelectionKey.OP_READ);
                                System.out.println(
                                    socketChannel.getRemoteAddress() + "上线了~");
                            }
                            //如果是读就绪事件
                            if (key.isReadable()) {
                                //读取消息，并且转发到其他客户端
                                this.readData(key);
                            }
                            it.remove();
                        }
                    } else {
                        System.out.println("等待...");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //获取客户端发送过来的消息
        private void readData(SelectionKey selectionKey) {
            SocketChannel socketChannel = null;
            try {
                //从selectionKey中获取channel
                socketChannel = (SocketChannel) selectionKey.channel();
                //创建一个缓冲区
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                //把通道的数据写入到缓冲区
                int count = socketChannel.read(byteBuffer);
                //判断返回的count是否大于0，大于0表示读取到了数据
                if (count > 0) {
                    //把缓冲区的byte[]转成字符串
                    String msg = new String(byteBuffer.array());
                    //输出该消息到控制台
                    System.out.println("from 客户端：" + msg);
                    //转发到其他客户端
                    this.notifyAllClient(msg, socketChannel);
                }
            } catch (Exception e) {
                try {
                    //打印离线的通知
                    System.out
                        .println(socketChannel.getRemoteAddress() + "离线了...");
                    //取消注册
                    selectionKey.cancel();
                    //关闭流
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        /**
         * 转发消息到其他客户端
         * msg 消息
         * noNotifyChannel 不需要通知的Channel（自己）
         */
        private void notifyAllClient(String msg, SocketChannel noNotifyChannel)
                throws Exception {
            System.out.println("服务器转发消息~");
            for (SelectionKey selectionKey : this.selector.keys()) {
                //通过 key 取出对应的 SocketChannel
                Channel channel = selectionKey.channel();
                //channel的类型实际类型是SocketChannel，并且排除不需要通知的通道
                if (channel instanceof SocketChannel
                    && channel != noNotifyChannel) {
                    //强转成SocketChannel类型
                    SocketChannel socketChannel = (SocketChannel) channel;
                    //通过消息，包裹获取一个缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                    socketChannel.write(byteBuffer);
                }
            }
        }

        public static void main(String[] args) throws Exception {
            GroupChatServer chatServer = new GroupChatServer();
            //启动服务器，监听
            chatServer.listen();
        }
    }
}
