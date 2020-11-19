package com.zhss.data.refill.center.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 积分service组件
 * @author zhonghuashishan
 *
 */
@RequestMapping("/credit")
public interface CreditApi {

	/**
	 * 增加积分
	 * @param userAccountId 用户账号id
	 */
	@RequestMapping(value = "/{userAccountId}", method = RequestMethod.PUT)
	void increment(@PathVariable("userAccountId") Long userAccountId, 
			@RequestParam("updatedPoint") Double updatedPoint);
	
}
