package com.zhss.data.refill.center.api;

import java.util.Date;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.mapper.AccountAmountMapper;

/**
 * 账号金额service组件
 * @author zhonghuashishan
 *
 */
@RestController
@Compensable(interfaceClass = AccountAmountApi.class, 
				confirmableKey = "accountAmountConfirmService", 
				cancellableKey = "accountAmountCancelService")
public class AccountAmountService implements AccountAmountApi {

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
	 * 
	 * 此时这个接口就相当于是try接口
	 * 
	 */
	@Transactional
	public void transfer(@RequestParam("fromUserAccountId") Long fromUserAccountId, 
			@RequestParam("toUserAccountId") Long toUserAccountId, 
			@RequestParam("amount") Double amount) {
		accountAmountMapper.tryTransferOut(fromUserAccountId, amount); 
		accountAmountMapper.tryTransferIn(toUserAccountId, amount);  
		System.out.println(new Date() + ": try资金转账接口");
	}
	
}
