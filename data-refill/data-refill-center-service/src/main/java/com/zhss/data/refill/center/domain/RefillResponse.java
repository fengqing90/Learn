package com.zhss.data.refill.center.domain;

/**
 * 充值结果
 * @author zhonghuashishan
 *
 */
public class RefillResponse {

	/**
	 * 充值结果编号：SUCCESS、FAILURE
	 */
	private String code;
	/**
	 * 充值结果说明
	 */
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
