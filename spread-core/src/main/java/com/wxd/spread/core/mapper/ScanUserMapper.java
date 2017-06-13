package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.ScanUser;

public interface ScanUserMapper {
	
	/**
	 * 通过主键查找扫码用户
	 * @param id
	 * @return
	 */
	public ScanUser selectById(@Param("id") Long id);
	
	/**
	 * 通过openid查找扫码用户
	 * @param openid
	 * @return
	 */
	public ScanUser selectByOpenid(@Param("openid") String openid);
	
	/**
	 * 插入扫码用户信息到数据库
	 * @param scanUser
	 * @return
	 */
	public int insert(ScanUser scanUser);
	
	
	/**
	 * 根据条件查询扫码用户列表
	 * @param nickname
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ScanUser> selectListByCriteria(@Param("nickname") String nickname, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	
}