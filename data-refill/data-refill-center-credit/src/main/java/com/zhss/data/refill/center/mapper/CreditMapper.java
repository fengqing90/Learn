package com.zhss.data.refill.center.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 积分mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface CreditMapper {

	@Update("UPDATE credit "
			+ "SET locked_point = locked_point + #{updatedPoint} "
			+ "WHERE user_account_id=#{userAccountId}")
	void tryIncrement(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedPoint") Double updatedPoint);
	
	@Update("UPDATE credit SET "
			+ "locked_point = locked_point - #{updatedPoint},"
			+ "point = point + #{updatedPoint} "
			+ "WHERE user_account_id=#{userAccountId}")
	void confirmIncrement(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedPoint") Double updatedPoint);
	
	@Update("UPDATE credit "
			+ "SET locked_point = locked_point - #{updatedPoint} "
			+ "WHERE user_account_id=#{userAccountId}")
	void cancelIncrement(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedPoint") Double updatedPoint);
	
}
