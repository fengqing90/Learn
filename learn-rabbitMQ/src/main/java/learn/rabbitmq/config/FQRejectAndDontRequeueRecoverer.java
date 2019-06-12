package learn.rabbitmq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.stereotype.Component;

@Component
public class FQRejectAndDontRequeueRecoverer
        extends RejectAndDontRequeueRecoverer {

    @Override
    public void recover(Message message, Throwable cause) {

        message.getMessageProperties().getHeaders().put("test",
            "@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@  自定义Recoverer");
        super.recover(message, cause);
    }
}
