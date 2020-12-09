package com.zhss.data.refill.center.mapper;

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

	@Update("UPDATE lottery_draw "
			+ "SET locked_lottery_draw_count = locked_lottery_draw_count + 1 "
			+ "WHERE user_account_id=#{userAccountId}")
	void tryIncrement(@Param("userAccountId") Long userAccountId);
	
	@Update("UPDATE lottery_draw SET "
			+ "locked_lottery_draw_count = locked_lottery_draw_count - 1,"
			+ "lottery_draw_count = lottery_draw_count + 1 "
			+ "WHERE user_account_id=#{userAccountId}")
	void confirmIncrement(@Param("userAccountId") Long userAccountId);
	
	@Update("UPDATE lottery_draw "
			+ "SET locked_lottery_draw_count = locked_lottery_draw_count - 1 "
			+ "WHERE user_account_id=#{userAccountId}")
	void cancelIncrement(@Param("userAccountId") Long userAccountId);
	
}
