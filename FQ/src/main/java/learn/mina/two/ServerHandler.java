package learn. mina.two;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.close(false);
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        String s = message.toString();
        System.out.println("收到请求:" + s);
        if (s != null) {
            int i = this.getPoint(s);
            if (session.isConnected()) {
                if (i >= 95) {
                    session.write("运气不错,你可以出去了.");
                    session.close(false);
                    return;
                }
                Integer count = (Integer) session.getAttribute(Server.KEY);
                count++;
                session.setAttribute(Server.KEY, count);
                session.write("抱歉,你运气太差了,第" + count + "次请求未被通过,继续在小黑屋里呆着吧.");
            } else {
                session.close(true);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("发给客户端: " + message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) {
        long l = session.getCreationTime();
        System.out.println("来自" + this.getInfo(session) + "的会话已经关闭,它已经存活了 "
            + (System.currentTimeMillis() - l) + "毫秒.");
    }

    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("给" + this.getInfo(session) + "创建了一个新会话.");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("来自" + this.getInfo(session) + "的会话在闲置,状态为"
            + status.toString());
    }

    @Override
    public void sessionOpened(IoSession session) {
        session.setAttribute(Server.KEY, 0);
        System.out.println("和" + this.getInfo(session) + "的会话已经打开.");
    }

    public String getInfo(IoSession session) {
        if (session == null) {
            return null;
        }
        InetSocketAddress address = (InetSocketAddress) session
            .getRemoteAddress();
        int port = address.getPort();
        String ip = address.getAddress().getHostAddress();
        return ip + ":" + port;
    }

    public int getPoint(String s) {
        if (s == null) {
            return -1;
        }
        Pattern p = Pattern.compile("^[\u0041-\uFFFF,]*(\\d+).*$");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            return Integer.valueOf(m.group(1));
        }
        return 0;
    }

}
