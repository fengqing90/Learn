package com.zhss.data.refill.center.service;

import java.util.List;

import com.zhss.data.refill.center.domain.DataPackage;

/**
 * 流量套餐service组件
 * @author zhonghuashishan
 *
 */
public interface DataPackageService {

	/**
	 * 查询所有的流量套餐
	 * @return 流量套餐
	 */
	List<DataPackage> queryAll();
	
}
