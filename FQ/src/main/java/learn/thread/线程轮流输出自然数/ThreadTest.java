package learn.thread.线程轮流输出自然数;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/6 17:03
 */
public class ThreadTest {
    static volatile int i;

    public static void main(String[] args) throws InterruptedException {

        // Object o = new Object();
        // Thread t1 = new Thread(new PrintNum(o, true));
        // Thread t2 = new Thread(new PrintNum(o, false));
        // t2.start();
        // t1.start();

        // PrintNumVolatile.main();

    }
}

class PrintNumVolatile {
    AtomicInteger i = new AtomicInteger();

    public void run() {
        System.out.println(
            Thread.currentThread().getName() + " @ " + i.getAndIncrement());
    }

    public static void main() {
        PrintNumVolatile p = new PrintNumVolatile();
        new Thread(() -> {
            for (;;) {
                try {
                    p.run();
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (;;) {
                try {
                    p.run();
                    TimeUnit.SECONDS.sleep(1);
                    // TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class PrintNum implements Runnable {

    private Object o;
    private static int num;
    private boolean isFirstThread;

    public PrintNum(Object o, boolean isFirstThread) {
        this.o = o;
        num = 0;
        this.isFirstThread = isFirstThread;
    }

    @Override
    public void run() {
        synchronized (this.o) {
            while (true) {
                if (this.isFirstThread) {
                    this.isFirstThread = false;
                } else {
                    try {
                        this.o.wait();
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out
                    .println(Thread.currentThread().getName() + ":" + num++);
                this.o.notifyAll();
            }
        }
    }
}

@Slf4j
class OddEvenPrinter {
    public static void main(String[] args) throws InterruptedException {

        OddEvenPrinter printer = new OddEvenPrinter(10, 0);
        Thread thread1 = new Thread(() -> {
            try {
                printer.print();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                printer.print();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                printer.print();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread4 = new Thread(() -> {
            try {
                printer.print();
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private final Object monitor = new Object();
    private final int limit;
    private volatile int count;

    public OddEvenPrinter(int limit, int initCount) {
        this.limit = limit;
        this.count = initCount;
    }

    public void print() throws InterruptedException {
        synchronized (monitor) {
            while (this.count < this.limit) {
                monitor.notifyAll();
                log.info("num:" + ++this.count);
                monitor.wait();
            }
        }
    }

}