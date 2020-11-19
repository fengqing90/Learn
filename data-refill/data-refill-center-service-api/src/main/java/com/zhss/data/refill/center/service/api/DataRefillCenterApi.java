package com.zhss.data.refill.center.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/dataRefillCenter/api")  
public interface DataRefillCenterApi {

	@RequestMapping(value = "/operation/status", method = RequestMethod.GET) 
	public Boolean queryOperationStatus(@RequestParam("message") String message);
	
}
