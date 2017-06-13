package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.App;

public interface AppMapper {

	/**
	 * 查找第三方产品
	 * 
	 * @param appid
	 * @return
	 */
	App selectByAppid(@Param("appid") String appid);

	/**
	 * 通过主键查找第三方产品信息 
	 * 
	 * @param id
	 * @return
	 */
	App selectById(@Param("id") Long id);

	/**
	 * 通过给定条件查找第三方产品列表 
	 * @param appid
	 * @param appName	产品名称	- 模糊查找
	 * @param companyName 公司名称 - 模糊查找
	 * @return
	 */
	List<App> selectListByCondition(@Param("appid") String appid, @Param("appName") String appName,
			@Param("companyName") String companyName,@Param("disabled") Boolean disabled);
	
	/**
	 * 启用/禁用第三方产品
	 * @param id
	 * @param disabled	true:禁用  false:启用
	 * @return
	 */
	int updateDisabledById(@Param("id") Long id,@Param("disabled") boolean disabled);
	
	/**
	 * 通过产品中的主键来更新产品信息
	 * 更新的内容包括 appName/whiteInvokeUrls/companyName/priority/disabled
	 * @param app
	 * @return
	 */
	int updateById(App app);
	
	/**
	 * 新增新产品
	 * @param app
	 * @return
	 */
	int insert(App app);

	/**
	 * 查询所有未被禁用的产品
	 * 
	 */
	List<App> listAbledOrderPriority();

	/**
	 * 按照产品名称排序查找所有可用产品列表
	 * @return
	 */
	List<App> listAbledOrderName();
	/**
	 * 按照产品名称排序查找所有产品列表
	 * @return
	 */
	List<App> listAllOrderName();

}