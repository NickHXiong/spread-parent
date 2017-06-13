package com.wxd.spread.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.UserIncome;

public interface UserIncomeMapper {
	
	/**
	 * 通过主键查找用户收入实体
	 * @param id
	 * @return
	 */
	UserIncome selectById(@Param("id") Long id);
	
	/**
	 * 通过条件查找收入
	 * @param userId
	 * @param userChannelId
	 * @param appId
	 * @param appName
	 * @param appChannelId
	 * @param subOpenid
	 * @param nickname
	 * @return
	 */
	List<UserIncome> selectListByCriteria(UserIncome userIncome);
	
	/**
	 * 插入用户收入
	 * @param userIncome
	 * @return
	 */
	int insert(UserIncome userIncome);
	
	/**
	 * 用于计算用户待解冻金额
	 * @param appId	产品ID
	 * @param appChannelIdList 渠道ID列表，如果是空列表，则传入null
	 * @param startDateLong	开始时间时间戳
	 * @param endDateLong	结束时间时间戳
	 * @return	{user_id:Integer,totalFee:BigDecimal}
	 */
	List<Map<String, Object>> selectMapGroupByUserId(@Param("appId") Long appId,
			@Param("appChannelIdList") List<Long> appChannelIdList, @Param("startDateLong") long startDateLong,
			@Param("endDateLong") long endDateLong);
}