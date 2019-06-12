package learn.rabbitmq.demo;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import learn.rabbitmq.utils.AmqpUtils;

@Component
public class TransactionTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void send() throws Exception {

        for (int j = 0; j < 10; j++) {
            Message message = AmqpUtils.messageBuilder(
                "Hello from RabbitMQ!"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                "文本");
            this.rabbitTemplate.convertAndSend("exchange.direct", "test",
                message);
            System.out.println("send count :" + j);
        }
        throw new RuntimeException("手动停止");
    }
}
