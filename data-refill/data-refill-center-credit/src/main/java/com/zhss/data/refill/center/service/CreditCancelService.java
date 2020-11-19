package com.zhss.data.refill.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.data.refill.center.api.CreditApi;
import com.zhss.data.refill.center.mapper.CreditMapper;

@Service("creditCancelmService")
public class CreditCancelService implements CreditApi {

	@Autowired
	private CreditMapper creditMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId, Double updatedPoint) {
		creditMapper.cancelIncrement(userAccountId, updatedPoint); 
	}

}
