package learn.设计模式.工厂模式;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:32
 */
import java.util.concurrent.TimeUnit;

public interface CacheService {

    String get(final String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);

}