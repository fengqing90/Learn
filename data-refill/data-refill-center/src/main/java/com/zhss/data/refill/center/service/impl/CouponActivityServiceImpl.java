package com.zhss.data.refill.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.mapper.activity.CouponActivityMapper;
import com.zhss.data.refill.center.service.CouponActivityService;

/**
 * 流量券活动service组件
 * @author zhonghuashishan
 *
 */
@Service
public class CouponActivityServiceImpl implements CouponActivityService {
	
	/**
	 * 流量券活动mapper组件
	 */
	@Autowired
	private CouponActivityMapper couponActivityMapper;
	
	/**
	 * 查询流量套餐绑定的状态处于"进行中"的流量券活动
	 * @return 流量券活动
	 */
	public CouponActivity queryByDataPackageId(Long dataPackageId) {
		return couponActivityMapper.queryByDataPackageId(dataPackageId);
	}

}
