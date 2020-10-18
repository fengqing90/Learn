package learn.springtask.task;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/18 20:46
 */
@Configuration
@EnableScheduling
public class MyTaskConfig {

    @Scheduled(fixedRate = 1000)
    public void work() {
        System.out.println(new Date());
    }

    @Scheduled(fixedRate = 500)
    public void doSomething() {
        System.out.println("doSomething" + new Date());
    }
}
