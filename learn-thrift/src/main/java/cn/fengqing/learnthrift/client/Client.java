package cn.fengqing.learnthrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import cn.fengqing.thrift.service.HelloService;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/21 17:41
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("客户端启动....");
        TTransport transport = null;
        try {
            // 建立连接,端口
            transport = new TSocket("localhost", 50005);

            // 二进制协议
            TProtocol protocol = new TBinaryProtocol(transport);

            // 创建调用接口
            HelloService.Client client = new HelloService.Client(protocol);

            // 打开
            transport.open();

            System.out.println("发送请求.....");
            // 请求调用
            String result = client.helloString("fengqing");
            System.out.println("请求返回.....");

            System.out.println(result);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

}
