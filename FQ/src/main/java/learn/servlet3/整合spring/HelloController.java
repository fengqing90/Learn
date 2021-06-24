package learn.servlet3.整合spring;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:19
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        String hello = helloService.sayHello("tomcat...");
        return hello;
    }

    // 处理suc请求
    @RequestMapping("/suc")
    public String success() {
        // 这儿直接返回"success"，那么它就会跟我们视图解析器中指定的那个前后缀进行拼串，来到指定的页面
        return "success";
    }
}