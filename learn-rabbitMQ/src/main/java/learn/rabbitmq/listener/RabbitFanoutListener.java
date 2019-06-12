package learn.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("【FanoutListener】-1：" + message);
    }
}
