package learn.rabbitmq.utils;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

public class AmqpUtils {

    public static Message messageBuilder(String jsonValue,
            String keywords) {

        return AmqpUtils.messageBuilder(jsonValue.getBytes(), keywords);
    }

    public static Message messageBuilder(byte[] value,
            String keywords) {
        return MessageBuilder.withBody(value)
            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
            .setMessageId("【" + keywords + "】-" + UUID.randomUUID().toString())
            .build();
    }
}
