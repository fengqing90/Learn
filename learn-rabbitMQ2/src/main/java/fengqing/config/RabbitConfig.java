package fengqing.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 15:44
 */
@Slf4j
@Configuration
public class RabbitConfig {
    @Autowired
    private ThreadPoolTaskExecutor consumerRabbitExecutor;

    @PostConstruct
    public void init() {
        Thread daemonTread = new Thread(() -> {
            while (true) {
                System.out.println(consumerRabbitExecutor.getThreadPoolExecutor().toString() + "@ " + consumerRabbitExecutor.getThreadPoolExecutor().getMaximumPoolSize());
                System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " consumerThreadPool 统计 【线程活跃数】" + consumerRabbitExecutor.getActiveCount() + "【核心线程数】"
                        + consumerRabbitExecutor.getCorePoolSize() + "【线程处理队列长度】"
                        + consumerRabbitExecutor.getThreadPoolExecutor().getQueue().size() + "【最大线程池数量】"
                        + consumerRabbitExecutor.getMaxPoolSize() + "【总线程数】：" + consumerRabbitExecutor.getPoolSize());
                try {
                    Thread.sleep(1000 * 5L);
                } catch (InterruptedException e) {
                }
            }
        });
        daemonTread.setDaemon(true);
        daemonTread.start();
    }

    /**
     * rabbitListenerContainerFactory <br/>
     * 作用：采用自定义工厂，覆盖默认的工厂，设置并发，设置Executor。
     *
     * @param configurer
     * @param consumerRabbitConnectionFactory
     * @param consumerRabbitExecutor
     * @return
     * @author FengQing
     * @date 2018年12月13日 下午2:19:19
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory consumerRabbitConnectionFactory,
            ThreadPoolTaskExecutor consumerRabbitExecutor) {
        MyRabbitListenerContainerFactory factory = new MyRabbitListenerContainerFactory();
        factory.setTaskExecutor(consumerRabbitExecutor);
        // 启动新的消费者最小时间间隔，默认10s
        factory.setStartConsumerMinInterval(1000L);
        // 停止空闲消费者最小时间间隔，默认60s
        factory.setStopConsumerMinInterval(1000L);
        // 消费者连续成功消费几条消息时触发considerAddingAConsumer，默认10条
        factory.setConsecutiveActiveTrigger(3);
        // 消费者连续空闲几次时触发considerStoppingAConsumer，默认10条，可配置
        factory.setConsecutiveIdleTrigger(3);
        configurer.configure(factory, consumerRabbitConnectionFactory);
        return factory;
    }


    /**
     * 消费者线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor consumerRabbitExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("MY");
        executor.setKeepAliveSeconds(2);//允许的空闲时间
        //这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功
        executor.setRejectedExecutionHandler(
                new MyRejectedExecutionHandler());
        executor.setQueueCapacity(0);//缓存队列
        executor.setCorePoolSize(2);//线程池维护线程的最少数量
        executor.setMaxPoolSize(5);
        return executor;
    }
}
