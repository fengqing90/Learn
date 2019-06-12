package learn.rabbitmq.config;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import learn.rabbitmq.config.AccountRabbitMQConfig.AccountListennerConfig;

@Component
public class KeplerRabbitListenerContainerFactory
        extends SimpleRabbitListenerContainerFactory {

//    protected static final UcMonitorLogger LOGGER = UcMonitorLoggerFactory
//        .getUcMonitorLogger(KeplerRabbitListenerContainerFactory.class);

    @Override
    protected void initializeContainer(
            SimpleMessageListenerContainer instance) {

        super.initializeContainer(instance);

        Map<String, AccountListennerConfig> mapping = AccountListennerConfig
            .getAllMapping();
        System.out.println("【before】" + instance);
        if (ArrayUtils.isNotEmpty(instance.getQueueNames())) {
            Stream.of(instance.getQueueNames()).forEach(q -> {

                AccountListennerConfig config = mapping.get(q);

                if (config != null) {
                    instance.setMaxConcurrentConsumers(
                        config.getMaxConcurrentConsumers());
                    instance
                    .setConcurrentConsumers(config.getConcurrentConsumers());
                } else {
//                    LOGGER.
                }
            });
        }
        System.out.println("【after】" + instance);

        instance.setExposeListenerChannel(true);
        //暴露channel的注册
        instance.setMaxConcurrentConsumers(20);//并发消费者的最大数目
        instance.setConcurrentConsumers(10);//在初始化监听器的时候，并发消费者的数目
        //在一个套接字框架内，从代理接收消息的数目。这个值越大，发送消息就越快，但是导致非顺序处理的风险就越高
        instance.setPrefetchCount(1);
        instance.setChannelTransacted(true);//使用事务

        //当没有消息投递时，可配置监听器容器来发布ListenerContainerIdleEvent 事件.
        //当容器是空闲的，事件会每隔idleEventInterval 毫秒发布事件
        instance.setIdleEventInterval(60000);
        //决定由于监听器抛出异常而拒绝的消息是否被重新放回队列。默认值为true
        instance.setDefaultRequeueRejected(false);
    }
}
