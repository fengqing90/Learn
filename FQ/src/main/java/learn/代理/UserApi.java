package learn.代理;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:28
 */
public class UserApi implements IUserApi {

    public String queryUserInfo() {
        return "小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！";
    }

    @Test
    public void test_reflect() throws Exception {
        Class<UserApi> clazz = UserApi.class;
        Method queryUserInfo = clazz.getMethod("queryUserInfo");
        Object invoke = queryUserInfo.invoke(clazz.newInstance());
        System.out.println(invoke);
    }
}