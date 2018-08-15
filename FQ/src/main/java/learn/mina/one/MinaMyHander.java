package learn. mina.one;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaMyHander extends IoHandlerAdapter {
	public static void init() {

		SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		//设置解析器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		acceptor.setHandler(new MinaMyHander());
		try {
			acceptor.bind(new InetSocketAddress(2));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("my handler ...................");
		super.messageReceived(session, message);
	}

}
