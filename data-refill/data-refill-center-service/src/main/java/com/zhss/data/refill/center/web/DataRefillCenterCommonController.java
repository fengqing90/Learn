package com.zhss.data.refill.center.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.data.refill.center.api.ReliableMessageService;
import com.zhss.data.refill.center.domain.RefillRequest;
import com.zhss.data.refill.center.mapper.DataRefillHistoryMapper;
import com.zhss.data.refill.center.rabbitmq.MessageProducer;

@RestController
@RequestMapping("/dataRefillCenter") 
public class DataRefillCenterCommonController {

	@Autowired
	private ReliableMessageService reliableMessageService;
	@Autowired
	private DataRefillHistoryMapper dataRefillHistoryMapper;
	@Autowired
	private MessageProducer messageProducer;
	
	@PutMapping("/informBossSystem") 
	public String informBossSystem(@RequestBody RefillRequest refillRequest) {
		// 我们要在这里去调用第三方运营商的BOSS系统，所以在这里就可以使用可靠消息最终一致性的方案
		// 通过跟可靠消息服务来进行交互，以达到最终一定会成功通知到运营商BOSS系统完成流量充值的事情
		Long messageId = null;
		
		try {
			String dataRefillNo = UUID.randomUUID().toString();
			
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("dataRefillNo", dataRefillNo);
			messageMap.put("phoneNumber", refillRequest.getPhoneNumber()); 
			messageMap.put("data", refillRequest.getData()); 
			
			String message = JSONObject.toJSONString(messageMap);
			
			messageId = reliableMessageService.prepareMessage(message);
			System.out.println("步骤1：流量充值服务发送待确认消息，message=" + message + ", messageId=" + messageId); 
			
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
			return "FAILURE";
		}
		return "SUCCESS";
	}
	
	@PutMapping("/sendMessage") 
	public String sendMessage(@RequestBody RefillRequest refillRequest) { 
		// 使用最大努力通知方案来通知消息服务发送短信
		try {
			JSONObject retryRule = new JSONObject();
			retryRule.put("type", 1);
			retryRule.put("retryInterval", 10);
			retryRule.put("maxRetryCount", 3);
			
			JSONObject message = new JSONObject();
			message.put("phoneNumber", refillRequest.getPhoneNumber());
			message.put("content", "已经为您的手机号" + refillRequest.getPhoneNumber() + "充值了" + refillRequest.getData() + "MB流量");
			message.put("retryRule", retryRule); 
			
			messageProducer.send(message.toJSONString());  
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILURE";
		}
		return "SUCCESS";
	}
	
}
