package com.zhss.data.refill.center.service;

import com.zhss.data.refill.center.domain.Coupon;

/**
 * 流量券service组件
 * @author zhonghuashishan
 *
 */
public interface CouponService {

	/**
	 * 查询用户账号的面额最高的一张流量券
	 * @param userAccountId 用户账号id
	 * @return 流量券
	 */
	Coupon queryByUserAccountId(Long userAccountId);
	
	/**
	 * 将流量券标记为已使用
	 * @param id 流量券id
	 */
	void markCouponUsed(Long id);
	
	/**
	 * 插入一张流量券
	 * @param coupon 流量券
	 */
	void insert(Coupon coupon);
	
}
