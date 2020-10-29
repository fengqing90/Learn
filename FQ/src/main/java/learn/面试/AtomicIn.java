package learn.面试;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/26 15:59
 */
public class AtomicIn {
    static AtomicInteger count = new AtomicInteger(0);

    public static void increment() {
        count.getAndIncrement();// 先返回在++
        System.err
            .println(Thread.currentThread().getName() + "--" + count + "-----");

    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Th1());
            t.start();
        }
        System.err.println("**********" + Thread.activeCount());
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.err.println(AtomicIn.count);
    }

}

class Th1 implements Runnable {
    @Override
    public void run() {
        AtomicIn.increment();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}