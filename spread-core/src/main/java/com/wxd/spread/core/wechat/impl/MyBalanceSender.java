package com.wxd.spread.core.wechat.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.model.SysTemplateMessage;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.service.SysTemplateMessageService;
import com.wxd.spread.core.service.UserService;
import com.wxd.spread.core.service.WechatService;
import com.wxd.spread.core.wechat.SysTemplateMsgSender;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Component("myBalanceSender")
public class MyBalanceSender implements SysTemplateMsgSender {
	@Autowired
	private SysTemplateMessageService sysTemplateMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private WechatService weixinService;
	@Resource(name="aboutUsSender")
	private SysTemplateMsgSender nextSender;
	
	private boolean selfSupport(String templateKey) {
		if (StringUtils.equals(templateKey, Constant.SYS_TEMPLATE_SEARCH_BALANCE_KEY)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void sendWechatTemplateMsg(SysTemplateMessage msg,String openid) {
		if (selfSupport(msg.getTemplateKey())) {
			// 查询用户信息
			User user = userService.findByOpenid(openid);
			
			WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
			templateMessage.setToUser(openid);
			templateMessage.setTemplateId("3xH4pe00JZptL6AyY2Ei5gATG6f4kPtu_hQyDunjuMw");
			
			List<WxMpTemplateData> dataList = new ArrayList<WxMpTemplateData>();
			WxMpTemplateData data1 = new WxMpTemplateData();
			data1.setColor("#173177");
			data1.setName("name");
			data1.setValue(user.getNickname());
			dataList.add(data1);
			templateMessage.setData(dataList);
			try {
				weixinService.getTemplateMsgService().sendTemplateMsg(templateMessage);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		if (nextSender != null) {
			nextSender.sendWechatTemplateMsg(msg,openid);
		}
	}

	@Override
	public SysTemplateMessage findByMenuKey(String menuKey) {
		if (StringUtils.equals(menuKey, Constant.WECHAT_MENU_MY_BALANCE_KEY)) {
			return sysTemplateMessageService.findByTemplateKey(Constant.SYS_TEMPLATE_SEARCH_BALANCE_KEY);
		}
		if (nextSender != null) {
			return nextSender.findByMenuKey(menuKey);
		}
		return null;
	}

}
