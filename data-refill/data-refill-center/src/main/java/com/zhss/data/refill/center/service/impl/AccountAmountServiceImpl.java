package com.zhss.data.refill.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.mapper.finance.AccountAmountMapper;
import com.zhss.data.refill.center.service.AccountAmountService;

/**
 * 账号金额service组件
 * @author zhonghuashishan
 *
 */
@Service
public class AccountAmountServiceImpl implements AccountAmountService {

	/**
	 * 账号金额mapper组件
	 */
	@Autowired
	private AccountAmountMapper accountAmountMapper;
	
	/**
	 * 转账
	 * @param fromUserAccountId 从谁那儿转账
	 * @param toUserAccountId 转到谁那儿去
	 * @param amount 转账金额
	 */
	public void transfer(Long fromUserAccountId, 
			Long toUserAccountId, Double amount) {
		accountAmountMapper.updateAmount(fromUserAccountId, -amount);
		accountAmountMapper.updateAmount(toUserAccountId, amount);
	}
	
}
