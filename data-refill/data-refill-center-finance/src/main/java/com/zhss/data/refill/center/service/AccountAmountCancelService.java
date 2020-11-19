package com.zhss.data.refill.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.api.AccountAmountApi;
import com.zhss.data.refill.center.mapper.AccountAmountMapper;

@Service("accountAmountCancelService")  
public class AccountAmountCancelService implements AccountAmountApi {

	@Autowired
	private AccountAmountMapper accountAmountMapper;
	
	@Override
	public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
		accountAmountMapper.cancelTransferOut(fromUserAccountId, amount); 
		accountAmountMapper.cancelTransferIn(toUserAccountId, amount); 
	}

}
