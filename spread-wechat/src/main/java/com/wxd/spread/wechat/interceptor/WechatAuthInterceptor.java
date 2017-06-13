package com.wxd.spread.wechat.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxd.spread.core.service.WechatService;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Component("wechatAuthInterceptor")
public class WechatAuthInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = Logger.getLogger(getClass());
	/**
	 * 用于微信认证的status字符串参数
	 */
	private static final String STATE = UUID.randomUUID().toString();
	@Autowired
	private WechatService wechatService;
	/**
	 * 先进行微信认证
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		WxMpUser wxMpUser = (WxMpUser)session.getAttribute("wxMpUser");
		if (wxMpUser != null) { // 已经授权认证且会话存在
			request.setAttribute("wxMpUser", wxMpUser);
			return true;
		}
		
		if (StringUtils.equals(state, STATE) && StringUtils.isNotBlank(code)) {
			// 静默授权到此为止
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wechatService.oauth2getAccessToken(code);
			wxMpUser = wechatService.oauth2getUserInfo(wxMpOAuth2AccessToken,"zh_CN");
			logger.info("网页授权认证获取的用户信息：" + wxMpUser);
			request.setAttribute("wxMpUser", wxMpUser);
			session.setAttribute("wxMpUser", wxMpUser);
			/// 已经认证通过的时候直接去处理controller的方法
			return true;
		}
		
		 String curl = request.getRequestURL().toString();
         String queryString = request.getQueryString();
         if(StringUtils.isNotEmpty(queryString)){
             curl = curl + "?" + queryString;
         }
         //重定向到微信网页收取地址，获取code
		String wxOauthUrl = wechatService.oauth2buildAuthorizationUrl(curl,"snsapi_userinfo",STATE);
		response.sendRedirect(wxOauthUrl); // 重定向到微信网页收取地址
		
		return false;
	}
}
