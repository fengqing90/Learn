package com.zhss.data.refill.center.api;

import java.util.List;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.RefillOrderService;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.mapper.RefillOrderMapper;

/**
 * 充值订单service组件
 * @author zhonghuashishan
 *
 */
@RestController
@Compensable(interfaceClass = RefillOrderApi.class, 
				confirmableKey = "refillOrderConfirmService", 
				cancellableKey = "refillOrderCancelService")
public class RefillOrderService implements RefillOrderApi, CompensableContextAware {

	/**
	 * 充值订单mapper组件
	 */
	@Autowired
	private RefillOrderMapper refillOrderMapper;
	
	private CompensableContext compensableContext;
	
	/**
	 * 增加一个充值订单
	 * @param refillOrder 充值订单
	 */
	@Transactional
	public void add(@RequestBody RefillOrder refillOrder) {
		refillOrder.setStatus(0); 
		refillOrderMapper.tryCreateRefillOrder(refillOrder); 
		this.compensableContext.setVariable("refillOrderId", refillOrder.getId());      
	}
	
	/**
	 * 查询所有的充值订单
	 * @return
	 */
	@Transactional
	public List<RefillOrder> queryAll(@PathVariable("userAccountId") Long userAccountId) {
		return refillOrderMapper.queryAll(userAccountId) ;
	}
	
	/**
	 * 查询充值订单
	 * @param id 充值订单id
	 * @return
	 */
	@Transactional
	public RefillOrder queryById(@PathVariable("id") Long id) {
		return refillOrderMapper.queryById(id);
	}

	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.compensableContext = aware;
	}
	
}
