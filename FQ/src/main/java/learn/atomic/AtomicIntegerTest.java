package learn.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 由于执行了thread.yield
 * 方法执行两次
 *
 * @ClassName AtomicIntegerTest
 * @Author FengQing
 * @Date 2019/8/5 14:59
 */
public class AtomicIntegerTest {

    private static final int THREADS_CONUT = 20;
    public static int count = 0;
    public static volatile int vcount = 0;

    // CAS机制 （Compare-And-Swap比较并交换）
    public static AtomicInteger count_atomoic = new AtomicInteger(0);


    public static void increase() {
        count++;
    }

    public static void increase_v() {
        vcount++;
    }

    public static void increase_atomoic() {
        count_atomoic.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        originInt();
        vInt();
        atomoicInt();
    }

    private static void atomoicInt() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    increase_atomoic();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // 始终都是20000
        System.out.println("atomoicInt:" + count_atomoic);
    }

    private static void originInt() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    increase();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("originInt:" + count);
    }

    private static void vInt() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    increase_v();
                }
            });
            threads[i].start();
        }


        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("vInt:" + vcount);
    }
}
