package learn.rabbitmq.listener;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class XmlListenerContainer implements MessageListener {

    @Resource
    private ThreadPoolTaskExecutor rabbitExecutor;
    @Override
    public void onMessage(Message message) {

        long start = System.currentTimeMillis();

        System.out
            .println("【XmlListenerContainer】@@@@@@"
                + Thread.currentThread().getName() + "@@@@"
                + message.getMessageProperties().getHeaders().get("count")
                + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
                + " " + Thread.currentThread().getName() + "@" + message);

        System.out.println(Thread.currentThread().getName() + ", active count:"
            + this.rabbitExecutor.getActiveCount() + ",  queue size :"
            + this.rabbitExecutor.getThreadPoolExecutor().getQueue().size()
            + " rest cost: " + (System.currentTimeMillis() - start));

//        // 测试死信队列
//        throw new RuntimeException("to 死信队列");
    }
}
