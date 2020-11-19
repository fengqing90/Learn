package com.zhss.data.refill.center.api;

import java.util.Date;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.BillService;
import com.zhss.data.refill.center.domain.Bill;
import com.zhss.data.refill.center.mapper.BillMapper;

@RestController
@Compensable(interfaceClass = BillApi.class, 
				confirmableKey = "billConfirmService", 
				cancellableKey = "billCancelService")
public class BillService implements BillApi, CompensableContextAware {

	@Autowired
	private BillMapper billMapper;
	private CompensableContext compensableContext;
	
	@Transactional
	public void add(@RequestBody Bill bill) {  
		billMapper.tryCreateBill(bill); 
		this.compensableContext.setVariable("billId", bill.getId()); 
		System.out.println(new Date() + ": try创建账单接口");
	}
	
	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.compensableContext = aware;
	}
	
}
