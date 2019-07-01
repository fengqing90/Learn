package learn.Learn_synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class RunnableMain {

    public static void main(String[] args) {
//        threadA();
//        threadAA();
//        threadCD();
//        threadABCD();
//        threadB();
//        threadBB();
//        threadAB();
//        threadAB_();
//        threadAAAB();
//        threadAAAB_();
        testA();
//        threadE();
    }


    /**
     * E,EE 同对象，并行执行非synchronized部分，串行执行synchronized部分
     * EEE  不同对象，并行执行
     */
    private static void threadE() {
        SyncTest syncTest = new SyncTest();
        Thread E = new Thread(() -> syncTest.syncEE());
        Thread EE = new Thread(() -> syncTest.syncEE());
        Thread EEE = new Thread(() -> new SyncTest().syncEE());
        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(E);
        exec.execute(EE);
        exec.execute(EEE);
        exec.shutdown();
    }

    /**
     * a,aa 同对象，串行   b和 (a|aa) 并行，锁同一个 object
     */
    private static void testA() {
        TestA testA = new TestA();
        TestA testb = new TestA();
        SyncTest syncTest = new SyncTest();

        Thread a = new Thread(() -> syncTest.testA(testA), "A");
        Thread b = new Thread(() -> syncTest.testA(testb), "B");
        Thread aa = new Thread(() -> syncTest.testA(testA), "AA");
        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(a);
        exec.execute(b);
        exec.execute(aa);
        exec.shutdown();
    }


    /**
     * AAA,B 同对象，锁不同，并行
     */
    private static void threadAAAB_() {

        SyncTest syncTest = new SyncTest();
        Thread aaa = new Thread(() -> syncTest.syncE());
        Thread b = new Thread(() -> syncTest.syncB());

        Runnable r = () -> Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__R__" + i));

        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(aaa);
        exec.execute(b);
        exec.execute(r);
        exec.shutdown();
    }

    /**
     * AAA,B 不同对象，锁不同，并行
     */
    private static void threadAAAB() {

        Thread aaa = new Thread(() -> new SyncTest().syncE());
        Thread b = new Thread(() -> new SyncTest().syncB());
        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(aaa);
        exec.execute(b);
        exec.shutdown();
    }

    /**
     * A,AA,AAA 都属于对象锁，不同对象并发，并行
     */
    private static void threadA() {

        Thread a = new Thread(() -> new SyncTest().syncA());
        Thread aa = new Thread(() -> new SyncTest().syncAA());
        Thread aaa = new Thread(() -> new SyncTest().syncE());
        Thread A = new Thread(() -> new SyncTest().A());
        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(A);
        exec.execute(a);
        exec.execute(aa);
        exec.execute(aaa);
        exec.shutdown();
    }

    /**
     * A,AA,AAA 都属于对象锁，同一个对象并发，串行
     */
    private static void threadAA() {

        SyncTest syncTest = new SyncTest();

        Thread a = new Thread(() -> syncTest.syncA());
        Thread aa = new Thread(() -> syncTest.syncAA());
        Thread aaa = new Thread(() -> syncTest.syncE());
        Thread A = new Thread(() -> syncTest.A());
        ExecutorService exec = Executors.newFixedThreadPool(10);

        exec.execute(A);
        exec.execute(a);
        exec.execute(aa);
        exec.execute(aaa);
        exec.shutdown();
    }

    /**
     * AB，多个对象，锁不同，并行
     */
    private static void threadAB() {

        Thread a = new Thread(() -> new SyncTest().syncA());
        Thread b = new Thread(() -> new SyncTest().syncB());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(a);
        exec.execute(b);
        exec.shutdown();
    }

    /**
     * AB，1个对象，锁不同，并行
     */
    private static void threadAB_() {

        SyncTest syncTest = new SyncTest();
        Thread a = new Thread(() -> syncTest.syncA());
        Thread b = new Thread(() -> syncTest.syncB());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(a);
        exec.execute(b);
        exec.shutdown();
    }

    /**
     * B，多个对象，同一个锁，并行
     */
    private static void threadB() {

        Thread b = new Thread(() -> new SyncTest().syncB());
        Thread bb = new Thread(() -> new SyncTest().syncB());
        Thread bbb = new Thread(() -> new SyncTest().syncB());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(b);
        exec.execute(bb);
        exec.execute(bbb);
        exec.shutdown();
    }

    /**
     * B，1个对象，同一个锁，串行
     */
    private static void threadBB() {

        SyncTest syncTest = new SyncTest();
        Thread b = new Thread(() -> syncTest.syncB());
        Thread bb = new Thread(() -> syncTest.syncB());
        Thread bbb = new Thread(() -> syncTest.syncB());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(b);
        exec.execute(bb);
        exec.execute(bbb);
        exec.shutdown();
    }

    /**
     * CD 都属于类锁，无论多少个对象，CD始终串行
     */
    private static void threadCD() {

        Thread c = new Thread(() -> new SyncTest().syncC());
        Thread d = new Thread(() -> new SyncTest().syncD());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(c);
        exec.execute(d);
        exec.shutdown();
    }

    /**
     * ABCD 多个对象，AB并行，CD始终串行
     */
    private static void threadABCD() {

        Thread a = new Thread(() -> new SyncTest().syncA());
        Thread b = new Thread(() -> new SyncTest().syncB());
        Thread c = new Thread(() -> new SyncTest().syncC());
        Thread d = new Thread(() -> new SyncTest().syncD());

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(a);
        exec.execute(b);
        exec.execute(c);
        exec.execute(d);
        exec.shutdown();
    }

}
