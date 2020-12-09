package com.zhss.data.refill.center.service;

/**
 * 账号金额service组件
 * @author zhonghuashishan
 *
 */
public interface AccountAmountService {

	/**
	 * 转账
	 * @param fromUserAccountId 从谁那儿转账
	 * @param toUserAccountId 转到谁那儿去
	 * @param amount 转账金额
	 */
	void transfer(Long fromUserAccountId, 
			Long toUserAccountId, Double amount);
	
}
