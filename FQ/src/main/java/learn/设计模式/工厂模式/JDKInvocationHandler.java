package learn.设计模式.工厂模式;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:11
 */

public class JDKInvocationHandler implements InvocationHandler {

    private ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        return ICacheAdapter.class
            .getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args))
            .invoke(cacheAdapter, args);
    }

}