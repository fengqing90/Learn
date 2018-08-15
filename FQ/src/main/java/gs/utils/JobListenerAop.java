package gs.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author FengQing 2016年11月29日
 *         Job加入默认监听
 */
@Aspect
@Component
public class JobListenerAop {
    /**
     * 日志记录器
     */
//    private static final UcMonitorLogger LOGGER = UcMonitorLoggerFactory
//        .getUcMonitorLogger(JobListenerAop.class);
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(JobListener.class);

    @Autowired
    private JobListener jobListener;

    @Before("execution(* org.springframework.batch.core.launch.JobLauncher.run(..))")
    public void check(JoinPoint joinPoint) {
        try {
            AbstractJob job = (AbstractJob) joinPoint.getArgs()[0];

            Field f = ReflectionUtils.findField(AbstractJob.class, "listener");
            f.setAccessible(true);
            CompositeJobExecutionListener listener = (CompositeJobExecutionListener) ReflectionUtils
                .getField(f, job);

            f = ReflectionUtils.findField(CompositeJobExecutionListener.class,
                "listeners");
            f.setAccessible(true);

            Object orderedComposite = ReflectionUtils.getField(f, listener);
            f = ReflectionUtils.findField(orderedComposite.getClass(), "list");
            f.setAccessible(true);

            List<Object> objs = (List<Object>) ReflectionUtils.getField(f,
                orderedComposite);
            // 由于目前Job一般只有一个Listener，所以做简单处理
            // 如果有多个Listener此处需要优化改造
            if (CollectionUtils.isEmpty(objs)) {
                job.registerJobExecutionListener(this.jobListener);
            }

        } catch (Exception e) {
            JobListenerAop.LOGGER.error("Job监控AOP失败。", e);
        }
    }
}
