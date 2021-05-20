package cn.fq.oauth.config;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * Oauth2.0 相关配置
 *
 * @author fengqing
 * @date 2021/4/29 16:42
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private DataSource dataSource;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;

    private String SIGNING_KEY = "salt";

    /**
     * 从数据库中查询出客户端信息
     * 
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(
            dataSource);
        jdbcClientDetailsService.setPasswordEncoder(this.passwordEncoder);
        return jdbcClientDetailsService;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(this.SIGNING_KEY);  //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }

    /**
     * token保存策略
     * 
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
        // return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 授权信息保存策略
     * 
     * @return
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 授权码模式专用对象
     * 
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 指定客户端登录信息来源
     * 
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //从数据库取数据
        clients.withClientDetails(clientDetailsService());

        // 从内存中取数据
        // clients.inMemory().withClient("baidu")
        //     .secret(passwordEncoder.encode("12345")).resourceIds("product_api")
        //     .authorizedGrantTypes("authorization_code", "password",
        //         "client_credentials", "implicit", "refresh_token")// 该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
        //     .scopes("read", "write")// 允许的授权范围
        //     .autoApprove(false)
        //     //加上验证回调地址
        //     .redirectUris("http://www.baidu.com");
    }

    /**
     * 检测token的策略
     * 
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer.allowFormAuthenticationForClients()    //允许form表单客户端认证,允许客户端使用client_id和client_secret获取token
            .checkTokenAccess("permitAll()")     //通过验证返回token信息
            .tokenKeyAccess("permitAll()")            // 获取token请求不进行拦截
            .passwordEncoder(this.passwordEncoder);
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setAccessTokenValiditySeconds(10800); // 令牌默认有效期2小时
        tokenServices.setRefreshTokenValiditySeconds(86400); // 刷新令牌默认有效期3天
        //新增对jwt配置
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain
            .setTokenEnhancers(Arrays.asList(accessTokenConverter()));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        return tokenServices;
    }

    /**
     * OAuth2的主配置信息
     * 
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {

        endpoints.approvalStore(approvalStore())
            .authenticationManager(this.authenticationManager)
            .authorizationCodeServices(authorizationCodeServices())
            .tokenStore(tokenStore())
            .userDetailsService(this.userDetailsService)
            .tokenServices(tokenService())
            .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
}
