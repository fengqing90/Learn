package learn.sync.场景八;

/**
 * 场景八：同步方法抛出异常后，JVM会自动释放锁的情况
 * 
 * <pre>
 * 结果分析：
 * 可以看出线程还是串行执行的，说明是线程安全的。而且出现异常后，不会造成死锁现象，JVM会自动释放出现异常线程的锁对象，其他线程获取锁继续执行。
 * </pre>
 *
 * @author fengqing
 * @date 2020/12/9 15:25
 */
public class Condition7 implements Runnable {

    private static Condition7 instance = new Condition7();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            //线程0,执行抛异常方法method0()
            method0();
        } else if (Thread.currentThread().getName().equals("Thread-1")) {
            //线程1,执行正常方法method1()
            method1();
        } else
            method0();
    }

    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //同步方法中，当抛出异常时，JVM会自动释放锁，不需要手动释放，其他线程即可获取到该锁
        System.out
            .println("线程名：" + Thread.currentThread().getName() + "，抛出异常，释放锁");
        throw new RuntimeException();

    }

    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行结束");
    }

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
