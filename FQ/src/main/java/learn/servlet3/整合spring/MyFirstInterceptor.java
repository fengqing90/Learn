package learn.servlet3.整合spring;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:31
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyFirstInterceptor implements HandlerInterceptor {

    // 在页面响应以后执行
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
        System.out.println("afterCompletion...");
    }

    // 在目标方法运行正确以后执行
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("postHandle...");
    }

    // 在目标方法运行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse arg1, Object arg2) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("preHandle...");
        return true; // 返回true，表示放行（目标方法）
    }

}