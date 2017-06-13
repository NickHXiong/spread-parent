package com.wxd.spread.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.mapper.AppChannelMapper;
import com.wxd.spread.core.mapper.PushStatisticsMapper;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.model.PushLog;
import com.wxd.spread.core.model.PushStatistics;
import com.wxd.spread.core.model.ScanUser;
import com.wxd.spread.core.model.SubscribeSucc;
import com.wxd.spread.core.model.SysConfig;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserChannel;
import com.wxd.spread.core.utils.DateUtils;
import com.wxd.spread.core.utils.ExecutorServiceUtil;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class AppChannelService {
	@Autowired
	private AppChannelMapper appChannelMapper;
	@Autowired
	private SubscribeSuccService subscribeSuccService;
	@Autowired
	private PushStatisticsMapper pushStatisticsMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private UserChannelService userChannelService;
	@Autowired
	private UserService userService;
	@Autowired
	private PushLogService pushLogService;
	@Autowired
	private AppService appService;
	
	
	/**
	 * 通过产品的ID查找产品的所有渠道列表
	 * @param appId
	 * @return
	 */
	public List<AppChannel> list(Long appId) {
		List<AppChannel> result = appChannelMapper.selectListByAppId(appId);
		if (result == null) {
			return new ArrayList<>();
		}
		return result;
	}
	
	/**
	 * 通过主键查找渠道
	 * @param id
	 * @return
	 */
	public AppChannel findById(Long id) {
		return appChannelMapper.selectById(id);
	}
	
	
	/**
	 * 通过给定的渠道ID来更新渠道的可用状态
	 * @param id
	 * @param disabled  true:禁用（不可用）   false:正常（可用）
	 * @return
	 */
	public boolean updateDisabledStatus(Long id,boolean disabled) {
		if (id == null) {
			return false;
		}
		AppChannel appChannel = appChannelMapper.selectById(id);
		// 判断当前要更新的状态就是不变的，则不做更新操作
		if (appChannel.getDisabled() != null && disabled == appChannel.getDisabled()) {
			return true;
		}
		
		if (disabled) { // 如果是禁用，则直接操作
			return appChannelMapper.updateDisabledStatus(id, disabled) > 0;
		} else { // 如果是启用，则判断相同的sceneValue有没有已经启用的
			List<AppChannel> acList = appChannelMapper.selectBySceneValue(appChannel.getSceneValue(), Boolean.FALSE);
			if (acList == null || acList.size() == 0 ) {
				return appChannelMapper.updateDisabledStatus(id, disabled) > 0;
			}
		}
		return false;
	}
	
	/**
	 * 通过主键更新渠道的优先级
	 * @param id
	 * @param priority 优先级，越大优先级越高
	 * @return
	 */
	public boolean updatePriority(Long id,int priority) {
		if (id == null) {
			return false;
		}
		return appChannelMapper.updatePriority(id, priority) > 0;
	}
	
	/**
	 * 插入新的产品渠道
	 * @param appChannel
	 * @return
	 */
	public Long insertAppChannel(AppChannel appChannel) {
		Map<String,Object> otherField = new HashMap<>();
		otherField.put("percentage", appChannel.getPercentage());
		otherField.put("priority", appChannel.getPriority());
		otherField.put("expireDate", appChannel.getExpireDate());
		otherField.put("maxNum", appChannel.getMaxNum());
		otherField.put("filterSex", appChannel.getFilterSex());
		otherField.put("filterCity", appChannel.getFilterCity());
		otherField.put("filterCountry", appChannel.getFilterCountry());
		otherField.put("filterProvince", appChannel.getFilterProvince());
		otherField.put("filterLanguage", appChannel.getFilterLanguage());
		
		return this.insertAppChannel(appChannel.getAppId(), appChannel.getSceneValue(), appChannel.getChannelUrl(), appChannel.getTicket(), appChannel.getPrice(), otherField);
	}
	
	
	/**
	 * 插入新渠道
	 * @param appId
	 * @param sceneValue	如果系统存在相同的场景值的渠道，且是正常状态，则插入失败
	 * @param channelUrl
	 * @param ticket
	 * @param price
	 * @param otherField - percentage/priority/expireDate/maxNum/filterSex/filterCity/filterCountry/filterProvince/filterLanguage
	 * @return	null:插入失败   其他：新插入渠道的ID
	 */
	public Long insertAppChannel(long appId,String sceneValue,String channelUrl,String ticket,long price,Map<String,Object> otherField) {
		// 参数判断
		if (appId <= 0 || StringUtils.isAnyBlank(sceneValue,channelUrl,ticket) || price <= 0) {
			return null;
		}
		
		//如果系统存在相同的场景值的渠道，且是正常状态，则插入失败
		AppChannel appChannel = appChannelMapper.selectByAppIdAndSceneValue(appId, sceneValue);
		if (appChannel != null) { // 说明已经存在了唯一产品的渠道
			return null;
		}
		
		// 构造渠道实体AppChannel并做保存操作
		appChannel = new AppChannel();
		appChannel.setAppId(appId);
		appChannel.setSceneValue(sceneValue);
		appChannel.setChannelUrl(channelUrl);
		appChannel.setTicket(ticket);
		appChannel.setPrice(price);
		// 防止空指针
		if (otherField == null) {
			otherField = new HashMap<String,Object>();
		}
		/// 渠道佣金的百分比分配给平台推广用户
		Integer percentage = (Integer)otherField.get("percentage");
		if (percentage == null) { // 输入为空则根据系统配置设置
			SysConfig percentageSysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_APPCHANNEL_PERCENTAGE_KEY);
			if (percentageSysConfig != null && percentageSysConfig.getIntValue() != null) {
				percentage = percentageSysConfig.getIntValue();
			}
		}
		if (percentage == null || percentage > 100) { // 超过100%或默认和管理员都没有设置则设置为100%
			percentage = 100;
		}
		appChannel.setPercentage(percentage);
		// 设置渠道的优先级
		Integer priority = (Integer)otherField.get("priority");
		if (priority == null) {
			SysConfig prioritySysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_APP_PRIORITY_KEY);
			if (prioritySysConfig != null && prioritySysConfig.getIntValue() != null) {
				priority = prioritySysConfig.getIntValue();
			}
		} 
		if (priority == null) {
			priority = 50;
		}
		appChannel.setPriority(priority);
		// 设置过期日期
		appChannel.setExpireDate((Date)otherField.get("expireDate"));
		// 设置最多推广上限
		Long maxNum = (Long)otherField.get("maxNum");
		if (maxNum != null && maxNum < 0) {
			maxNum = null;
		}
		appChannel.setMaxNum(maxNum);
		// 其他的性别，地区，语言字段设置
		appChannel.setFilterSex((String)otherField.get("filterSex"));
		appChannel.setFilterCity((String)otherField.get("filterCity"));
		appChannel.setFilterCountry((String)otherField.get("filterCountry"));
		appChannel.setFilterProvince((String)otherField.get("filterProvince"));
		appChannel.setFilterLanguage((String)otherField.get("filterLanguage"));
		// 日期和状态设置
		appChannel.setDisabled(Boolean.FALSE);
		appChannel.setCreateTime(new Date());
		// 保存操作
		if (appChannelMapper.insert(appChannel) > 0){
			return appChannel.getId();
		}
		return null;
	}
	
	/**
	 * 调整产品渠道的过滤条件
	 * @param appChannel
	 * @return
	 */
	public boolean updateFilterField(AppChannel appChannel) {
		if (appChannel.getId() == null) {
			return false;
		}
		return appChannelMapper.updateFilterFieldById(appChannel) > 0;
	}
	
	/**
	 * 获取所有可推广的渠道并按照优先级排序返回
	 * TODO 这里直接从数据库获取，效率低下，必要时需要缓存这部分数据
	 * @return
	 */
	public List<AppChannel> findUsableChannel() {
		List<AppChannel> list = appChannelMapper.selectUsableChannel();
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;
	}
	
	/**
	 * 获得产品的第一个可用的渠道
	 * @param appId 产品ID
	 * @return
	 */
	public AppChannel findFirstUsableChannel(Long appId) {
		return appChannelMapper.findFirstUsableChannel(appId);
	}
	
	
	/**
	 * 通过给定扫码的用户获取要推广的产品渠道并记录日志
	 * @param wxMpUser
	 * @param channelCode	用户渠道码
	 * @return
	 * @throws Exception
	 */
	public AppChannel findSpreadChannel(final WxMpUser wxMpUser,final String channelCode) throws Exception{
		List<AppChannel> list = appChannelMapper.selectUsableChannel();
		for (AppChannel channel : list) {
			// 先查看渠道的过滤条件是否符合此用户
			if (filterScanUser(wxMpUser,channel)) {
				continue;
			}
			
			// 查看用户是否已经订阅了此产品
			SubscribeSucc subscribe = subscribeSuccService.findByAppIdAndOpenid(channel.getAppId(), wxMpUser.getOpenId());
			if (subscribe != null) {
				continue;
			}
			
			// 查看用户是否在5分钟内订阅了此产品，如果没有，则查看是否推送数量超限
			final PushStatistics pushStatistics = pushStatisticsMapper.selectByAppIdAndOpenid(channel.getAppId(),wxMpUser.getOpenId());
			final SysConfig MaxNumSysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_ACAN_MAXNUM_KEY);
			if (pushStatistics != null) {
				// 比较时间是否在5分钟内
				Date prePushTime = pushStatistics.getPrePushTime();
				if (prePushTime != null && DateUtils.minuteOffsets(-5).compareTo(prePushTime) > 1) {
					continue;
				}
				// 查看推送次数是否上限
				if (MaxNumSysConfig != null && MaxNumSysConfig.getIntValue() != null && pushStatistics.getNum() >= MaxNumSysConfig.getIntValue() ) {
					continue;
				}
				
			}
			final Long appId = channel.getAppId();
			final Long appChannelId = channel.getId();
			// 异步处理推送日志和推送统计
			ExecutorServiceUtil.execute(new Runnable() {
				@Override
				public void run() {
					// 通过用户的推广码查找推广信息
					UserChannel userChannel = userChannelService.findByChannelCode(channelCode);
					Long userChannelId = null;
					Long userId = null;
					String nickname = null;
					if (userChannel != null) {
						userChannelId = userChannel.getId();
						userId = userChannel.getUserId();
					}
					if (userId != null) {
						User user = userService.findById(userId);
						if (user != null) {
							nickname = user.getNickname();
						}
					}
					App app = appService.findById(appId);
					// 记录推送日志
					PushLog pushLog = new PushLog();
					pushLog.setOpenid(wxMpUser.getOpenId());
					pushLog.setUserId(userId);
					pushLog.setUserChannelId(userChannelId);
					pushLog.setNickname(nickname);
					pushLog.setAppId(appId);
					pushLog.setAppChannelId(appChannelId);
					pushLog.setAppName(app != null?app.getAppName():"");
					pushLog.setSuccess(false);
					pushLog.setCreateTime(new Date());
					pushLogService.insert(pushLog);
					// 用户推送统计 +1
					if (pushStatistics == null) { // 插入推送统计信息
						PushStatistics ps = new PushStatistics();
						ps.setAppId(appId);
						ps.setOpenid(wxMpUser.getOpenId());
						ps.setNum(1);
						Date now = new Date();
						ps.setPrePushTime(now);
						ps.setCreateTime(now);
						pushStatisticsMapper.insert(ps);
					} else { // 新增1
						pushStatisticsMapper.plusNumByAppIdAndOpenid(appId, wxMpUser.getOpenId());
						// 用户超限则记录订阅信息
						if (MaxNumSysConfig != null && MaxNumSysConfig.getIntValue() != null && (pushStatistics.getNum() + 1) >= MaxNumSysConfig.getIntValue() ) {
							SubscribeSucc sSucc = new SubscribeSucc();
							sSucc.setAppId(appId);
							sSucc.setOpenid(wxMpUser.getOpenId());
							sSucc.setSuccess(false);
							sSucc.setPushLimit(true);
							sSucc.setDescribe("单用户推送数量超过系统配置" + MaxNumSysConfig.getIntValue() + "次数");
							sSucc.setCreateTime(new Date());
							subscribeSuccService.insert(sSucc);
						}
					}
				}
			});
			
			return channel;
		}
		return null;
	}
	
	/**
	 * 查看用户是否符合渠道的推广条件
	 * @param wxMpUser
	 * @param channel
	 * @return	true:wxMpUser不符合渠道条件   false:wxMpUser符合渠道条件
	 */
	private boolean filterScanUser(WxMpUser wxMpUser,AppChannel channel) {
		// 是否过期判断
		if (channel.getExpireDate() != null && channel.getExpireDate().before(new Date())) {
			return true;
		}
		// 查看订阅数量是否超过限定推广人数了
		Long maxNum = channel.getMaxNum();
		if (maxNum != null && maxNum > 0 ) {
			if (subscribeSuccService.succCount(channel.getAppId()) >= maxNum ) {
				return true;
			}
		}
		// 检查过滤性别，只有在filterSex中的才能通过
		String filterSex = channel.getFilterSex();
		if (StringUtils.isNotBlank(filterSex) && (StringUtils.isBlank(wxMpUser.getSex())
				|| !StringUtils.equalsAny(wxMpUser.getSex(), filterSex.split(",")))) {
			return true;
		}
		// 检查过滤城市，只有在filterCity中的才能通过
		String filterCity = channel.getFilterCity();
		if (StringUtils.isNotBlank(filterCity) && (StringUtils.isBlank(wxMpUser.getCity())
				|| !StringUtils.equalsAny(wxMpUser.getCity(), filterCity.split(",")))) {
			return true;
		}
		// 检查过滤国家，只有在filterCountry中的才能通过
		String filterCountry = channel.getFilterCountry();
		if (StringUtils.isNotBlank(filterCountry) && (StringUtils.isBlank(wxMpUser.getCountry())
				|| !StringUtils.equalsAny(wxMpUser.getCountry(), filterCountry.split(",")))) {
			return true;
		}
		// 检查过滤省份，只有在filterProvince中的才能通过
		String filterProvince = channel.getFilterProvince();
		if (StringUtils.isNotBlank(filterProvince) && (StringUtils.isBlank(wxMpUser.getProvince())
				|| !StringUtils.equalsAny(wxMpUser.getProvince(), filterProvince.split(",")))) {
			return true;
		}
		// 检查过滤语言，只有在filterLanguage中的才能通过
		String filterLanguage = channel.getFilterLanguage();
		if (StringUtils.isNotBlank(filterLanguage) && (StringUtils.isBlank(wxMpUser.getLanguage())
				|| !StringUtils.equalsAny(wxMpUser.getLanguage(), filterLanguage.split(",")))) {
			return true;
		}
		
		return false;
	}

	/**
	 * 根据条件查询产品渠道信息
	 * @param pageInt
	 * @param pageSizeInt
	 * @param criteria
	 * @return
	 */
	public PageInfo<AppChannel> findByCriteria(int page, int pageSize, AppChannel criteria,Integer priceMin,Integer priceMax) {
		PageHelper.startPage(page, pageSize);
		
		List<AppChannel> list = appChannelMapper.selectListByCriteria(criteria,priceMin,priceMax);
		PageInfo<AppChannel> result = new PageInfo<>(list);
		
		return result;
	}

	/**
	 * 根据产品ID和场景值唯一查找渠道
	 * @param appId
	 * @param sceneValue
	 * @return
	 */
	public AppChannel findByAppIdAndSceneValue(Long appId, String sceneValue) {
		return appChannelMapper.selectByAppIdAndSceneValue(appId, sceneValue);
	}
	
}
