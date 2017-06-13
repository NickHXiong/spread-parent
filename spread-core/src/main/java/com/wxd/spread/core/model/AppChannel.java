package com.wxd.spread.core.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.wxd.spread.core.utils.DateUtils;

/**
 * 产品渠道实体信息（需要推广的微信的不同场景渠道）
 * 
 * @author wangxiaodan
 */
public class AppChannel {
	private Long id;
	/**
	 * 产品实体ID
	 */
	private Long appId;
	/**
	 * 设置的二维码渠道值
	 * 可以有多个渠道有相同的sceneValue值，但是相同的sceneValue值中最多只能有一个是启用状态
	 */
	private String sceneValue;
	/**
	 * 二维码图片解析后的地址,可根据此url生成自己的二维码样式
	 */
	private String channelUrl;
	/**
	 * 获取的二维码ticket
	 */
	private String ticket;
	/**
	 * 推广价格，单位分
	 */
	private Long price;
	/**
	 * 系统用户各渠道的百分比，整数，不超过100，默认80
	 */
	private Integer percentage;
	/**
	 * 产品内的优先级，用于本系统渠道推广时的顺序，越大优先级越高，默认50
	 */
	private Integer priority;
	/**
	 * 过期时间，如果null，标识永不过期，过期后不生成二维码
	 */
	private Date expireDate;
	/**
	 * 最多推广关注人数，0/null表示无上限，超过此人数不生成二维码
	 */
	private Long maxNum;
	/**
	 * 性别过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔
	 */
	private String filterSex;
	/**
	 * 城市过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔
	 */
	private String filterCity;
	/**
	 * 国家过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔
	 */
	private String filterCountry;
	/**
	 * 省份过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔
	 */
	private String filterProvince;
	/**
	 * 语言过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔
	 */
	private String filterLanguage;
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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getSceneValue() {
		return sceneValue;
	}

	public void setSceneValue(String sceneValue) {
		this.sceneValue = sceneValue;
	}

	public String getChannelUrl() {
		return channelUrl;
	}

	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public void setExpireDateStr(String expireDateStr) {
		this.expireDate = DateUtils.format(expireDateStr, DateUtils.DATE);
	}

	public Long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Long maxNum) {
		this.maxNum = maxNum;
	}

	public String getFilterSex() {
		return filterSex;
	}

	public void setFilterSex(String filterSex) {
		this.filterSex = filterSex;
	}
	
	public String getFilterCity() {
		return filterCity;
	}

	public void setFilterCity(String filterCity) {
		this.filterCity = filterCity;
	}

	public String getFilterCountry() {
		return filterCountry;
	}

	public void setFilterCountry(String filterCountry) {
		this.filterCountry = filterCountry;
	}

	public String getFilterProvince() {
		return filterProvince;
	}

	public void setFilterProvince(String filterProvince) {
		this.filterProvince = filterProvince;
	}

	public String getFilterLanguage() {
		return filterLanguage;
	}

	public void setFilterLanguage(String filterLanguage) {
		this.filterLanguage = filterLanguage;
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
