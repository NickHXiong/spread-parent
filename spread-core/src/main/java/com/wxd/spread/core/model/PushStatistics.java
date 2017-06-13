package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 推送统计信息（系统配置表中配置最高推送次数，推送了max次后还没有关注app，说明此用户已经关注，不在推送）
 * 
 * @author wangxiaodan
 *
 */
public class PushStatistics {
	private Long id;
	/**
	 * 产品ID
	 */
	private Long appId;
	/**
	 * 要扫码的用户的微信openid
	 */
	private String openid;
	/**
	 * 推送次数
	 */
	private long num;
	/**
	 * 最近一次推送时间，用于判断5分钟内部给此用户推送此产品
	 */
	private Date prePushTime;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public Date getPrePushTime() {
		return prePushTime;
	}

	public void setPrePushTime(Date prePushTime) {
		this.prePushTime = prePushTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
