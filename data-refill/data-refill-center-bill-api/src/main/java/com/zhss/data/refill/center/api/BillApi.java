package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.data.refill.center.domain.Bill;

@RequestMapping("/bill")  
public interface BillApi {

	@RequestMapping(value = "/", method = RequestMethod.POST)
	void add(@RequestBody Bill bill);

}
