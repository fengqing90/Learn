package com.zhss.data.refill.center.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.api.AccountAmountService;
import com.zhss.data.refill.center.api.CouponActivityService;
import com.zhss.data.refill.center.api.CouponService;
import com.zhss.data.refill.center.api.CreditService;
import com.zhss.data.refill.center.api.DataPackageService;
import com.zhss.data.refill.center.api.LotteryDrawService;
import com.zhss.data.refill.center.api.PromotionActivityService;
import com.zhss.data.refill.center.api.RefillOrderService;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.domain.DataPackage;
import com.zhss.data.refill.center.domain.PromotionActivity;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.domain.RefillResponse;
import com.zhss.data.refill.center.service.RefillDataCenterService;

@RestController
@RequestMapping("/dataRefillCenter")
@Compensable(interfaceClass = RefillDataCenterService.class, simplified = true)
public class DataRefillCenterController implements RefillDataCenterService {
	
	@Autowired
	private DataPackageService dataPackageService;
	@Autowired
	private PromotionActivityService promotionActivityService;
	@Autowired
	private CouponActivityService couponActivityService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private RefillOrderService refillOrderService;
	@Autowired
	private AccountAmountService accountAmountService;
	@Autowired
	private LotteryDrawService lotteryDrawService;
	@Autowired
	private CreditService creditService;
	
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
	
	@PutMapping("/finishRefillData")
	@Transactional
	public RefillResponse finishRefillData(@RequestBody RefillRequest refillRequest) {
		System.out.println(new Date() + ": try流量充值接口");  
		
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("SUCCESS");
		refillResponse.setMessage("流量充值成功");
		
		// 完成支付转账
		accountAmountService.transfer(refillRequest.getUserAccountId(), 
				refillRequest.getBusinessAccountId(), refillRequest.getPayAmount());  
		// 创建充值订单
		refillOrderService.add(createRefillOrder(refillRequest));  
		// 给用户增加一次抽奖机会
		lotteryDrawService.increment(refillRequest.getUserAccountId()); 
		// 给用户增加充值面值5%的积分
		creditService.increment(refillRequest.getUserAccountId(),
				(double)Math.round((refillRequest.getPayAmount() * 0.05) * 100) / 100); 
		// 如果使用了流量券的话，标记使用的流量券状态为已使用
		if(refillRequest.getCoupon() != null && refillRequest.getCoupon().getId() != null) {
			couponService.markCouponUsed(refillRequest.getCoupon().getId());  
		}
		// 如果要赠送流量券的话，就会插入一张流量券
		CouponActivity couponActivity = refillRequest.getDataPackage().getCouponActivity();
		if(couponActivity != null && couponActivity.getId() != null) {
			couponService.insert(createCoupon(refillRequest, couponActivity));  
		}
		
//		throw new IllegalStateException("rollback!");
		
		return refillResponse;
	}
	
	@CompensableConfirm
	@Transactional
	public RefillResponse confirmFinishRefillData(@RequestBody RefillRequest refillRequest) {
		System.out.println(new Date() + ": confirm流量充值接口");  
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("SUCCESS");
		refillResponse.setMessage("流量充值成功");
		return refillResponse;
	}
	
	@CompensableCancel
	@Transactional
	public RefillResponse cancelFinishRefillData(@RequestBody RefillRequest refillRequest) {
		System.out.println(new Date() + ": cancel流量充值接口");  
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("FAILURE");
		refillResponse.setMessage("流量充值失败");  
		return refillResponse;
	}
	
	/**
	 * 创建流量充值订单
	 * @param refillRequest
	 * @return
	 */
	private RefillOrder createRefillOrder(RefillRequest refillRequest) {
		RefillOrder refillOrder = new RefillOrder();
		refillOrder.setOrderNo(UUID.randomUUID().toString().replace("-", ""));  
		refillOrder.setUserAccountId(refillRequest.getUserAccountId()); 
		refillOrder.setBusinessAccountId(refillRequest.getBusinessAccountId()); 
		refillOrder.setBusinessName(refillRequest.getBusinessName()); 
		refillOrder.setAmount(refillRequest.getPayAmount()); 
		refillOrder.setTitle("手机流量充值");  
		refillOrder.setType("通讯物流");  
		refillOrder.setPayType(refillRequest.getPayType()); 
		refillOrder.setRefillComment("给手机号码" + refillRequest.getPhoneNumber() 
				+ "充值" + refillRequest.getData() + "MB流量");
		refillOrder.setRefillPhoneNumber(refillRequest.getPhoneNumber()); 
		refillOrder.setRefillData(refillRequest.getData()); 
		refillOrder.setCredit((double)Math.round((refillRequest.getPayAmount() * 0.05) * 100) / 100);
		return refillOrder;
	}
	
	/**
	 * 创建流量券实体对象
	 * @param refillRequest
	 * @param couponActivity
	 * @return
	 */
	private Coupon createCoupon(RefillRequest refillRequest, 
			CouponActivity couponActivity) {
		Coupon coupon = new Coupon();
		coupon.setUserAccountId(refillRequest.getUserAccountId()); 
		coupon.setCouponAmount(couponActivity.getCouponAmount()); 

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			coupon.setEndTime(sdf.parse("2019-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}   
		
		return coupon;
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
