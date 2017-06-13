package com.wxd.spread.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wxd.spread.core.model.SysTemplateMessage;
import com.wxd.spread.core.service.SysTemplateMessageService;

@Controller
@RequestMapping("/sysTplMsg")
public class SysTemplateMessageController {
	
	@Autowired
	private SysTemplateMessageService sysTemplateMessageService;
	
	/**
	 * 系统模板列表页面
	 * @param request
	 * @param page	当前页码
	 * @param pageSize	页大小
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		List<SysTemplateMessage> list = sysTemplateMessageService.list(null);
		mv.addObject("list", list);
		
		mv.setViewName("system/sys_template_msg_list");
		return mv;
	}
}
