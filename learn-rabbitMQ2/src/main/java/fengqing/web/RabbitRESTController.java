package fengqing.web;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: RabbitRESTController
 * @Description: 查看rabbitinfo
 * @author: FengQing
 * @date: 2018年7月20日 下午3:29:01
 */
@RestController
@RequestMapping("/rest/rabbit")
public class RabbitRESTController {

    @Resource
    private ThreadPoolTaskExecutor consumerRabbitExecutor;
    @Resource
    private CachingConnectionFactory consumerRabbitConnectionFactory;

    private static Map<String, Object> rabbitExecutor(
            ThreadPoolTaskExecutor executor) {
        ThreadPoolExecutor threadPoolExecutor = executor
                .getThreadPoolExecutor();
        Map<String, Object> info = new HashMap<>();
        info.put("MaxPoolSize", threadPoolExecutor.getMaximumPoolSize());
        info.put("CorePoolSize", threadPoolExecutor.getCorePoolSize());
        info.put("PoolSize", threadPoolExecutor.getPoolSize());
        info.put("ActiveCount", threadPoolExecutor.getActiveCount());
        info.put("QueueSize",
                threadPoolExecutor.getQueue().size());
        return info;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("【Consumer Connection】",
                this.consumerRabbitConnectionFactory.getCacheProperties());
        info.put("【Consumer Executor】",
                RabbitRESTController.rabbitExecutor(this.consumerRabbitExecutor));
        return info;
    }
}
