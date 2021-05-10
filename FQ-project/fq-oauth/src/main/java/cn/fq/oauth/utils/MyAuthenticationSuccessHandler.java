package cn.fq.oauth.utils;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/7 16:17
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("【登录】username=[{}],成功！", authentication.getName());
        log.info("【登录】成功-详细信息：{}",
            this.objectMapper.writeValueAsString(authentication));

        // response.setContentType("application/json;charset=UTF-8");
        // response.getWriter()
        //     .write(objectMapper.writeValueAsString(authentication));
        // 静态html页面不能post请求
        // request.getRequestDispatcher("/index.html").forward(request, response);

        new DefaultRedirectStrategy().sendRedirect(request, response,
            "/index.html?userName=" + authentication.getName()
                + "&authentication="
                + URLEncoder.encode(
                    this.objectMapper.writeValueAsString(authentication),
                    "UTF-8"));
    }
}
