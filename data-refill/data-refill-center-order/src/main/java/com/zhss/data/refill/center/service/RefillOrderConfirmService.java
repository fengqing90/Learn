package com.zhss.data.refill.center.service;

import java.util.Date;
import java.util.List;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.RefillOrderApi;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.mapper.RefillOrderMapper;

@Service("refillOrderConfirmService")  
@RequestMapping("/refillOrder/confirm")  
public class RefillOrderConfirmService implements RefillOrderApi, CompensableContextAware {

	@Autowired
	private RefillOrderMapper refillOrderMapper;
	
	private CompensableContext compensableContext;  
	
	@Override
	@Transactional
	public void add(RefillOrder refillOrder) {
		Long refillOrderId = (Long) this.compensableContext.getVariable("refillOrderId");  
		refillOrderMapper.confirmCreateRefillOrder(refillOrderId); 
		System.out.println(new Date() + ": confirm创建订单接口");
	}

	@Override
	@Transactional
	public List<RefillOrder> queryAll(Long userAccountId) {
		return refillOrderMapper.queryAll(userAccountId);
	}

	@Override
	@Transactional
	public RefillOrder queryById(Long id) {
		return refillOrderMapper.queryById(id);
	}

	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.compensableContext = aware;
	}

}
