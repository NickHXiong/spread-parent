package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 系统配置表
 * 
 * @author wangxiaodan
 *
 */
public class App {
	private Long id;
	/**
	 * 微信公众号appid
	 */
	private String appid;
	/**
	 * 产品名称(公众号名称)
	 */
	private String appName;
	/**
	 * 回调地址白名单，多个用英文逗号分隔
	 */
	private String whiteInvokeUrls;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 优先级，用于本系统渠道推广时的顺序，越大优先级越高，默认50
	 */
	private Integer priority;
	/**
	 * true/null:禁用 false:启用
	 */
	private Boolean disabled;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getWhiteInvokeUrls() {
		return whiteInvokeUrls;
	}
	
	public void setWhiteInvokeUrls(String whiteInvokeUrls) {
		this.whiteInvokeUrls = whiteInvokeUrls;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
