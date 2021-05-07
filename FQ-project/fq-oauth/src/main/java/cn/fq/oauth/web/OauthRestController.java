package cn.fq.oauth.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 16:21
 */
@Slf4j
@RestController
public class OauthRestController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping("/ping")
    public Object test() {
        return new Date();
    }

    @RequestMapping("/auth/require")
    public Object requireAuth(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 获取前一次的跳转请求
        SavedRequest savedRequest = this.requestCache.getRequest(request,
            response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("【登录】引发跳转的URL是：[{}]", targetUrl);

            // 如果是未登录访问的html页面,直接跳转 /signin.html 登录页面
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                this.redirectStrategy.sendRedirect(request, response,
                    "/signin.html");
            }
        }
        return "访问的服务需要身份认证，请登录。";
    }
}
