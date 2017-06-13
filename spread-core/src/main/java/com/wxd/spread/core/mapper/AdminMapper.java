package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.Admin;

public interface AdminMapper {
	
	/**
	 * 通过主键查找管理员对象
	 * @param id
	 * @return
	 */
	Admin selectById(@Param("id")Long id);

	/**
	 * 根据模糊条件查询管理员列表
	 * @param nickname
	 * @param trueName
	 * @param mobile
	 * @param email
	 * @param account
	 * @return
	 */
	List<Admin> selectListByCondition(@Param("nickname") String nickname, @Param("trueName") String trueName,
			@Param("mobile") String mobile, @Param("email") String email, @Param("account") String account);

	/**
	 * 管理员根据旧密码修改成新密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	int updatePassword(@Param("id") Long id, @Param("oldPassword") String oldPassword,
			@Param("newPassword") String newPassword);

	/**
	 * 修改管理员的昵称和真实姓名
	 * @param id
	 * @param nickname
	 * @param trueName
	 * @return
	 */
	int updateNickNameAndTruename(@Param("id")Long id,@Param("nickname") String nickname,@Param("trueName") String trueName);

	/**
	 * 修改管理员状态
	 * @param id
	 * @param disabled
	 * @return
	 */
	int updateNormalAdminDisabledStatus(@Param("id")Long id, @Param("disabled") boolean disabled);

	/**
	 * 根据账号、电话、邮箱其中一个查找管理员
	 * @param account
	 * @param mobile
	 * @param email
	 * @return
	 */
	Admin selectByAccountOrMobileOrEmail(@Param("account") String account,@Param("mobile") String mobile,@Param("email") String email);

	/**
	 * 插入新的普通管理员
	 * @param admin
	 * @return
	 */
	int insert(Admin admin);
	
}