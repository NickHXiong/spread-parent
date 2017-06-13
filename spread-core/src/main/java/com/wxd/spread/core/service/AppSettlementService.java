package com.wxd.spread.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.AppSettlementMapper;
import com.wxd.spread.core.mapper.UserIncomeMapper;
import com.wxd.spread.core.mapper.UserMapper;
import com.wxd.spread.core.model.Admin;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.model.AppSettlement;
import com.wxd.spread.core.utils.DateUtils;

/**
 * 结算管理服务类
 * @author wangxiaodan
 *
 */
@Service
public class AppSettlementService {
	@Autowired
	private AppSettlementMapper appSettlementMapper;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AppService appService;
	@Autowired
	private AppChannelService appChannelService;
	@Autowired
	private UserIncomeMapper userIncomeMapper;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 通过主键查找结算记录
	 * 
	 * @param id
	 * @return
	 */
	public AppSettlement findById(Long id) {
		if (id != null) {
			return appSettlementMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 产品结算
	 * 此方法是管理员操作，不异步，相应时间不是第一要素
	 * @param appSettlement
	 * @return	true:结算成功  false:结算成功
	 */
	@Transactional
	public boolean settlement(AppSettlement appSettlement) throws Exception{
		if (appSettlement == null) {
			return false;
		}
		// 开始时间在结束时间在之后，根本不正确
		if (appSettlement.getDateFrom().after(appSettlement.getDateEnd())) { 
			return false;
		}
		// 检查管理员是否存在且未被禁用
//		Long adminId = appSettlement.getAdminId();
//		Admin admin = adminService.findById(adminId);
//		if (admin == null || (admin.getDisabled() != null && admin.getDisabled())) { // 用户禁用或者不存在都不会结算成功
//			return false;
//		}
		// 检查app是否是平台推广的产品
		Long appId = appSettlement.getAppId();
		App app = appService.findById(appId);
		if (app == null) {
			return false;
		}
		/// 判断渠道是否填写正确
		Long appChannelId = appSettlement.getAppChannelId();
		if (appChannelId == null) {
			return false;
		}
		List<Long> appChannelIdList = new ArrayList<>();
		AppChannel channel = appChannelService.findById(appChannelId);
		appChannelIdList.add(channel.getId());
		
		// 全部验证通过
		appSettlement.setCreateTime(new Date()); // 设置当前创建时间
		// 插入失败则返回失败
		if (appSettlementMapper.insert(appSettlement) < 0) {
			 return false;
		}
		
		
		long startDateLong = DateUtils.date2wechatTimestamp(DateUtils.formatDayStart(appSettlement.getDateFrom()));
		long endDateLong = DateUtils.date2wechatTimestamp(DateUtils.formatDayEnd(appSettlement.getDateEnd()));
		// 结算数据插入成功，则结算用户的冻结金额
		List<Map<String,Object>> userIdList = userIncomeMapper.selectMapGroupByUserId(appId,appChannelIdList,startDateLong,endDateLong);
		Integer userId = null;
		Long fee = null;
		for (Map<String,Object> map:userIdList) {
			userId = (Integer)map.get("user_id");
			fee = ((BigDecimal)map.get("totalFee")).longValue();
			int num = userMapper.unfreezeUserBalance(userId,fee);
			if (num < 0) {
				throw new Exception("结算用户冻结金额异常{userId:"+ userId +",fee:"+ fee +"}");
			}
		}
		return true;
	}
	
	
	/**
	 * 根据条件查询结算信息列表
	 * 
	 * @param appSettlement.appId
	 * @param appSettlement.adminId
	 * @return
	 */
	public PageInfo<AppSettlement> findListByCriteria(int page,int pageSize,AppSettlement appSettlement) {
		PageHelper.startPage(page, pageSize);
		AppSettlement as = null;
		if (appSettlement == null) {
			as = new AppSettlement();
		} else {
			as = appSettlement;
		}
		List<AppSettlement> list =  appSettlementMapper.selectListByCriteria(as);
		
		PageInfo<AppSettlement> result = new PageInfo<>(list);
		return result;
	}

}
