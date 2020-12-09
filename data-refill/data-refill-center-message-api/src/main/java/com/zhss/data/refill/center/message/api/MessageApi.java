package com.zhss.data.refill.center.message.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/messagee") 
public interface MessageApi {

	@RequestMapping(value = "/send", method = RequestMethod.POST)  
	public Boolean send(
			@RequestParam("phoneNumber") String phoneNumber, 
			@RequestParam("content") String content);
	
}
