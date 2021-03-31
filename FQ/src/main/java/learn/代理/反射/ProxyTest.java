package learn.代理.反射;

import java.lang.reflect.Method;

import org.junit.Test;

import learn.代理.UserApi;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:29
 */
public class ProxyTest {

    @Test
    public void test_reflect() throws Exception {
        Class<UserApi> clazz = UserApi.class;
        Method queryUserInfo = clazz.getMethod("queryUserInfo");
        Object invoke = queryUserInfo.invoke(clazz.newInstance());
        System.out.println(invoke);
    }
}
