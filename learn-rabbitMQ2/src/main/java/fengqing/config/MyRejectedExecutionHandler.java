package fengqing.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName MyRejectedExecutionHandler
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 17:33
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(executor);

        new Thread(r,"MyRejected").start();
    }
}
