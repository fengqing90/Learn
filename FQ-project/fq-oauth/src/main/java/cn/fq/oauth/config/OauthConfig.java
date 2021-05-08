package cn.fq.oauth.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/4/29 16:42
 */
@Slf4j
@Configuration
public class OauthConfig {
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
}
