package cn.fq.oauth.config;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import cn.fq.common.utils.RsaKeyProperties;
import cn.fq.oauth.utils.JwtVerifyFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengqing
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties(RsaKeyProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationFailureHandler myFailHandler;
    @Resource
    private AuthenticationSuccessHandler mySuccessHandler;
    @Resource
    private UserDetailsService userDetailsServiceImpl;
    // @Resource
    // private PersistentTokenRepository persistentTokenRepository;
    // @Resource
    // private PasswordEncoder passwordEncoder;
    @Resource
    private Filter validateCodeFilter;
    @Resource
    private RsaKeyProperties prop;
    @Resource
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置TokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        jdbcTokenRepository.setDataSource(this.dataSource);
        return jdbcTokenRepository;

        // return new InMemoryTokenRepositoryImpl();
    }

    /** TODO “AuthenticationManager对象在OAuth2认证服务中要使用，提取放入IOC容器中” why？？？ **/
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(this.userDetailsServiceImpl)
            .passwordEncoder(passwordEncoder());
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
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(18000)               // token有效时间
//////
            .and()                                          //  添加自定义权限验证
            // .addFilter(new JwtLoginFilter(authenticationManager()))                            //  1.添加jwt方式认证，由于自己new出来的，类里面属性是默认，导致这方法里面的配置都会失效
            .addFilter(new JwtVerifyFilter(authenticationManager(), this.prop,                    //  2.添加验证jwt，由于自己new出来的，类里面属性是默认，导致这方法里面的配置都会失效
                this.myFailHandler))
            .addFilterBefore(this.validateCodeFilter,
                UsernamePasswordAuthenticationFilter.class) // 添加图片验证，在UsernamePasswordAuthenticationFilter之前，顺序见: org.springframework.security.config.annotation.web.builders.FilterComparator.FilterComparator（）

            .authorizeRequests() // 身份认证设置
            .antMatchers("/login.html", "/403.html", "/failure.html",
                "/favicon.ico")
            .permitAll()                            // 匹配 login.html 不需要认证
            .antMatchers("/ping", "/code/image", "/error", "/oauth/authorize")
            .permitAll()    // 匹配/auth/*  不需要认证
            .antMatchers("/**").hasAnyRole("USER")                // 所有匹配都需要ADMIN权限
            .anyRequest().authenticated()           // 其他需要认证

            .and().sessionManagement()              // 前后端分离，可以直接禁用改成无状态
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////
            .and().cors() //
            .and().csrf().disable();// 禁用跨脚本攻击csrf
    }
}