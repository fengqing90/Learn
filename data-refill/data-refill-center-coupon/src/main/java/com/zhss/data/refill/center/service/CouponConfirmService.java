package com.zhss.data.refill.center.service;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.CouponApi;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.mapper.CouponMapper;

@Service("couponConfirmService") 
@RequestMapping("/coupon/confirm")  
public class CouponConfirmService implements CouponApi, CompensableContextAware {
	
	@Autowired
	private CouponMapper couponMapper;
	
	private CompensableContext context;

	@Override
	@Transactional
	public Coupon queryByUserAccountId(Long userAccountId) {
		return couponMapper.queryByUserAccountId(userAccountId);
	}

	@Override
	@Transactional
	public void markCouponUsed(Long id) {
		couponMapper.updateStatus(id, 2);  
	}

	@Override
	@Transactional
	public void insert(Coupon coupon) {
		Long couponId = (Long) context.getVariable("couponId"); 
		couponMapper.updateStatus(couponId, 1); 
	}
	
	@Override
	public void setCompensableContext(CompensableContext context) {
		this.context = context;
	}

}
