package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 系统配置表
 * 
 * @author wangxiaodan
 */
public class UserChannel {
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 是否是基本渠道，生成用户就存在，true:不可修改和编辑 false/null:用户后来添加的渠道
	 */
	private Boolean base;
	/**
	 * 渠道码,唯一,uuid生成
	 */
	private String channelCode;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getBase() {
		return base;
	}

	public void setBase(Boolean base) {
		this.base = base;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
