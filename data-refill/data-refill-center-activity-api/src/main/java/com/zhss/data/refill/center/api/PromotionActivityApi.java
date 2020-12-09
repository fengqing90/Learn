package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.PromotionActivity;

/**
 * 优惠活动service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/activity/promotion")  
public interface PromotionActivityApi {

	/**
	 * 查询流量套餐绑定的状态处于"进行中"的优惠活动
	 * @return 优惠活动
	 */
	@RequestMapping(value = "/{dataPackageId}", method = RequestMethod.GET)
	PromotionActivity queryByDataPackageId(@PathVariable("dataPackageId") Long dataPackageId); 
	
}
