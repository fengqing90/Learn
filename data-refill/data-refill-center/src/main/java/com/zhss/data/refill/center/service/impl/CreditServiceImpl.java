package com.zhss.data.refill.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.mapper.credit.CreditMapper;
import com.zhss.data.refill.center.service.CreditService;

/**
 * 积分service组件
 * @author zhonghuashishan
 *
 */
@Service
public class CreditServiceImpl implements CreditService {

	/**
	 * 积分mapper组件
	 */
	@Autowired
	private CreditMapper creditMapper;
	
	/**
	 * 增加积分
	 * @param userAccountId 用户账号id
	 */
	public void increment(Long userAccountId, Double updatedPoint) {
		creditMapper.increment(userAccountId, updatedPoint); 
	}
	
}
