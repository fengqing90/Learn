package com.zhss.data.refill.center.domain;

import java.util.Date;

/**
 * 流量充值订单
 * @author zhonghuashishan
 *
 */
public class RefillOrder {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 用户账号id
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
	 * 支付金额
	 */
	private Double amount;
	/**
	 * 订单标题
	 */
	private String title;
	/**
	 * 订单分类
	 */
	private String type;
	/**
	 * 订单状态
	 */
	private Integer status;
	/**
	 * 支付方式类型
	 */
	private Integer payType;
	/**
	 * 充值说明
	 */
	private String refillComment;
	/**
	 * 充值手机号码
	 */
	private String refillPhoneNumber;
	/**
	 * 充值流量
	 */
	private Long refillData;
	/**
	 * 赠送积分
	 */
	private Double credit;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 修改时间
	 */
	private Date modifiedTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getRefillComment() {
		return refillComment;
	}
	public void setRefillComment(String refillComment) {
		this.refillComment = refillComment;
	}
	public String getRefillPhoneNumber() {
		return refillPhoneNumber;
	}
	public void setRefillPhoneNumber(String refillPhoneNumber) {
		this.refillPhoneNumber = refillPhoneNumber;
	}
	public Long getRefillData() {
		return refillData;
	}
	public void setRefillData(Long refillData) {
		this.refillData = refillData;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
}
