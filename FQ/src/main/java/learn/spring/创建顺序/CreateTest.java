package learn.spring.创建顺序;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/10 19:00
 */
@Slf4j
public class CreateTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
            "learn.spring.创建顺序");

        Bean bean = applicationContext.getBean(Bean.class);
        log.info("获取bean = [{}]", bean);
        MyFactoryBean myFactoryBean = applicationContext
            .getBean(MyFactoryBean.class);
        log.info("MyFactoryBean = [{}]", myFactoryBean);

        applicationContext.publishEvent(new ApplicationEvent("获取Bean 事件") {
        });
        applicationContext.close();
    }
}
