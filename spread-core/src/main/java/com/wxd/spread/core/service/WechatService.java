package com.wxd.spread.core.service;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxd.spread.core.handler.LogHandler;
import com.wxd.spread.core.handler.MenuHandler;
import com.wxd.spread.core.handler.SubscribeHandler;
import com.wxd.spread.core.handler.UnsubscribeHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
/**
 * 微信操作服务类
 * @author wangxiaodan
 *
 */
@Service
public class WechatService extends WxMpServiceImpl {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	protected LogHandler logHandler;
	@Autowired
	private SubscribeHandler subscribeHandler;
	@Autowired
	private UnsubscribeHandler unsubscribeHandler;
	@Autowired
	private MenuHandler menuHandler;
	@Autowired
	private WxMpConfigStorage wxMpConfigStorage;
	private WxMpMessageRouter router;

	@PostConstruct
	public void init() {
		super.setWxMpConfigStorage(wxMpConfigStorage);
		this.refreshRouter();
	}

	private void refreshRouter() {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);
		// 记录所有事件的日志
		newRouter.rule().handler(this.logHandler).next();
		// 关注事件，false表示是同步处理
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE)
				.handler(this.getSubscribeHandler()).end();
		// 取消关注事件，true表示是异步处理
		newRouter.rule().async(true).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE)
		.handler(this.getUnsubscribeHandler()).end();
		
		// 自定义菜单事件
	    newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
	        .event(WxConsts.BUTTON_CLICK).handler(this.getMenuHandler()).end();
		
		this.router = newRouter;
	}

	public WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
		return null;
	}


	private SubscribeHandler getSubscribeHandler() {
		return this.subscribeHandler;
	}
	private UnsubscribeHandler getUnsubscribeHandler() {
		return unsubscribeHandler;
	}
	
	private MenuHandler getMenuHandler() {
		return menuHandler;
	}
}
