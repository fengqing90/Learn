package learn.rabbitmq.demo;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import learn.rabbitmq.utils.AmqpUtils;

@Component
@RestController
@Transactional // 标签无效
public class RabbitMsgProcess {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplateKepler;
    @Autowired
    private TransactionTest tt;

    // 事务生效
    @Transactional // 标签无效
    public void send4Transaction() throws Exception {
        this.tt.send();
    }

    // 事务不生效，消息还是会被发出去
    @Transactional // 标签无效
    public void send() throws Exception {
        int i = 0;
        while (i < 500000) {
            Message message = AmqpUtils.messageBuilder(
                "Hello from RabbitMQ!"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                "文本");
            this.rabbitTemplate.convertAndSend("exchange.direct", "test",
                message);
            i++;
            Thread.sleep(500);
        }
//        throw new RuntimeException("手动停止");
    }

/////////////////////////////////////////////////////////////////////////////////////

    @Transactional // 标签无效
    public void sendObject()
            throws JsonProcessingException, InterruptedException {

        int i = 0;
        while (i < 50) {
            User user = new User("冯庆", 28,
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Message message = null;

            // 源码中，如果send是个message 将不会创建Message，如果是Object、byte[]、String将会创建message，并且设置:
            // messageProperties.setContentType(MessageProperties.CONTENT_TYPE_BYTES);
            // messageProperties.setContentLength(bytes.length);
//        message = MessageBuilder.withBody(SerializationUtils.serialize(user))
//            .setContentType(MessageProperties.CONTENT_TYPE_BYTES)
//            .setMessageId("kepler-业务-" + UUID.randomUUID().toString()) // kepler-业务-uuid
//            .setUserId("fengqing").build();

            message = AmqpUtils.messageBuilder(
                new ObjectMapper().writeValueAsBytes(user), "User");
            message.getMessageProperties().setHeader("count", i);

            this.rabbitTemplate.convertAndSend("exchange.direct", "object",
                message);
            Thread.sleep(500);
            i++;
            System.out.println("@@@@@@@@@count" + i);
        }


    }

/////////////////////////////////////////////////////////////////////////////////////

    @Transactional // 标签无效
    private void sendFanout() throws Exception {
        int i = 0;
        while (true) {
            Message message = AmqpUtils.messageBuilder(
                "Hello from RabbitMQ!"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                "sendFanout");
            message.getMessageProperties().setHeader("count", i);

            this.rabbitTemplate.convertAndSend("exchange.fanout", null,
                message);

            message.getMessageProperties().setHeader("from", "vhost1");
            this.rabbitTemplateKepler.convertAndSend("kepler", "direct",
                message);
            Thread.sleep(100);
            System.out.println("count" + i);
            i++;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////

    public void send4Dlx() throws Exception {
        int i = 0;
        while (i < 10) {
            Message message = AmqpUtils.messageBuilder(
                "Hello from RabbitMQ!"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                "sendFanout");
            message.getMessageProperties().setHeader("count", i);
            message.getMessageProperties().setHeader("from", "vhost1");

            this.rabbitTemplate.convertAndSend("exchange.direct", "test",
                message);

            Thread.sleep(100);
            System.out.println("send count:" + i);
            i++;
        }

    }

/////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("/dlx")
    void dlx() throws Exception {
        this.send4Dlx();
    }

    @RequestMapping("/")
    void home() throws Exception {
        this.send();
    }

    @RequestMapping("/tr")
    void tr() throws Exception {
        this.send4Transaction();
    }

    @RequestMapping("/object")
    void object() throws Exception {
        this.sendObject();
    }

    @RequestMapping("/fanout")
    void fanout() throws Exception {
        this.sendFanout();
    }

    @RequestMapping("/thread/{count}")
    void thread(@PathVariable int count) {
        this.threadSend(count);
    }

    private void threadSend(int count) {

        Executor executor = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executor.execute(() -> {
                try {
                    RabbitMsgProcess.this.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println(i);
        }
    }

}
