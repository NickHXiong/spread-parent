package com.wxd.spread.core.model;

import org.apache.commons.lang3.StringUtils;

import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * 系统配置表
 * 
 * @author wangxiaodan
 */
public class SysConfig {
	private Long id;
	/**
	 * 配置键
	 */
	private String key;
	/**
	 * 值类型 1：string 2:integer 3:float
	 */
	private Integer valueType;
	/**
	 * 配置值
	 */
	private String value;
	/**
	 * 配置描述
	 */
	private String describe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}
	
	public Integer getIntValue() {
		try {
			if (ValueTypeEnum.getEnum(valueType) == ValueTypeEnum._INTEGER && StringUtils.isNotBlank(value)) {
				return Integer.parseInt(this.value);
			}
		}catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Float getFloatValue() {
		try {
			if (ValueTypeEnum.getEnum(valueType) == ValueTypeEnum._FLOAT && StringUtils.isNotBlank(value)) {
				return Float.parseFloat(this.value);
			}
		}catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	

	/**
	 * 值类型枚举类，1：string 2:integer 3:float
	 * 
	 * @author wangxiaodan
	 *
	 */
	public static enum ValueTypeEnum {
		_STRING(1,"字符串"), _INTEGER(2,"整数"), _FLOAT(3,"浮点");
		private int type;
		private String desc;

		private ValueTypeEnum(int type,String desc) {
			this.type = type;
			this.desc = desc;
		}

		public int getType() {
			return type;
		}
		
		public String getDesc() {
			return desc;
		}

		public static ValueTypeEnum getEnum(Integer type) {
			if (type == null) {
				return null;
			}
			ValueTypeEnum[] values = ValueTypeEnum.values();
			for (ValueTypeEnum vt : values) {
				if (vt.type == type) {
					return vt;
				}
			}
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		return ToStringUtils.toSimpleString(this);
	}
}
