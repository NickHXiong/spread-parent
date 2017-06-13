package com.wxd.spread.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.ScanUser;
import com.wxd.spread.core.service.ScanUserService;

@Controller
@RequestMapping("/scanUser")
public class ScanUserController {
	

	@Autowired
	private ScanUserService scanUserService;
	
	/**
	 * 产品列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		PageInfo<ScanUser> pageInfo = scanUserService.findListByCriteria(pageInt, pageSizeInt, null,null,null);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/scan_user_list");
		return mv;
	}

}
