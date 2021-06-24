package learn.future;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/27 19:45
 */
public class FutureTest {

    static ExecutorService exe = Executors
        .newCachedThreadPool(new Delayer.DaemonThreadFactory());

    public static void main(String[] args) throws Exception {
        Thread s1 = new Thread(() -> {
            while (true) {
                // System.out.println("****************************************");
                try {
                    otherStaticMethod();
                    // TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // System.out.println("****************************************");
            }
        }, "S1");
        s1.start();

        Thread s2 = new Thread(() -> {
            while (true) {
                // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                try {
                    otherStaticMethod();
                    // TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
        }, "S2");
        s2.start();
        //
        // new Thread(() -> {
        //     while (true) {
        //         try {
        //             otherStaticMethod();
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }, "S3").start();
        //
        // new Thread(() -> {
        //     while (true) {
        //         try {
        //             otherStaticMethod();
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }, "S4").start();
    }

    public static void otherStaticMethod() throws Exception {
        // System.out.println(new Date());
        Set<String> data = new CopyOnWriteArraySet<>();
        CompletableFuture<String> futureOne = CompletableFuture
            .supplyAsync(() -> {
                try {
                    // System.out.println("supplyAsync 是否为守护线程 "
                    //     + Thread.currentThread().isDaemon());
                } catch (Exception e) {
                }
                return "futureOneResult";
            });
        CompletableFuture<Void> futureOne_1 = futureOne.thenAccept(data::add);

        CompletableFuture<String> futureTwo = CompletableFuture
            .supplyAsync(() -> getString4futureTwo_3(2));

        CompletableFuture<Void> futureTwo_2 = Delayer
            .within(futureTwo, 3, TimeUnit.SECONDS).exceptionally(throwable -> {
                System.out.println(Thread.currentThread() + "  ： futureTwo 超时");
                return null;
            }).thenAccept(data::add);

        // CompletableFuture f = CompletableFuture.allOf(futureOne, futureTwo);
        CompletableFuture f = CompletableFuture.anyOf(futureOne, futureTwo);
        Object join = f.join();
        f.get();
        System.out.println("@@@@@@@@@@@@" + join);
        System.out.println("4：" + new Date() + " @ "
            + Thread.currentThread().getName() + " ：" + data);

        if (data.size() == 0) {
            System.out.println("data is null");
            System.exit(0);
        }

    }

    private static String getString4futureTwo_3(long l) {
        try {
            // TimeUnit.SECONDS.sleep(l);
            // getString4futureTwo_1(1);
        } catch (Exception e) {
        }
        return "futureTwoResult";
    }

    private static void getString4futureTwo_1(int i)
            throws InterruptedException {
        TimeUnit.SECONDS.sleep(i);
    }

    private static void test1()
            throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("********************");
                System.out.println("executorService 是否为守护线程 :"
                    + Thread.currentThread().isDaemon());
                return null;
            }
        });
        final CompletableFuture<String> completableFuture = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("********************");
                System.out.println("this is lambda supplyAsync");
                System.out.println(
                    "supplyAsync 是否为守护线程 " + Thread.currentThread().isDaemon());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("this lambda is executed by forkJoinPool");
                return "result1";
            });
        final CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("********************");
                System.out.println("this is task with executor");
                System.out.println("supplyAsync 使用executorService 时是否为守护线程 : "
                    + Thread.currentThread().isDaemon());
                return "result2";
            }, executorService);
        System.out.println(completableFuture.get());
        System.out.println(future.get());
        executorService.shutdown();
    }

    ExecutorService es = Executors.newFixedThreadPool(4);

    @GetMapping("/test2")
    public void test2()
            throws ExecutionException, InterruptedException, TimeoutException {

        CountDownLatch cb = new CountDownLatch(4);
        // CountDownLatch cb = new CyclicBarrier(4, new Runnable() {
        //     @Override
        //     public void run() {
        // System.out.println("1111");
        //     }
        // });
        List<Future<List<Integer>>> result = new ArrayList<>(4);

        Callable<List<Integer>> idNo = () -> this.getRelationByIdNo(cb, "IdNo");

        Future<List<Integer>> idNoResult = this.es.submit(idNo);
        result.add(idNoResult);

        Callable<List<Integer>> mobile = () -> this.getRelationByMobile(cb,
            "mobile");
        Future<List<Integer>> mobileResult = this.es.submit(mobile);
        result.add(mobileResult);

        Callable<List<Integer>> contactMobiles = () -> this
            .getRelationByContactMobiles(cb, new String[] { "contactMobiles" });
        Future<List<Integer>> contactMobilesResult = this.es
            .submit(contactMobiles);
        result.add(contactMobilesResult);

        Callable<List<Integer>> companyName = () -> this
            .getRelationByCompanyName(cb, "companyName");
        Future<List<Integer>> companyNameResult = this.es.submit(companyName);
        result.add(companyNameResult);

        cb.await(3, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "：执行完。");

        List<Integer> integers = new ArrayList<>();
        for (Future<List<Integer>> r : result) {
            List<Integer> data = null;
            try {
                data = r.get(1, TimeUnit.SECONDS);
            } catch (TimeoutException e) {

            }
            if (data != null) {
                integers.addAll(data);
            }
        }
        System.out.println(integers);
    }

    @GetMapping("/test")
    public void test() throws Exception {

        CountDownLatch cb = new CountDownLatch(4);
        // CountDownLatch cb = new CyclicBarrier(4, new Runnable() {
        //     @Override
        //     public void run() {
        // System.out.println("1111");
        //     }
        // });

        List<List<Integer>> result = new ArrayList<>(4);

        Callable<List<Integer>> idNo = () -> this.getRelationByIdNo(cb, "IdNo");
        FutureTask<List<Integer>> idNoFutureTask = new FutureTask<>(idNo);
        // idNoFutureTask.run();
        new Thread(idNoFutureTask).start();

        List<Integer> idNoResult = idNoFutureTask.get(10, TimeUnit.SECONDS);
        result.add(idNoResult);

        Callable<List<Integer>> mobile = () -> this.getRelationByMobile(cb,
            "mobile");
        FutureTask<List<Integer>> mobileFutureTask = new FutureTask<>(mobile);
        // mobileFutureTask.run();
        new Thread(mobileFutureTask).start();
        List<Integer> mobileResult = mobileFutureTask.get(10, TimeUnit.SECONDS);
        result.add(mobileResult);

        Callable<List<Integer>> contactMobiles = () -> this
            .getRelationByContactMobiles(cb, new String[] { "contactMobiles" });
        FutureTask<List<Integer>> contactMobileFutureTask = new FutureTask<>(
            contactMobiles);
        // contactMobileFutureTask.run();
        new Thread(contactMobileFutureTask).start();
        List<Integer> contactMobilesResult = contactMobileFutureTask.get(10,
            TimeUnit.SECONDS);
        result.add(contactMobilesResult);

        Callable<List<Integer>> companyName = () -> this
            .getRelationByCompanyName(cb, "companyName");
        FutureTask<List<Integer>> companyNameFutureTask = new FutureTask<>(
            companyName);
        // companyNameFutureTask.run();
        new Thread(companyNameFutureTask).start();
        List<Integer> companyNameResult = companyNameFutureTask.get(10,
            TimeUnit.SECONDS);
        result.add(companyNameResult);

        cb.await();
        System.out.println(Thread.currentThread().getName() + "：执行完。");

        for (List<Integer> r : result) {
            System.out.println(r);
        }
    }

    public List<Integer> getRelationByIdNo(CountDownLatch cb, String IdNo)
            throws InterruptedException, TimeoutException,
            BrokenBarrierException {

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        // cb.await(10, TimeUnit.SECONDS);
        Thread.sleep(5000);
        System.out.println(
            Thread.currentThread().getName() + "：getRelationByIdNo 执行完了");
        cb.countDown();
        return ids;
    }

    public List<Integer> getRelationByMobile(CountDownLatch cb, String Mobile)
            throws InterruptedException, TimeoutException,
            BrokenBarrierException {

        List<Integer> ids = new ArrayList<>();
        ids.add(2);
        // cb.await(10, TimeUnit.SECONDS);
        System.out.println(
            Thread.currentThread().getName() + "：getRelationByMobile 执行完了");
        cb.countDown();

        return ids;
    }

    public List<Integer> getRelationByContactMobiles(CountDownLatch cb,
            String[] contactMobiles) throws InterruptedException,
            TimeoutException, BrokenBarrierException {

        List<Integer> ids = new ArrayList<>();
        ids.add(3);
        // cb.await(10, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName()
            + "：getRelationByContactMobiles 执行完了");
        cb.countDown();
        return ids;
    }

    public List<Integer> getRelationByCompanyName(CountDownLatch cb,
            String companyName) throws InterruptedException, TimeoutException,
            BrokenBarrierException {

        List<Integer> ids = new ArrayList<>();
        ids.add(4);
        // cb.await(10, TimeUnit.SECONDS);
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName()
            + "：getRelationByCompanyName 执行完了");
        cb.countDown();
        return ids;
    }
}
