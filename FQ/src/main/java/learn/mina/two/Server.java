package learn. mina.two;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {

	public static final int PORT = 2534;
	public static final String ENCODE = "UTF-8";
	public static final String KEY = "roll";

	public static void main(String[] args) {
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("text",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(ENCODE))));
		acceptor.setHandler(new ServerHandler());
		try {
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("游戏开始,你想出去吗,来,碰碰运气吧!");
		} catch (IOException e) {
			e.printStackTrace();
			acceptor.dispose();
		}
	}
}
