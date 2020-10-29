package learn.设计模式.工厂模式;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:12
 */
public class EGMCacheAdapter implements ICacheAdapter {
    private EGM egm = new EGM();

    @Override
    public String get(String key) {
        return this.egm.gain(key);
    }

    @Override
    public void set(String key, String value) {
        this.egm.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        this.egm.setEx(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        this.egm.delete(key);
    }
}