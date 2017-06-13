package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 第三方系统回调表
 * 通过回调信息和pushLog中的信息比对来分成渠道
 * @author wangxiaodan
 *
 */
public class AppInvokeLog {
	private Long id;
	/**
	 * 对应的产品appid
	 */
	private String appid;
	/**
	 * 关注用户的微信openid
	 */
	private String openid;
	/**
	 * 渠道场景值
	 */
	private String sceneValue;
	/**
	 * 整型，产品方接受到事件创建的时间
	 * 重要，通过此字段和另外字段时间比对
	 */
	private Long invokeTime;
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSceneValue() {
		return sceneValue;
	}
	public void setSceneValue(String sceneValue) {
		this.sceneValue = sceneValue;
	}
	public Long getInvokeTime() {
		return invokeTime;
	}
	public Date getInvokeDate(){
		if (invokeTime != null) {
			return new Date(invokeTime * 1000);
		}
		return null;
	}
	public void setInvokeTime(Long invokeTime) {
		this.invokeTime = invokeTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
