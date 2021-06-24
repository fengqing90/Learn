package learn.spring.创建顺序;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/16 18:51
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@Import({ MyImportBeanDefinitionRegistrar.class }) // @Import快速地导入组件，id默认是组件的全类名
public class Config extends AbstractConfig implements
        // ImportBeanDefinitionRegistrar, 结合@Import使用 优先处理
        InitializingBean, DisposableBean, ApplicationListener<ApplicationEvent>,
        BeanDefinitionRegistryPostProcessor, BeanFactoryPostProcessor,
        BeanPostProcessor,
        // Ordered, PriorityOrdered
        SmartInitializingSingleton, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // 在对象创建完成并且属性赋值完成之后调用
    @PostConstruct
    public void init() {
        log.info("【调用顺序】【@PostConstruct】");
    }

    // 在容器销毁（移除）对象之前调用
    @PreDestroy
    public void destory() {
        log.info("【调用顺序】【@PreDestroy】");
    }

    public Config() {
        log.info("【调用顺序】Config：构造方法");
    }

    @Override
    public void afterPropertiesSet() {
        log.info(
            "【调用顺序】【InitializingBean】b.f.InitializingBean.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        log.info("【调用顺序】destroy：销毁");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("【调用顺序】【ApplicationListener】收到事件："
            + (StringUtils.isBlank(event.getClass().getSimpleName())
                ? event.getSource().toString()
                : event.getClass().getSimpleName()));
    }

    @EventListener(classes = { ApplicationEvent.class })
    public void onApplicationEventBy注解(ApplicationEvent event) {
        // log.info("【调用顺序】【EventListenerMethodProcessor】UserService...监听到的事件："
        //     + (StringUtils.isBlank(event.getClass().getSimpleName())
        //         ? event.getSource().toString()
        //         : event.getClass().getSimpleName()));
    }

    @Override
    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        log.info(
            "【调用顺序】【BeanDefinitionRegistryPostProcessor】b.f.support.BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry @"
                + registry.getBeanDefinitionCount());
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info(
            "【调用顺序】【BeanFactoryPostProcessor】b.f.c.BeanFactoryPostProcessor.postProcessBeanFactory");
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info(
            "【调用顺序】【SmartInitializingSingleton】b.f.SmartInitializingSingleton.afterSingletonsInstantiated");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        log.info(
            "【调用顺序】【BeanPostProcessor】b.f.c.BeanPostProcessor.postProcessBeforeInitialization @"
                + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        log.info(
            "【调用顺序】【BeanPostProcessor】b.f.c.BeanPostProcessor.postProcessAfterInitialization @"
                + beanName);
        return bean;
    }

    /**
     * invokeAwareInterfaces
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
        log.info(
            "【调用顺序】【ApplicationContextAware】context.ApplicationContextAware.setApplicationContext ");
    }
}
