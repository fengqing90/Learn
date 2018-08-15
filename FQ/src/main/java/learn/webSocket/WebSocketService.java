package learn. webSocket;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author jay
 */
@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @Scheduled(fixedRate = 5000)
    public void getTime() {
        this.template.convertAndSendToUser("admin", "/queue/time", new Date());
    }
}
