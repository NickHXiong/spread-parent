package com.wxd.spread.core.model;

/**
 * 用户等级实体信息
 * 
 * @author wangxiaodan
 *
 */
public class UserLevel {
	private Long id;
	/**
	 * 等级数值
	 */
	private Integer level;
	/**
	 * 等级名称
	 */
	private String levelName;
	/**
	 * 所需积分
	 */
	private Long integral;
	/**
	 * 得到渠道金额的百分比，例如：50，就是得到渠道金额的50%
	 */
	private Integer percentage;
	/**
	 * 是否可以存在多个用户渠道 true:可以 false/null:不可以
	 */
	private Boolean multiChannel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public Boolean getMultiChannel() {
		return multiChannel;
	}

	public void setMultiChannel(Boolean multiChannel) {
		this.multiChannel = multiChannel;
	}

}
