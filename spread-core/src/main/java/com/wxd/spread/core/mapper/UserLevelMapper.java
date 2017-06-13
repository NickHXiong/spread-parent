package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.UserLevel;

public interface UserLevelMapper {
	
	/**
	 * 获取最小用户等级
	 * @return
	 */
	public UserLevel selectMinUserLevel();
	
	/**
	 * 查询指定等级数字的用户等级
	 * @param level
	 * @return
	 */
	public UserLevel selectByLevel(@Param("level") Integer level);
	
	/**
	 * 通过主键查找用户等级
	 * @param id
	 * @return
	 */
	public UserLevel selectById(@Param("id") Long id);
	
	/**
	 * 查询所有用户等级列表
	 * @return
	 */
	public List<UserLevel> selectAll();
}