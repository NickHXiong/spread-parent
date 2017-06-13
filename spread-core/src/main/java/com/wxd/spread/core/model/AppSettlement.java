package com.wxd.spread.core.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 第三方产品结算实体信息
 * 
 * @author wangxiaodan
 */
public class AppSettlement {
	private Long id;
	/**
	 * 产品实体ID
	 */
	private Long appId;
	/**
	 * 多个渠道英文逗号分隔
	 */
	private Long appChannelId;
	/**
	 * 结算金额，单位分
	 */
	private Long amountFee;
	/**
	 * 结算开始日期（包含当天的，yyyy-MM-dd）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	private Date dateFrom;
	/**
	 * 结算结束日期（包含当天的，yyyy-MM-dd）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date DateEnd;
	/**
	 * 结算描述
	 */
	private String describe;
	/**
	 * 管理员ID
	 */
	private Long adminId;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getAppChannelId() {
		return appChannelId;
	}
	
	public void setAppChannelId(Long appChannelId) {
		this.appChannelId = appChannelId;
	}

	public Long getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(Long amountFee) {
		this.amountFee = amountFee;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateEnd() {
		return DateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		DateEnd = dateEnd;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
