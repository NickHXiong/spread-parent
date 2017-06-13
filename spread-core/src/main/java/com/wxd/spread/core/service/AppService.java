package com.wxd.spread.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.AppMapper;
import com.wxd.spread.core.model.App;

@Service
public class AppService {
	@Autowired
	private AppMapper appMapper;
	
	/**
	 * 分页查找产品列表
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageInfo<App> list(int page,int pageSize,String appid, String appName,String companyName,Boolean disabled) {
		PageHelper.startPage(page, pageSize);
		
		List<App> list = appMapper.selectListByCondition(appid,appName,companyName,disabled);
		
		PageInfo<App> result = new PageInfo<>(list);
		return result;
	}
	
	
	/**
	 * 获取所有可推广的app列表
	 * @return
	 */
	public List<App> listAbledOrderPriority() {
		
		List<App> appList = appMapper.listAbledOrderPriority();
		if (appList == null) {
			appList = new ArrayList<>();
		}
		return appList;
	}
	
	/**
	 * 获取所有可推广的app列表,按照产品名称排序
	 * @return
	 */
	public List<App> listAbledOrderName() {
		
		List<App> appList = appMapper.listAbledOrderName();
		if (appList == null) {
			appList = new ArrayList<>();
		}
		return appList;
	}
	
	/**
	 * 获取所有可推广的app列表,按照产品名称排序
	 * @return
	 */
	public List<App> listAllOrderName() {
		
		List<App> appList = appMapper.listAbledOrderName();
		if (appList == null) {
			appList = new ArrayList<>();
		}
		return appList;
	}
	
	/**
	 * 通过appid查找第三方产品
	 * @param appid
	 * @return
	 */
	public App findByAppid(String appid) {
		if (StringUtils.isBlank(appid)) {
			return null;
		}
		return appMapper.selectByAppid(appid);
	}
	
	/**
	 * 通过id查找第三方产品
	 * @param id
	 * @return
	 */
	public App findById(Long id) {
		if (id == null) {
			return null;
		}
		return appMapper.selectById(id);
	}
	
	/**
	 * 更新产品的是否禁用状态
	 * @param id	产品ID
	 * @param disabled	true:禁用  false:正常
	 * @return
	 */
	public boolean updateDisabledStatus(Long id,boolean disabled) {
		return appMapper.updateDisabledById(id, disabled) > 0;
	}
	
	/**
	 * 插入第三方app信息到系统
	 * @param appid
	 * @param appName
	 * @param whiteInvokeUrls
	 * @param companyName
	 * @param priority
	 * @return
	 */
	public Long insertApp(String appid,String appName,String whiteInvokeUrls,String companyName,Integer priority) {
		if (StringUtils.isBlank(appid)) {
			return null;
		}
		App app = new App();
		app.setAppid(appid);
		app.setAppName(appName);
		app.setCompanyName(companyName);
		app.setWhiteInvokeUrls(whiteInvokeUrls);
		if (priority == null) {
			priority = 50;
		}
		app.setPriority(priority);
		app.setDisabled(false);
		app.setCreateTime(new Date());
		int insertNum = appMapper.insert(app);
		if (insertNum > 0) {
			return app.getId();
		}
		return null;
	}
	
	/**
	 * 根据ID更新第三方商品
	 * @param id
	 * @param appName
	 * @param whiteInvokeUrls
	 * @param companyName
	 * @param priority
	 * @param disabled
	 * @return
	 */
	public boolean updateApp(Long id,String appName,String whiteInvokeUrls,String companyName,Integer priority,Boolean disabled) {
		if (id == null) {
			return false;
		}
		App app = new App();
		app.setId(id);
		app.setAppName(appName);
		app.setCompanyName(companyName);
		app.setWhiteInvokeUrls(whiteInvokeUrls);
		if (priority != null) {
			app.setPriority(priority);
		}
		app.setDisabled(disabled == null?true:disabled);
		return appMapper.updateById(app) > 0;
	}
}
