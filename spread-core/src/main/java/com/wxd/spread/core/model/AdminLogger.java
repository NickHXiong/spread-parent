package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 系统配置表
 * 
 * @author wangxiaodan 
 */
public class AdminLogger {
	private Long id;
	/**
	 * 管理员ID
	 */
	private Long adminId;
	/**
	 * 日志类型，具体查看TypeEnum类型
	 */
	private Integer type;
	/**
	 * 用户操作日志内容
	 */
	private String content;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 管理员日志操作类型枚举
	 * 
	 * @author wangxiaodan
	 *
	 */
	public static enum TypeEnum {
		LOGIN(1, "登入"), LOGOUT(2, "登出"),EDIT_APP(3,"修改APP"),EDIT_APP_CHANNEL(4,"修改APP渠道"),
		EDIT_ADMIN(5,"修改管理员"),ADD_ADMIN(6,"新增管理员"),DISABLED_ADMIN(7,"禁用管理员"),ENABLED_ADMIN(8,"启用管理员");
		private int value;
		private String desc;

		private TypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public int getValue() {
			return value;
		}

		public static TypeEnum getEnum(Integer value) {
			if (value == null) {
				return null;
			}
			TypeEnum[] values = TypeEnum.values();
			for (TypeEnum vt : values) {
				if (vt.value == value) {
					return vt;
				}
			}
			return null;
		}
	}

}
