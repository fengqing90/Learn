package cn.fq.oauth.web;

import java.io.IOException;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fq.oauth.bean.ImageCode;
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

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @RequestMapping("/ping")
    public Object test() {
        return new Date();
    }

    @RequestMapping("/requireAuth")
    public Object requireAuth() {
        return "身份已认证：" + new Date();
    }

    /**
     * 3种权限写法
     */
    @Secured("ROLE_FQ")
    @RolesAllowed("ROLE_FQ")
    @PreAuthorize("ROLE_FQ")
    @RequestMapping("/requireAuth4ROLE_FQ")
    public Object requireAuth4ROLE_FQ() {
        return "已有[ROLE_FQ]权限，身份已认证：" + new Date();
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

            // 如果是未登录访问的html页面,直接跳转 /login.html 登录页面
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                this.redirectStrategy.sendRedirect(request, response,
                    "/login.html");
            }
        }
        return "访问的服务需要身份认证，请登录。";
    }

    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        ImageCode imageCode = ImageCode.createImageCode();
        request.getSession().setAttribute(SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
