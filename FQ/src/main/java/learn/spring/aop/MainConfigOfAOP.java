package learn.spring.aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/22 18:01
 */
@EnableAspectJAutoProxy
@Configuration
@Slf4j
public class MainConfigOfAOP {

    // 将业务逻辑类（目标方法所在类）加入到容器中
    @Bean
    public MathCalculator calculator() {
        return new MathCalculator();
    }

    // 将切面类加入到容器中
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }

    // @Bean
    public BeanPostProcessor myBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean,
                    String beanName) throws BeansException {
                log.info(
                    "【调用顺序】【BeanPostProcessor】.postProcessBeforeInitialization @"
                        + beanName);
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean,
                    String beanName) throws BeansException {
                log.info(
                    "【调用顺序】【BeanPostProcessor】.postProcessAfterInitialization @"
                        + beanName);
                return bean;
            }
        };
    }

    // @Bean
    public BeanDefinitionRegistryPostProcessor myBeanDefinitionRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(
                    BeanDefinitionRegistry registry) throws BeansException {
                log.info(
                    "【调用顺序】【BeanDefinitionRegistryPostProcessor】.postProcessBeanDefinitionRegistry Bean count:"
                        + registry.getBeanDefinitionCount());
            }

            @Override
            public void postProcessBeanFactory(
                    ConfigurableListableBeanFactory beanFactory)
                    throws BeansException {
                log.info(
                    "【调用顺序】【BeanFactoryPostProcessor】.postProcessBeanFactory Bean count:"
                        + beanFactory.getBeanDefinitionCount());
            }
        };
    }
}