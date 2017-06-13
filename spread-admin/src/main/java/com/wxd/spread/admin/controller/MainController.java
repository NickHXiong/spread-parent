package com.wxd.spread.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页请求
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/main")
public class MainController {
	
	/**
	 * 首页控制台
	 * @return
	 */
	@RequestMapping()
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	
}
