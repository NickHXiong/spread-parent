package com.wxd.spread.core.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxd.spread.core.mapper.SysConfigMapper;
import com.wxd.spread.core.model.SysConfig;
import com.wxd.spread.core.model.SysConfig.ValueTypeEnum;

@Service
public class SysConfigService {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	/**
	 * 新增系统配置信息
	 * @param key	配置键
	 * @param valueType	值类型
	 * @param value	配置值
	 * @param describe	配置描述
	 * @return	新插入的配置ID,如果返回null标识插入失败
	 */
	@Deprecated
	public Long insert(String key,ValueTypeEnum valueType,String value,String describe) {
		SysConfig sysConfig = new SysConfig();
		sysConfig.setKey(key);
		sysConfig.setValueType(valueType.getType());
		sysConfig.setValue(value);
		sysConfig.setDescribe(describe);
		int insertNum = sysConfigMapper.insert(sysConfig);
		if (insertNum > 0) {
			logger.info("新增系统配置：" + sysConfig);
			return sysConfig.getId();
		}
		return null;
	}
	
	/**
	 * 查找所有系统配置
	 * @return
	 */
	public List<SysConfig> findAll() {
		return sysConfigMapper.selectAll();
	}
	
	/**
	 * 修改系统配置值
	 * 修改的值必须和值类型匹配，否则修改失败
	 * @param id 系统配置ID
	 * @param value	要修改的值
	 * @return
	 */
	public boolean updateValueById(Long id,String value) {
		if (id == null || StringUtils.isBlank(value)) {
			// 参数错误
			return false;
		}
		// 查看系统中要修改的配置是否存在
		SysConfig sysConfig = sysConfigMapper.selectById(id);
		if (sysConfig == null) {
			return false;
		}
		// 如果操作过了的值是相同的，就不操作数据库
		if (value.equals(sysConfig.getValue())) { 
			return true;
		}
		// 判断要修改的值是否符合配置的类型
		ValueTypeEnum valueType = SysConfig.ValueTypeEnum.getEnum(sysConfig.getValueType());
		try {
			if (valueType == ValueTypeEnum._INTEGER) {
				Integer.parseInt(value);
			} else if (valueType == ValueTypeEnum._FLOAT) {
				Float.parseFloat(value);
			}
		}catch(NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		logger.info("修改配置文件，修改前的数据：" + sysConfig + ";修改后的value:" + value);
		int updateNum = sysConfigMapper.updateValueById(id, sysConfig.getValue(), value);
		if (updateNum == 0) {
			logger.info("修改配置文件失败ID：" + id);
			return false;
		}
		return true;
	}
	
	/**
	 * 根据键查找对应配置信息
	 * @param key
	 * @return
	 */
	public SysConfig findByKey(String key) {
		return sysConfigMapper.selectByKey(key);
	}
}
