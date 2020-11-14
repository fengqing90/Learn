package com.zhss.c2c.social.govern.reviewer.service;

import com.zhss.c2c.social.govern.reviewer.api.ReviewerService;
import org.apache.dubbo.config.annotation.Service;

@Service(
        version = "1.0.0",
        interfaceClass = ReviewerService.class,
        cluster = "failfast",
        loadbalance = "roundrobin"
)
public class ReviewerServiceImpl implements ReviewerService {

}
