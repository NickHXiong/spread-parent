package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.PushLogMapper;
import com.wxd.spread.core.model.PushLog;

@Service
public class PushLogService {

	@Autowired
	private PushLogMapper pushLogMapper;
	
	/**
	 * 通过主键查找推送记录
	 * @param id
	 * @return
	 */
	public PushLog findById(Long id) {
		if (id != null) {
			return pushLogMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 查找最近一条没有设置成功的推送日志记录
	 * @param appId
	 * @param openid
	 * @param date
	 * @return
	 */
	public PushLog findUnsuccFirstPushLog(Long appId,String openid,Date date) {
		return pushLogMapper.selectUnsuccFirstPushLog(appId,openid,date);
	}
	
	
	
	/**
	 * 插入推送记录信息到数据库
	 * 系统调用
	 * @param pushLog
	 * @return
	 */
	public Long insert(PushLog pushLog) {
		if (pushLog != null) {
			int insertNum = pushLogMapper.insert(pushLog);
			if (insertNum > 0) {
				 return pushLog.getId();
			}
		}
		return null;
	}
	
	
	/**
	 * 根据条件查询推送记录列表
	 * @param pushLog.openid
	 * @param pushLog.userId
	 * @param pushLog.nickname	like查找
	 * @param pushLog.userChannelId
	 * @param pushLog.appId
	 * @param pushLog.appName	like查找
	 * @param pushLog.appChannelId
	 * @param pushLog.success
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PageInfo<PushLog> findListByCriteria(int page,int pageSize,PushLog pushLog, Date startDate,
			Date endDate) {
		PageHelper.startPage(page, pageSize);
		PushLog pl = null;
		if (pushLog == null) {
			pl = new PushLog();
		} else {
			pl = pushLog;
		}
		List<PushLog> list =  pushLogMapper.selectListByCriteria(pl, startDate, endDate);
		
		PageInfo<PushLog> result = new PageInfo<>(list);
		return result;
	}
	
	/**
	 * 将给定的推广日志记录修改为订阅成功
	 * @param id
	 * @return
	 */
	public boolean updateSuccById(Long id) {
		return pushLogMapper.updateSuccById(id) > 0;
	}
}
