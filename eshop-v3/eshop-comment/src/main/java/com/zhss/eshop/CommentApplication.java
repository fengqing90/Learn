package com.zhss.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zhss.eshop.comment.db.DruidDataSourceConfig;

/**
 * 系统启动类
 * @author zhonghuashishan
 *
 */
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@Import(DruidDataSourceConfig.class)
public class CommentApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(CommentApplication.class, args);
	}
	
}
