package com.zhss.data.refill.center.api;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.CouponApi;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.mapper.CouponMapper;

/**
 * 流量券service组件
 * @author zhonghuashishan
 *
 */
@RestController
@Compensable(interfaceClass = CouponApi.class, 
				confirmableKey = "couponConfirmService", 
				cancellableKey = "couponCancelService")
public class CouponService implements CouponApi, CompensableContextAware {
	
	private CompensableContext context; 
	
	/**
	 * 流量券mapper组件
	 */
	@Autowired
	private CouponMapper couponMapper;
	
	/**
	 * 查询用户账号的面额最高的一张流量券
	 * @param userAccountId 用户账号id
	 * @return 流量券
	 */
	@Transactional
	public Coupon queryByUserAccountId(@PathVariable("userAccountId") Long userAccountId) {
		return couponMapper.queryByUserAccountId(userAccountId);
	}
	
	/**
	 * 将流量券标记为已使用
	 * @param id 流量券id
	 */
	@Transactional
	public void markCouponUsed(@PathVariable("id") Long id) {
		couponMapper.updateStatus(id, -1);
	}
	
	/**
	 * 插入一张流量券
	 * @param coupon 流量券
	 */
	@Transactional
	public void insert(@RequestBody Coupon coupon) {
		coupon.setStatus(0);  
		couponMapper.insert(coupon); 
		context.setVariable("couponId", coupon.getId());  
		
	}

	@Override
	public void setCompensableContext(CompensableContext context) {
		this.context = context;
	}
	
}
