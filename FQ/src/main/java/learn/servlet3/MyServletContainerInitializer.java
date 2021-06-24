package learn.servlet3;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 15:41
 */

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;

import learn.servlet3.web三大组件.UserFilter;
import learn.servlet3.web三大组件.UserListener;
import learn.servlet3.web三大组件.UserServlet;

@HandlesTypes(value = { HelloService.class })
public class MyServletContainerInitializer
        implements ServletContainerInitializer {

    /*
     * 参数：
     * ServletContext
     * arg1：代表当前web应用。一个web应用就对应着一个ServletContext对象，此外，它也是我们常说的四大域对象之一，
     * 我们给它里面存个东西，只要应用在不关闭之前，我们都可以在任何位置获取到
     * Set<Class<?>> arg0：我们感兴趣的类型的所有后代类型
     */
    @Override
    public void onStartup(Set<Class<?>> arg0, ServletContext sc)
            throws ServletException {
        // TODO Auto-generated method stub
        System.out.println("我们感兴趣的所有类型：");
        // 好，我们把这些类型来遍历一下
        for (Class<?> clz : arg0) {
            System.out.println(clz);
        }
        //    输出
        // AbstractHelloService
        // HelloServiceImpl
        // HelloServiceExt

        // 注册Servlet组件
        ServletRegistration.Dynamic servlet = sc.addServlet("userServlet",
            new UserServlet());
        // 配置Servlet的映射信息
        servlet.addMapping("/user");

        // 注册Listener组件
        sc.addListener(UserListener.class);

        // 注册Filter组件
        FilterRegistration.Dynamic filter = sc.addFilter("userFilter",
            UserFilter.class);

        // 配置Filter的映射信息
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
            true, "/*");
    }
}