package com.wxd.spread.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserChannel;
import com.wxd.spread.core.model.UserIncome;
import com.wxd.spread.core.service.UserChannelService;
import com.wxd.spread.core.service.UserIncomeService;
import com.wxd.spread.core.service.UserService;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userSerivce;
	@Autowired
	private UserIncomeService userIncomeService;
	@Autowired
	private UserChannelService userChannelService;

	/**
	 * 用户请求获取自己的用户信息
	 * @param openid	用户的openId
	 * @param cId		用户的渠道ID
	 * @return
	 */
	@RequestMapping("/channel_qr")
	public ModelAndView channel_qr(HttpServletRequest request,Long cId) {
		ModelAndView mv = new ModelAndView();
		
		// 拦截器中已经设置了用户授权
		WxMpUser wxMpUser = (WxMpUser)request.getAttribute("wxMpUser");
		
		String openid = wxMpUser.getOpenId();
		User user = null;
		UserChannel userChannel = null;
		if (StringUtils.isNotBlank(openid)) {
			user = userSerivce.findByOpenid(openid);
		}
		if (user != null) { // 用户存在
			if (cId != null) {
				userChannel = userChannelService.findById(cId);
			}
			if (userChannel == null) {
				userChannel = userChannelService.findBaseChannel(user.getId());
			}
			mv.addObject("user", user);
			mv.addObject("userChannel", userChannel);
			mv.setViewName("user_channel_qr");
		} else {
			// 未关注
			mv.setViewName("not_subscribe");
		}
		
		return mv;
	}
	
	
	
	/**
	 * 用户收入详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/income/details")
	public ModelAndView incomeDetails(HttpServletRequest request,Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		// 拦截器中已经设置了用户授权
		WxMpUser wxMpUser = (WxMpUser)request.getAttribute("wxMpUser");
		
		String openid = wxMpUser.getOpenId();
		User user = userSerivce.findByOpenid(openid);
		if (user != null) { // 用户存在
			int pageInt = page != null?page:DEFAULT_PAGE;
			if (pageInt < 1) {
				pageInt = DEFAULT_PAGE;
			}
			int pageSizeInt = pageSize != null?pageSize:DEFAULT_PAGE_SIZE;
			if (pageSizeInt < 1) {
				pageSizeInt = DEFAULT_PAGE_SIZE;
			}
			// 查找收入列表
			PageInfo<UserIncome> pageInfo = userIncomeService.list(pageInt, pageSizeInt, user.getId(), null, null, null, null, null, null);
			mv.addObject("pageInfo", pageInfo);
			
			mv.setViewName("user_income_details");
		} else {
			// 未关注
			mv.setViewName("not_subscribe");
		}
		
		return mv;
	}
	
	/**
	 * 默认起始页码
	 */
	private static final Integer DEFAULT_PAGE = 1;
	/**
	 * 默认分页大小
	 */
	private static final Integer DEFAULT_PAGE_SIZE = 20;
	
}
