package com.wxd.spread.core.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wxd.spread.core.model.SysTemplateMessage;
import com.wxd.spread.core.utils.ExecutorServiceUtil;
import com.wxd.spread.core.wechat.SysTemplateMsgSender;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class MenuHandler extends AbstractHandler {
	@Resource(name="firstSender")
	private SysTemplateMsgSender sysTemplateMsgSender;
    @Override
    public WxMpXmlOutMessage handle(final WxMpXmlMessage wxMessage,
            Map<String, Object> context, final WxMpService weixinService,
            WxSessionManager sessionManager) {
        
        if (WxConsts.BUTTON_VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        
        final SysTemplateMessage templateMsg = sysTemplateMsgSender.findByMenuKey(wxMessage.getEventKey());
        if (templateMsg != null) {
        	if (templateMsg != null) {
        		if (templateMsg.getTemplate() != null && templateMsg.getTemplate()) {
        			// 是微信模板消息，异步发送微信模板消息
        			ExecutorServiceUtil.execute(new Runnable() {
						@Override
						public void run() {
							sysTemplateMsgSender.sendWechatTemplateMsg(templateMsg, wxMessage.getFromUser());
						}
					});
        		} else {
        			return WxMpXmlOutMessage.TEXT().content(templateMsg.getContent())
                            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                            .build();
        		}
        	}
        }

        return null;
    }

}
