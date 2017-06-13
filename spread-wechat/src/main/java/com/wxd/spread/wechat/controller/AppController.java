package com.wxd.spread.wechat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserLevel;
import com.wxd.spread.core.service.AppChannelService;
import com.wxd.spread.core.service.AppService;
import com.wxd.spread.core.service.ScanUserService;
import com.wxd.spread.core.service.UserLevelService;
import com.wxd.spread.core.service.UserService;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 产品相关controller
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/app")
public class AppController {
	@Autowired
	private AppChannelService appChannelService;
	@Autowired
	private AppService appService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserLevelService userLevelService;
	@Autowired
	private ScanUserService scanUserService;
	
	/**
	 * 用户请求获取自己的用户信息
	 * @param uccd	用户推广渠道码
	 * @return
	 */
	@RequestMapping("/channel_qr")
	public ModelAndView channel_qr(HttpServletRequest request,String uccd) {
		ModelAndView mv = new ModelAndView();
		WxMpUser wxMpUser = (WxMpUser)request.getAttribute("wxMpUser");
		try {
			// 异步插入用户
			scanUserService.insertAsync(wxMpUser);
			
			AppChannel appChannel = null;
			App app = null;
			appChannel = appChannelService.findSpreadChannel(wxMpUser, uccd);
			if (appChannel != null) {
				app = appService.findById(appChannel.getAppId());
				if (app != null) {
					mv.addObject("app", app);
				}
				mv.addObject("appChannel", appChannel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("app_channel_qr");
		return mv;
	}
	
	/**
	 * 所有可推广产品集合列表
	 * 用户存在则返回列表页面，否则返回到关注二维码页面
	 * @param request
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		WxMpUser wxMpUser = (WxMpUser)request.getAttribute("wxMpUser");
		
		String openid = wxMpUser.getOpenId();
		User user = userService.findByOpenid(openid);
		if (user != null) {
			List<Map<String,Object>> list = new ArrayList<>();
			// 用户等级
			UserLevel userLevel = userLevelService.findById(user.getUserLevelId());
			// 所有可用的产品列表
			List<App> appList = appService.listAbledOrderPriority();
			
			AppChannel appChannel = null;
			App app = null;
			Map<String,Object> map = null;
			Iterator<App> ite = appList.iterator();
			while (ite.hasNext()) {
				app = ite.next();
				appChannel = appChannelService.findFirstUsableChannel(app.getId());
				if (appChannel == null) {
					ite.remove();
					continue;
				}
				map = new HashMap<String,Object>();
				
				map.put("id", app.getId());
				map.put("appName", app.getAppName());
				map.put("companyName", app.getCompanyName());
				map.put("price", appChannel.getPrice() * appChannel.getPercentage() / 100 * userLevel.getPercentage() / 100);
				
				list.add(map);
			}
			
			mv.addObject("list", list);
			mv.setViewName("app_list");
		} else {
			mv.setViewName("not_subscribe");
		}
		return mv;
	}
}
