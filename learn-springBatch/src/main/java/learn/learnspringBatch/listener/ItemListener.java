package learn.learnspringBatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * KEP-TODO
 *
 * @ClassName ItemListener
 * @Author FengQing
 * @Date 2019/9/23 14:22
 */
@Component
public class ItemListener {

    private static final Logger log = LoggerFactory.getLogger(ItemListener.class);


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "q_test",
                    durable = "true", autoDelete = "false",
                    exclusive = "false"),
            exchange = @Exchange(
                    value = "ex_test",
                    durable = "true"),
            key = "key_test"))
    public void onMessage(Message message) {
        log.info(message.toString());
    }

}
