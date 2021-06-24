package learn.spring.创建顺序;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/17 16:43
 */
@Slf4j
public class MyImportBeanDefinitionRegistrar
        implements ImportBeanDefinitionRegistrar {

    /**
     * AnnotationMetadata：当前类的注解信息
     * BeanDefinitionRegistry：BeanDefinition注册类
     * 我们可以通过调用BeanDefinitionRegistry接口中的registerBeanDefinition方法，手动注册所有需要添加到容器中的bean
     */
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        log.info(
            "【调用顺序】【ImportBeanDefinitionRegistrar】MyImportBeanDefinitionRegistrar.registerBeanDefinitions");
        registry.registerBeanDefinition("myBean", BeanDefinitionBuilder
            .rootBeanDefinition(Bean.class).getBeanDefinition());
        registry.registerBeanDefinition("myFactoryBean", BeanDefinitionBuilder
            .rootBeanDefinition(MyFactoryBean.class).getBeanDefinition());
    }

}