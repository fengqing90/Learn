package com.zhss.eshop.schedule.api;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.schedule.constant.StockUpdateEvent;
import com.zhss.eshop.schedule.constant.TccType;
import com.zhss.eshop.schedule.curator.CuratorClient;
import com.zhss.eshop.schedule.dao.ScheduleOrderPickingItemDAO;
import com.zhss.eshop.schedule.dao.ScheduleOrderSendOutDetailDAO;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.domain.ScheduleOrderPickingItemDO;
import com.zhss.eshop.schedule.domain.ScheduleOrderSendOutDetailDO;
import com.zhss.eshop.schedule.mapper.UniqueRecordMapper;
import com.zhss.eshop.schedule.service.SaleDeliveryScheduler;
import com.zhss.eshop.schedule.stock.PayOrderScheduleStockUpdater;
import com.zhss.eshop.schedule.stock.ScheduleStockUpdaterFactory;
import com.zhss.eshop.schedule.stock.SubmitOrderScheduleStockUpdater;
import com.zhss.eshop.schedule.util.BeanConvertUtils;

@RestController
@Compensable(interfaceClass = ScheduleTccApi.class, 
				confirmableKey = "scheduleTccConfirmService", 
				cancellableKey = "scheduleTccCancelService")
public class ScheduleTccService implements ScheduleTccApi {

	/**
	 * 仓储服务
	 */
	@Autowired
	private WmsTccService wmsTccService;
	/**
	 * 销售出库调度器
	 */
	@Autowired
	private SaleDeliveryScheduler saleDeliveryScheduler;
	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private ScheduleStockUpdaterFactory stockUpdaterFactory;
	
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	/**
	 * 拣货条目管理DAO组件
	 */
	@Autowired
	private ScheduleOrderPickingItemDAO pickingItemDAO;
	/**
	 * 发货明细管理DAO组件
	 */
	@Autowired
	private ScheduleOrderSendOutDetailDAO sendOutDetailDAO;
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Transactional(rollbackFor = Exception.class)  
	public Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO order) throws Exception {
		uniqueRecordMapper.insert("ScheduleTccService_informSubmitOrderEvent_" + order.getId()); 
		
		for(OrderItemDTO orderItem : order.getOrderItems()) {
			CuratorFramework curatorClient = CuratorClient.getInstance();
			InterProcessLock lock = new InterProcessMutex(curatorClient, "/locks/schedule_stock_lock_" + orderItem.getGoodsSkuId());
			lock.acquire();
			
			// 这里会进行调度的计算
			SaleDeliveryScheduleResult scheduleResult = 
					saleDeliveryScheduler.schedule(orderItem);
			
			// 根据调度的计算结果反过来去锁定对应的货位库存
			SubmitOrderScheduleStockUpdater stockUpdater = (SubmitOrderScheduleStockUpdater) 
					stockUpdaterFactory.create(StockUpdateEvent.SUBMIT_ORDER, scheduleResult);
			stockUpdater.setTccType(TccType.TRY);  
			stockUpdater.update();
			
			// 通知wms中心锁定库存
			wmsTccService.informSubmitOrderEvent(
					BeanConvertUtils.convertSaleDeliveryScheduleResult(scheduleResult)); 
			
			lock.release();
		}
		
		return true;
	}
	
	@Transactional(rollbackFor = Exception.class)  
	public Boolean informPayOrderEvent(@RequestBody OrderInfoDTO order) throws Exception {
		uniqueRecordMapper.insert("ScheduleTccService_informPayOrderEvent_" + order.getId()); 
		
		for(OrderItemDTO orderItem : order.getOrderItems()) {
			SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
					.getScheduleResult(orderItem);
			
			List<ScheduleOrderPickingItemDO> pickingItems = ObjectUtils.convertList(
					scheduleResult.getPickingItems(), ScheduleOrderPickingItemDO.class);
			List<ScheduleOrderSendOutDetailDO> sendOutDetails = ObjectUtils.convertList(
					scheduleResult.getSendOutDetails(), ScheduleOrderSendOutDetailDO.class);
			
			pickingItemDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), pickingItems); 
			sendOutDetailDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), sendOutDetails);
			
			PayOrderScheduleStockUpdater stockUpdater = (PayOrderScheduleStockUpdater)
					stockUpdaterFactory.create(StockUpdateEvent.PAY_ORDER, scheduleResult);
			stockUpdater.setTccType(TccType.TRY);  
			stockUpdater.update();
			
			wmsTccService.informPayOrderEvent(
					BeanConvertUtils.convertSaleDeliveryScheduleResult(scheduleResult));
		}
		
		return true;
	}
	
}
