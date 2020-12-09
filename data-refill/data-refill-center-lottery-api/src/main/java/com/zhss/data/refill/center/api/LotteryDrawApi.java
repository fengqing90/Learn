package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 抽奖机会service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/lottery")  
public interface LotteryDrawApi {

	/**
	 * 增加一次抽奖次数
	 * @param userAccountId 用户账号id
	 */
	@RequestMapping(value = "/draw/increment/{userAccountId}", method = RequestMethod.PUT)  
	void increment(@PathVariable("userAccountId") Long userAccountId);
	
}
