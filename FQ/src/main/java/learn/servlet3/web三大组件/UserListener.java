package learn.servlet3.web三大组件;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener的作用：监听项目的启动和停止
 * 
 * @author liayun
 */
public class UserListener implements ServletContextListener {

    // 这个方法是来监听ServletContext销毁的，也就是说，我们这个项目的停止
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println("UserListener...contextDestroyed...");
    }

    // 这个方法是来监听ServletContext启动初始化的
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        ServletContext servletContext = arg0.getServletContext();
        System.out.println("UserListener...contextInitialized...");
    }

}