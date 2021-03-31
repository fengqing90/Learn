package learn.代理.ByteBuddy代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.junit.Test;

import learn.代理.IUserApi;
import learn.代理.UserApi;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/3/31 14:42
 */
@Slf4j
public class ByteBuddyProxy {

    public static <T> T getProxy(Class clazz) throws Exception {

        DynamicType.Unloaded<?> dynamicType = new ByteBuddy().subclass(clazz)
            .method(ElementMatchers.<MethodDescription> named("queryUserInfo"))
            .intercept(MethodDelegation.to(InvocationHandler.class)).make();

        return (T) dynamicType
            .load(Thread.currentThread().getContextClassLoader()).getLoaded()
            .newInstance();
    }

    @RuntimeType
    public static Object intercept(@Advice.Origin Method method,
            @Advice.AllArguments Object[] args, @SuperCall Callable<?> callable)
            throws Exception {
        System.out.println(method.getName() + " 你被代理了，By Byte-Buddy！");
        return callable.call();
    }

    @Test
    public void test_ByteBuddyProxy() throws Exception {
        IUserApi userApi = ByteBuddyProxy.getProxy(UserApi.class);
        String invoke = userApi.queryUserInfo();
        log.info("测试结果：{}", invoke);
    }

}

/**
 * 测试结果：
 * queryUserInfo 你被代理了，By Byte-Buddy！
 * 20:19:44.498 [main] INFO org.itstack.interview.test.ApiTest -
 * 测试结果：小傅哥，公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！
 * Process finished with exit code 0
 */