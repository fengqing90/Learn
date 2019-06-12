package learn.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class Vhost1ProcessKeplerQueue implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("【Vhost1ProcessKeplerQueue】="
            + message.getMessageProperties().getHeaders().get("count"));

    }
}
