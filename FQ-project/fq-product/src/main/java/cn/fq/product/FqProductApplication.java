package cn.fq.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cn.fq.common.utils.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class FqProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(FqProductApplication.class, args);
    }

}
