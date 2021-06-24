package learn.spring.创建顺序;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/16 16:21
 */
@Slf4j
// @Service
public class MyApplicationListenerBy注解 {
    // 一些其他的方法...

    // @EventListener(classes = ApplicationEvent.class)
    // public void listen() {
    //     log.info(
    //         "【EventListener】@EventListener  UserService...");
    // }

    // @EventListener(classes=ApplicationEvent.class)
    @EventListener(classes = { ApplicationEvent.class })
    public void listen(ApplicationEvent event) {
        log.info("【@EventListener】UserService...监听到的事件：" + event);
    }
}
