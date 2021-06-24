package learn.spring.创建顺序;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/17 16:15
 */
public abstract class AbstractConfig {

    /** InitializingBean **/
    public void afterPropertiesSet() {
    }

    /** SmartInitializingSingleton **/
    public void afterSingletonsInstantiated() {
    }

    /** ApplicationContextAware **/
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

    }

    /** 在invokeBeanFactoryPostProcessors 中优先按顺序处理 PriorityOrdered ,Ordered **/
    public int getOrder() {
        return 0;
    }
}
