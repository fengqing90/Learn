package learn.rabbitmq.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.rabbitmq.config.AbstractRabbitListener;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountExchange;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountQueueName;
import learn.rabbitmq.config.AccountRabbitMQConfig.AccountRoutingKey;
import learn.rabbitmq.config.KeplerRabbitListenerContainerFactory;
import learn.rabbitmq.utils.AmqpUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

// @SpringBootApplication
// @EnableAutoConfiguration
// @EnableRabbit
public class SimpleTest implements CommandLineRunner {

    private String addresses = "10.255.33.108:5672";
    private int requestedHeartbeat = 15;
    private String virtualHost = "vhost_accounting";
    private String password = "admin";
    private String username = "admin";
    private int channelSize = 100;

    public static void main(String[] args) {
        SpringApplication.run(SimpleTest.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println(this.rabbitTemplate);
        System.out
            .println(this.rabbitTemplate.getConnectionFactory().toString());

        this.send(1000);

        try {
//            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        RabbitAdmin admin = this.context.getBean(RabbitAdmin.class);
//        admin.deleteQueue(AccountQueueName.AUTOPAY_QUEUE);
//        admin.deleteExchange(AccountExchange.KEPLER_EXCHANGE);
//
//        admin.deleteQueue(AccountQueueName.DLQ_QUEUE_KEPLER);
//        admin.deleteExchange(AccountExchange.DEAD_EXCHANGE);
        this.context.close();
    }

    private void send(int count) {
        for (int i = 1; i <= count; i++) {
            this.rabbitTemplate.convertAndSend("exchange.kepler.direct",
//                "key_direct_autopay",
                "key_direct_whole_compens",
                //                AmqpUtils.messageBuilder("【" + i + "】:fengqing@ "
                //                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                //                    "FQ"));
                AmqpUtils.messageBuilder("{\r\n"
                    + "    \"relationId\": 5631092,\r\n"
                    + "    \"phase\": 1,\r\n" + "    \"amount\": 3332,\r\n"
                    + "    \"overduePrincipal\": 1619.53,\r\n"
                    + "    \"overdueInterest\": 328.53,\r\n"
                    + "    \"penaltyInterest\": 12,\r\n"
                    + "    \"restPrincipal\": 0.00,\r\n"
                    + "    \"transTime\": \"2018-10-23 15:25:03\"\r\n" + "}",
                    "WHOLECOMPENS_QUEUE"));
        }
        // throw new RuntimeException("发送测试@" + DateFormatUtils.format(new Date(),
        // "yyyy-MM-dd HH:mm:ss"));
    }

    @Resource
    private ConfigurableApplicationContext context;
    @Resource
    private RabbitTemplate rabbitTemplate;
    // @Resource
    // private ConnectionFactory consumerConnectionFactory;
    // @Resource
    // private ConnectionFactory producerConnectionFactory;
    // @Resource
    // private SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;

    @Bean
    public KeplerRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory consumerConnectionFactory,
            ThreadPoolTaskExecutor consumerRabbitExecutor) {
        KeplerRabbitListenerContainerFactory factory = new KeplerRabbitListenerContainerFactory();
        factory.setTaskExecutor(consumerRabbitExecutor);
        configurer.configure(factory, consumerConnectionFactory);
        return factory;
    }

    /**
     * 消费者线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor consumerRabbitExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setKeepAliveSeconds(60);// 允许的空闲时间
        // 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功
        executor.setRejectedExecutionHandler(
            new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setQueueCapacity(2000);// 缓存队列
        executor.setCorePoolSize(500);// 线程池维护线程的最少数量
        return executor;
    }

    @Bean
    public ConnectionFactory consumerConnectionFactory(
            ThreadPoolTaskExecutor consumerRabbitExecutor) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.addresses);
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setRequestedHeartBeat(this.requestedHeartbeat);// 心跳监测间隔

        // 如果达到了限制,调用线程将会阻塞，直到某个通道可用或者超时, 在后者的情况中，将抛出 AmqpTimeoutException异常
        connectionFactory.setChannelCacheSize(this.channelSize);
        connectionFactory.setExecutor(consumerRabbitExecutor);

        // 其缓存模式为通道缓存
        connectionFactory.setCacheMode(CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory producerConnectionFactory(
            ThreadPoolTaskExecutor producerRabbitExecutor) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.addresses);
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setRequestedHeartBeat(this.requestedHeartbeat);// 心跳监测间隔

        // 如果达到了限制,调用线程将会阻塞，直到某个通道可用或者超时, 在后者的情况中，将抛出 AmqpTimeoutException异常
        connectionFactory.setChannelCacheSize(this.channelSize + 100);
        connectionFactory.setExecutor(producerRabbitExecutor);

        // 其缓存模式为通道缓存
        connectionFactory.setCacheMode(CacheMode.CHANNEL);
        return connectionFactory;
    }

    /**
     * 生产者线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor producerRabbitExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setKeepAliveSeconds(60);// 允许的空闲时间
        // 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功
        executor.setRejectedExecutionHandler(
            new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setQueueCapacity(2000);// 缓存队列
        executor.setCorePoolSize(500);// 线程池维护线程的最少数量
        return executor;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory producerConnectionFactory,
            RetryTemplate retryTemplate) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(producerConnectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }

    // @Bean
    // public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
    // SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory
    // connectionFactory) {
    // SimpleRabbitListenerContainerFactory factory = new
    // SimpleRabbitListenerContainerFactory();
    // configurer.configure(factory, connectionFactory);
    // return factory;
    // }

    // @Bean
    // public ConnectionFactory connectionFactory() {
    // CachingConnectionFactory cachingConnectionFactory = new
    // CachingConnectionFactory("10.255.33.108", 5672);
    // cachingConnectionFactory.setVirtualHost("vhost_fq");
    // cachingConnectionFactory.setUsername("admin");
    // cachingConnectionFactory.setPassword("admin");
    // cachingConnectionFactory.setCacheMode(CacheMode.CHANNEL);
    // cachingConnectionFactory.setRequestedHeartBeat(15);
    // return cachingConnectionFactory;
    // }

    // @Bean
    // public RabbitTemplate rabbitTemplate() {
    // return new RabbitTemplate(this.connectionFactory());
    // }

    @Bean
    public RabbitAdmin keplerRabbitAdmin(
            ConnectionFactory producerConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(producerConnectionFactory);
        return rabbitAdmin;
    }

    // @Bean
    // public Binding autoPayBinding(org.springframework.amqp.core.Queue
    // autoPayQueue, DirectExchange keplerExchange) {
    // return
    // BindingBuilder.bind(autoPayQueue).to(keplerExchange).with("key_direct_autopay");
    // }

//	 @Bean
//	 public DirectExchange keplerExchange(RabbitAdmin keplerRabbitAdmin) {
//	 return new DirectExchange("exchange.kepler.direct");
//	 }

    // @Bean
    // public org.springframework.amqp.core.Queue autoPayQueue() {
    // Map<String, Object> deadMap = new HashMap<>();
    // deadMap.put("x-dead-letter-exchange", "DLE.exchange");
    // deadMap.put("x-dead-letter-routing-key", "x-dead-letter-routing-key");
    // return new org.springframework.amqp.core.Queue("queue.kepler.direct.autopay",
    // true, false, false, deadMap);
    // }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AccountQueueName.AUTOPAY_QUEUE,
                    durable = "true", autoDelete = "false", exclusive = "false",
                    arguments = {
                        @Argument(name = "x-dead-letter-exchange",
                                value = AccountExchange.DEAD_EXCHANGE),
                        @Argument(name = "x-dead-letter-routing-key",
                                value = AccountRoutingKey.DEAD_QUEUE_KEY) }),
            exchange = @Exchange(value = AccountExchange.KEPLER_EXCHANGE,
                    durable = "true", autoDelete = "false",
                    type = ExchangeTypes.DIRECT),
            key = AccountRoutingKey.REPAY_KEY))
    public String handle(Message message) {
        System.out.println(Thread.currentThread().getName() + " @@@"
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " @ "
            + new String(message.getBody()));
        throw new RuntimeException(Thread.currentThread().getName() + " 接受测试 @ "
            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = AccountQueueName.DLQ_QUEUE_KEPLER,
//                    durable = "true", autoDelete = "false",
//                    exclusive = "false"),
//            exchange = @Exchange(value = AccountExchange.DEAD_EXCHANGE,
//                    durable = "true", autoDelete = "false",
//                    type = ExchangeTypes.DIRECT),
//            key = AccountRoutingKey.DEAD_QUEUE_KEY))
//    public void dle(Message message) {
//        System.out.println(Thread.currentThread().getName() + " @@@【【【【死信队列】】】】"
//            + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
//            + new String(message.getBody()));
//    }
//    @Resource
//    private SimpleListenner a;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AccountQueueName.DLQ_QUEUE_KEPLER,
                    durable = "true", autoDelete = "false",
                    exclusive = "false"),
            exchange = @Exchange(value = AccountExchange.DEAD_EXCHANGE,
                    durable = "true", autoDelete = "false",
                    type = ExchangeTypes.DIRECT),
            key = AccountRoutingKey.DEAD_QUEUE_KEY))
    @Component
    public class SimpleListenner extends AbstractRabbitListener<String> {

        @RabbitHandler
        public void onMessage(byte[] message, Message messagea) {
            try {
                MessageProperties properties = messagea.getMessageProperties();
                Map<String, Object> headers = properties.getHeaders();
                List<?> deathInfo = (List<?>) headers.get("x-death");

                Map<String, Object> infoMap = new HashMap<>();
                infoMap.put("Payload", new String(messagea.getBody()));
                infoMap.put("MessageId", properties.getMessageId());
                infoMap.put("TraceId", headers.get("trace-id"));
                infoMap.put("ContentType", properties.getContentType());

                if (deathInfo != null) {
                    Map<String, Object> map = (Map<String, Object>) deathInfo
                        .get(0);
                    infoMap.put("Exchange", map.get("exchange"));
                    infoMap.put("RoutingKeys", map.get("routing-keys"));
                    infoMap.put("Queue", map.get("queue"));
                    infoMap.put("Exchange", map.get("exchange"));
                    infoMap.put("Time", DateFormatUtils
                        .format((Date) map.get("time"), "yyyy-MM-dd HH:mm:ss"));
                }
                System.out
                    .println(new ObjectMapper().writeValueAsString(infoMap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out
                .println(Thread.currentThread().getName() + " @@@【【【【死信队列】】】】"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
                    //                    + new String(message.getBody()));
                    + new String(message));
        }
    }
}
