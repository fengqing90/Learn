package com.zhss.data.refill.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.zhss.data.refill.center.db.DataSourceConfig;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@Import(DataSourceConfig.class)
public class DataRefillCenterPackageApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(DataRefillCenterPackageApplication.class, args);
	}

}
