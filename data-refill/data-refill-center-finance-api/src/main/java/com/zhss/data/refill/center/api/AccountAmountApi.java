package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账号金额service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/finance")
public interface AccountAmountApi {

	/**
	 * 转账
	 * @param fromUserAccountId 从谁那儿转账
	 * @param toUserAccountId 转到谁那儿去
	 * @param amount 转账金额
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.PUT)  
	void transfer(@RequestParam("fromUserAccountId") Long fromUserAccountId, 
			@RequestParam("toUserAccountId") Long toUserAccountId, 
			@RequestParam("amount") Double amount);  
	
}
