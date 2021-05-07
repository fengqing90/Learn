package cn.fq.oauth.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fengqing
 */
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationFailureHandler myFailHandler;
    @Resource
    private AuthenticationSuccessHandler mySuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1、表单登录
        // http.formLogin().and().authorizeRequests().anyRequest().authenticated();

        // 2、basic登录
        // http.httpBasic().and().authorizeRequests().anyRequest().authenticated();

        // 3、自定义登录
        http.formLogin().loginPage("/auth/require") // 设置登录路由（未登录的请求会转发到 /auth/require 处理）
            .loginProcessingUrl("/auth/form")       // 登录处理url
            .successHandler(this.mySuccessHandler)  // 登录成功处理
            .failureHandler(this.myFailHandler)     // 登录失败处理

            .and().authorizeRequests() // 身份认证设置
            .antMatchers("/signin.html").permitAll()        // 匹配 signin.html 不需要认证
            .antMatchers("/auth/*", "/ping").permitAll()    // 匹配/auth/*  不需要认证
            .anyRequest().authenticated()// 其他需要认证

            .and().csrf().disable();// 禁用跨脚本攻击csrf
    }

    @Bean
    public AuthenticationSuccessHandler mySuccessHandler(
            ObjectMapper objectMapper) {
        return (request, response, authentication) -> {

            log.info("【登录】成功！");

            response.setContentType("application/json;charset-UTF-8");
            response.getWriter()
                .write(objectMapper.writeValueAsString(authentication));
        };
    }

    @Bean
    public AuthenticationFailureHandler myFailHandler(
            ObjectMapper objectMapper) {
        return (request, response, exception) -> {
            log.info("【登录】失败！");
            //这里处理登录失败后就会输出错误信息
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset-UTF-8");
            response.getWriter()
                .write(objectMapper.writeValueAsString(exception));
        };
    }
}