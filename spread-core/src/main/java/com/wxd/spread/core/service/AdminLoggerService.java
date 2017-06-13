package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.AdminLoggerMapper;
import com.wxd.spread.core.model.AdminLogger;
import com.wxd.spread.core.model.AdminLogger.TypeEnum;
import com.wxd.spread.core.utils.ExecutorServiceUtil;

@Service
public class AdminLoggerService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private AdminLoggerMapper adminLoggerMapper;
	
	/**
	 * 通过主键查询管理员日志信息
	 * @param id
	 * @return
	 */
	public AdminLogger findById(Long id) {
		if (id != null) {
			return adminLoggerMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 通过条件分页查询管理员日志信息
	 * @param page
	 * @param pageSize
	 * @param adminId	管理员ID
	 * @param type	日志类型
	 * @param startDate	日志开始日期，包含
	 * @param endDate	日志结束日期，包含
	 * @return
	 */
	public PageInfo<AdminLogger> list(int page,int pageSize, Long adminId,TypeEnum type,Date startDate,Date endDate) {
		PageHelper.startPage(page, pageSize);
		
		List<AdminLogger> list = adminLoggerMapper.selectListByCondition(adminId, type==null?null:type.getValue(), startDate, endDate);
		
		PageInfo<AdminLogger> result = new PageInfo<>(list);
		return result;
	}
	
	/**
	 * 异步新增日志
	 * @param adminId
	 * @param type
	 * @param content
	 */
	public void syncInsert(final Long adminId,final TypeEnum type,final String content) {
		ExecutorServiceUtil.execute(new Runnable() {
			@Override
			public void run() {
				insert(adminId,type,content);
			}
		});
	}
	
	
	public void insert(Long adminId,TypeEnum type,String content) {
		logger.info("记录日志[adminId:" + adminId + ",type:"
				+ (type == null ? "" : (type.getValue() + "-" + type.getDesc())) + ",content:" + content + "]");
		try {
			AdminLogger adminLogger = new AdminLogger();
			adminLogger.setAdminId(adminId);
			adminLogger.setType(type!=null?type.getValue():null);
			adminLogger.setContent(content);
			adminLogger.setCreateTime(new Date());
			
			adminLoggerMapper.insert(adminLogger);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
