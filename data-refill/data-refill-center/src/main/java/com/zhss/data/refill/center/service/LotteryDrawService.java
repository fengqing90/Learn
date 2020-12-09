package com.zhss.data.refill.center.service;

/**
 * 抽奖机会service组件
 * @author zhonghuashishan
 *
 */
public interface LotteryDrawService {

	/**
	 * 增加一次抽奖次数
	 * @param userAccountId 用户账号id
	 */
	void increment(Long userAccountId);
	
}
