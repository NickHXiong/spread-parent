package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.AdminLogger;

public interface AdminLoggerMapper {

	/**
	 * 通过主键查询管理员日志信息
	 * @param id
	 * @return
	 */
	AdminLogger selectById(@Param("id") Long id);

	/**
	 * 根据条件查询管理员日志信息
	 * @param adminId	管理员ID
	 * @param type	日志类型
	 * @param startDate	日志开始日期，包含
	 * @param endDate	日志结束日期，包含
	 * @return
	 */
	List<AdminLogger> selectListByCondition(@Param("adminId") Long adminId, @Param("type") Integer type,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 插入日志信息
	 * @param adminLogger
	 */
	void insert(AdminLogger adminLogger);
}