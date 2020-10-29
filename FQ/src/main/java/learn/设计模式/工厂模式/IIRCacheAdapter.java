package learn.设计模式.工厂模式;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:12
 */
public class IIRCacheAdapter implements ICacheAdapter {
    private IIR iir = new IIR();

    @Override
    public String get(String key) {
        return this.iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        this.iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        this.iir.setExpire(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        this.iir.del(key);
    }
}