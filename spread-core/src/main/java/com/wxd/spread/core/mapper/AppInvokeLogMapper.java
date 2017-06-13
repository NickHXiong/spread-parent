package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.AppInvokeLog;

public interface AppInvokeLogMapper {
	
	/**
	 * 通过主键查找回调信息
	 * @param id
	 * @return
	 */
	public AppInvokeLog selectById(@Param("id") Long id);
	
	/**
	 * 插入回调信息到数据库
	 * @param pushLog
	 * @return
	 */
	public int insert(AppInvokeLog appInvokeLog);
	
	
	/**
	 * 根据条件查询回调信息列表
	 * @param appInvokeLog.appid
	 * @param appInvokeLog.openid
	 * @param appInvokeLog.sceneValue
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AppInvokeLog> selectListByCriteria(@Param("record") AppInvokeLog appInvokeLog, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	/**
	 * 根据业务字段判断是否存在多个回调信息
	 * @param appid
	 * @param openid
	 * @param sceneValue
	 * @param invokeTime
	 * @return
	 */
	public long countByAppidAndOpenidAndSceneValueAndInvokeTime(@Param("appid") String appid,@Param("openid") String openid,@Param("sceneValue") String sceneValue,
			@Param("invokeTime")long invokeTime);
}