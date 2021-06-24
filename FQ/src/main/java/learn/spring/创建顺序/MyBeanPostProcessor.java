package learn.spring.创建顺序;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/10 18:59
 */
/**
 * 后置处理器，在初始化前后进行处理工作
 * 
 * @author liayun
 */
// @Component // 将后置处理器加入到容器中，这样的话，Spring就能让它工作了
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // TODO Auto-generated method stub
        System.out.println(
            "postProcessBeforeInitialization..." + beanName + "=>" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        // TODO Auto-generated method stub
        System.out.println(
            "postProcessAfterInitialization..." + beanName + "=>" + bean);
        return bean;
    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 3;
    }
}