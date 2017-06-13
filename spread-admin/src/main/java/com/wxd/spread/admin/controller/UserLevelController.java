package com.wxd.spread.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wxd.spread.core.model.UserLevel;
import com.wxd.spread.core.service.UserLevelService;

/**
 * 用户等级控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/userLevel")
public class UserLevelController {

	@Autowired
	private UserLevelService userLevelService;
	
	/**
	 * 查询用户等级列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		
		List<UserLevel> list = userLevelService.list();
		mv.addObject("list", list);
		mv.setViewName("user/user_level_list");
		return mv;
	}
}
