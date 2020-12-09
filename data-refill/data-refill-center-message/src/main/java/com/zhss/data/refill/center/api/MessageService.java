package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.data.refill.center.message.api.MessageApi;

@RestController
public class MessageService implements MessageApi {

	@Override
	public Boolean send(
			@RequestParam("phoneNumber") String phoneNumber, 
			@RequestParam("phoneNumber") String content) {
		System.out.println("给" + phoneNumber + "发送了一条短信：" + content); 
		return true;
//		throw new IllegalStateException("error");
	}

}
