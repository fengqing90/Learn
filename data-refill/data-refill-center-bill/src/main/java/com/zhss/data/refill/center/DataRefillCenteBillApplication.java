package com.zhss.data.refill.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.zhss.data.refill.center.db.DataSourceConfig;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@Import(DataSourceConfig.class)
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" })  
public class DataRefillCenteBillApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(DataRefillCenteBillApplication.class, args);
	}

}
