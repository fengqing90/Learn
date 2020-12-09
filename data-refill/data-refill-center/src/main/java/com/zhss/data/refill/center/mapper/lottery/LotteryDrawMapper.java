package com.zhss.data.refill.center.mapper.lottery;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 抽奖机会mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface LotteryDrawMapper {

	/**
	 * 增加一次抽奖次数
	 * @param userAccountId 用户账号id
	 */
	@Update("UPDATE lottery_draw "
			+ "SET lottery_draw_count = lottery_draw_count + 1 "
			+ "WHERE user_account_id=#{userAccountId}")
	void increment(@Param("userAccountId") Long userAccountId);
	
}
