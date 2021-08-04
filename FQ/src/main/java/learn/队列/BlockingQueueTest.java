package learn.队列;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/1/13 17:04
 */
@Slf4j
public class BlockingQueueTest {

    private static final int MAX_NUM = 10;
    private static final BlockingQueue<String> QUEUE = new LinkedBlockingQueue<>(
            MAX_NUM);

    public void produce(String str) {
        try {
            QUEUE.put(str);
            log.info("  🔥🔥🔥 队列放入元素 :: {}, 队列元素数量 :: {}", str, QUEUE.size());
        } catch (InterruptedException ie) {
            // ignore
        }
    }

    public String consume() {
        String str = null;
        try {
            str = QUEUE.take();
            log.info("  🔥🔥🔥 队列移出元素 :: {}, 队列元素数量 :: {}", str, QUEUE.size());
        } catch (InterruptedException ie) {
            // ignore
        }
        return str;
    }

    public static void main(String[] args) {
        BlockingQueueTest queueTest = new BlockingQueueTest();
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(() -> {
                String str = "元素-";
                while (true) {
                    queueTest.produce(str + finalI);
                }
            }).start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                while (true) {
                    queueTest.consume();
                }
            }).start();
        }
    }
}