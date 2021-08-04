package learn.sync.场景六;

/**
 * 场景六：两个线程同时访问同一个对象的不同的synchronized
 * 
 * <pre>
 * 结果分析：
 * 两个方法（method0()和method1()）的synchronized修饰符，虽没有指定锁对象，但默认锁对象为this对象为锁对象，
 * 所以对于同一个实例（instance），两个线程拿到的锁是同一把锁，此时同步方法会串行执行。
 * 这也是synchronized关键字的可重入性的一种体现。
 * </pre>
 *
 * @author fengqing
 * @date 2020/12/9 15:10
 */
public class Condition5 implements Runnable {
    static Condition5 instance = new Condition5();

    @Override
    public void run() {
        try {
            if (Thread.currentThread().getName().equals("Thread-0")) {
                //线程0,执行synchronized method0()
                method0();
            } else if (Thread.currentThread().getName().equals("Thread-1")) {
                //线程1,执行synchronized method1()
                method1();
            } else
                method2();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void method0() throws InterruptedException {
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 0，运行开始");
        Thread.sleep(4000);
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 0，运行结束");
    }

    private synchronized void method1() throws InterruptedException {
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 1，运行开始");
        Thread.sleep(4000);
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 1，运行结束");
    }

    private synchronized void method2() throws InterruptedException {
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 2，运行开始");
        Thread.sleep(4000);
        System.out.println(
            "线程名：" + Thread.currentThread().getName() + "，synchronized 2，运行结束");
    }

    //运行结果:串行
    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        Thread thread3 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread3.start();
        while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive()) {
        }
        System.out.println("测试结束");
    }
}