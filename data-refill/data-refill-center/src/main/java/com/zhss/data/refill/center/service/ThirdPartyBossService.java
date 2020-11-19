package com.zhss.data.refill.center.service;

/**
 * 访问第三方运营商BOSS系统service组件
 * @author zhonghuashishan
 *
 */
public interface ThirdPartyBossService {

	/**
	 * 充值流量
	 * @param phoneNumber 手机号
	 * @param data 流量
	 */
	void refillData(String phoneNumber, Long data);   
	
}
