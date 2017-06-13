package com.wxd.spread.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.WechatEventMessage;
import com.wxd.spread.core.model.WechatEventMessage.MsgTypeEnum;
import com.wxd.spread.core.model.WechatEventMessage.WechatEventEnum;
import com.wxd.spread.core.service.WechatEventMessageService;
import com.wxd.spread.core.utils.DateUtils;

/**
 * 用户消息控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/wechatEventMessage")
public class WechatEventMessageController {

	@Autowired
	private WechatEventMessageService wechatEventMessageService;
	/**
	 * 用户消息列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, Integer page, Integer pageSize, String openid, String msgType,
			String wechatEvent, Integer read, String startDate, String endDate) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		Date sDate = DateUtils.format(startDate, DateUtils.DATE);
		Date eDate = DateUtils.format(endDate, DateUtils.DATE);
		if (eDate != null) {
			eDate = DateUtils.formatDayEnd(eDate);
		}
		WechatEventMessage criteria = new WechatEventMessage();
		criteria.setOpenid(StringUtils.defaultIfBlank(openid,null));
		criteria.setMsgType(StringUtils.defaultIfBlank(msgType,null));
		criteria.setWechatEvent(StringUtils.defaultIfBlank(wechatEvent,null));
		Boolean readFlag = null;
		if (read != null) {
			readFlag = (read == 1?true:false);
		}
		criteria.setRead(readFlag);
		
		PageInfo<WechatEventMessage> pageInfo = wechatEventMessageService.findListByCriteria(pageInt, pageSizeInt, criteria, sDate, eDate);
		
		MsgTypeEnum[] msgTypes = WechatEventMessage.MsgTypeEnum.values();
		WechatEventEnum[] wechatEvents = WechatEventMessage.WechatEventEnum.values();
		
		mv.addObject("msgTypes", msgTypes);
		mv.addObject("wechatEvents", wechatEvents);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("user/wechat_event_message_list");
		return mv;
	}
	
	/**
	 * 消息详情
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView detail(Long id) {
		ModelAndView mv = new ModelAndView();
		
		WechatEventMessage msg = wechatEventMessageService.findById(id);
		MsgTypeEnum[] msgTypes = WechatEventMessage.MsgTypeEnum.values();
		WechatEventEnum[] wechatEvents = WechatEventMessage.WechatEventEnum.values();
		
		mv.addObject("msgTypes", msgTypes);
		mv.addObject("wechatEvents", wechatEvents);
		mv.addObject("msg", msg);
		mv.setViewName("user/wechat_event_message_detail");
		
		return mv;
	}
}
