package com.zhss.data.refill.center.mapper.datapackage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zhss.data.refill.center.domain.DataPackage;

/**
 * 流量套餐mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface DataPackageMapper {

	/**
	 * 查询所有的流量套餐
	 * @return 流量套餐
	 */
	@Select("SELECT * FROM data_package")  
	@Results({
		@Result(column = "created_time", property = "createdTime"),
		@Result(column = "modified_time", property = "modifiedTime")
	})
	List<DataPackage> queryAll();
	
}
