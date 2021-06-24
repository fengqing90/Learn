package learn.servlet3.整合spring;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:15
 */

// 该配置类相当于Spring MVC的配置文件
// Spring MVC容器只扫描Controller，它是一个子容器
// useDefaultFilters=false：禁用默认的过滤规则

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan(value = "learn.servlet3.整合spring",
        includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = { Controller.class }) },
        useDefaultFilters = false)
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        // TODO Auto-generated method stub
        configurer.enable();
    }

    // 定制视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // TODO Auto-generated method stub
        // super.configureViewResolvers(registry); 注释掉这行代码，因为其父类中的方法都是空的

        // 如果直接调用jsp方法，那么默认所有的页面都从/WEB-INF/目录下开始找，即找所有的jsp页面
        // registry.jsp();

        /*
         * 当然了，我们也可以自己来编写规则，比如指定一个前缀，即/WEB-INF/views/，再指定一个后缀，即.jsp，
         * 很显然，此时，所有的jsp页面都会存放在/WEB-INF/views/目录下，自然地，程序就会去/WEB-INF/views/
         * 目录下面查找jsp页面了
         */
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    // 定制拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        // super.addInterceptors(registry);

        /*
         * addInterceptor方法里面要传一个拦截器对象，该拦截器对象可以从容器中获取过来，也可以我们自己来new一个，
         * 很显然，这儿我们是new了一个我们自定义的拦截器对象。
         * 虽然创建出了一个拦截器，但是最关键的一点还是指示拦截器要拦截哪些请求，因此还得继续使用addPathPatterns方法来配置一下，
         * 若在addPathPatterns方法中传入了"/**"，则表示拦截器会拦截任意请求，而不管该请求是不是有任意多层路径
         */
        registry.addInterceptor(new MyFirstInterceptor())
            .addPathPatterns("/**");
    }

}
