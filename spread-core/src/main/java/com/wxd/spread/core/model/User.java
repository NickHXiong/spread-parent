package com.wxd.spread.core.model;

import java.util.Date;

import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * 系统配置表
 * 
 * @author wangxiaodan
 */
public class User {
	private Long id;
	private String openid;
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private int sex;
	private String country;
	private String province;
	private String city;
	private String language;
	private String headimgurl;
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	private Boolean subscribe;
	private Long subscribeTime;
	/**
	 * 微信分组
	 */
	private Integer groupid;
	/**
	 * 微信备注
	 */
	private String remark;
	/**
	 * 当前可用余额，单位分
	 */
	private Long balance;
	/**
	 * 待结算金额，单位分
	 */
	private Long waitBalance;
	/**
	 * 用户等级ID
	 */
	private Long userLevelId;
	/**
	 * 用户积分
	 */
	private Long integral;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Long getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	public Date getSubscribeDate(){
		if (subscribeTime != null) {
			return new Date(subscribeTime * 1000);
		}
		return null;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	public Long getWaitBalance() {
		return waitBalance;
	}
	public void setWaitBalance(Long waitBalance) {
		this.waitBalance = waitBalance;
	}

	public Long getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(Long userLevelId) {
		this.userLevelId = userLevelId;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
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
