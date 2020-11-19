package com.zhss.data.refill.center.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhss.data.refill.center.api.ReliableMessageService;
import com.zhss.data.refill.center.mapper.DataRefillHistoryMapper;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_RELIABLE_MESSAGE)
public class ReliableMessageConsumer {
	
	@Autowired
	private ReliableMessageService reliableMessageService;
	@Autowired
	private DataRefillHistoryMapper dataRefillHistoryMapper;
 
	@RabbitHandler
	public void process(String message) {
		System.out.println("步骤6：BOSS服务消费了一条消息，message=" + message); 
		
		JSONObject messageJSONObject = JSONObject.parseObject(message);
		String dataRefillNo = messageJSONObject.getString("dataRefillNo");  
		
		try {
			// 取出来message中的一个dataRefillNo，就是每一次流量充值的请求都会有一个唯一的串号
			// 在这里你其实可以将这个串号插入数据库中，这个data_refill_no字段建立一个唯一索引
			// 每次在这里只要获取到一条消息，你就往库里尝试插入一条数据
			// 唯一索引就可以保证你的幂等性，如果你之前已经成功的充值了流量之后
			try {
				dataRefillHistoryMapper.create(dataRefillNo);
				System.out.println("为本次流量充值操作插入一条串号，dataRefillNo=" + dataRefillNo);
			} catch (Exception e) {
				Long messageId = messageJSONObject.getLong("_messageId"); 
				reliableMessageService.finishMessage(messageId);
				System.out.println("该流量充值操作已经执行成功，只需再次通知可靠消息服务即可，dataRefillNo=" + dataRefillNo);
				e.printStackTrace(); 
				return;
			}
			
			// 第7步：其实就应该去调用第三方运营商BOSS系统的接口，发送请求，等待结果和确认结果
			System.out.println("步骤7：为手机号" + messageJSONObject.getString("phoneNumber") + "充值" + messageJSONObject.getLongValue("data") + "MB流量");   
			Boolean dataRefillResult = true;
			
			if(dataRefillResult) {
				// 我们这里就可以将手动ack确认步骤都省略掉
				// 因为即使你使用的是自动ack确认的机制也没关系
				// 因为我们的可靠消息服务会自动去扫描消息的状态，确保整个消息的一致性
				try {
					Long messageId = messageJSONObject.getLong("_messageId"); 
					reliableMessageService.finishMessage(messageId);
					System.out.println("步骤9：充值成功，通知可靠消息服务，messageId=" + messageId); 
				} catch (Exception e) {
					e.printStackTrace();  
				}
			} else {
				dataRefillHistoryMapper.remove(dataRefillNo); 
				System.out.println("充值失败，删除流量充值串号，dataRefillNo=" + dataRefillNo);  
			}
		} catch (Exception e) {
			dataRefillHistoryMapper.remove(dataRefillNo); 
			System.out.println("充值失败，删除流量充值串号，dataRefillNo=" + dataRefillNo);  
			e.printStackTrace();  
		}
	}

}