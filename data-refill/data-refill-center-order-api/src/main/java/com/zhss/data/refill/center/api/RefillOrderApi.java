package com.zhss.data.refill.center.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.RefillOrder;

/**
 * 充值订单service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/refillOrder")  
public interface RefillOrderApi {

	/**
	 * 增加一个充值订单
	 * @param refillOrder 充值订单
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	void add(@RequestBody RefillOrder refillOrder);
	
	/**
	 * 查询所有的充值订单
	 * @return
	 */
	@RequestMapping(value = "/{userAccountId}", method = RequestMethod.GET)
	List<RefillOrder> queryAll(@PathVariable("userAccountId") Long userAccountId);
	
	/**
	 * 查询充值订单
	 * @param id 充值订单id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	RefillOrder queryById(@PathVariable("id") Long id);
	
}
