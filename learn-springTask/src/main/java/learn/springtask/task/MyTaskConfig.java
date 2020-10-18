package learn.springtask.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/18 20:46
 */
@Configuration
@EnableScheduling
public class MyTaskConfig {

    @Bean
    public MyTask task() {
        return new MyTask();
    }
}
