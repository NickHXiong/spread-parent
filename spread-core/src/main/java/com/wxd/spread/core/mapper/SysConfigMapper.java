package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.SysConfig;

public interface SysConfigMapper {
	
	/**
	 * 新增系统配置
	 * @param sysConfig  系统配置信息
	 * @return
	 */
	public int insert(SysConfig sysConfig);
	
	
	/**
	 * 通过主键查找系统配置
	 * @param id
	 * @return
	 */
	public SysConfig selectById(@Param("id") Long id);
	
	/**
	 * 通过配置键查找系统配置
	 * @param id
	 * @return
	 */
	public SysConfig selectByKey(@Param("key") String key);
	
	
	/**
	 * 通过主键和原来的配置值修改系统配置的value值
	 * @param id	系统配置ID
	 * @param beforeValue	修改前的值
	 * @param value	修改后的值
	 * @return
	 */
	public int updateValueById(@Param("id") Long id, @Param("beforeValue") String beforeValue, @Param("value") String value);


	/**
	 * 查找系统所有配置
	 * @return
	 */
	public List<SysConfig> selectAll();
	
}