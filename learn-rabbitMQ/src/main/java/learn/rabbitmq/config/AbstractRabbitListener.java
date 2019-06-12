package learn.rabbitmq.config;

import java.io.Serializable;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class AbstractRabbitListener<T extends Serializable>
        implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(1111);
    }
}
