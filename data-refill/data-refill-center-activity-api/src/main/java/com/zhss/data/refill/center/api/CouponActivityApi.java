package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.CouponActivity;

/**
 * 流量券活动service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/activity/coupon")  
public interface CouponActivityApi {

	/**
	 * 查询流量套餐绑定的状态处于"进行中"的流量券活动
	 * @return 流量券活动
	 */
	@RequestMapping(value = "/{dataPackageId}", method = RequestMethod.GET)
	CouponActivity queryByDataPackageId(@PathVariable("dataPackageId") Long dataPackageId); 
	
}
