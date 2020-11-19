package com.zhss.data.refill.center.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.data.refill.center.api.AccountAmountService;
import com.zhss.data.refill.center.api.CouponActivityService;
import com.zhss.data.refill.center.api.CouponService;
import com.zhss.data.refill.center.api.CreditService;
import com.zhss.data.refill.center.api.DataPackageService;
import com.zhss.data.refill.center.api.LotteryDrawService;
import com.zhss.data.refill.center.api.PromotionActivityService;
import com.zhss.data.refill.center.api.RefillOrderService;
import com.zhss.data.refill.center.api.ReliableMessageService;
import com.zhss.data.refill.center.domain.Coupon;
import com.zhss.data.refill.center.domain.CouponActivity;
import com.zhss.data.refill.center.domain.DataPackage;
import com.zhss.data.refill.center.domain.PromotionActivity;
import com.zhss.data.refill.center.domain.RefillOrder;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.domain.RefillResponse;
import com.zhss.data.refill.center.mapper.DataRefillHistoryMapper;
import com.zhss.data.refill.center.service.RefillDataCenterService;

/**
 * 流量充值controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/dataRefillCenter")
@Compensable(interfaceClass = RefillDataCenterService.class, simplified = true)
public class DataRefillCenterController 
		implements RefillDataCenterService, CompensableContextAware {
	
	private CompensableContext compensableContext; 

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
	 * 可靠消息服务
	 */
	@Autowired
	private ReliableMessageService reliableMessageService;
	/**
	 * 账号金额service组件
	 */
	@Autowired
	private AccountAmountService accountAmountService;
	@Autowired
	private DataRefillHistoryMapper dataRefillHistoryMapper;
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
		
		this.compensableContext.setVariable("dataRefillNo", UUID.randomUUID().toString());  
		
		return refillResponse;
	}
	
	@CompensableConfirm
	@Transactional
	public RefillResponse confirmFinishRefillData(@RequestBody RefillRequest refillRequest) {
		System.out.println(new Date() + ": confirm流量充值接口");  
		
		RefillResponse refillResponse = new RefillResponse();
		refillResponse.setCode("SUCCESS");
		refillResponse.setMessage("流量充值成功");
		
//		thirdPartyBossService.refillData(refillRequest.getPhoneNumber(), 
//		refillRequest.getData()); 
//		messageService.send(refillRequest.getPhoneNumber(), "流量已经充值成功"); 
		
		// 我们要在这里去调用第三方运营商的BOSS系统，所以在这里就可以使用可靠消息最终一致性的方案
		// 通过跟可靠消息服务来进行交互，以达到最终一定会成功通知到运营商BOSS系统完成流量充值的事情
		String dataRefillNo = (String) this.compensableContext.getVariable("dataRefillNo"); 
		
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("dataRefillNo", dataRefillNo);
		messageMap.put("phoneNumber", refillRequest.getPhoneNumber()); 
		messageMap.put("data", refillRequest.getData()); 
		
		String message = JSONObject.toJSONString(messageMap);
		
		Long messageId = reliableMessageService.prepareMessage(message);
		System.out.println("步骤1：流量充值服务发送待确认消息，message=" + message + ", messageId=" + messageId); 
		
		try {
			// 模拟一下：这里流量充值服务其实也可以操作自己的本地数据实现一些业务逻辑
			// 假设这里是成功的 
			// 假设一下，可以插入数据库一条数据，里面包含了那个关键性的dataRefillNo，就是一次流量充值的串号
			dataRefillHistoryMapper.create(dataRefillNo);  
			System.out.println("步骤3，流量充值服务操作本地数据库，dataRefillNo=" + dataRefillNo);  
			
			try {
				reliableMessageService.confirmMessage(messageId);
				System.out.println("步骤4，流量充值服务通知可靠消息服务确认消息，messageId=" + messageId);   
			} catch (Exception e) {
				System.out.println("流量充值服务通知可靠消息服务确认消息的时候报错了");  
				e.printStackTrace();
			}
		} catch (Exception e) {
			reliableMessageService.removeMessage(messageId);
			System.out.println("步骤4，流量充值服务通知可靠消息服务删除消息，messageId=" + messageId);   
			e.printStackTrace(); 
		}
		
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

	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.compensableContext = aware;
	}

}
