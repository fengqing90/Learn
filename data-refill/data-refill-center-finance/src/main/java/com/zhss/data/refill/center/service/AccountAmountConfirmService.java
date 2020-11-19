package com.zhss.data.refill.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.data.refill.center.api.AccountAmountApi;
import com.zhss.data.refill.center.mapper.AccountAmountMapper;

/**
 * 资金转账的confirm业务
 * @author zhonghuashishan
 *
 */
@Service("accountAmountConfirmService")  
public class AccountAmountConfirmService implements AccountAmountApi {

	@Autowired
	private AccountAmountMapper accountAmountMapper;
	
	@Override
	@Transactional
	public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
		accountAmountMapper.confirmTransferOut(fromUserAccountId, amount);
		accountAmountMapper.confirmTransferIn(toUserAccountId, amount);  
	}

}
