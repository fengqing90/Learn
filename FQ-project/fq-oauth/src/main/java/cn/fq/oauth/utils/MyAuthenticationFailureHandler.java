package cn.fq.oauth.utils;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/7 16:15
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String message = exception.getMessage();

        if (exception instanceof BadCredentialsException) {
            message = message + "-密码错误";
        }

        log.info("【登录】失败！[{}]", message);
        log.info("【登录】失败-详细信息：{}",
            this.objectMapper.writeValueAsString(exception));
        //这里处理登录失败后就会输出错误信息
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // response.setContentType("application/json;charset=UTF-8");
        // response.getWriter()
        //     .write(this.objectMapper.writeValueAsString(Result.build(message)));

        new DefaultRedirectStrategy().sendRedirect(request, response,
            "/failure.html?exception=" + URLEncoder.encode(message, "UTF-8"));

    }
}
