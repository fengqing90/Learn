package learn.rabbitmq.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.annotation.AliasFor;
import org.springframework.messaging.handler.annotation.MessageMapping;

import learn.rabbitmq.config.AccountRabbitMQConfig.AccountExchange;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountRoutingKey;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Inherited
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(
        //            value = AccountQueueName.AUTOPAY_QUEUE,
                durable = "true",
                autoDelete = "false", exclusive = "false",
                arguments = {
                    @Argument(name = "x-dead-letter-exchange",
                            value = AccountExchange.DEAD_EXCHANGE),
                    @Argument(name = "x-dead-letter-routing-key",
                            value = AccountRoutingKey.DEAD_QUEUE_KEY) }),
        exchange = @Exchange(value = AccountExchange.KEPLER_EXCHANGE,
                durable = "true", autoDelete = "false",
                type = ExchangeTypes.DIRECT)
//        ,key = AccountRoutingKey.REPAY_KEY
))
public @interface KeplerRabbitListenner {

    @AliasFor(value = "value", annotation = Queue.class)
    String queueName() default "";

    String exchangeName() default "";

    @AliasFor(value = "key", annotation = QueueBinding.class)
    String routingKey() default "";
    String exchangeType() default ExchangeTypes.DIRECT;

}

