package learn.java8;

import java.util.function.Function;
import java.util.stream.Stream;

public class Test {
    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
//            System.out.println("Result: " + sum);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }

    //串行流
    public static Stream<Long> sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n);
    }

    // 传统for循环
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    //并行流
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel()
            .reduce(0L, Long::sum);
    }

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
//        System.out.println("Sequential sum done in:"
//            + Test.measureSumPerf(Test::sequentialSum, 10_000_000) + " msecs");
        System.out.println("Iterative sum done in:"
            + Test.measureSumPerf(Test::iterativeSum, 10_000_000) + " msecs");
        System.out.println("Parallel sum done in: "
            + Test.measureSumPerf(Test::parallelSum, 10_000_000) + " msecs");
    }
}
