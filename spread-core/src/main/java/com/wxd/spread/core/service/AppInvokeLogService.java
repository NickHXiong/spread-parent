package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.AppInvokeLogMapper;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.model.AppInvokeLog;
import com.wxd.spread.core.model.PushLog;
import com.wxd.spread.core.model.ScanUser;
import com.wxd.spread.core.model.SubscribeSucc;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserChannel;
import com.wxd.spread.core.model.UserLevel;
import com.wxd.spread.core.utils.DateUtils;
import com.wxd.spread.core.utils.ExecutorServiceUtil;

@Service
public class AppInvokeLogService {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SubscribeSuccService subscribeSuccService;
	@Autowired
	private AppService appService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserChannelService userChannelService;
	@Autowired
	private ScanUserService scanUserService;
	@Autowired
	private UserLevelService userLevelService;
	@Autowired
	private AppChannelService appChannelService;
	@Autowired
	private PushLogService pushLogService;
	@Autowired
	private UserIncomeService userIncomeService;
	@Autowired
	private AppInvokeLogMapper appInvokeLogMapper;
	
	/**
	 * 通过主键查找回调信息
	 * @param id
	 * @return
	 */
	public AppInvokeLog findById(Long id) {
		if (id != null) {
			return appInvokeLogMapper.selectById(id);
		}
		return null;
	}
	
	
	
	/**
	 * 订阅公众号成功后的回调异步处理
	 * - 回调记录信息插入数据库保存，查询是否是重复回调（4个业务值都相同的回调）， 重复则停止下面的流程
     * - 查询订阅记录是否已经存在，插入订阅记录成功信息到数据库 存在则停止下面的流程
     * - 查找最接近（1分钟内）日期且success=false的推送日志,标记为此用户的渠道订阅成功（关键核心，这个还需要设计其他条件）
     * - 将上条记录修改为success=true，查询失败则停止下面的流程
     * - 修改用户余额、新增用户收入记录 （事务）
     * 
	 * @param appid
	 * @param openid
	 * @param sceneValue
	 * @param invokeTime	用户关注公众号的微信时间戳
	 */
	public void invokeCallAsyncHandle(final String appid,final String openid,final String sceneValue,final long invokeTime) {
		ExecutorServiceUtil.execute(new Runnable() {
			@Override
			public void run() {
				// 查询是否已经回调过
				long count = appInvokeLogMapper.countByAppidAndOpenidAndSceneValueAndInvokeTime(appid, openid, sceneValue, invokeTime);
				// 插入本次回调
				AppInvokeLog appInvokeLog = new AppInvokeLog();
				appInvokeLog.setAppid(appid);
				appInvokeLog.setOpenid(openid);
				appInvokeLog.setSceneValue(sceneValue);
				appInvokeLog.setInvokeTime(invokeTime);
				appInvokeLog.setCreateTime(new Date());
				appInvokeLogMapper.insert(appInvokeLog);
				if (count > 0) {
					return;
				}
				
				long succCount = subscribeSuccService.countSelective(openid, null, appid, true, null);
				if (succCount > 0) {
					return ;
				}
				long subCount = subscribeSuccService.countSelective(openid, null, appid, null, null);
				App app = appService.findByAppid(appid);
				boolean subFlag = false;
				if (subCount > 0) { // 更新
					subFlag = subscribeSuccService.updateSuccByOpenidAndAppid(openid,appid);
				} else { // 新增
					SubscribeSucc subscribeSucc = new SubscribeSucc();
					subscribeSucc.setAppId(app.getId());
					subscribeSucc.setOpenid(openid);
					subscribeSucc.setAppid(appid);
					subscribeSucc.setSuccess(true);
					subscribeSucc.setPushLimit(false);
					subscribeSucc.setCreateTime(new Date());
					subFlag = subscribeSuccService.insert(subscribeSucc) != null;
				}
				
				if (!subFlag) {
					logger.error("订阅成功信息成功，但是操作订阅信息失败，业务终止[appid:"+appid+",openid:"+openid+",sceneValue:"+sceneValue+",invokeTime:"+invokeTime+"]");
					return;
				}
				
				// 这里是将微信的长整型日志转换为java日期
				Date invokeDate = DateUtils.wechatTimestamp2Date(invokeTime);
				
				PushLog pushLog = pushLogService.findUnsuccFirstPushLog(app.getId(), openid, invokeDate);
				if (pushLog == null) { // 不是任何渠道推广出去的，平台赚到
					return ;
				}
				// 判断是否是1分钟内关注,如果推码一分钟后关注的，则不计入推广中
				if (DateUtils.minuteOffsets(pushLog.getCreateTime(),1).before(invokeDate)){
					return ;
				}
				
				// 上面条件通过，更新本次为成功，并给用户分成推广费
				boolean updateFlag = pushLogService.updateSuccById(pushLog.getId());
				if (!updateFlag) {
					logger.info("推广信息的订阅成功更新失败[pushLogId:"+pushLog.getId()+"]");
				}
				// 获取各种需要的对象
				// 获取产品渠道
				AppChannel appChannel = appChannelService.findById(pushLog.getAppChannelId());
				if (appChannel == null) { // 渠道部存在，数据肯定是错误的，返回
					return;
				}
				// 获取用户
				User user = userService.findById(pushLog.getUserId());
				if (user == null) {
					return;
				}
				// 用户分成等级
				UserLevel userLevel = userLevelService.findById(user.getUserLevelId());
				if (userLevel == null) {
					return;
				}
				// 用户渠道
				UserChannel userChannel = userChannelService.findById(pushLog.getUserChannelId());
				if(userChannel == null) {
					return;
				}
				// 扫码用户
				ScanUser scanUser = scanUserService.findByOpenid(openid);
				if (scanUser == null ) {
					return;
				}
				
				// 计算推广费,单位分
				long fee = appChannel.getPrice() * appChannel.getPercentage() / 100 * userLevel.getPercentage() / 100;
				try {
					userIncomeService.userIncomeHandle(fee,userChannel,app,appChannel,scanUser,invokeTime);
				}catch(Exception ex) {
					logger.error("用户账户计入推广渠道费失败，重要，务必帮渠道用户追回金额[fee:"+fee+",pushLog:"+pushLog+"]");
					ex.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * 根据条件查询回调信息列表
	 * @param appInvokeLog.appid
	 * @param appInvokeLog.openid
	 * @param appInvokeLog.sceneValue
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PageInfo<AppInvokeLog> findListByCriteria(int page,int pageSize,AppInvokeLog appInvokeLog, Date startDate,
			Date endDate) {
		PageHelper.startPage(page, pageSize);
		AppInvokeLog ail = null;
		if (appInvokeLog == null) {
			ail = new AppInvokeLog();
		} else {
			ail = appInvokeLog;
		}
		List<AppInvokeLog> list =  appInvokeLogMapper.selectListByCriteria(ail, startDate, endDate);
		
		PageInfo<AppInvokeLog> result = new PageInfo<>(list);
		return result;
	}


}
