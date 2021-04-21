package cn.fengqing.learnthrift.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.thrift.TException;

import cn.fengqing.thrift.service.HelloService;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/21 17:39
 */
public class HelloServiceImpl implements HelloService.Iface {
    @Override
    public String helloString(String para) throws TException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(new Date())
            + " : " + para;
    }
}
