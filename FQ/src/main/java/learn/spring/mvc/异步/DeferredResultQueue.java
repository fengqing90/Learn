package learn.spring.mvc.异步;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 17:04
 */
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.context.request.async.DeferredResult;

public class DeferredResultQueue {

    // DeferredResult对象临时保存的地方
    private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedQueue<DeferredResult<Object>>();

    // 临时保存DeferredResult对象的方法
    public static void save(DeferredResult<Object> deferredResult) {
        queue.add(deferredResult);
    }

    // 获取DeferredResult对象的方法
    public static DeferredResult<Object> get() {
        /*
         * poll()：检索并且移除，移除的是队列头部的元素
         */
        return queue.poll();
    }

}