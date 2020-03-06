package fengqing.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @ClassName MyRabbitListenerContainerFactory
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 19:26
 */
public class MyRabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {
    @Override
    protected void initializeContainer(
            SimpleMessageListenerContainer instance) {

        super.initializeContainer(instance);

        instance.setConsumerStartTimeout(5000L);
    }
}
