package com.zhss.data.refill.center.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.PromotionActivityApi;
import com.zhss.data.refill.center.domain.PromotionActivity;
import com.zhss.data.refill.center.mapper.PromotionActivityMapper;

/**
 * 优惠活动service组件
 * @author zhonghuashishan
 *
 */
@RestController
public class PromotionActivityService implements PromotionActivityApi {
	
	/**
	 * 优惠活动mapper组件
	 */
	@Autowired
	private PromotionActivityMapper promotionActivityMapper;
	
	/**
	 * 查询流量套餐绑定的状态处于"进行中"的优惠活动
	 * @return 优惠活动
	 */
	public PromotionActivity queryByDataPackageId(
			@PathVariable("dataPackageId") Long dataPackageId) {
		return promotionActivityMapper.queryByDataPackageId(dataPackageId);
	}

}
