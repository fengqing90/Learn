package cn.fq.oauth.config;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    @Resource
    private Filter validateCodeFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1、表单登录
        // http.formLogin().and().authorizeRequests().anyRequest().authenticated();

        // 2、basic登录
        // http.httpBasic().and().authorizeRequests().anyRequest().authenticated();

        // 3、自定义登录
        http.addFilterBefore(this.validateCodeFilter,
            UsernamePasswordAuthenticationFilter.class)// 添加图片验证，在UsernamePasswordAuthenticationFilter之前，顺序见: org.springframework.security.config.annotation.web.builders.FilterComparator.FilterComparator（） 

            .formLogin().loginPage("/auth/require") // 设置登录路由（未登录的请求会转发到 /auth/require 处理）
            .loginProcessingUrl("/auth/form")       // 登录处理url
            .successHandler(this.mySuccessHandler)  // 登录成功处理
            .failureHandler(this.myFailHandler)     // 登录失败处理

            .and().authorizeRequests() // 身份认证设置
            .antMatchers("/signin.html").permitAll()        // 匹配 signin.html 不需要认证
            .antMatchers("/auth/*", "/ping", "/code/image").permitAll()    // 匹配/auth/*  不需要认证
            .anyRequest().authenticated()// 其他需要认证

            .and().csrf().disable();// 禁用跨脚本攻击csrf
    }

}