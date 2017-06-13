package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.PushLog;

public interface PushLogMapper {
	
	/**
	 * 通过主键查找推送记录
	 * @param id
	 * @return
	 */
	public PushLog selectById(@Param("id") Long id);
	
	/**
	 * 插入推送记录信息到数据库
	 * @param pushLog
	 * @return
	 */
	public int insert(PushLog pushLog);
	
	
	/**
	 * 根据条件查询推送记录列表
	 * @param pushLog.openid
	 * @param pushLog.userId
	 * @param pushLog.nickname	like查找
	 * @param pushLog.userChannelId
	 * @param pushLog.appId
	 * @param pushLog.appName	like查找
	 * @param pushLog.appChannelId
	 * @param pushLog.success
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PushLog> selectListByCriteria(@Param("pushLog") PushLog pushLog, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	/**
	 * 查找最近一条没有设置成功的推送日志记录
	 * @param appId
	 * @param openid
	 * @param date
	 * @return
	 */
	public PushLog selectUnsuccFirstPushLog(@Param("appId") Long appId,@Param("openid") String openid,@Param("date") Date date);

	/**
	 * 将给定的推广日志记录修改为订阅成功
	 * @param id
	 * @return
	 */
	public int updateSuccById(Long id);

}