package org.mfm.learn.learn_kafka;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        this.template.send("topic_1", "foo1");
        this.template.send("topic_1", "foo2");
        this.template.send("topic_1", "foo3");
        this.latch.await(5, TimeUnit.SECONDS);
        Application.logger.info("All received");
    }

    @KafkaListener(topics = "topic_1")
    public void listen(ConsumerRecord<?, ?> cr) {
        Application.logger.info(cr.toString());
        this.latch.countDown();
    }

}