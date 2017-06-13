package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.UserIncomeMapper;
import com.wxd.spread.core.mapper.UserMapper;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.model.ScanUser;
import com.wxd.spread.core.model.UserChannel;
import com.wxd.spread.core.model.UserIncome;

/**
 * 用户收入服务类
 * @author wangxiaodan
 *
 */
@Service
public class UserIncomeService {

	@Autowired
	private UserIncomeMapper userIncomeMapper;
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * 通过主键查找用户收入
	 * @param id
	 * @return
	 */
	public UserIncome findById(Long id) {
		if (id != null) {
			return userIncomeMapper.selectById(id);
		}
		return null;
	}
	
	
	/**
	 * 根据昵称分页查找用户收入列表
	 * @param page
	 * @param pageSize
	 * @param userId
	 * @param userChannelId
	 * @param appId
	 * @param appName	模糊查询
	 * @param appChannelId
	 * @param subOpenid
	 * @param subNickname	模糊查询
	 * @return
	 */
	public PageInfo<UserIncome> list(int page, int pageSize, Long userId, Long userChannelId, Long appId,
			String appName, Long appChannelId, String subOpenid, String subNickname) {
		PageHelper.startPage(page, pageSize);
		
		UserIncome userIncomeCriteria = new UserIncome();
		userIncomeCriteria.setUserId(userId);
		userIncomeCriteria.setUserChannelId(userChannelId);
		userIncomeCriteria.setAppId(appId);
		userIncomeCriteria.setAppName(appName);
		userIncomeCriteria.setAppChannelId(appChannelId);
		userIncomeCriteria.setSubOpenid(subOpenid);
		userIncomeCriteria.setSubNickname(subNickname);
		List<UserIncome> list = userIncomeMapper.selectListByCriteria(userIncomeCriteria);
		
		PageInfo<UserIncome> result = new PageInfo<>(list);
		return result;
	}
	
	/**
	 * 只能系统调用，用户和管理员都无法调用
	 * @param userIncome - 其中金额、产品ID、关注人openid绝对不能为空
	 * @return
	 */
	public Long insert(UserIncome userIncome) {
		// 条件检查
		if (userIncome.getFee() == null || userIncome.getFee() <= 0 || userIncome.getAppId() == null || StringUtils.isBlank(userIncome.getSubOpenid())) {
			return null;
		}
		int insertNum = userIncomeMapper.insert(userIncome);
		if (insertNum > 0) {
			return userIncome.getId();
		}
		return null;
	}
	
	/**
	 * 用户收入处理
	 * 插入用户收入记录，增加用户账户金额
	 * @throws Exception
	 */
	@Transactional
	public void userIncomeHandle(long fee, UserChannel userChannel, App app, AppChannel appChannel, ScanUser scanUser,Long invokeTime)
			throws Exception {
		UserIncome userIncome = new UserIncome();
		userIncome.setUserId(userChannel.getUserId());
		userIncome.setUserChannelId(userChannel.getId());
		userIncome.setAppId(app.getId());
		userIncome.setAppName(app.getAppName());
		userIncome.setAppChannelId(appChannel.getId());
		userIncome.setSubOpenid(scanUser.getOpenid());
		userIncome.setSubNickname(scanUser.getNickname());
		userIncome.setFee(fee);
		userIncome.setSubscribeTime(invokeTime);
		userIncome.setCreateTime(new Date());
		
		int insertNum = userIncomeMapper.insert(userIncome);
		if (insertNum > 0) {
			int plusNum = userMapper.plusWaitBalance(userChannel.getId(),fee);
			if (plusNum > 0) {
				return;
			}
		}
		throw new Exception("用户收入处理出现异常");
	}
	
}
