package learn.sync.场景七;

/**
 * 场景七：两个线程分别同时访问静态synchronized和非静态synchronized方法
 * 
 * <pre>
 * 运行结果:并行
 * 问题原因： 线程1的锁是类锁（*.class）对象，线程2的锁是方法锁（this）对象,两个线程的锁不一样，自然不会互相影响，所以会并行执行。
 * </pre>
 *
 * @author fengqing
 * @date 2020/12/9 15:16
 */
public class Condition6 implements Runnable {
    static Condition6 instance = new Condition6();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            //线程0,执行静态 synchronized method0()
            method0();
        } else if (Thread.currentThread().getName().equals("Thread-1")) {
            //线程1,执行非静态 synchronized method1()
            method1();
        } else
            method0();
    }

    // 重点：用static synchronized 修饰的方法，属于类锁，锁对象为（*.class）对象。
    private static synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，静态 synchronized 0，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，静态 synchronized 0，运行结束");
    }

    // 重点：synchronized 修饰的方法，属于方法锁，锁对象为（this）对象。
    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，非静态 synchronized 1，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，非静态 synchronized 1，运行结束");
    }

    //运行结果:并行
    public static void main(String[] args) {
        //问题原因： 线程1的锁是类锁（*.class）对象，线程2的锁是方法锁（this）对象,两个线程的锁不一样，自然不会互相影响，所以会并行执行。
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        Thread thread3 = new Thread(instance);
        Thread thread4 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive()
            || thread4.isAlive()) {
        }
        System.out.println("测试结束");
    }
}