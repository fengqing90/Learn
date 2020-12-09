package com.zhss.data.refill.center.service;

import java.util.List;

import com.zhss.data.refill.center.domain.RefillOrder;

/**
 * 充值订单service组件
 * @author zhonghuashishan
 *
 */
public interface RefillOrderService {

	/**
	 * 增加一个充值订单
	 * @param refillOrder 充值订单
	 */
	void add(RefillOrder refillOrder);
	
	/**
	 * 查询所有的充值订单
	 * @return
	 */
	List<RefillOrder> queryAll(Long userAccountId);
	
	/**
	 * 查询充值订单
	 * @param id 充值订单id
	 * @return
	 */
	RefillOrder queryById(Long id);
	
}
