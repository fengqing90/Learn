package com.zhss.data.refill.center.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.DataPackage;

/**
 * 流量套餐service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/package")  
public interface DataPackageApi {

	/**
	 * 查询所有的流量套餐
	 * @return 流量套餐
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET) 
	List<DataPackage> queryAll();
	
}
