package cn.fq.oauth.web;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 16:21
 */
@RestController
@RequestMapping("/oauth")
public class OauthRestController {

    @RequestMapping("/test")
    public Object test() {
        return new Date();
    }
}
