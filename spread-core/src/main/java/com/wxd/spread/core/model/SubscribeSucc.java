package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 订阅记录实体信息（在此表中的记录都不在推送给用户二维码）
 * 
 * @author wangxiaodan
 *
 */
public class SubscribeSucc {
	private Long id;
	/**
	 * 微信订阅用户的微信openid
	 */
	private String openid;
	/**
	 * 订阅的产品ID
	 */
	private Long appId;
	
	/**
	 * 产品的微信appid
	 */
	private String appid;
	/**
	 * true:订阅成功 false/null:未订阅
	 */
	private Boolean success;
	/**
	 * true:推送超限（猜测此微信用户已经订阅此公众号） false/null:未超限
	 */
	private Boolean pushLimit;
	/**
	 * 其他描述
	 */
	private String describe;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean getPushLimit() {
		return pushLimit;
	}

	public void setPushLimit(Boolean pushLimit) {
		this.pushLimit = pushLimit;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
