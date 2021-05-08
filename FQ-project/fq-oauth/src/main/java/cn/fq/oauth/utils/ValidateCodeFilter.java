package cn.fq.oauth.utils;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.fq.oauth.bean.ImageCode;
import cn.fq.oauth.web.OauthRestController;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证码验证
 *
 * @author fengqing
 * @date 2021/5/7 10:52
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationFailureHandler myFailHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.POST.name())
            && request.getRequestURI().equals("/auth/form")) {
            try {
                this.validate(request);
            } catch (ValidateCodeException e) {
                this.myFailHandler.onAuthenticationFailure(request, response,
                    e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {

        ImageCode codeInSession = (ImageCode) request.getSession()
            .getAttribute(OauthRestController.SESSION_KEY);
        String codeInRequest = request.getParameter("imageCode");

        if (!StringUtils.hasText(codeInRequest)) {
            throw new ValidateCodeException("请输入验证码");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("未生成验证码");
        }

        if (codeInSession.isExpired()) {
            request.getSession()
                .removeAttribute(OauthRestController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!codeInRequest.trim().equals(codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不正确");
        }

        // 验证通过，清除session中的图片验证码
        request.getSession().removeAttribute(OauthRestController.SESSION_KEY);
    }
}
