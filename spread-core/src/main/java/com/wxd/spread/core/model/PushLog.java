package com.wxd.spread.core.model;

import java.util.Date;

import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * 推送日志实体信息（公众号方回调后，通过比对这个表中的时间来分成渠道）
 * @author wangxiaodan
 */
public class PushLog {
	private Long id;
	/**
	 * 要关注用户的微信openid
	 */
	private String openid;
	private Long userId;
	/**
	 * 推广人的昵称
	 */
	private String nickname; 
	private Long userChannelId;
	private Long appId;
	private String appName;
	private Long appChannelId;
	/**
	 * 重要字段
	 * 是否订阅成功，默认false，如果有回调，且回调的时间和这个时间距离最近，同时此字段为false/null，则设置true，并给用户分成
	 */
	private Boolean success;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return ToStringUtils.toSimpleString(this);
	}
	
}
