package learn.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@EnableAutoConfiguration
public class TransactionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransactionApplication.class, args);
        Foo foo = context.getBean(Foo.class);
        try {
            foo.send("foo");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        foo.send("bar");
        RabbitTemplate template = context.getBean(RabbitTemplate.class);
        // should not get any foos...    此处有bug还是能得到foos，原因是事务导致
        System.out.println("foo@" + template.receiveAndConvert("foo", 10_000));
        System.out.println("bar@" + template.receiveAndConvert("bar", 10_000));
        // should be null
        System.out.println(template.receiveAndConvert("foo", 0));


        try {
            // 不会发送foo 和 bar
            foo.send2();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
//        System.out.println(template.receiveAndConvert("foo", 0));

        RabbitAdmin admin = context.getBean(RabbitAdmin.class);
        admin.deleteQueue("foo");
        admin.deleteQueue("bar");
        context.close();
    }

    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @RabbitListener(queues = "foo")
    public void listener(Message message) {
        System.out.println("listener@@@" + new String(message.getBody()));
    }
    @Bean
    public Queue foo() {
        return new Queue("foo");
    }

    @Bean
    public Queue bar() {
        return new Queue("bar");
    }

    @Bean
    public Foo fooBean() {
        return new Foo();
    }

    @Bean
    public RabbitTransactionManager transactionManager(
            ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    public static class Foo {

        @Autowired
        private RabbitTemplate amqpTemplate;

        @Transactional
        public void send(String in) {
            this.amqpTemplate.convertAndSend("foo", in);
            this.amqpTemplate.convertAndSend("bar", in);
        }

        @Transactional
        public void send2() {
            this.send("fq");
            throw new RuntimeException("测试事务,异常回滚");
        }

    }

}