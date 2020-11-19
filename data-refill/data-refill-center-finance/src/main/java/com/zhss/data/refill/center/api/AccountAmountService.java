package com.zhss.data.refill.center.api;

import java.util.Date;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.domain.Bill;
import com.zhss.data.refill.center.mapper.AccountAmountMapper;

@RestController
@Compensable(interfaceClass = AccountAmountApi.class, 
				confirmableKey = "accountAmountConfirmService", 
				cancellableKey = "accountAmountCancelService")
public class AccountAmountService implements AccountAmountApi {

	@Autowired
	private AccountAmountMapper accountAmountMapper;
	@Autowired
	private BillService billService;
	
	@Transactional
	public void transfer(@RequestParam("fromUserAccountId") Long fromUserAccountId, 
			@RequestParam("toUserAccountId") Long toUserAccountId, 
			@RequestParam("amount") Double amount) {
		accountAmountMapper.tryTransferOut(fromUserAccountId, amount); 
		accountAmountMapper.tryTransferIn(toUserAccountId, amount);  
		
		Bill bill = new Bill();
		bill.setFromAccountId(fromUserAccountId); 
		bill.setToAccountId(toUserAccountId); 
		bill.setTransferAmount(amount); 
		
		billService.add(bill); 
		
		System.out.println(new Date() + ": try资金转账接口");
	}
	
}
