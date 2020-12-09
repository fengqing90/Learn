package com.zhss.data.refill.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import com.zhss.data.refill.center.db.ActivityDataSourceConfig;

/**
 * 流量充值中心启动类
 * @author zhonghuashishan
 *
 */
@SpringBootApplication
@ServletComponentScan
@Import(ActivityDataSourceConfig.class)
public class DataRefillCenterApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(DataRefillCenterApplication.class, args);
	}

}
