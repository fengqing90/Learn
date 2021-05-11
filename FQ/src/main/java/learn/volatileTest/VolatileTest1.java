package learn.volatileTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/23 15:29
 */
public class VolatileTest1 {
    private static volatile int count = 0;

    private static void addCount() {
        for (int i = 0; i < 100; i++) {
            count++;
        }

        System.out
            .println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            // executor.execute(() -> VolatileTest1.addCount());
            new Thread(() -> {
                VolatileTest1.addCount();
            }).start();
        }

        executor.shutdown();
    }
}
