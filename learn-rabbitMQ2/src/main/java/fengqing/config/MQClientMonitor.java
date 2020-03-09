package fengqing.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MQClientMonitor
 * @Description 监控当期消费者
 * @Author fengqing
 * @Date 2020/3/9 10:15
 */
@Slf4j
@Component
public class MQClientMonitor {

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    /**
     * 所有的队列监听容器MAP
     **/
    private final Map<String, SimpleMessageListenerContainer> allQueue2ContainerMap = new ConcurrentHashMap<>();


    private Map<String, SimpleMessageListenerContainer> getQueue2ContainerAllMap() {
        synchronized (allQueue2ContainerMap) {
            registry.getListenerContainers().forEach(container -> {
                SimpleMessageListenerContainer simpleContainer = (SimpleMessageListenerContainer) container;
                Arrays.stream(simpleContainer.getQueueNames()).forEach(queueName ->
                        allQueue2ContainerMap.putIfAbsent(StringUtils.trim(queueName), simpleContainer));
            });
        }
        return allQueue2ContainerMap;
    }

    public void logInfoAll() {
        statAllMessageQueueDetail().forEach(q -> {
            log.info("【MQClientMonitor】 Queue = {} , running = {} , activeContainer = {} , activeConsumerCount = {} , containerIdentity = {}",
                    q.getQueueName(), q.running, q.activeContainer, q.activeConsumerCount, q.containerIdentity);
        });
    }

    /**
     * 统计所有消息队列详情
     *
     * @return
     */
    public List<MessageQueueDetail> statAllMessageQueueDetail() {
        List<MessageQueueDetail> queueDetailList = new ArrayList<>();
        getQueue2ContainerAllMap().entrySet().forEach(entry -> {
            String queueName = entry.getKey();
            SimpleMessageListenerContainer container = entry.getValue();
            MessageQueueDetail deatil = new MessageQueueDetail(queueName, container);
            queueDetailList.add(deatil);
        });

        return queueDetailList;
    }

    @Data
    public static final class MessageQueueDetail {
        /** 队列名称 **/
        private String queueName;

        /** 监听容器标识 **/
        private String containerIdentity;

        /** 监听是否有效 **/
        private boolean activeContainer;

        /** 是否正在监听 **/
        private boolean running;

        /** 活动消费者数量 **/
        private int activeConsumerCount;

        public MessageQueueDetail(String queueName, SimpleMessageListenerContainer container) {
            this.queueName = queueName;
            this.running = container.isRunning();
            this.activeContainer = container.isActive();
            this.activeConsumerCount = container.getActiveConsumerCount();
            this.containerIdentity = "Container@" + ObjectUtils.getIdentityHexString(container);
        }

    }
}
