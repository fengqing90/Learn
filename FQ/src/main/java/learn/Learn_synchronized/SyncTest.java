package learn.Learn_synchronized;

import java.util.stream.Stream;

/**
 * KEP-TODO
 *
 * @ClassName SyncTest
 * @Author FengQing
 * @Date 2019/6/12 14:51
 */
public class SyncTest {

    private final String b = new String();

    void A() {
        Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__" + i));
    }

    synchronized void syncA() {
        Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__A__" + i));
    }


    synchronized void syncAA() {
        Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__AA__" + i));
    }


    void syncE() {
        synchronized (this) {
            Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__AAA__" + i));
        }
    }

    void syncEE() {
        System.out.println(Thread.currentThread().getId() + "@@@@@@@@@@________EE");
        synchronized (this) {
            Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__EE__" + i));
        }
    }

    void syncB() {
        synchronized (this.b) {
            Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__B__" + i));
        }
    }


    void syncC() {
        synchronized (SyncTest.class) {
            Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__C__" + i));
        }
    }

    synchronized static void syncD() {
        Stream.iterate(1, i -> i + 1).limit(100).forEach(i -> System.out.println(Thread.currentThread().getId() + "__D__" + i));
    }


    void testA(TestA testA) {
        synchronized (testA) {
            testA.add();
        }
    }
}
