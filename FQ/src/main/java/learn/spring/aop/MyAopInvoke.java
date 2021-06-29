package learn.spring.aop;

import java.util.List;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/23 14:35
 */
public class MyAopInvoke implements AopInvoke {

    public MyAopInvoke(List<AopInvoke> invokeList) {
        this.invokeList = invokeList;
    }

    private final List<AopInvoke> invokeList;

    private int currentInterceptorIndex = -1;

    public Object proceed() throws Throwable {

        if (this.currentInterceptorIndex == this.invokeList.size() - 1) {
            return getObject();
        }
        return this.invokeList.get(++this.currentInterceptorIndex).invoke(this);
    }

    private String getObject() {
        System.out.println("执行获取值：getObject() ");
        return "Object";
    }
}

interface AopInvoke {
    default Object invoke(AopInvoke aopInvoke) throws Throwable {
        return null;
    }

    default Object proceed() throws Throwable {
        return null;
    }
}

class BeforeAopInvoke implements AopInvoke {

    @Override
    public Object invoke(AopInvoke aopInvoke) throws Throwable {
        System.out.println("BeforeAopInvoke...");
        return aopInvoke.proceed();
    }
}

class AfterAopInvoke implements AopInvoke {

    @Override
    public Object invoke(AopInvoke aopInvoke) throws Throwable {
        Object proceed;
        try {
            proceed = aopInvoke.proceed();
        } finally {
            System.out.println("AfterAopInvoke...");
        }
        return proceed;
    }
}