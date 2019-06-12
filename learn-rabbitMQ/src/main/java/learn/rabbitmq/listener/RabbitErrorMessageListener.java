package learn.rabbitmq.listener;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

@Component
public class RabbitErrorMessageListener {

    public void onMessage(Object message) {
        System.out
            .println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
                + " " + Thread.currentThread().getName() + " -->ERROR = "
            + message.getClass());
        throw new RuntimeException();
    }
}
