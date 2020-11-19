package com.zhss.data.refill.center.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.AccountAmountApi;
import com.zhss.data.refill.center.mapper.AccountAmountMapper;

@Service("accountAmountCancelService")
@RequestMapping("/finance/cancel")
public class AccountAmountCancelService implements AccountAmountApi {

	@Autowired
	private AccountAmountMapper accountAmountMapper;
	
	@Override
	@Transactional
	public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
		accountAmountMapper.cancelTransferOut(fromUserAccountId, amount); 
		accountAmountMapper.cancelTransferIn(toUserAccountId, amount); 
		System.out.println(new Date() + ": cancel资金转账接口");
	}

}
