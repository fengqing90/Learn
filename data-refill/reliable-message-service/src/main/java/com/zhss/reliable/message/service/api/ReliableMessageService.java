package com.zhss.reliable.message.service.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.reliable.message.service.constant.MessageStatus;
import com.zhss.reliable.message.service.domain.Message;
import com.zhss.reliable.message.service.mapper.MessageMapper;
import com.zhss.reliable.message.service.rabbitmq.MessageProducer;

@RestController
@Transactional
public class ReliableMessageService implements ReliableMessageApi {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageProducer messageProducer;
	
	@Override
	public Long prepareMessage(@RequestParam("message") String message) {
		Message reliableMessage = new Message();
		reliableMessage.setContent(message); 
		reliableMessage.setStatus(MessageStatus.PREPARED); 
		reliableMessage.setCreatedTime(new Date());  
		
		messageMapper.create(reliableMessage);
		
		return reliableMessage.getId(); 
	}
	
	@Override
	public Boolean confirmMessage(@RequestParam("message") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.CONFIRMED); 
		reliableMessage.setConfirmedTime(new Date());  
		
		messageMapper.confirm(reliableMessage); 
		messageProducer.send(reliableMessage.getContent());  
		
		return true;
	}

	@Override
	public Boolean removeMessage(@RequestParam("message") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.REMOVED); 
		reliableMessage.setRemovedTime(new Date());  
		
		messageMapper.remove(reliableMessage); 
		
		return true;
	}

	@Override
	public Boolean finishMessage(@RequestParam("message") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.FINISHED); 
		reliableMessage.setFinishedTime(new Date());  
		
		messageMapper.finish(reliableMessage); 
		
		return true;
	}

}
