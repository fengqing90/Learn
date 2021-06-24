package learn.spring.创建顺序;

import org.springframework.beans.factory.SmartInitializingSingleton;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/16 18:39
 */
// @Component
@Slf4j
public class MySmartInitializingSingleton
        implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        log.info(
            "【SmartInitializingSingleton】MySmartInitializingSingleton... 所有bean都初始化完，在refresh.finishBeanFactoryInitialization(beanFactory) 中执行。");
    }
}
