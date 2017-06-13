package com.wxd.spread.core.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.service.UserService;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {
	@Autowired
	private UserService userService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // 更新本地数据库为取消关注状态
        userService.updateUserUnsubscribe(openId);
        return null;
    }

}
