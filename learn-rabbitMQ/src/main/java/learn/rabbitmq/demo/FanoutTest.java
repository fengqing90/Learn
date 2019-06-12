package learn.rabbitmq.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * KEP-TODO
 *
 * @ClassName fanout
 * @Author FengQing
 * @Date 2019/5/31 11:20
 */

@SpringBootApplication
@EnableRabbit
@Import(Test.class)
@Configuration
public class FanoutTest {

    @Bean
    public Queue queue1() {
        return new Queue("queue1", true);
    }

    @Bean
    public Exchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange", true, false, null);
    }

    @Bean
//    public Binding fanoutBinding(Queue queue1, Exchange fanoutExchange) {
    public Binding fanoutBinding() {
        Queue queue = QueueBuilder.durable("fanout.queue1").build();
//        this.rabbitAdmin.declareQueue(queue);
        Exchange exchange = ExchangeBuilder.fanoutExchange("fanoutExchange")
                .durable().build();

//        this.rabbitAdmin.declareExchange(exchange);

        Binding binding = BindingBuilder.bind(queue).to(exchange).with("")
                .and(null);
//        this.rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Binding fanoutBinding(Queue queue1, Exchange fanoutExchange) {
        Binding binding = BindingBuilder.bind(queue1).to(fanoutExchange).with("")
                .and(null);
        return binding;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory producerRabbitConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(producerRabbitConnectionFactory);
        return rabbitAdmin;
    }


    @Bean
    public ConnectionFactory producerRabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("172.16.2.132");
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setUsername("accounting");
        connectionFactory.setPassword("accounting");
        connectionFactory.setVirtualHost("vhost_fq_test");
        connectionFactory.setRequestedHeartBeat(15);//心跳监测间隔

        //如果达到了限制,调用线程将会阻塞，直到某个通道可用或者超时, 在后者的情况中，将抛出 AmqpTimeoutException异常
        connectionFactory.setChannelCacheSize(100);

        //其缓存模式为通道缓存
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory consumerRabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("172.16.2.132");
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setUsername("accounting");
        connectionFactory.setPassword("accounting");
        connectionFactory.setVirtualHost("vhost_fq_test");
        connectionFactory.setRequestedHeartBeat(15);//心跳监测间隔

        //如果达到了限制,调用线程将会阻塞，直到某个通道可用或者超时, 在后者的情况中，将抛出 AmqpTimeoutException异常
        connectionFactory.setChannelCacheSize(100);

        //其缓存模式为通道缓存
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return connectionFactory;
    }


    public static void main(String[] args) {
        SpringApplication.run(FanoutTest.class, args);
    }
}
