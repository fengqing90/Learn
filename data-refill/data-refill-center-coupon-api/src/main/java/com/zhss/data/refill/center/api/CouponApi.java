package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.Coupon;

/**
 * 流量券service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/coupon")  
public interface CouponApi {

	/**
	 * 查询用户账号的面额最高的一张流量券
	 * @param userAccountId 用户账号id
	 * @return 流量券
	 */
	@RequestMapping(value = "/{userAccountId}", method = RequestMethod.GET) 
	Coupon queryByUserAccountId(@PathVariable("userAccountId") Long userAccountId);
	
	/**
	 * 将流量券标记为已使用
	 * @param id 流量券id
	 */
	@RequestMapping(value = "/markCouponUsed/{id}", method = RequestMethod.PUT)
	void markCouponUsed(@PathVariable("id") Long id);
	
	/**
	 * 插入一张流量券
	 * @param coupon 流量券
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	void insert(@RequestBody Coupon coupon);
	
}
