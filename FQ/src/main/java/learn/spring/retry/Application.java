package learn.spring.retry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/21 17:44
 */
@Configuration
@EnableRetry
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
            "learn.spring.retry");
        // applicationContext.register(Application.class);
        // applicationContext.register(RetryService.class);

        RetryService service1 = applicationContext.getBean("service",
            RetryService.class);
        service1.service();
    }

}
