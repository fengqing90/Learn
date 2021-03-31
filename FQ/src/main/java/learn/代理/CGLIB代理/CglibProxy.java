package learn.代理.CGLIB代理;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import learn.代理.UserApi;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:34
 */
@Slf4j
public class CglibProxy implements MethodInterceptor {
    public Object newInstall(Object object) {
        return Enhancer.create(object.getClass(), this);
    }

    public Object intercept(Object o, Method method, Object[] objects,
            MethodProxy methodProxy) throws Throwable {
        System.out.println("我被CglibProxy代理了");
        return methodProxy.invokeSuper(o, objects);
    }

    @Test
    public void test_CglibProxy() throws Exception {
        CglibProxy cglibProxy = new CglibProxy();
        UserApi userApi = (UserApi) cglibProxy.newInstall(new UserApi());
        String invoke = userApi.queryUserInfo();
        log.info("测试结果：{}", invoke);
    }
}

/**
 * 测试结果：
 * queryUserInfo 你被代理了，By CglibProxy！
 * 19:55:47.319 [main] INFO org.itstack.interview.test.ApiTest -
 * 测试结果：小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！
 * Process finished with exit code 0
 */