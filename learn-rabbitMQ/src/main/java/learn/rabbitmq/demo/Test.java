package learn.rabbitmq.demo;

/**
 * KEP-TODO
 *
 * @ClassName Test
 * @Author FengQing
 * @Date 2019/5/31 15:47
 */

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 类说明：自动声明
 *
 * @author zhangkewei
 * @date 2018/11/22下午9:55
 */
@ComponentScan
@Configuration
public class Test {

    /**
     * 自动声明queue
     *
     * @return
     */
    @Bean
    public Queue debugQueue() {
        return new Queue("testAuto.debug.queue", true);
    }

    @Bean
    public Queue infoQueue() {
        return new Queue("testAuto.info.queue", true);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue("testAuto.error.queue", true);
    }

    @Bean
    public Queue amqQueue() {
        return new Queue("testAuto.amq.log", true);
    }

    @Bean
    public Queue pay() {
        Queue q = new Queue("pay", true);
        q.setShouldDeclare(true);
        return q;
    }

    /**
     * 自动声明绑定
     *
     * @return
     */
    @Bean
    public Binding b1() {
        return new Binding("debug.queue", Binding.DestinationType.QUEUE,
                "log.direct.exchange", "debug", new HashMap<>());
    }

    @Bean
    public Binding b2() {
        return new Binding("info.queue", Binding.DestinationType.QUEUE,
                "log.direct.exchange", "info", new HashMap<>());
    }

    @Bean
    public Binding b3() {
        return new Binding("error.queue", Binding.DestinationType.QUEUE,
                "log.direct.exchange", "error", new HashMap<>());
    }

    /**
     * 一次性批量生成多个队列
     *
     * @return
     */
    @Bean
    public List<Queue> logQueues() {
        List<Queue> list = new ArrayList<>();
        list.add(new Queue("testAutoBatch.log.debug", true));
        list.add(new Queue("testAutoBatch.log.info", true));
        list.add(new Queue("testAutoBatch.log.error", true));
        return list;
    }

    /**
     * 一次性批量生成多个exchange
     *
     * @return
     */
    @Bean
    public List<Exchange> logExchanges() {
        List<Exchange> list = new ArrayList<>();
        list.add(new TopicExchange("testAutoBatch.debug.topic.exchange", true,
                false));
        list.add(new TopicExchange("testAutoBatch.info.topic.exchange", true,
                false));
        list.add(new TopicExchange("testAutoBatch.error.topic.exchange", true,
                false));
        return list;
    }

    /**
     * 一次性批量生成多个exchange
     *
     * @return
     */
    @Bean
    public List<Binding> listBindings() {

        List<Binding> list = new ArrayList<>();
        list.add(BindingBuilder.bind(new Queue("testAutoBatch.log.debug"))
                .to(new TopicExchange("debug.topic.exchange")).with("debug.*"));
        list.add(BindingBuilder.bind(new Queue("testAutoBatch.log.info"))
                .to(new TopicExchange("debug.topic.exchange")).with("info.*"));
        list.add(BindingBuilder.bind(new Queue("testAutoBatch.log.error"))
                .to(new TopicExchange("debug.topic.exchange")).with("error.*"));
        return list;
    }

    /**
     * 单独申请一个exchange
     *
     * @return
     */
    @Bean
    public Exchange directExchange() {
        return new DirectExchange("test.directExchange", true, false, null);
    }

    @Bean
    public Exchange topicExchange() {
        return new TopicExchange("test.topicExchange", true, false, null);
    }

    @Bean
    public Exchange headersExchange() {
        return new HeadersExchange("test.headersExchange", true, false, null);
    }

    @Bean
    public Exchange fanoutExchange() {
        return new FanoutExchange("test.fanoutExchange", true, false, null);
    }

}
