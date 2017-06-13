package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.User;

public interface UserMapper {

	/**
	 * 通过用户openid查找唯一用户
	 * @param openid
	 * @return
	 */
	User selectByOpenid(@Param("openid") String openid);
	
	/**
	 * 通过主键查找用户
	 * @param id
	 * @return
	 */
	User selectById(@Param("id") Long id);
	
	/**
	 * 用户关注公众号
	 * @param openid
	 * @param time	关注时间
	 */
	public int updateSubscribe(@Param("openid")String openid,@Param("time")Long time);
	
	/**
	 * 用户取消关注公众号
	 * @param openid
	 * @param time	取消时间
	 */
	public int updateUnsubscribe(@Param("openid")String openid,@Param("time")Long time);
	/**
	 * 新增系统用户
	 * @param user
	 * @return
	 */
	int insert(User user);

	/**
	 * 查询用户列表
	 * @return
	 */
	List<User> selectList();

	/**
	 * 增加用户的待结算金额
	 * @param id	用户ID
	 * @param fee	要增加的金额
	 * @return
	 */
	int plusWaitBalance(@Param("id") Long id,@Param("fee") Long fee);

	/**
	 * 解冻用户金额
	 * @param userId	用户ID
	 * @param fee	要解冻的金额
	 * @return
	 */
	int unfreezeUserBalance(@Param("id") Integer userId,@Param("fee") Long fee);
}