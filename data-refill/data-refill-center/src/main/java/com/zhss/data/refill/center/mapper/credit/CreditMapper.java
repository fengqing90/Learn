package com.zhss.data.refill.center.mapper.credit;

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

	/**
	 * 增加积分
	 * @param userAccountId 用户账号id
	 */
	@Update("UPDATE credit "
			+ "SET point = point + #{updatedPoint} "
			+ "WHERE user_account_id=#{userAccountId}")
	void increment(@Param("userAccountId") Long userAccountId,
			@Param("updatedPoint") Double updatedPoint);
	
}
