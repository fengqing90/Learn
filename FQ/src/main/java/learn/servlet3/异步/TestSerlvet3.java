package learn.servlet3.异步;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import learn.aop.RecordLog;
import learn.ioc_di.Interface.WeatherService;
import learn.util.SpringUtil;

/**
 * https://liayun.blog.csdn.net/article/details/115019809
 */
@WebServlet(asyncSupported = true, urlPatterns = "/servlet/Test")
public class TestSerlvet3 extends HttpServlet {

    /** 
     * 
     */
    private static final long serialVersionUID = 7381091892289099880L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间:" + new Date() + ".");
        out.println("主线程开始..." + Thread.currentThread());
        out.print("</br>");
        out.flush();

        //
        AsyncContext ctx = req.startAsync();
        //////////////////////////
        ctx.addListener(new AsyncListener() {
            @Override
            @RecordLog
            public void onComplete(AsyncEvent asyncEvent) {
                // TODO
                //	            	System.out.println("onComplete ***************");
            }

            @Override
            public void onError(AsyncEvent arg0) {
            }

            @Override
            public void onStartAsync(AsyncEvent arg0) {
            }

            @Override
            public void onTimeout(AsyncEvent arg0) {
            }
        });

        //////////////////////////
        new Thread(new Executor(ctx)).start();
        out.println("结束Servlet的时间：" + new Date() + ".");
        out.print("</br>");
        out.flush();

        WeatherService ws = (WeatherService) SpringUtil
            .getBean("weatherServiceImpl");
        System.out.println("High was: " + ws
            .getHistoricalHigh(new GregorianCalendar(2004, 0, 1).getTime()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

}
