package com.wxd.spread.core.mapper;

import java.util.List;

import com.wxd.spread.core.model.WechatMenu;

public interface WechatMenuMapper {
	
	/**
	 * 查询所有菜单列表
	 * @return
	 */
	public List<WechatMenu> selectAll();
	
}