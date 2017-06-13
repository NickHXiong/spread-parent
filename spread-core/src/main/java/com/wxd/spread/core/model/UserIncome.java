package com.wxd.spread.core.model;

import java.util.Date;

import com.wxd.spread.core.utils.DateUtils;

/**
 * 用户收入实体信息
 * 
 * @author wangxiaodan
 */
public class UserIncome {
	private Long id;
	/**
	 * 用户标识
	 */
	private Long userId;
	/**
	 * 用户渠道标识
	 */
	private Long userChannelId;
	/**
	 * 对应的app表ID
	 */
	private Long appId;
	
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 对应的渠道ID
	 */
	private Long appChannelId;
	/**
	 * 关注用户的openid
	 */
	private String subOpenid;
	
	/**
	 * 关注用户的昵称
	 */
	private String subNickname;
	
	/**
	 * 金额，单位分
	 */
	private Long fee;
	
	/**
	 * 用户什么时候关注后来的收入
	 */
	private Long subscribeTime;
	
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

	public Long getUserChannelId() {
		return userChannelId;
	}

	public void setUserChannelId(Long userChannelId) {
		this.userChannelId = userChannelId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getAppChannelId() {
		return appChannelId;
	}

	public void setAppChannelId(Long appChannelId) {
		this.appChannelId = appChannelId;
	}


	public String getSubOpenid() {
		return subOpenid;
	}

	public void setSubOpenid(String subOpenid) {
		this.subOpenid = subOpenid;
	}

	public String getSubNickname() {
		return subNickname;
	}

	public void setSubNickname(String subNickname) {
		this.subNickname = subNickname;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}
	public Long getSubscribeTime() {
		return subscribeTime;
	}
	public Date getSubscribeDate() {
		if (subscribeTime != null) {
			return DateUtils.wechatTimestamp2Date(subscribeTime);
		}
		return null;
	}
	public void setSubscribeTime(Long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
