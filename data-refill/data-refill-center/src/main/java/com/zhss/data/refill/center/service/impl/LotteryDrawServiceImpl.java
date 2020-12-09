package com.zhss.data.refill.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.mapper.lottery.LotteryDrawMapper;
import com.zhss.data.refill.center.service.LotteryDrawService;

/**
 * 抽奖机会service组件
 * @author zhonghuashishan
 *
 */
@Service
public class LotteryDrawServiceImpl implements LotteryDrawService {

	/**
	 * 抽奖机会mapper组件
	 */
	@Autowired
	private LotteryDrawMapper lotteryDrawMapper;
	
	/**
	 * 增加一次抽奖次数
	 * @param userAccountId 用户账号id
	 */
	public void increment(Long userAccountId) {
		lotteryDrawMapper.increment(userAccountId);  
	}
	
}
