package com.zhss.data.refill.center.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.CouponActivityService;
import com.zhss.data.refill.center.api.CouponService;
import com.zhss.data.refill.center.api.DataPackageService;
import com.zhss.data.refill.center.api.PromotionActivityService;
import com.zhss.data.refill.center.api.RefillOrderService;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.domain.DataPackage;
import com.zhss.data.refill.center.domain.PromotionActivity;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.domain.RefillResponse;
import com.zhss.data.refill.center.service.MessageService;
import com.zhss.data.refill.center.service.RefillDataCenterService;
import com.zhss.data.refill.center.service.ThirdPartyBossService;

/**
 * 流量充值controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/dataRefillCenter")
public class DataRefillCenterController implements RefillDataCenterService {

	/**
	 * 流量套餐service组件
	 */
	@Autowired
	private DataPackageService dataPackageService;
	/**
	 * 优惠活动service组件
	 */
	@Autowired
	private PromotionActivityService promotionActivityService;
	/**
	 * 流量券活动service组件
	 */
	@Autowired
	private CouponActivityService couponActivityService;
	/**
	 * 流量券service组件
	 */
	@Autowired
	private CouponService couponService;
	/**
	 * 充值订单service组件
	 */
	@Autowired
	private RefillOrderService refillOrderService;
	/**
	 * 第三方运营商BOSS系统访问service组件
	 */
	@Autowired
	private ThirdPartyBossService thirdPartyBossService;
	/**
	 * 消息服务service组件
	 */
	@Autowired
	private MessageService messageService;
	@Autowired
	private RefillDataCenterService dataRefillCenterService;
	
	/**
	 * 查询所有的流量套餐
	 * @return
	 */
	@GetMapping("/dataPackages")  
	public List<DataPackage> queryAllDataPackage() {
		List<DataPackage> dataPackages = new ArrayList<DataPackage>();
		
		try {
			dataPackages = dataPackageService.queryAll();
			
			if(dataPackages != null && dataPackages.size() > 0) {
				for(DataPackage dataPackage : dataPackages) {
					PromotionActivity promotionActivity = promotionActivityService
							.queryByDataPackageId(dataPackage.getId());
					dataPackage.setPromotionActivity(promotionActivity); 
					
					CouponActivity couponActivity = couponActivityService
							.queryByDataPackageId(dataPackage.getId());
					dataPackage.setCouponActivity(couponActivity); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return dataPackages;
	}
	
	/**
	 * 查询用户账号的面额最高的流量券
	 * @param userAccountId 用户账号id
	 * @return 流量券
	 */
	@GetMapping("/coupon/{userAccountId}")  
	public Coupon queryCoupon(
			@PathVariable("userAccountId") Long userAccountId) {
		try {
			Coupon coupon = couponService.queryByUserAccountId(userAccountId);
			if(coupon != null) {
				return coupon;
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		
		return new Coupon();  
	}
	
	/**
	 * 为流量充值来进行支付
	 * @param refillRequest 充值请求
	 * @return 充值响应
	 */
	@PutMapping("/finishRefillData")
	@Transactional
	public RefillResponse finishRefillData(@RequestBody RefillRequest refillRequest) {
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("SUCCESS");
		refillResponse.setMessage("流量充值成功");
		
		try {
			dataRefillCenterService.finishRefillData(refillRequest);
			thirdPartyBossService.refillData(refillRequest.getPhoneNumber(), 
					refillRequest.getData()); 
			messageService.send(refillRequest.getPhoneNumber(), "流量已经充值成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			refillResponse.setCode("FAILURE");
			refillResponse.setMessage("流量充值失败");  
		}
		
		return refillResponse;
	}
	
	/**
	 * 查询用户的所有充值订单
	 * @param userAccountId 用户账号id
	 * @return
	 */
	@GetMapping("/refillOrders/{userAccountId}")  
	public List<RefillOrder> queryAllRefillOrders(
			@PathVariable("userAccountId") Long userAccountId) {
		return refillOrderService.queryAll(userAccountId);
	}
	
	/**
	 * 查询充值订单
	 * @param id 订单id
	 * @return
	 */
	@GetMapping("/refillOrder/{id}")  
	public RefillOrder queryRefillOrder(
			@PathVariable("id") Long id) {
		return refillOrderService.queryById(id);
	}

}
