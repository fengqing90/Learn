package learn_quartz.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import learn_quartz.job.HelloJob;

/**
 * KEP-TODO
 *
 * @ClassName QuartzConfig
 * @Author FengQing
 * @Date 2019/4/29 19:38
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private Scheduler scheduler;

    @Bean
    public void hello() throws SchedulerException {

        this.scheduler.scheduleJob(this.createJobDetail(),
            this.createCronTrigger());
//        this.scheduler.scheduleJob(this.createCronTrigger2());
    }

    public JobDetail createJobDetail() {
        JobDetail job = newJob(HelloJob.class).withIdentity("name").build();
        return job;
    }

    public Trigger createCronTrigger() {
        Trigger trigger = newTrigger().withIdentity("name")
            .withSchedule(cronSchedule("0 0/1 * * * ? *")).build();
        return trigger;
    }

    public Trigger createCronTrigger2() {
        Trigger trigger = newTrigger().withIdentity("name2")
            .withSchedule(cronSchedule("0/5 * * * * ? *")).startAt(new Date())
            .usingJobData("param", "fengqing").forJob("name").build();
        return trigger;
    }
}
