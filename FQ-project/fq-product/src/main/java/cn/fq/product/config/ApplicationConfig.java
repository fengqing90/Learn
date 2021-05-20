package cn.fq.product.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import cn.fq.common.utils.RsaKeyProperties;

/**
 * 程序注解配置
 *
 * @author ruoyi
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@EnableConfigurationProperties(RsaKeyProperties.class)
// @MapperScan("cn.fq.**.mapper")
public class ApplicationConfig {

}
