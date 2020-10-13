package learn.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Parallellimit
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 14:03
 */
public class Parallellimit {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            CountRunnable runnable = new CountRunnable(cdl);
            pool.execute(runnable);
        }
        pool.shutdown();
    }
}

class CountRunnable implements Runnable {
    private CountDownLatch countDownLatch;

    public CountRunnable(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            synchronized (this.countDownLatch) {
                /*** 每次减少一个容量*/
                this.countDownLatch.countDown();
                System.out.println("thread counts = " + this.countDownLatch.getCount());
            }
            this.countDownLatch.await();
            System.out.println("concurrency counts = " + (100 - this.countDownLatch.getCount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}