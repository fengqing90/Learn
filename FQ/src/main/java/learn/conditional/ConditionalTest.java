package learn.conditional;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Conditional是Spring4新提供的注解，也是用来注册bean的，作用如下：
 *                                              按照一定的条件进行判断，满足条件的给容器注册bean
 *                                              从源码中我们可以看到，可以作用在类和方法上
 *                                              需要传入一个Class数组，并继承Condition接口
 * @author fengqing
 * @date 2021/7/16 15:24
 */
public class ConditionalTest {

    // 启动类
    @Test
    public void TestMain() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
            AppConfig.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}

// User
class User {
}

// 配置类
@Configuration
class AppConfig {
    // 如果WindowsCondition的实现方法返回true，则注入这个bean
    @Conditional({ WindowsCondition.class })
    @Bean
    public User windowsCondition_User() {
        return new User();
    }

    // 如果LinuxCondition的实现方法返回true，则注入这个bean
    @Conditional({ LinuxCondition.class })
    @Bean
    public User linuxCondition_User() {
        return new User();
    }
}

// Windows系统判断条件
class WindowsCondition implements Condition {
    /**
     * @author ONESTAR
     * @date 2021/2/10 10:56
     * @param conditionContext:判断条件，能使用的上下问环境
     * @param annotatedTypeMetadata：注释信息
     * @return
     *         boolean
     */
    public boolean matches(ConditionContext conditionContext,
            AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 获取当前环境
        Environment environment = conditionContext.getEnvironment();
        // 判断是否是Windows系统
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")) {
            return true;
        }
        return false;
    }
}

// Linux系统判断条件
class LinuxCondition implements Condition {
    /**
     * @description 判断操作系统是否是Linux系统
     * @author ONESTAR
     * @date 2021/2/10 10:56
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @throws
     * @return
     *         boolean
     */
    public boolean matches(ConditionContext conditionContext,
            AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 获取当前环境
        Environment environment = conditionContext.getEnvironment();
        // 判断是否是Linux系统
        String property = environment.getProperty("os.name");
        if (property.contains("linux")) {
            return true;
        }
        return false;
    }
}