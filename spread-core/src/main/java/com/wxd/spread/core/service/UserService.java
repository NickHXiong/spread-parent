package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.mapper.UserLevelMapper;
import com.wxd.spread.core.mapper.UserMapper;
import com.wxd.spread.core.model.SysConfig;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserLevel;
import com.wxd.spread.core.utils.DateUtils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class UserService {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserLevelMapper userLevelMapper;
	@Autowired
	private UserChannelService userChannelService;
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 初始化平台用户,从关注事件是此方法的唯一入口
	 * 1.用户（openid）是否在系统中存在，存在只修改关注字段，不存在跳2
	 * 2.新增用户，设置最小等级 3.新增默认一个用户渠道
	 * @param userWxInfo - 微信用户信息
	 */
	public void initUser(WxMpUser userWxInfo) throws Exception{
		logger.info("初始化用户："  + userWxInfo.toString());
		String openId = userWxInfo.getOpenId();
		if (StringUtils.isBlank(openId)) {
			return;
		}
		User user = findByOpenid(openId);
		if (user != null) { // 这次是用户再次关注
			userMapper.updateSubscribe(openId, userWxInfo.getSubscribeTime());
		} else { // 用户不存在，新增用户到系统库
			UserLevel userLevel = null;
			SysConfig userLevelSysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_USER_LEVEL_NUM_KEY);
			if (userLevelSysConfig != null && userLevelSysConfig.getIntValue() != null) {
				userLevel = userLevelMapper.selectByLevel(userLevelSysConfig.getIntValue());
				
			}
			if (userLevel == null) {
				// 获取最小的系统等级《倔强青铜》
				userLevel = userLevelMapper.selectMinUserLevel();
			}
			
			// 新增用户
			user = new User();
			user.setOpenid(openId);
			user.setNickname(userWxInfo.getNickname());
			user.setSex(userWxInfo.getSexId());
			user.setCountry(userWxInfo.getCountry());
			user.setProvince(userWxInfo.getProvince());
			user.setCity(userWxInfo.getCity());
			user.setLanguage(userWxInfo.getLanguage());
			user.setHeadimgurl(userWxInfo.getHeadImgUrl());
			user.setSubscribe(userWxInfo.getSubscribe());
			user.setSubscribeTime(userWxInfo.getSubscribeTime());
			user.setGroupid(userWxInfo.getGroupId());
			user.setRemark(userWxInfo.getRemark());
			user.setBalance(0L);
			user.setWaitBalance(0L);
			user.setUserLevelId(userLevel.getId());
			user.setIntegral(0L);
			user.setCreateTime(new Date());
			
			int insertNum = userMapper.insert(user);
			if (insertNum > 0) { // 新增用户成功 
				Long userChannelId = userChannelService.createNewUserChannel(user.getId());
				if (userChannelId == null) { // 如果渠道新增失败，则直接报错，回滚事务
					logger.error("新增用户失败，原因为渠道创建失败：" + String.valueOf(user));
					throw new Exception("新增用户异常，原因为渠道创建失败");
				}
			} else { // 错误，记录日志
				logger.error("新增用户失败：" + String.valueOf(user));
			}
		}
	}
	
	/**
	 * 用户取消关注微信公众号
	 * 取消关注只能从事件那里异步过来，没有返回值
	 * @param openid
	 */
	public void updateUserUnsubscribe(String openid) {
		userMapper.updateUnsubscribe(openid, DateUtils.date2wechatTimestamp(null));
	}
	
	
	/**
	 * 通过openid查找系统唯一用户
	 * @param openid
	 * @return
	 */
	public User findByOpenid(String openid) {
		if(StringUtils.isNotBlank(openid)) {
			return userMapper.selectByOpenid(openid);
		}
		return null;
	}
	
	/**
	 * 通过ID查找系统唯一用户
	 * @param openid
	 * @return
	 */
	public User findById(Long id) {
		if(id != null) {
			return userMapper.selectById(id);
		}
		return null;
	}
	
	
	/**
	 * 分页查询所有用户列表
	 * TODO 如果页面有需要可以加条件查询
	 * @return
	 */
	public PageInfo<User> list(int page,int pageSize) {
		PageHelper.startPage(page, pageSize);
		
		List<User> list = userMapper.selectList();
		
		PageInfo<User> result = new PageInfo<>(list);
		return result;
	}
	
}
