package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.AppSettlement;

public interface AppSettlementMapper {

	/**
	 * 通过主键查找结算记录
	 * 
	 * @param id
	 * @return
	 */
	public AppSettlement selectById(@Param("id") Long id);

	/**
	 * 插入结算记录信息到数据库
	 * 
	 * @param appSettlement
	 * @return
	 */
	public int insert(AppSettlement appSettlement);

	/**
	 * 根据条件查询结算信息列表
	 * 
	 * @param appSettlement.appId
	 * @param appSettlement.adminId
	 * @return
	 */
	public List<AppSettlement> selectListByCriteria(AppSettlement appSettlement);

}