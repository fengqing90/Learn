package learn.learnspringBatch.web;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SpringBatchRestController
 * @Author FengQing
 * @Date 2019/9/23 14:15
 */
@RestController
public class SpringBatchRestController {

    @GetMapping("/")
    public Object info() {
        return new Date();
    }
}
