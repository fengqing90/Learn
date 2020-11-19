package com.zhss.data.refill.center.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.zhss.data.refill.center.api.AccountAmountService;
import com.zhss.data.refill.center.api.CouponService;
import com.zhss.data.refill.center.api.CreditService;
import com.zhss.data.refill.center.api.LotteryDrawService;
import com.zhss.data.refill.center.api.RefillOrderService;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.domain.RefillResponse;
import com.zhss.data.refill.center.service.RefillDataCenterService;

@Service
@Compensable(interfaceClass = RefillDataCenterService.class, simplified = true)
public class RefillDataCenterServiceImpl implements RefillDataCenterService {

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
	 * 账号金额service组件
	 */
	@Autowired
	private AccountAmountService accountAmountService;
	/**
	 * 抽奖机会service组件
	 */
	@Autowired
	private LotteryDrawService lotteryDrawService;
	/**
	 * 积分service组件
	 */
	@Autowired
	private CreditService creditService;
	
	@Override
	@Transactional
	public RefillResponse finishRefillData(RefillRequest refillRequest) {
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
		return null;
	}
	
	@CompensableConfirm
	@Transactional
	public RefillResponse confirmFinishRefillData(@RequestBody RefillRequest refillRequest) {
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("SUCCESS");
		refillResponse.setMessage("流量充值成功");
		return refillResponse;
	}
	
	@CompensableCancel
	@Transactional
	public RefillResponse cancelFinishRefillData(@RequestBody RefillRequest refillRequest) {
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

}
