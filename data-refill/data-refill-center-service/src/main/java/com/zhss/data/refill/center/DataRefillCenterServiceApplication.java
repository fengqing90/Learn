package com.zhss.data.refill.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" })  
public class DataRefillCenterServiceApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(DataRefillCenterServiceApplication.class, args);
	}

}
