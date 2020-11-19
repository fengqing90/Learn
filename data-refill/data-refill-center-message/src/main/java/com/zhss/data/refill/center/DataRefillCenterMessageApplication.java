package com.zhss.data.refill.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
public class DataRefillCenterMessageApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(DataRefillCenterMessageApplication.class, args);
	}

}
