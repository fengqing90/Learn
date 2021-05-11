package cn.fq.common.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 16:08
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {
    private String publicKeyFile;
    private String privateKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {

        if (StringUtils.hasText(publicKeyFile)) {
            publicKey = RsaUtils.getPublicKey(publicKeyFile);
        } else {
            log.warn("【配置加载】rsa.publicKeyFile 不存在。");
        }

        if (StringUtils.hasText(privateKeyFile)) {
            privateKey = RsaUtils.getPrivateKey(privateKeyFile);
        } else {
            log.warn("【配置加载】rsa.privateKeyFile 不存在。");
        }

    }
}
