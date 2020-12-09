package com.zhss.data.refill.center.domain;

/**
 * 充值请求
 * @author zhonghuashishan
 *
 */
public class RefillRequest {

	/**
	 * 充值的用户账号id
	 */
	private Long userAccountId;
	/**
	 * 商户账号id
	 */
	private Long businessAccountId;
	/**
	 * 商户名称
	 */
	private String businessName;
	/**
	 * 实际支付的金额
	 */
	private Double payAmount;
	/**
	 * 支付类型
	 */
	private Integer payType;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 流量
	 */
	private Long data;
	/**
	 * 流量套餐
	 */
	private DataPackage dataPackage;
	/**
	 * 使用的流量券
	 */
	private Coupon coupon;
	
	public Long getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	public Long getBusinessAccountId() {
		return businessAccountId;
	}
	public void setBusinessAccountId(Long businessAccountId) {
		this.businessAccountId = businessAccountId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getData() {
		return data;
	}
	public void setData(Long data) {
		this.data = data;
	}
	public DataPackage getDataPackage() {
		return dataPackage;
	}
	public void setDataPackage(DataPackage dataPackage) {
		this.dataPackage = dataPackage;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
}
