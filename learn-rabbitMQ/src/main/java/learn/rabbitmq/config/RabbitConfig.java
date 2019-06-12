package learn.rabbitmq.config;

import java.util.List;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
@ImportResource({ "classpath:spring-portfolio-rabbit.xml" })
public class RabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private List<MessageListenerContainer> containers;

    RetryOperationsInterceptor initRetry(boolean reject) {
        return RetryInterceptorBuilder.stateless().maxAttempts(5)
            .recoverer(reject ? new RejectAndDontRequeueRecoverer()
                : new RepublishMessageRecoverer(this.rabbitTemplate))
            .build();
    }

    @Bean
    List<MessageListenerContainer> setRetry() {
        this.containers.forEach(c -> {
            SimpleMessageListenerContainer s = (SimpleMessageListenerContainer) c;
            if (!ArrayUtils.contains(s.getQueueNames(), "ERROR.queue.test")) {
                s.setAdviceChain(this.initRetry(true), this.initRetry(false));
            }
        });
        System.out.println(this.containers);
        return this.containers;
    }

//    @Bean
//    public RabbitTransactionManager rabbitTransactionManager(
//            ConnectionFactory rabbitConnectionFactory) {
//        return new RabbitTransactionManager(rabbitConnectionFactory);
//    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, rabbitConnectionFactory);
        Executors.newFixedThreadPool(500);
        return factory;
    }

}
