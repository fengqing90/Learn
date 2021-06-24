package learn.servlet3;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import learn.servlet3.整合spring.AppConfig;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:01
 */
// 我们可以编写一个类来实现WebApplicationInitializer接口哟，当然了，你也可以编写一个类来实现ServletContainerInitializer接口
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // 然后，我们来创建一个AnnotationConfigWebApplicationContext对象，它应该代表的是web的IOC容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // 加载我们的配置类
        context.register(AppConfig.class);

        // 在容器启动的时候，我们自己来创建一个DispatcherServlet对象，并将其注册在ServletContext中
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext
            .addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        // 这儿是来配置DispatcherServlet的映射信息的
        registration.addMapping("/app/*");
    }
}