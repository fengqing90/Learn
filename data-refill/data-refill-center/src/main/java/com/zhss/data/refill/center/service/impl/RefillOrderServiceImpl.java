package com.zhss.data.refill.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.mapper.order.RefillOrderMapper;
import com.zhss.data.refill.center.service.RefillOrderService;

/**
 * 充值订单service组件
 * @author zhonghuashishan
 *
 */
@Service
public class RefillOrderServiceImpl implements RefillOrderService {

	/**
	 * 充值订单mapper组件
	 */
	@Autowired
	private RefillOrderMapper refillOrderMapper;
	
	/**
	 * 增加一个充值订单
	 * @param refillOrder 充值订单
	 */
	public void add(RefillOrder refillOrder) {
		refillOrderMapper.add(refillOrder); 
	}
	
	/**
	 * 查询所有的充值订单
	 * @return
	 */
	public List<RefillOrder> queryAll(Long userAccountId) {
		return refillOrderMapper.queryAll(userAccountId) ;
	}
	
	/**
	 * 查询充值订单
	 * @param id 充值订单id
	 * @return
	 */
	public RefillOrder queryById(Long id) {
		return refillOrderMapper.queryById(id);
	}
	
}
