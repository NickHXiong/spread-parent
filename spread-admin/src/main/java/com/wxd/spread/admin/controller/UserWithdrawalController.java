package com.wxd.spread.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.UserWithdrawal;
import com.wxd.spread.core.model.UserWithdrawal.OperationRoleEnum;
import com.wxd.spread.core.model.UserWithdrawal.StatusEnum;
import com.wxd.spread.core.service.UserWithdrawalService;

/**
 * 用户提现控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/userWithdrawal")
public class UserWithdrawalController {
	
	@Autowired
	private UserWithdrawalService userWithdrawalService;
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		PageInfo<UserWithdrawal> pageInfo = userWithdrawalService.findListByCriteria(pageInt, pageSizeInt, null);
		
		OperationRoleEnum[] operationRoles = UserWithdrawal.OperationRoleEnum.values();
		StatusEnum[] statusEnums = UserWithdrawal.StatusEnum.values();
		mv.addObject("operationRoles", operationRoles);
		mv.addObject("statusEnums", statusEnums);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("user/user_withdrawal_list");
		return mv;
	}
}
