package learn.spring.创建顺序;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/16 15:57
 */
// 当然了，监听器这东西要工作，我们还得把它添加在容器中
@Slf4j
// @Component
public class MyApplicationListener
        implements ApplicationListener<ApplicationEvent> {

    // 当容器中发布此事件以后，下面这个方法就会被触发
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // TODO Auto-generated method stub
        log.info("【EventListener】收到事件：" + event);
    }

}