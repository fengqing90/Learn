package learn_quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * KEP-TODO
 *
 * @ClassName HelloJob
 * @Author FengQing
 * @Date 2019/4/29 19:45
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        System.out.println("****************************************");
        System.out.println(Thread.currentThread().getName() + " " + new Date());
        System.out.println("****************************************");
    }
}
