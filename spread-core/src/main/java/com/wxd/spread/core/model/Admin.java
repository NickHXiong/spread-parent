package com.wxd.spread.core.model;

import java.util.Date;

import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * 管理员用户实体信息
 * 
 * @author wangxiaodan
 */
public class Admin {
	private Long id;
	/**
	 * 管理员昵称
	 */
	private String nickname;
	/**
	 * 真实姓名
	 */
	private String trueName;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 账号
	 */
	private String account;
	
	private boolean superAdmin;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * true:用户禁用 false/null:用户启用
	 */
	private Boolean disabled;
	/**
	 * 禁用/启用时间
	 */
	private Date disabledTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Date getDisabledTime() {
		return disabledTime;
	}

	public void setDisabledTime(Date disabledTime) {
		this.disabledTime = disabledTime;
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
