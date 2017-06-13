package com.wxd.spread.core.wechat.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.model.SysTemplateMessage;
import com.wxd.spread.core.wechat.SysTemplateMsgSender;

@Component("firstSender")
public class FirstSender implements SysTemplateMsgSender {
	@Resource(name="myBalanceSender")
	private SysTemplateMsgSender nextSender;

	@Override
	public void sendWechatTemplateMsg(SysTemplateMessage msg,String openid) {
		if (msg == null) {
			return;
		}
		nextSender.sendWechatTemplateMsg(msg,openid);
	}

	@Override
	public SysTemplateMessage findByMenuKey(String menuKey) {
		if (StringUtils.isBlank(menuKey)) {
			return null;
		}
		return nextSender.findByMenuKey(menuKey);
	}

}
