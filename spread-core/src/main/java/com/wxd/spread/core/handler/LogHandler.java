package com.wxd.spread.core.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.service.WechatEventMessageService;
import com.wxd.spread.core.utils.JsonUtils;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class LogHandler extends AbstractHandler {
	@Autowired
	private WechatEventMessageService wechatEventMessageService;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		// 记录事件数据
		this.logger.info("\n接收到请求消息，内容：" + JsonUtils.toJson(wxMessage));
		wechatEventMessageService.recordMessage(wxMessage);
		return null;
	}

}
