package com.wxd.spread.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.wxd.spread.admin.constant.Constant;
import com.wxd.spread.core.model.Admin;
import com.wxd.spread.core.service.AdminService;
import com.wxd.spread.core.utils.MD5;

@Controller
@RequestMapping("/login")
public class LoginController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private AdminService adminService;
	
	/**
	 * 管理员认证
	 * @param request
	 * @param account	账号/邮箱/电话
	 * @param password	密码
	 * @param nonce	验证码
	 * @return	{success:true/false,msg:'操作说明'}
	 */
	@ResponseBody
	@PostMapping(produces = "application/json; charset=UTF-8")
	public Map<String,Object> authAdmin(HttpServletRequest request, String account, String password,  String nonce) {
		boolean success = true;
		String errMsg = null;
		if (StringUtils.isAnyBlank(account,password,nonce)) {
			success = false;
			errMsg = "参数错误";
		}
		
		HttpSession session = request.getSession();
		String verifyCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (success && !StringUtils.equalsIgnoreCase(verifyCode, nonce)) {
			session.removeAttribute(Constants.KAPTCHA_SESSION_KEY); // 移除当次的验证码重新生成
			success = false;
			errMsg = "验证码不正确";
		}
		
		if (success) {
			String pwd = MD5.getMD5(password);
			Admin admin = adminService.findByAccountOrMobileOrEmailAndPwd(account, account, account, pwd);
			if (admin != null) {
				session.setAttribute(Constant.SESSION_ADMIN_USER_KEY, admin);
				logger.info("管理员登录成功{account:"+admin.getAccount()+"}");
			} else {
				success = false;
				errMsg = "账号或密码不正确";
			}
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("msg", errMsg == null ? "" : errMsg);
		
		return result;
	}
}
