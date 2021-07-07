package learn.thread.两个线程交替打印奇偶数;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/7/6 17:29
 */
interface IPrintAB {
    public static void main(String[] args) {
        // new PrintABAtomic().printAB();
        new PrintABVolatile().printAB();
        // new PrintABCountDownLatch().printAB();
    }

    void printAB();
}

@Slf4j
class PrintABAtomic implements IPrintAB {
    private static final int MAX_PRINT_NUM = 100;
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void printAB() {
        new Thread(() -> {
            while (atomicInteger.get() < MAX_PRINT_NUM) {
                // 打印奇数.
                if (atomicInteger.get() % 2 == 0) {
                    log.info(Thread.currentThread().getName() + " @ num:"
                        + atomicInteger.get());
                    atomicInteger.incrementAndGet();
                }
            }
        }).start();

        new Thread(() -> {
            while (atomicInteger.get() < MAX_PRINT_NUM) {
                // 打印偶数.
                if (atomicInteger.get() % 2 == 1) {
                    log.info(Thread.currentThread().getName() + " @ num:"
                        + atomicInteger.get());
                    atomicInteger.incrementAndGet();
                }
            }
        }).start();
    }
}

/**
 * 2线程都依赖每个线程count++ 后计算取模的值 才能进行下一步
 */
@Slf4j
class PrintABVolatile implements IPrintAB {
    private static final int MAX_PRINT_NUM = 100;
    private volatile int count = 0;

    @Override
    public void printAB() {
        new Thread(() -> {
            while (count < MAX_PRINT_NUM) {
                if (count % 2 == 0) {
                    log.info("num:" + count);
                    count++;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (count < MAX_PRINT_NUM) {
                if (count % 2 == 1) {
                    log.info("num:" + count);
                    count++;
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
