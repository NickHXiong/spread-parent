package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 系统模板信息（当用户发送指定消息或事件后发送消息给用户）
 * 
 * @author wangxiaodan
 */
public class SysTemplateMessage {
	private Long id;
	/**
	 * 系统模板消息的键
	 */
	private String templateKey;
	/**
	 * 模板标题
	 */
	private String title;
	/**
	 * 模板内容
	 */
	private String content;
	/**
	 * 是否是微信模板 true:微信模板 false/null:用户事件回复消息
	 */
	private Boolean template;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * true:禁用 false/null:正常（默认）
	 */
	private Boolean disabled;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateKey() {
		return templateKey;
	}
	
	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getTemplate() {
		return template;
	}

	public void setTemplate(Boolean template) {
		this.template = template;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
