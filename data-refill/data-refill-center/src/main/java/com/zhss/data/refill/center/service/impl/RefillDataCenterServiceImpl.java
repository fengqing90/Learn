package com.zhss.data.refill.center.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.service.AccountAmountService;
import com.zhss.data.refill.center.service.CouponService;
import com.zhss.data.refill.center.service.CreditService;
import com.zhss.data.refill.center.service.LotteryDrawService;
import com.zhss.data.refill.center.service.RefillDataCenterService;
import com.zhss.data.refill.center.service.RefillOrderService;
import com.zhss.data.refill.center.service.ThirdPartyBossService;

/**
 * 流量充值中心service组件
 * @author zhonghuashishan
 *
 */
@Service
@Transactional(transactionManager = "xatx", rollbackFor = Exception.class)
public class RefillDataCenterServiceImpl implements RefillDataCenterService {

	/**
	 * 流量券service组件
	 */
	@Autowired
	private CouponService couponService;
	/**
	 * 账号金额service组件
	 */
	@Autowired
	private AccountAmountService accountAmountService;
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
	 * 抽奖机会service组件
	 */
	@Autowired
	private LotteryDrawService lotteryDrawService;
	/**
	 * 积分service组件
	 */
	@Autowired
	private CreditService creditService;
	
	/**
	 * 完成流量充值
	 * @param refillRequest
	 */
	public void finishRefillData(RefillRequest refillRequest) {
		//=======================================//
		
		// 大家注意一下，这一大坨逻辑，其实都是在流量充值中心自己本地的数据库里在执行
		// 所以说，下面这一大坨逻辑，加了事务以后，任何一个地方，只要报错
		// 都会导致整个事务回滚，要么一起成功，要么一起失败
		
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
		
		//=======================================//
		
		// 下面的这个东西，可不是在自己本地的数据库执行一些逻辑
		// 调用外部的第三方系统的接口，在做一个非常的核心的一个逻辑
		// 如果下面这个调用成功了，那么就是皆大欢喜，上面的所有数据库的更新逻辑全部成功
		// 如果下面这个调用失败了，那么也没关系，如果失败了，一定会报错
		// 只要他抛出了异常，就会导致上面的所有本地数据库的操作全部回滚。。。
		
		// 调用第三方运营商的系统，完成流量充值
		thirdPartyBossService.refillData(refillRequest.getPhoneNumber(), 
				refillRequest.getData()); 
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
		refillOrder.setStatus(1); 
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
		coupon.setStatus(1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			coupon.setEndTime(sdf.parse("2019-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}   
		
		return coupon;
 	}
	
}
