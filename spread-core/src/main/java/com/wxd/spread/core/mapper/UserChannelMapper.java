package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.UserChannel;

public interface UserChannelMapper {
	/**
	 * 查询是否已经存在在用户渠道码
	 * @param channelCode
	 * @return
	 */
	int selectCountByChannelCode(@Param("channelCode")String channelCode);
	
	/**
	 * 通过渠道码查找用户渠道实体信息
	 * @param channelCode	用户渠道码
	 * @return
	 */
	UserChannel selectByChannelCode(@Param("channelCode")String channelCode);

	/**
	 * 插入新的用户渠道
	 * @param userChannel
	 * @return
	 */
	int insert(UserChannel userChannel);

	/**
	 * 查询用户的渠道数量
	 * @param userId
	 * @return
	 */
	int selectCountByUserId(@Param("userId")Long userId);

	/**
	 * 查找用户的渠道列表
	 * @param userId	用户ID
	 * @return
	 */
	List<UserChannel> selectByUserId(@Param("userId")Long userId);

	/**
	 * 通过ID查找用户渠道
	 * @param id
	 * @return
	 */
	UserChannel selectById(@Param("id")Long id);

	/**
	 * 查找用户的基本渠道
	 * @param userId
	 * @return
	 */
	UserChannel selectBaseChannelByUserId(Long userId);
}