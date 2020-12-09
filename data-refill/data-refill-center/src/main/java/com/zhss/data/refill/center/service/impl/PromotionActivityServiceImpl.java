package com.zhss.data.refill.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.domain.PromotionActivity;
import com.zhss.data.refill.center.mapper.activity.PromotionActivityMapper;
import com.zhss.data.refill.center.service.PromotionActivityService;

/**
 * 优惠活动service组件
 * @author zhonghuashishan
 *
 */
@Service
public class PromotionActivityServiceImpl implements PromotionActivityService {
	
	/**
	 * 优惠活动mapper组件
	 */
	@Autowired
	private PromotionActivityMapper promotionActivityMapper;
	
	/**
	 * 查询流量套餐绑定的状态处于"进行中"的优惠活动
	 * @return 优惠活动
	 */
	public PromotionActivity queryByDataPackageId(Long dataPackageId) {
		return promotionActivityMapper.queryByDataPackageId(dataPackageId);
	}

}
