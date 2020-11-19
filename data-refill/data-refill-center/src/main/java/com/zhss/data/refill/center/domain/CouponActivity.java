package com.zhss.data.refill.center.domain;

import java.util.Date;

/**
 * 流量券活动
 * @author zhonghuashishan
 *
 */
public class CouponActivity {

	/**
	 * 流量券活动id
	 */
	private Long id;
	/**
	 * 流量套餐id
	 */
	private Long dataPackageId;
	/**
	 * 流量券金额
	 */
	private Double couponAmount;
	/**
	 * 流量券活动的开始时间
	 */
	private Date startTime;
	/**
	 * 流量券活动的结束时间
	 */
	private Date endTime;
	/**
	 * 流量券活动的状态
	 */
	private Integer status;
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
	public Long getDataPackageId() {
		return dataPackageId;
	}
	public void setDataPackageId(Long dataPackageId) {
		this.dataPackageId = dataPackageId;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
