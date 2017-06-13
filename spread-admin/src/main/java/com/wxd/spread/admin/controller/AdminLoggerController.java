package com.wxd.spread.admin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.AdminLogger;
import com.wxd.spread.core.model.AdminLogger.TypeEnum;
import com.wxd.spread.core.service.AdminLoggerService;
import com.wxd.spread.core.utils.DateUtils;

/**
 * 管理员日志控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/adminLogger")
public class AdminLoggerController {
	@Autowired
	private AdminLoggerService adminLoggerService;
	
	/**
	 * 管理员日志列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Integer page,Integer pageSize,Integer type,String startDate,String endDate) {
		ModelAndView mv = new ModelAndView();

		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		TypeEnum typeEnum = AdminLogger.TypeEnum.getEnum(type);
		Date sDate = DateUtils.format(startDate, DateUtils.DATE);
		Date eDate = DateUtils.format(endDate, DateUtils.DATE);
		if (eDate != null) {
			eDate = DateUtils.formatDayEnd(eDate);
		}
		
		PageInfo<AdminLogger> pageInfo = adminLoggerService.list(pageInt, pageSizeInt, null, typeEnum, sDate, eDate);
		
		TypeEnum[] types = AdminLogger.TypeEnum.values();
		
		mv.addObject("types", types);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("admin/admin_logger");
		return mv;
	}
}
