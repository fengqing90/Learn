package com.zhss.data.refill.center.service;

import java.util.Date;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.BillApi;
import com.zhss.data.refill.center.domain.Bill;
import com.zhss.data.refill.center.mapper.BillMapper;

@Service("billConfirmService")  
@RequestMapping("/bill/confirm")  
public class BillConfirmService implements BillApi, CompensableContextAware {

	@Autowired
	private BillMapper billMapper;
	private CompensableContext compensableContext;  
	
	@Override
	@Transactional
	public void add(Bill bill) {
		Long refillOrderId = (Long) this.compensableContext.getVariable("billId");  
		billMapper.confirmCreateBill(refillOrderId); 
		System.out.println(new Date() + ": confirm创建账单接口");  
	}

	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.compensableContext = aware;
	}

}
