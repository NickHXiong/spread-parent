package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.PushStatistics;

public interface PushStatisticsMapper {

	/**
	 * 通过产品ID和扫码用户的openid查找统计信息
	 * @param appId
	 * @param openid
	 * @return
	 */
	PushStatistics selectByAppIdAndOpenid(@Param("appId") Long appId,@Param("openid") String openid);
	
	/**
	 * 插入新的推送统计信息
	 * @param PushStatistics
	 * @return
	 */
	int insert(PushStatistics PushStatistics);
	
	/**
	 * 通过产品ID和扫码用户的openid信息增加1推送数量
	 * @param appId
	 * @param openid
	 * @return
	 */
	int plusNumByAppIdAndOpenid(@Param("appId") Long appId,@Param("openid") String openid);

	/**
	 * 通过扫码用户openid模糊查询推送的列表
	 * @param openid
	 * @return
	 */
	List<PushStatistics> selectByOpenid(@Param("openid") String openid);
}