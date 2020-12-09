package com.zhss.data.refill.center.service;

import java.util.Date;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.CouponApi;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.mapper.CouponMapper;

@Service("couponCancelService") 
@RequestMapping("/coupon/cancel")
public class CouponCancelService implements CouponApi, CompensableContextAware {
	
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
		couponMapper.updateStatus(id, 1);  
		System.out.println(new Date() + ": cancel使用流量券接口");  
	}
	
	@Override
	@Transactional
	public void insert(Coupon coupon) {
		Long couponId = (Long) context.getVariable("couponId"); 
		couponMapper.delete(couponId);   
		System.out.println(new Date() + ": cancel插入流量券接口");  
	}
	
	@Override
	public void setCompensableContext(CompensableContext context) {
		this.context = context;
	}

}
