package com.wxd.spread.core.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.builder.TextBuilder;
import com.wxd.spread.core.service.UserService;
import com.wxd.spread.core.utils.ExecutorServiceUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author wxd
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	private UserService userService;
	
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        final WxMpUser userWxInfo = weixinService.getUserService()
            .userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            // 异步添加关注用户到本地
        	ExecutorServiceUtil.execute(new Runnable() {
				@Override
				public void run() {
					try {
						userService.initUser(userWxInfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage,weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(final WxMpXmlMessage wxMessage, final WxMpService weixinService)
            throws Exception {
        return null;
    }

}
