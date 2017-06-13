package com.wxd.spread.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.PushLog;
import com.wxd.spread.core.model.PushStatistics;
import com.wxd.spread.core.service.PushLogService;
import com.wxd.spread.core.service.PushStatisticsService;

@Controller
@RequestMapping("/push")
public class PushController {
	
	@Autowired
	private PushLogService pushLogService;
	@Autowired
	private PushStatisticsService pushStatisticsService;
	
	/**
	 * 推送日志列表
	 * @return
	 */
	@RequestMapping("/log_list")
	public ModelAndView logList(HttpServletRequest request,Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		PageInfo<PushLog> pageInfo = pushLogService.findListByCriteria(pageInt, pageSizeInt,null,null,null);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/log_list");
		return mv;
	}

	
	/**
	 * 推送统计列表
	 * @return
	 */
	@RequestMapping("/statistics_list")
	public ModelAndView statisticsList(HttpServletRequest request,Integer page,Integer pageSize,String openid) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		PageInfo<PushStatistics> pageInfo = pushStatisticsService.findListByCriteria(pageInt, pageSizeInt, openid);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/statistics_list");
		return mv;
	}

}
