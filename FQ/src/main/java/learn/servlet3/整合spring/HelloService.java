package learn.servlet3.整合spring;

import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:18
 */
@Service
public class HelloService {

    public String sayHello(String name) {
        return "Hello, " + name;
    }

}