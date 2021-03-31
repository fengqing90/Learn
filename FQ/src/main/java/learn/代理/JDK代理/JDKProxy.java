package learn.代理.JDK代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import learn.代理.IUserApi;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:31
 */
@Slf4j
public class JDKProxy {
    public static <T> T getProxy(Class clazz) throws Exception {
        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        return (T) Proxy.newProxyInstance(classLoader, new Class[] { clazz },
            new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    System.out
                        .println(method.getName() + " 你被代理了，By JDKProxy！");
                    return "小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！";
                }
            });
    }

    @Test
    public void test_JDKProxy() throws Exception {
        IUserApi userApi = JDKProxy.getProxy(IUserApi.class);
        String invoke = userApi.queryUserInfo();
        log.info("测试结果：{}", invoke);
    }

    /**
     * 测试结果：
     * queryUserInfo 你被代理了，By JDKProxy！
     * 19:55:47.319 [main] INFO org.itstack.interview.test.ApiTest -
     * 测试结果：小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！
     * Process finished with exit code 0
     */
}
