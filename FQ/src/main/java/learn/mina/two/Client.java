package learn. mina.two;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client extends IoHandlerAdapter {
    private Random random = new Random(System.currentTimeMillis());

    public Client() {
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast(
            "text",
            new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                .forName(Server.ENCODE))));
        connector.setHandler(this);
        ConnectFuture future = connector.connect(new InetSocketAddress(
            "127.0.0.1", Server.PORT));
        future.awaitUninterruptibly();
        future.addListener(new IoFutureListener<ConnectFuture>() {

            @Override
            public void operationComplete(ConnectFuture future) {
                IoSession session = future.getSession();
                while (!session.isClosing()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String message = "你好,我roll了"
                        + Client.this.random.nextInt(100) + "点.";
                    session.write(message);
                }
            }
        });
        connector.dispose();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println("批复:" + message.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("报告:" + message.toString());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.close(true);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Client();
        }
    }
}