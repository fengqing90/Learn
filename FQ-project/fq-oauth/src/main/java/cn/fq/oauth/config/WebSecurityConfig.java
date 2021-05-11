package cn.fq.oauth.config;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(securedEnabled = true)
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
            .formLogin().loginPage("/login.html")   // 登录
            .loginProcessingUrl("/login")
            // .successForwardUrl("/index.html")       // 成功forward跳转到index.html，如果配置了successHandler 需要再里面单独处理
            // .failureForwardUrl("/failure.html")     // 失败forward跳转到failure.html，如果配置了 failureHandler 需要再里面单独处理
            .successHandler(this.mySuccessHandler)  // 登录成功处理
            .failureHandler(this.myFailHandler)     // 登录失败处理
            .permitAll()
//////
            .and().logout()                         // 登出
            .logoutUrl("/logout").logoutSuccessUrl("/login.html")
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
            .antMatchers("/login.html", "/403.html", "/failure.html",
                "/favicon.ico")
            .permitAll()                 // 匹配 login.html 不需要认证
            .antMatchers("/ping", "/code/image", "/error").permitAll()    // 匹配/auth/*  不需要认证
            .antMatchers("/**").hasAnyRole("USER")                // 所有匹配都需要ADMIN权限
            .anyRequest().authenticated()// 其他需要认证
//////
            .and().csrf().disable();// 禁用跨脚本攻击csrf
    }
}