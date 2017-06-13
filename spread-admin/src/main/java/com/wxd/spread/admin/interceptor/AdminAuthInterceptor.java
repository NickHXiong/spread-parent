package com.wxd.spread.admin.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wxd.spread.admin.constant.Constant;
import com.wxd.spread.admin.util.RequestUtils;
import com.wxd.spread.core.model.Admin;

/**
 * 用户管理员认证过滤器
 * @author wangxiaodan
 *
 */
@Component("adminAuthInterceptor")
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = Logger.getLogger(getClass());
	
	
	
	/**
	 * 查看用户是否存在，如果不存在则重定向到首页登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/*Admin admin = (Admin)request.getSession().getAttribute(Constant.SESSION_ADMIN_USER_KEY);
		if (admin == null) {
			logger.info("用户不存在，重定向到首页登录页");
			// 重定向到首页
			response.sendRedirect(Constant.DEFAULT_CONTEXT_PATH + "/index.html");
			return false;
		}*/
		// 设置参数回传
		Map<String, Object> params = RequestUtils.getAllParams(request);
		request.setAttribute("_params", params);
		return true;
	}
}
