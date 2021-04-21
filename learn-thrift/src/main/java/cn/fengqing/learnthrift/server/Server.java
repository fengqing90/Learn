package cn.fengqing.learnthrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import cn.fengqing.learnthrift.impl.HelloServiceImpl;
import cn.fengqing.thrift.service.HelloService;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/21 17:40
 */
public class Server {

    public static void main(String args[]) {
        try {
            System.out.println("服务端开启....");
            // 创建处理类
            TProcessor tprocessor = new HelloService.Processor<HelloService.Iface>(
                new HelloServiceImpl());

            // 开启服务端口
            TServerSocket serverTransport = new TServerSocket(50005);

            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TSimpleServer(tArgs);

            // 启动服务
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
