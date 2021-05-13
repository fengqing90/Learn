package cn.fq.oauth.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.fq.common.bean.entity.SysUser;
import cn.fq.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 添加jwt方式认证，由于自己new出来的，类里面属性是默认，导致这方法里面的配置都会失效
 * 不建议用
 * 
 * @author fengqing
 * @date 2021/5/11 17:51
 */
@Slf4j
@Deprecated
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * 重写springsecurity获取用户名和密码操作
     * 
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {

            if (request.getRequestURI().endsWith("/login")) {
                return super.attemptAuthentication(request, response);
            }

            //从输入流中获取用户名和密码，而不是表单
            SysUser sysUser = JsonUtils.toBean(request.getInputStream(),
                SysUser.class);

            if (sysUser == null) {
                throw new AuthenticationServiceException("读取user为null");
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                sysUser.getUsername(), sysUser.getPassword());
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException(
                "读取user失败" + e.getMessage(), e);
        }
    }
}
