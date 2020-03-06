package fengqing.web;

import fengqing.listener.ConsumerThreadPoolListener;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName OperationRestController
 * @Description TODO
 * @Author fengqing
 * @Date 2020/3/6 15:52
 */
@RestController
public class OperationRestController {

    @Resource
    private CachingConnectionFactory consumerRabbitConnectionFactory;

    @RequestMapping("/")
    public String index() {
        return "Hello World.";
    }

    @GetMapping("/send/{max}")
    public void send(@PathVariable int max) {
        ExecutorService exe = Executors.newFixedThreadPool(max);

        exe.execute(new Thread(() -> {
            int y = 0;
            RabbitTemplate rabbitTemplate = new RabbitTemplate(consumerRabbitConnectionFactory);
            while (y < 1000) {
                rabbitTemplate.convertAndSend(ConsumerThreadPoolListener.EX_NAME, ConsumerThreadPoolListener.RK_NAME, y + " @" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                y++;
            }
        }));
        exe.shutdown();
    }
}
