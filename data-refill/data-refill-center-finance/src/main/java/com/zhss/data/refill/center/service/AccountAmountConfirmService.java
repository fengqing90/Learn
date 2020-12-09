package com.zhss.data.refill.center.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.AccountAmountApi;
import com.zhss.data.refill.center.mapper.AccountAmountMapper;

/**
 * 资金转账的confirm业务
 * @author zhonghuashishan
 *
 */
@Service("accountAmountConfirmService")  
@RequestMapping("/finance/confirm")
public class AccountAmountConfirmService implements AccountAmountApi {

	@Autowired
	private AccountAmountMapper accountAmountMapper;
	
	@Override
	@Transactional
	public void transfer(Long fromUserAccountId, Long toUserAccountId, Double amount) {
		accountAmountMapper.confirmTransferOut(fromUserAccountId, amount);
		accountAmountMapper.confirmTransferIn(toUserAccountId, amount);
		System.out.println(new Date() + ": confirm资金转账接口");
//		throw new IllegalStateException("error");
	}

}
