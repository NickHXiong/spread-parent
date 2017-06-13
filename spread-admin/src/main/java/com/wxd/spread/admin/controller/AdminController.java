package com.wxd.spread.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.constant.Constant;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.admin.util.VerifyUtils;
import com.wxd.spread.core.model.Admin;
import com.wxd.spread.core.model.AdminLogger.TypeEnum;
import com.wxd.spread.core.service.AdminLoggerService;
import com.wxd.spread.core.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminLoggerService adminLoggerService;
	
	/**
	 * 管理员列表页面
	 * @param request
	 * @param page	当前页码
	 * @param pageSize	页大小
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,Integer page,Integer pageSize,String nickname,String trueName,String mobile,String email,String account) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		PageInfo<Admin> pageInfo = adminService.list(pageInt, pageSizeInt, nickname, trueName, mobile, email, account);
		
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("admin/admin_list");
		return mv;
	}
	
	
	/**
	 * 新增管理员页面
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/add",method=RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("flag", "add");
		mv.setViewName("admin/admin_edit");
		return mv;
	}
	

	/**
	 * 新增管理员操作
	 * @param request
	 * @param account	用户账号，必须是9位
	 * @param mobile	用户电话
	 * @param email		用户邮箱
	 * @param nickname	昵称
	 * @param trueName	真实姓名
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/add",method=RequestMethod.POST)
	public Map<String,Object> addDo(HttpServletRequest request,String account, String mobile, String email, String nickname, String trueName) {
		Map<String,Object> result = null;
		Admin admin = (Admin)request.getSession().getAttribute(Constant.SESSION_ADMIN_USER_KEY);
		
		result = checkoutParams(account, mobile, email, nickname, trueName);
		if (result != null) {
			return result;
		}
		result = new HashMap<String,Object>();
		
		Long id = adminService.insertNormalAdmin(account, mobile, email, nickname, trueName);
		if (id != null) {
			result.put("success", true);
			result.put("new_id", id);
			// 增加管理员日志
			adminLoggerService.syncInsert(admin==null?-1:admin.getId(), TypeEnum.ADD_ADMIN,
					String.format("新增管理员{id:%d,account:%s,mobile:%s,email:%s,nickname:%s,trueName:%s}", id, account,
							mobile, email, nickname, trueName));
		} else {
			result.put("success", false);
			result.put("msg", "账号、邮箱、手机电话必须在系统中唯一");
		}
		
		return result;
	}
	
	/**
	 * 对管理员进行更新操作
	 * @param request
	 * @param id
	 * @param nickname
	 * @param trueName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/edit",method=RequestMethod.POST)
	public Map<String,Object> editDo(HttpServletRequest request,Long id,String nickname, String trueName) {
		Map<String,Object> result = new HashMap<String,Object>();
		Admin admin = (Admin)request.getSession().getAttribute(Constant.SESSION_ADMIN_USER_KEY);
		if (id == null || StringUtils.isAnyBlank(nickname,trueName)) {
			result.put("success", false);
			result.put("msg", "参数不能为空");
			return result;
		}
		boolean updateAdminInfo = adminService.updateAdminInfo(id, nickname, trueName);
		if (updateAdminInfo) {
			result.put("success", true);
			// 修改管理员日志
			adminLoggerService.syncInsert(admin==null?-1:admin.getId(), TypeEnum.EDIT_ADMIN,
					String.format("修改管理员{id:%d,nickname:%s,trueName:%s}", id, nickname, trueName));
		} else {
			result.put("success", false);
			result.put("msg", "数据库更新操作失败");
		}
		return result;
	}
	
	/**
	 * 编辑管理员页面
	 * @param request
	 * @param id	管理员id
	 * @return
	 */
	@RequestMapping(path = "/edit",method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request,Long id) {
		ModelAndView mv = new ModelAndView();
		
		Admin admin = adminService.findById(id);
		
		mv.addObject("admin", admin);
		mv.addObject("flag", "edit");
		mv.setViewName("admin/admin_edit");
		return mv;
	}
	
	/**
	 * 修改管理员的可用状态
	 * @param id	管理员id
	 * @param status	修改后的状态  0：可用  1：禁用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/changeDisabledStatus",method=RequestMethod.POST)
	public Map<String,Object> changeDisabledStatus(HttpServletRequest request,Long id,Integer status) {
		// 初始化返回结果
		Map<String,Object> result = new HashMap<String,Object>(2);
		Admin admin = (Admin)request.getSession().getAttribute(Constant.SESSION_ADMIN_USER_KEY);
		
		boolean continueFlag = true;
		if (id == null || status == null) {
			result.put("msg", "参数不完整");
			result.put("success", false);
			return result;
		}
		Boolean disabledFlag = null;
		if (continueFlag) {
			if (status == 0) {
				disabledFlag = false;
			} else if (status == 1) {
				disabledFlag = true;
			} else {
				result.put("msg", "状态不正确");
				result.put("success", false);
				return result;
			}
		}
		
		
		boolean updateNormalAdminDisabledStatus = adminService.updateNormalAdminDisabledStatus(id, disabledFlag);
		if (updateNormalAdminDisabledStatus) { // 修改成功
			result.put("msg", "");
			result.put("success", true);
			// 增加管理员日志
			adminLoggerService.syncInsert(admin == null ? -1 : admin.getId(),
					disabledFlag ? TypeEnum.DISABLED_ADMIN : TypeEnum.ENABLED_ADMIN,
					String.format("修改管理员状态{id:%d,opt:%s}", id, disabledFlag ? "禁用" : "启用"));
		} else {
			result.put("msg", "修改失败");
			result.put("success", false);
		}
		
		return result;
	}
	
	
	private Map<String,Object> checkoutParams(String account, String mobile, String email, String nickname, String trueName) {
		Map<String,Object> result = new HashMap<String,Object>();
		// 参数检查
		if (StringUtils.isBlank(account) || !VerifyUtils.verifyAccount(account)) {
			result.put("success", false);
			result.put("msg", "账号格式错误");
			return result;
		}
		if (StringUtils.isBlank(mobile) || !VerifyUtils.isPhoneLegal(mobile)) {
			result.put("success", false);
			result.put("msg", "电话号码格式错误");
			return result;
		}
		
		if (StringUtils.isBlank(mobile) || !VerifyUtils.isPhoneLegal(mobile)) {
			result.put("success", false);
			result.put("msg", "电话号码格式错误");
			return result;
		}
		if (StringUtils.isBlank(email) || !VerifyUtils.checkEmail(email)) {
			result.put("success", false);
			result.put("msg", "邮箱格式错误");
			return result;
		}
		if (StringUtils.isBlank(nickname)) {
			result.put("success", false);
			result.put("msg", "昵称不能为空");
			return result;
		}
		if (StringUtils.isBlank(trueName)) {
			result.put("success", false);
			result.put("msg", "真实姓名不能为空");
			return result;
		}
		return null;
	}
}
