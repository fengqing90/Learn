package com.zhss.data.refill.center.service;

import com.zhss.data.refill.center.domain.RefillRequest;

/**
 * 流量充值中心service组件
 * @author zhonghuashishan
 *
 */
public interface RefillDataCenterService {

	/**
	 * 完成流量充值
	 * @param refillRequest
	 */
	void finishRefillData(RefillRequest refillRequest);
	
}
