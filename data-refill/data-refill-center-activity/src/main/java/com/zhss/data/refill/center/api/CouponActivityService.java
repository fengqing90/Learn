package com.zhss.data.refill.center.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.CouponActivityApi;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.mapper.CouponActivityMapper;

/**
 * 流量券活动service组件
 * @author zhonghuashishan
 *
 */
@RestController
public class CouponActivityService implements CouponActivityApi {
	
	/**
	 * 流量券活动mapper组件
	 */
	@Autowired
	private CouponActivityMapper couponActivityMapper;
	
	/**
	 * 查询流量套餐绑定的状态处于"进行中"的流量券活动
	 * @return 流量券活动
	 */
	public CouponActivity queryByDataPackageId(
			@PathVariable("dataPackageId") Long dataPackageId) {
		return couponActivityMapper.queryByDataPackageId(dataPackageId);
	}

}
