package com.zhss.data.refill.center.domain;

import java.util.Date;

/**
 * 优惠活动
 * @author zhonghuashishan
 *
 */
public class PromotionActivity {

	/**
	 * 优惠活动id
	 */
	private Long id;
	/**
	 * 流量套餐id
	 */
	private Long dataPackageId;
	/**
	 * 优惠价格
	 */
	private Double discountPrice;
	/**
	 * 优惠活动的开始时间
	 */
	private Date startTime;
	/**
	 * 优惠活动的结束时间
	 */
	private Date endTime;
	/**
	 * 优惠活动的状态
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
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
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
