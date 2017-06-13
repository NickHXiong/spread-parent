package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.SubscribeSucc;

public interface SubscribeSuccMapper {
	/**
	 * 通过主键查找订阅记录信息
	 * @param id
	 * @return
	 */
	public SubscribeSucc selectById(Long id);
	
	/**
	 * 通过条件查询订阅记录列表
	 * @param subscribeSucc.openid
	 * @param subscribeSucc.appId
	 * @param subscribeSucc.success
	 * @param subscribeSucc.pushLimit
	 * @param startDate	时间过滤条件
	 * @param endDate	时间过滤条件
	 * @return
	 */
	List<SubscribeSucc> selectListByCriteria(@Param("subscribeSucc")SubscribeSucc subscribeSucc,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	
	/**
	 * 插入订阅记录信息
	 * @param subscribeSucc
	 * @return
	 */
	int insert(SubscribeSucc subscribeSucc);

	/**
	 * 通过扫码用户的openid来查找是否已经订阅此产品
	 * @param appId	产品ID
	 * @param openid	扫码用户openid
	 * @return
	 */
	public SubscribeSucc selectByAppIdAndOpenid(@Param("appId") Long appId,@Param("openid") String openid);

	/**
	 * 查询订阅总数
	 * @param appId
	 * @param success
	 * @param pushLimit
	 * @return
	 */
	public long countSelective(@Param("openid") String openid, @Param("appId") Long appId, @Param("appid") String appid,
			@Param("success") Boolean success, @Param("pushLimit") Boolean pushLimit);

	/**
	 * 更新为订阅成功
	 * @param openid	
	 * @param appid
	 * @return
	 */
	public int updateSuccByOpenidAndAppid(@Param("openid")String openid, @Param("appid")String appid);
}