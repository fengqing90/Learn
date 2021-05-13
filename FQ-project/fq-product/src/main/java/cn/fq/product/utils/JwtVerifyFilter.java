package cn.fq.product.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import cn.fq.common.bean.Payload;
import cn.fq.common.bean.entity.SysUser;
import cn.fq.common.utils.JwtUtils;
import cn.fq.common.utils.RsaKeyProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 16:21
 */
@Slf4j
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties prop;
    // private AuthenticationFailureHandler myFailHandler;

    public JwtVerifyFilter(AuthenticationManager authenticationManager,
            RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    /**
     * 过滤请求
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //请求体的头中是否包含Authorization
        //Authorization中是否包含Bearer，不包含直接返回
        String headerAuthorization = request
            .getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuthorization == null
            || !headerAuthorization.startsWith("Bearer ")
            || request.getRequestURI().endsWith("/failure.html")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = this
                .getAuthentication(headerAuthorization);

            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext()
                .setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw e;
            // this.myFailHandler.onAuthenticationFailure(request, response,
            //     new AuthenticationServiceException("Token 已过期。", e));
        }
    }

    /**
     * 通过token，获取用户信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(
            String headerAuthorization) {

        //通过token解析出载荷信息
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(
            headerAuthorization.replace("Bearer ", ""),
            this.prop.getPublicKey(), SysUser.class);

        SysUser user = payload.getUserInfo();
        log.info("【Token验证】 user = [{}]", user);

        return new UsernamePasswordAuthenticationToken(user, null,
            user.getRoles());
    }
}
