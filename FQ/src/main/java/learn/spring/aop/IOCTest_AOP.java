package learn.spring.aop;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/22 18:02
 */
public class IOCTest_AOP {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
            MainConfigOfAOP.class);

        // 不要自己创建这个对象
        // MathCalculator mathCalculator = new MathCalculator();
        // mathCalculator.div(1, 1);

        // 我们要使用Spring容器中的组件
        MathCalculator mathCalculator = applicationContext
            .getBean(MathCalculator.class);
        mathCalculator.div(1, 1);

        // 关闭容器
        applicationContext.close();
    }

    @Test
    public void MyAopInvoke() throws Throwable {
        List<AopInvoke> invokeList = new ArrayList<>();
        invokeList.add(new AfterAopInvoke());
        invokeList.add(new BeforeAopInvoke());

        MyAopInvoke myAopInvoke = new MyAopInvoke(invokeList);
        System.out.println("值" + myAopInvoke.proceed());
    }
}
