package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 用户提现信息
 * @author wangxiaodan
 *
 */
public class UserWithdrawal {
	private Long id;
	private Long userId;
	/**
	 * 提现金额，单位分
	 */
	private Long costFee;
	/**
	 * 1:系统（待系统开通支付功能字段转账用）  2：管理员(必须存在管理员ID，且前期都是手工提现)
	 * 见OperationRoleEnum枚举类
	 */
	private Integer operationRole;
	/**
	 * 管理员ID
	 */
	private Long adminId;
	/**
	 * 管理员或系统提现说明
	 */
	private String describe;
	/**
	 * 管理员或系统提现说明
	 */
	private Date handleTime;
	/**
	 * 提现状态 0：申请提现，待处理  1：提现成功，已处理
	 */
	private Integer status;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCostFee() {
		return costFee;
	}

	public void setCostFee(Long costFee) {
		this.costFee = costFee;
	}

	public Integer getOperationRole() {
		return operationRole;
	}

	public void setOperationRole(Integer operationRole) {
		this.operationRole = operationRole;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 提现角色枚举类
	 * 1:系统（待系统开通支付功能字段转账用）  2：管理员(必须存在管理员ID，且前期都是手工提现)
	 * @author wangxiaodan
	 *
	 */
	public static enum OperationRoleEnum {
		SYSTEM(1, "系统"), ADMIN(2, "管理员");
		private int value;
		private String desc;

		private OperationRoleEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public int getValue() {
			return value;
		}

		public static OperationRoleEnum getEnum(Integer value) {
			if (value == null) {
				return null;
			}
			OperationRoleEnum[] values = OperationRoleEnum.values();
			for (OperationRoleEnum vt : values) {
				if (vt.value == value) {
					return vt;
				}
			}
			return null;
		}
	}
	
	/**
	 * 提现状态枚举类
	 * 提现状态 0：申请提现，待处理  1：提现成功，已处理
	 * @author wangxiaodan
	 *
	 */
	public static enum StatusEnum {
		WHAIT(0, "申请提现，待处理"), SUCC(1, "提现成功，已处理");
		private int value;
		private String desc;

		private StatusEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public int getValue() {
			return value;
		}

		public static StatusEnum getEnum(Integer value) {
			if (value == null) {
				return null;
			}
			StatusEnum[] values = StatusEnum.values();
			for (StatusEnum vt : values) {
				if (vt.value == value) {
					return vt;
				}
			}
			return null;
		}
	}
}
