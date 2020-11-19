package com.zhss.data.refill.center.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.CreditApi;
import com.zhss.data.refill.center.mapper.CreditMapper;

@Service("creditCancelService")
@RequestMapping("/credit/cancel")
public class CreditCancelService implements CreditApi {

	@Autowired
	private CreditMapper creditMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId, Double updatedPoint) {
		creditMapper.cancelIncrement(userAccountId, updatedPoint); 
		System.out.println(new Date() + ": cancel增加积分接口");  
	}

}
