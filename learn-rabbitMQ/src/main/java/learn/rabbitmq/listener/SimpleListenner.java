package learn.rabbitmq.listener;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import learn.rabbitmq.config.AbstractRabbitListener;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountExchange;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountQueueName;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountRoutingKey;

@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = AccountQueueName.DLQ_QUEUE_KEPLER,
                durable = "true", autoDelete = "false", exclusive = "false"),
        exchange = @Exchange(value = AccountExchange.DEAD_EXCHANGE,
                durable = "true", autoDelete = "false",
                type = ExchangeTypes.DIRECT),
        key = AccountRoutingKey.DEAD_QUEUE_KEY))
@Component
public class SimpleListenner extends AbstractRabbitListener<String> {

    @Override
    public void onMessage(Message message) {
        System.out.println(Thread.currentThread().getName() + " @@@【【【【死信队列】】】】"
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
            + new String(message.getBody()));
    }
}
