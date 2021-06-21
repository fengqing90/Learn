package learn.rabbitmq.demo;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @ClassName SendTest
 * @Description TODO
 * @Author fengqing
 * @Date 2020/2/21 14:48
 */
public class SendTest {

    private static final String host = "172.16.2.132";
    private static final int port = 5672;
    private static final String accounting = "accounting";
    private static final String vhost_fq = "vhost_fq";
    private static final String exchange = "ex_test";
    private static final String key = "key_test";

    public static void main(String[] args)
            throws IOException, TimeoutException, InterruptedException {
//        doAmpqSend();

        doSpringSend();
    }

    private static void doAmpqSend()
            throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(accounting);
        factory.setPassword(accounting);
        factory.setVirtualHost(vhost_fq);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = "RabbitMQ Demo Test:"
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        channel.basicPublish(exchange, key,
            MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        Thread.sleep(2000);
        channel.close();
        connection.close();

        System.out.println("doAmpqSend end.");
    }

    private static void doSpringSend() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(accounting);
        connectionFactory.setPassword(accounting);
        connectionFactory.setVirtualHost(vhost_fq);
        connectionFactory.setChannelCacheSize(100);
        connectionFactory
            .setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(key);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        String message = "RabbitMQ Demo Test:"
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        rabbitTemplate.correlationConvertAndSend(message,
            new CorrelationData(RandomStringUtils.random(1)));

        System.out.println("doSpringSend end.");
        connectionFactory.destroy();

    }
}
