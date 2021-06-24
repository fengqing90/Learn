package learn.spring.创建顺序;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/10 18:57
 */
@Slf4j
// @Component
public class Bean implements InitializingBean, DisposableBean {

    // @Autowired
    private Dog dog;

    public Bean() {
        log.info("【Bean】构造方法");
    }

    /**
     * 会在容器关闭的时候进行调用
     */
    @Override
    public void destroy() throws Exception {
        log.info("【Bean】destroy");
    }

    /**
     * 会在bean创建完成，并且属性都赋好值以后进行调用
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【Bean】afterPropertiesSet");
    }

    // 在对象创建完成并且属性赋值完成之后调用
    @PostConstruct
    public void init() {
        log.info("【Bean】@PostConstruct");
    }

    // 在容器销毁（移除）对象之前调用
    @PreDestroy
    public void destory() {
        log.info("【Bean】 @PreDestroy");
    }
}
