package com.wxd.spread.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.SubscribeSucc;
import com.wxd.spread.core.service.SubscribeSuccService;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController {
	
	@Autowired
	private SubscribeSuccService subscribeSuccService;
	
	/**
	 * 订阅成功列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		PageInfo<SubscribeSucc> pageInfo = subscribeSuccService.findListByCriteria(pageInt, pageSizeInt,null,null,null);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/subscribe_list");
		return mv;
	}
}
