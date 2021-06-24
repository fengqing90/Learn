package learn.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/22 17:59
 */
@Aspect
public class LogAspects {

    // 如果切入点表达式都一样的情况下，那么我们可以抽取出一个公共的切入点表达式
    // 1. 本类引用
    // 2. 如果是外部类，即其他的切面类引用，那就在这@After("...")写的是方法的全名，而我们就要把切入点写在这儿@Pointcut("...")
    @Pointcut("execution(public int learn.spring.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

    // @Before：在目标方法（即div方法）运行之前切入，public int learn.spring.aop.MathCalculator.div(int, int)这一串就是切入点表达式，指定在哪个方法切入
    // @Before("public int learn.spring.aop.MathCalculator.*(..)")
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        // System.out.println("除法运行......@Before，参数列表是：{}");

        Object[] args = joinPoint.getArgs(); // 拿到参数列表，即目标方法运行需要的参数列表
        System.out.println(joinPoint.getSignature().getName()
            + "运行......@Before，参数列表是：{" + Arrays.asList(args) + "}");
    }

    // 在目标方法（即div方法）结束时被调用
    // @After("pointCut()")
    @After("learn.spring.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        // System.out.println("除法结束......@After");

        System.out
            .println(joinPoint.getSignature().getName() + "结束......@After");
    }

    // 在目标方法（即div方法）正常返回了，有返回值，被调用
    // @AfterReturning("pointCut()")
    @AfterReturning(value = "pointCut()", returning = "result") // returning来指定我们这个方法的参数谁来封装返回值
    /*
     * 如果方法正常返回，我们还想拿返回值，那么返回值又应该怎么拿呢？
     */
    public void logReturn(JoinPoint joinPoint, Object result) { // 一定要注意：JoinPoint这个参数要写，一定不能写到后面，它必须出现在参数列表的第一位，否则Spring也是无法识别的，就会报错
        // System.out.println("除法正常返回......@AfterReturning，运行结果是：{}");

        System.out.println(joinPoint.getSignature().getName()
            + "正常返回......@AfterReturning，运行结果是：{" + result + "}");
    }

    // 在目标方法（即div方法）出现异常，被调用
    @AfterThrowing("pointCut()")
    public void logException() {
        System.out.println("除法出现异常......异常信息：{}");
    }
}
