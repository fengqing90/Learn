package learn.springtask.task;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/10/18 20:55
 */
public class MyTask {

    @Scheduled(fixedRate = 1000)
    public void work() {
        System.out.println(new Date());
    }
}
