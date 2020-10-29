package learn.设计模式.工厂模式;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:13
 */
public interface ICacheAdapter {
    String get(String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}
