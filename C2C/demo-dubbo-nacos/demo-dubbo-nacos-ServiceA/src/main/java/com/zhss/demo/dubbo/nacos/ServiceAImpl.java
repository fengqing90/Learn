package com.zhss.demo.dubbo.nacos;

import com.zhss.demo.dubbo.nacos.api.ServiceA;
import org.apache.dubbo.config.annotation.Service;

@Service(
        version = "1.0.0",
        interfaceClass = ServiceA.class,
        cluster = "failfast",
        loadbalance = "roundrobin"
)
public class ServiceAImpl implements ServiceA {

    public String greet(String name) {
        return "hello, " + name;
    }
}
