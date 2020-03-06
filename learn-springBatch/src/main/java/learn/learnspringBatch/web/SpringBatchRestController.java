package learn.learnspringBatch.web;

import org.apache.http.client.utils.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName SpringBatchRestController
 * @Author FengQing
 * @Date 2019/9/23 14:15
 */
@RestController
public class SpringBatchRestController {

    @GetMapping("/")
    public String info() {
        return DateUtils.formatDate(new Date());
    }
}
