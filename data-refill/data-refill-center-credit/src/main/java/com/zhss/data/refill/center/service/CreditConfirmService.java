package com.zhss.data.refill.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.CreditApi;
import com.zhss.data.refill.center.mapper.CreditMapper;

@Service("creditConfirmService")
@RequestMapping("/credit/confirm")
public class CreditConfirmService implements CreditApi {

	@Autowired
	private CreditMapper creditMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId, Double updatedPoint) {
		creditMapper.confirmIncrement(userAccountId, updatedPoint); 
	}

}
