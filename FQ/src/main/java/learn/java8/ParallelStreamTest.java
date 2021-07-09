package learn.java8;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

/**
 * 控制并发线程
 * https://www.cnblogs.com/wugang/p/14367605.html
 *
 * @author fengqing
 * @date 2021/7/1 21:24
 */
public class ParallelStreamTest {
    public static void main(String[] args) {
        // testParalle();
        testParalleNum(2);
    }

    private static void testParalle() {
        Stream<Integer> parallel = Arrays.asList(1, 2, 3, 5, 6, 7, 8).stream()
            .parallel();
        parallel.forEach(ParallelStreamTest::doSome);
    }

    private static void doSome(int num) {
        try {

            System.out.println(
                String.format("time：%s begin:%s", LocalTime.now(), num));
            Thread.sleep(num * 500);
            System.out
                .println(String.format("time：%s end:%s  ", LocalTime.now(), num)
                    + Thread.currentThread().getName());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void testParalleNum(int parallelism) {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 5, 6, 7, 8).stream();
        new ForkJoinPool(parallelism)
            .submit(() -> stream.parallel().forEach(ParallelStreamTest::doSome))
            .join();
    }

}
