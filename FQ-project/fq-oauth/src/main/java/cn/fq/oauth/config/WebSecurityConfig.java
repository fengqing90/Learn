package cn.fq.oauth.config;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
    @Resource
    private UserDetailsService myUserDetailsService;
    @Resource
    private PersistentTokenRepository persistentTokenRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(this.myUserDetailsService)
            .passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1、表单登录
        // http.formLogin().and().authorizeRequests().anyRequest().authenticated();

        // 2、basic登录
        // http.httpBasic().and().authorizeRequests().anyRequest().authenticated();

        // 3、自定义登录
        http//////
            .formLogin().loginPage("/login.html")   // 设置登录路由
            .loginProcessingUrl("/login")           // 登录处理url
            .successForwardUrl("/index.html")
            .successHandler(this.mySuccessHandler)  // 登录成功处理
            .failureForwardUrl("/failure.html")
            .failureHandler(this.myFailHandler)     // 登录失败处理
            .permitAll()
//////
            .and().logout()                         // 登出
            .logoutUrl("/logout").logoutSuccessUrl("/index.html")
            .invalidateHttpSession(true)            // 登出时销毁session
            .permitAll()
//////
            .and().rememberMe()                     // 开启记住我功能
            .tokenRepository(this.persistentTokenRepository)
            .tokenValiditySeconds(60)               // token有效时间 
//////
            .and()                                  // 权限验证
            .addFilterBefore(this.validateCodeFilter,
                UsernamePasswordAuthenticationFilter.class)// 添加图片验证，在UsernamePasswordAuthenticationFilter之前，顺序见: org.springframework.security.config.annotation.web.builders.FilterComparator.FilterComparator（） 
            .authorizeRequests() // 身份认证设置
            .antMatchers("/*.html").permitAll()                 // 匹配 login.html 不需要认证
            .antMatchers("/ping", "/code/image").permitAll()    // 匹配/auth/*  不需要认证
            .anyRequest().authenticated()// 其他需要认证
//////
            .and().csrf().disable();// 禁用跨脚本攻击csrf
    }
}