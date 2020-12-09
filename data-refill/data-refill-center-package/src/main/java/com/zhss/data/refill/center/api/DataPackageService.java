package com.zhss.data.refill.center.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.DataPackageService;
import com.zhss.data.refill.center.domain.DataPackage;
import com.zhss.data.refill.center.mapper.DataPackageMapper;

/**
 * 流量套餐service组件
 * @author zhonghuashishan
 *
 */
@RestController
public class DataPackageService implements DataPackageApi {

	/**
	 * 流量套餐mapper组件
	 */
	@Autowired
	private DataPackageMapper dataPackageMapper;
	
	/**
	 * 查询所有的流量套餐
	 * @return 流量套餐
	 */
	public List<DataPackage> queryAll() {
		return dataPackageMapper.queryAll();
	}
	
}
