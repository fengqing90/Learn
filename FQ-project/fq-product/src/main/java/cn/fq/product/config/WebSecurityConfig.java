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
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
            .authorizeRequests() //
            .antMatchers("/**").hasAnyRole("USER")
            //其他的需要授权后访问
            .antMatchers("/error").permitAll().anyRequest().authenticated()
            //增加自定义验证认证过滤器
            .and()
            .addFilter(
                new JwtVerifyFilter(this.authenticationManager(), this.prop))
            // 前后端分离是无状态的，不用session了，直接禁用。
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UserDetailsService类
        auth.userDetailsService(this.userDetailsServiceImpl)
            //加密策略
            .passwordEncoder(this.passwordEncoder());
    }

    /**
     * 【标记-1】
     * 指定token的持久化策略
     * 其下有
     * <li>RedisTokenStore保存到redis</li>
     * <li>JdbcTokenStore保存到数据库</li>
     * <li>InMemoryTokenStore保存到内存中等实现类</li>
     * 这里我们选择保存在数据库中
     */
    @Bean
    public TokenStore tokenStore() {

        // return new JdbcTokenStore(oauth2DataSource);
        return new JwtTokenStore(this.accessTokenConverter());
    }

    /**
     * 【标记-1】盐
     */
    private final String SIGNING_KEY = "salt";

    /**
     * 【标记-1】 获取token后的转换
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(this.SIGNING_KEY);  //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }
}