package cn.fq.product.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import cn.fq.common.utils.RsaKeyProperties;
import cn.fq.product.utils.JwtVerifyFilter;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 15:26
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private RsaKeyProperties prop;
    @Resource
    private UserDetailsService userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //关闭跨站请求防护
            .cors().and().csrf().disable()
            //允许不登陆就可以访问的方法，多个用逗号分隔
            .authorizeRequests().antMatchers("/product/list").hasAnyRole("USER")
            //其他的需要授权后访问
            .antMatchers("/error").permitAll().anyRequest().authenticated()
            .and()
            //增加自定义验证认证过滤器
            .addFilter(new JwtVerifyFilter(authenticationManager(), this.prop))
            // 前后端分离是无状态的，不用session了，直接禁用。
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UserDetailsService类
        auth.userDetailsService(this.userDetailsServiceImpl)
            //加密策略
            .passwordEncoder(passwordEncoder());
    }

}