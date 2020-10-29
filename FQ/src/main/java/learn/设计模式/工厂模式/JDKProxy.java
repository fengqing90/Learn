package learn.设计模式.工厂模式;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/28 18:11
 */
public class JDKProxy {

    public static <T> T getProxy(Class<T> interfaceClass,
            ICacheAdapter cacheAdapter) throws Exception {
        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter);
        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        Class<?>[] classes = interfaceClass.getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader,
            new Class[] { classes[0] }, handler);
    }
}
