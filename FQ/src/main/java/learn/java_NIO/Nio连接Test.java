package learn.java_NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/6 20:22
 */
public class Nio连接Test {

    void baseNio() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector sel = Selector.open();

        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));
        SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);

        while (true) {
            sel.select();
            Iterator it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey skey = (SelectionKey) it.next();
                it.remove();
                if (skey.isAcceptable()) {
                    SocketChannel ch = ssc.accept();
                }
            }
        }
    }
}
