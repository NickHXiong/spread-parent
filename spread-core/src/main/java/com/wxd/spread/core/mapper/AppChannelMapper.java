package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.AppChannel;

public interface AppChannelMapper {
	
	/**
	 * 通过渠道id查找产品的渠道
	 * @param id
	 * @return
	 */
	AppChannel selectById(@Param("id")Long id);
	
	/**
	 * 根据场景值查找渠道列表
	 * @param sceneValue	场景值
	 * @param disabled	true:禁用列表  false:启用列表   null:所有列表
	 * @return
	 */
	List<AppChannel> selectBySceneValue(@Param("sceneValue")String sceneValue,@Param("disabled")Boolean disabled);
	
	/**
	 * 通过产品id和场景值唯一查找一个产品渠道
	 * @param appId
	 * @param sceneValue
	 * @return
	 */
	AppChannel selectByAppIdAndSceneValue(@Param("appId") Long appId,@Param("sceneValue")String sceneValue);
	
	/**
	 * 通过给定产品id查找渠道列表 
	 * @param appId	产品的ID
	 * @return
	 */
	List<AppChannel> selectListByAppId(@Param("appId") Long appId);
	
	
	/**
	 * 根据渠道id来修改渠道是否禁用的状态
	 * @param id
	 * @param disabled   true:禁用渠道   false:启用渠道
	 * @return 更新影响的行数
	 */
	int updateDisabledStatus(@Param("id")Long id,@Param("disabled")boolean disabled);
	
	/**
	 * 更新产品渠道的优先级
	 * @param id
	 * @param priority	优先级数值，越大优先级越高
	 * @return
	 */
	int updatePriority(@Param("id")Long id,@Param("priority")int priority);
	
	/**
	 * 更新渠道的过滤字段
	 * 包括expireDate、maxNum、filterSex、filterCity、filterCountry、filterProvince、filterLanguage字段
	 * @param appChannel
	 * @return 更新影响的行数
	 */
	int updateFilterFieldById(@Param("appChannel")AppChannel appChannel);
	
	/**
	 * 插入产品渠道
	 * @param appChannel
	 * @return 插入成功的行数
	 */
	int insert(AppChannel appChannel);

	/**
	 * 查询所有可用的产品渠道并优先级排序
	 * @return
	 */
	List<AppChannel> selectUsableChannel();

	/**
	 * 获得产品的第一个可用的渠道
	 * @param appId 产品ID
	 * @return
	 */
	AppChannel findFirstUsableChannel(@Param("appId") Long appId);

	/**
	 * 根据条件查询产品渠道列表
	 * @param criteria
	 * @return
	 */
	List<AppChannel> selectListByCriteria(@Param("appChannel") AppChannel criteria,@Param("priceMin") Integer priceMin,@Param("priceMax") Integer priceMax);
}