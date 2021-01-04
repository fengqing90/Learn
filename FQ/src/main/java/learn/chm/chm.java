package learn.chm;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/31 14:14
 */
@Slf4j
public class chm {

    public static void main(String[] args) throws InterruptedException {
        // System.out.println(chm.wrong());
        // System.out.println(chm.right());

        System.out.println(chm.normalUse());
        System.out.println(chm.goodUse());

    }

    static int ITEM_COUNT = 1000;
    static int THREAD_COUNT = 10;
    static int LOOP_COUNT = 1000_0000;

    public static Map<String, Long> goodUse() throws InterruptedException {
        log.info("time = {}", System.currentTimeMillis());
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(
            ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                String key = "item"
                    + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                freqs.computeIfAbsent(key, v -> new LongAdder()).increment();
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("end time = {}", System.currentTimeMillis());
        return freqs.entrySet().stream().collect(
            Collectors.toMap(Map.Entry::getKey, v -> v.getValue().longValue()));
    }

    public static Map<String, Long> normalUse() throws InterruptedException {
        log.info("time = {}", System.currentTimeMillis());
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(
            ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                String key = "item"
                    + ThreadLocalRandom.current().nextInt(ITEM_COUNT);

                synchronized (freqs) {
                    if (freqs.containsKey(key)) {
                        freqs.put(key, freqs.get(key) + 1);
                    } else
                        freqs.put(key, 1L);
                }
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("end time = {}", System.currentTimeMillis());
        return freqs;
    }

    public static String right() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(
            ITEM_COUNT - 100);

        // 初始化 900
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        // 线程并发处理
        forkJoinPool.execute(
            () -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {

                synchronized (concurrentHashMap) {

                    // 查询还需要补多少个
                    int gap = ITEM_COUNT - concurrentHashMap.size();

                    log.info("gap size:{}", gap);

                    // 补充
                    concurrentHashMap.putAll(getData(gap));
                }
            }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        log.info("finish size :{}", concurrentHashMap.size());
        return "ok";
    }

    public static String wrong() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(
            ITEM_COUNT - 100);

        // 初始化 900
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        // 线程并发处理
        forkJoinPool.execute(
            () -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
                // 查询还需要补多少个
                int gap = ITEM_COUNT - concurrentHashMap.size();

                log.info("gap size:{}", gap);

                // 补充
                concurrentHashMap.putAll(getData(gap));

            }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        log.info("finish size :{}", concurrentHashMap.size());
        return "ok";
    }

    private static ConcurrentHashMap<String, Long> getData(int i) {

        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        Stream.iterate(1, j -> j++).limit(i).parallel()
            .forEach(j -> map.put(UUID.randomUUID().toString(), (long) j));
        return map;
    }
}
