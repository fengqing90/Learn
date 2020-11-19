package com.zhss.data.refill.center.api;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.mapper.CreditMapper;

/**
 * 积分service组件
 * @author zhonghuashishan
 *
 */
@RestController
@Compensable(interfaceClass = CreditApi.class, 
				confirmableKey = "creditConfirmService", 
				cancellableKey = "creditCancelService")
public class CreditService implements CreditApi {

	/**
	 * 积分mapper组件
	 */
	@Autowired
	private CreditMapper creditMapper;
	
	/**
	 * 增加积分
	 * @param userAccountId 用户账号id
	 */
	@Transactional
	public void increment(@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("updatedPoint") Double updatedPoint) {
		creditMapper.tryIncrement(userAccountId, updatedPoint); 
	}
	
}
