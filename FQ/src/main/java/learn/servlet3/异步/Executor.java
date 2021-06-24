package learn.servlet3.异步;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;

import learn.aop.SysLogService;
import learn.util.SpringUtil;

public class Executor implements Runnable {
    private AsyncContext ctx = null;

    public Executor(AsyncContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            PrintWriter out = this.ctx.getResponse().getWriter();
            SysLogService services = (SysLogService) SpringUtil
                .getBean("SysLogService");
            out.println("业务处理完毕的时间：" + new Date() + ".");
            out.println("副线程结束..." + Thread.currentThread());
            out.flush();
            this.ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}