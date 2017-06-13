package com.wxd.spread.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxd.spread.core.mapper.UserLevelMapper;
import com.wxd.spread.core.model.UserLevel;

@Service
public class UserLevelService {

	@Autowired
	private UserLevelMapper userLevelMapper;

	/**
	 * 通过主键查找用户等级
	 * 
	 * @param id
	 * @return
	 */
	public UserLevel findById(Long id) {
		if (id != null) {
			return userLevelMapper.selectById(id);
		}
		return null;
	}

	/**
	 * 查询所有用户等级列表
	 * 
	 * @return
	 */
	public List<UserLevel> list() {
		return userLevelMapper.selectAll();
	}

}
