package learn.servlet3.整合spring;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:12
 */
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 在web容器启动的时候创建对象，而且在整个创建对象的过程中，会调用相应方法来初始化容器以及前端控制器
 * 编写好该类之后，就相当于是在以前我们配置好了web.xml文件
 * 
 * @author liayun
 */
public class MyWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*
     * 获取根容器的配置类，该配置类就类似于我们以前经常写的Spring的配置文件，然后创建出一个父容器
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // TODO Auto-generated method stub
        return new Class<?>[] { RootConfig.class };
    }

    /*
     * 获取web容器的配置类，该配置类就类似于我们以前经常写的Spring MVC的配置文件，然后创建出一个子容器
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // TODO Auto-generated method stub
        return new Class<?>[] { AppConfig.class };
    }

    // 获取DispatcherServlet的映射信息
    /*
     * /：拦截所有请求，包括静态资源，比如xxx.js文件、xxx.png等等等等，但是不包括*.jsp，也即不会拦截所有的jsp页面
     * /*：真正来拦截所有请求了，包括*.jsp，也就是说就连jsp页面都拦截
     */
    @Override
    protected String[] getServletMappings() {
        // TODO Auto-generated method stub
        return new String[] { "/" }; // 不包括*.jsp
        // return new String[] { "/*" }; // 所有
    }

}
