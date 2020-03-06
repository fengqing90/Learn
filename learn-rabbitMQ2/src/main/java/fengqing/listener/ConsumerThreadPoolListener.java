package fengqing.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName listener
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 16:11
 */
@Slf4j
@Component
public class ConsumerThreadPoolListener {


    public static final String Q_NAME = "q.ConsumerThreadPool";
    public static final String EX_NAME = "ex.ConsumerThreadPool";
    public static final String RK_NAME = "rk.ConsumerThreadPool";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Q_NAME,
                    durable = "true", autoDelete = "false",
                    exclusive = "false"),
            exchange = @Exchange(
                    value = EX_NAME,
                    durable = "true", autoDelete = "false",
                    type = ExchangeTypes.DIRECT),
            key = RK_NAME))
    public void onMessage(Message message, Channel channel) throws Exception {

        log.info("{}, msg = {}", Thread.currentThread().getName(), new String(message.getBody()));
        Thread.sleep(1000);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
