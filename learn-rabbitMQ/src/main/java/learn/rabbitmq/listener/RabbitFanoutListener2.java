package learn.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutListener2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("【FanoutListener】-2：" + message);
    }
}
