package com.wxd.spread.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.PushStatisticsMapper;
import com.wxd.spread.core.model.PushStatistics;

@Service
public class PushStatisticsService {

	@Autowired
	private PushStatisticsMapper pushStatisticsMapper;
	
	/**
	 * 根据条件查询推送记录列表
	 * @param openid
	 * @return
	 */
	public PageInfo<PushStatistics> findListByCriteria(int page,int pageSize,String openid) {
		PageHelper.startPage(page, pageSize);
		
		
		List<PushStatistics> list =  pushStatisticsMapper.selectByOpenid(openid);
		
		PageInfo<PushStatistics> result = new PageInfo<>(list);
		return result;
	}
}
