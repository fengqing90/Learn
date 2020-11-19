package com.zhss.data.refill.center.api;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.mapper.LotteryDrawMapper;

/**
 * 抽奖机会service组件
 * @author zhonghuashishan
 *
 */
@RestController
@Compensable(interfaceClass = LotteryDrawApi.class, 
				confirmableKey = "lotteryConfirmService", 
				cancellableKey = "lotteryCancelService")
public class LotteryDrawService implements LotteryDrawApi {

	/**
	 * 抽奖机会mapper组件
	 */
	@Autowired
	private LotteryDrawMapper lotteryDrawMapper;
	
	/**
	 * 增加一次抽奖次数
	 * @param userAccountId 用户账号id
	 */
	@Transactional
	public void increment(@PathVariable("userAccountId") Long userAccountId) {
		lotteryDrawMapper.tryIncrement(userAccountId);  
	}
	
}
