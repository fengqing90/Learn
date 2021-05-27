package learn.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/27 16:01
 */
public class Delayer {

    static final ScheduledThreadPoolExecutor delayer;

    static {
        (delayer = new ScheduledThreadPoolExecutor(1,
            new DaemonThreadFactory())).setRemoveOnCancelPolicy(true);
    }

    static final class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("CompletableFutureDelayScheduler");
            return t;
        }
    }

    public static ScheduledFuture<?> delay(Runnable command, long delay,
            TimeUnit unit) {
        return delayer.schedule(command, delay, unit);
    }

    public static <T> CompletableFuture<T> timeoutAfter(long timeout,
            TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<T>();
        delay(() -> result.completeExceptionally(new TimeoutException()),
            timeout, unit);
        return result;
    }

    public static <T> CompletableFuture<T> within(CompletableFuture<T> future,
            long timeout, TimeUnit unit) {
        final CompletableFuture<T> timeoutFuture = timeoutAfter(timeout, unit);
        return future.applyToEither(timeoutFuture, Function.identity());
    }
}
