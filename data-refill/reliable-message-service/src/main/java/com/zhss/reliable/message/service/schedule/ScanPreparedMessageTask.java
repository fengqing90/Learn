package com.zhss.reliable.message.service.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.reliable.message.service.api.DataRefillCenterService;
import com.zhss.reliable.message.service.constant.MessageStatus;
import com.zhss.reliable.message.service.domain.Message;
import com.zhss.reliable.message.service.mapper.MessageMapper;
import com.zhss.reliable.message.service.rabbitmq.MessageProducer;

@Component
public class ScanPreparedMessageTask {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private DataRefillCenterService dataRefillCenterService;
	@Autowired
	private MessageProducer messageProducer;
	
	@Scheduled(fixedRate = 10 * 60 * 1000)
	public void execute() {
		List<Message> messages = messageMapper.findPrepared();
		
		for(Message message : messages) {
			try {
				checkPreparedMessage(message); 
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
	}
	
	@Transactional
	private void checkPreparedMessage(Message message) {
		long createdTime = message.getCreatedTime().getTime();
		long currentTime = new Date().getTime();
		
		if(currentTime - createdTime > 10 * 60 * 1000) {
			Boolean operationStatus = dataRefillCenterService.queryOperationStatus(
					message.getContent()); 
			
			if(operationStatus) {
				message.setStatus(MessageStatus.CONFIRMED); 
				message.setConfirmedTime(new Date());  
				
				messageMapper.confirm(message); 
				messageProducer.send(message.getContent());  
			} else {
				message.setStatus(MessageStatus.REMOVED); 
				message.setRemovedTime(new Date());  
				
				messageMapper.remove(message); 
			}
		}
	}
	
}
