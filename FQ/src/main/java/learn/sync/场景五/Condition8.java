package learn.sync.场景五;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/8 15:06
 */
public class Condition8 implements Runnable {

    static Condition8 instance = new Condition8();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            //直接调用普通方法
            method2();
        } else {
            // 先调用synchronized 方法，在synchronized 方法内调用普通方法
            method1();
        }
    }

    // synchronized 方法
    private static synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，synchronized 方法，运行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程名：" + Thread.currentThread().getName()
            + "，synchronized 方法，运行结束,开始调用普通方法");
        method2();
    }

    // 普通方法
    private static void method2() {
        System.out
            .println("线程名：" + Thread.currentThread().getName() + "，普通方法，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out
            .println("线程名：" + Thread.currentThread().getName() + "，普通方法，运行结束");
    }

    public static void main(String[] args) {
        // 此线程直接调用普通方法
        Thread thread0 = new Thread(instance);
        // 这两个线程直接调用synchronized 方法
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread0.start();
        thread1.start();
        thread2.start();
        while (thread0.isAlive() || thread1.isAlive() || thread2.isAlive()) {
        }
        System.out.println("测试结束");
    }

}