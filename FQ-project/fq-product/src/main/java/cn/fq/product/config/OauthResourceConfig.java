package cn.fq.product.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import cn.fq.product.utils.MyOAuth2AuthenticationProcessingFilter;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/13 15:22
 */
@Configuration
@EnableResourceServer
public class OauthResourceConfig extends ResourceServerConfigurerAdapter {
    @Resource
    private TokenStore tokenStore;

    /**
     * 【标记-1】盐
     */
    private final String SIGNING_KEY = "salt";

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
    public TokenStore tokenStore(DataSource oauth2DataSource) {

        // return new JdbcTokenStore(oauth2DataSource);
        return new JwtTokenStore(this.accessTokenConverter());
    }

    /**
     * 【标记-1】 获取token后的转换
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(this.SIGNING_KEY);  //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }

    /**
     * 【主标记-1】资源服务令牌解析服务,如果配置了远程获取token 就无需配TokenStore
     * 
     * @return
     */
    // @Bean
    // public ResourceServerTokenServices tokenService() {
    //     //使用远程服务请求授权服务器校验token,必须指定校验token  的url、client_id，client_secret
    //     RemoteTokenServices service = new RemoteTokenServices();
    //     service.setCheckTokenEndpointUrl(
    //         "http://localhost:8081/fq-oauth/oauth/check_token");
    //     service.setClientId("baidu");
    //     service.setClientSecret("12345");
    //     return service;
    // }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.resourceId("product_api")//指定当前资源的id，非常重要！必须写！
            .tokenStore(this.tokenStore);//指定保存token的方式
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
            .antMatchers(HttpMethod.GET, "/**")
            .access("#oauth2.hasScope('read')")

            .antMatchers(HttpMethod.POST, "/**")
            .access("#oauth2.hasScope('write')")

            .antMatchers(HttpMethod.PATCH, "/**")
            .access("#oauth2.hasScope('write')")

            .antMatchers(HttpMethod.PUT, "/**")
            .access("#oauth2.hasScope('write')")

            .antMatchers(HttpMethod.DELETE, "/**")
            .access("#oauth2.hasScope('write')").and().headers()

            .addHeaderWriter((request, response) -> {
                response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
                if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
                    response.setHeader("Access-Control-Allow-Methods",
                        request.getHeader("Access-Control-Request-Method"));
                    response.setHeader("Access-Control-Allow-Headers",
                        request.getHeader("Access-Control-Request-Headers"));
                }
            })
            // 【标记-1】 如果oauth2用jdbc保存相关数据，并且不是用主数据源，需要手动切换数据源。
            .and().addFilterBefore(new MyOAuth2AuthenticationProcessingFilter(),
                AbstractPreAuthenticatedProcessingFilter.class);
    }
}
