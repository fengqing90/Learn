package learn.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Before("execution(* aop.SysLogServiceImpl.*(..))")
    public void methodAspect() {
        System.out.println(
            ">>>>>>>>>>>>>>>>>>>>>>>>>>>>> MyAspect  Before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
