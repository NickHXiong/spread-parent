package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.UserWithdrawal;

public interface UserWithdrawalMapper {
	
	/**
	 * 通过主键查找提现记录
	 * @param id
	 * @return
	 */
	public UserWithdrawal selectById(@Param("id") Long id);
	
	/**
	 * 插入提现记录信息到数据库
	 * @param pushLog
	 * @return
	 */
	public int insert(UserWithdrawal userWithdrawal);
	
	
	/**
	 * 根据条件查询提现记录列表
	 * @param userWithdrawal.userId
	 * @param userWithdrawal.operationRole
	 * @param userWithdrawal.adminId
	 * @return
	 */
	public List<UserWithdrawal> selectListByCriteria(UserWithdrawal userWithdrawal);
	
	
	/**
	 * 通过id更新对象中不为空的数据
	 * @param userWithdrawal
	 * @return
	 */
	int updateSelectiveById(UserWithdrawal userWithdrawal);
	
}