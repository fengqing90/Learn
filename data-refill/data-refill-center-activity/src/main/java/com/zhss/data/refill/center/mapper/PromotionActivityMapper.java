package com.zhss.data.refill.center.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zhss.data.refill.center.domain.PromotionActivity;

/**
 * 优惠活动mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface PromotionActivityMapper {

	/**
	 * 查询流量套餐绑定的状态处于"进行中"的优惠活动
	 * @return 优惠活动
	 */
	@Select("SELECT * "
			+ "FROM promotion_activity "
			+ "WHERE data_package_id=#{dataPackageId} "
			+ "AND status=2")  
	@Results({
		@Result(column = "data_package_id", property = "dataPackageId"),
		@Result(column = "discount_price", property = "discountPrice"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "created_time", property = "createdTime"),
		@Result(column = "modified_time", property = "modifiedTime")
	})
	PromotionActivity queryByDataPackageId(
			@Param("dataPackageId") Long dataPackageId); 
	
}
