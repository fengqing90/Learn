package com.zhss.data.refill.center.domain;

import java.util.Date;

/**
 * 流量套餐
 * @author zhonghuashishan
 *
 */
public class DataPackage {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 流量套餐的售价
	 */
	private Double price;
	/**
	 * 流量的总额
	 */
	private Long data;
	/**
	 * 流量套餐的类型
	 */
	private Integer type;
	/**
	 * 流量套餐的说明
	 */
	private String comment;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 修改时间
	 */
	private Date modifiedTime;
	/**
	 * 优惠活动
	 */
	private PromotionActivity promotionActivity;
	/**
	 * 流量券活动
	 */
	private CouponActivity couponActivity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getData() {
		return data;
	}
	public void setData(Long data) {
		this.data = data;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public PromotionActivity getPromotionActivity() {
		return promotionActivity;
	}
	public void setPromotionActivity(PromotionActivity promotionActivity) {
		this.promotionActivity = promotionActivity;
	}
	public CouponActivity getCouponActivity() {
		return couponActivity;
	}
	public void setCouponActivity(CouponActivity couponActivity) {
		this.couponActivity = couponActivity;
	}
	
}
