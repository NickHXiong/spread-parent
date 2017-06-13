package com.wxd.spread.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.service.AppService;
/**
 * 产品控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppService appService;
	
	/**
	 * 产品列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Integer page,Integer pageSize,String appName,String companyName,Integer disabled) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		Boolean disabledFlag = null;
		if (disabled != null) {
			if (disabled == 0) {
				disabledFlag = false;
			} else if (disabled == 1) {
				disabledFlag = true;
			}
		}
		
		PageInfo<App> pageInfo = appService.list(pageInt, pageSizeInt, null,appName,companyName,disabledFlag);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/app_list");
		return mv;
	}
	
	/**
	 * 修改产品的状态
	 * @param id
	 * @param status	true:禁用产品  false:启用产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeDisableStatus")
	public Map<String,Object> changeDisableStatus(Long id,Boolean status) {
		Map<String,Object> result = new HashMap<String,Object>();
		if (id == null || status == null) {
			result.put("success", false);
			result.put("msg", "参数不完整");
		}
		
		App app = appService.findById(id);
		if (app == null) {
			result.put("success", false);
			result.put("msg", "对应产品不存在");
			return result;
		}
		
		// 状态不相同，直接修改
		if (app.getDisabled() != status) {
			boolean updateFlag = appService.updateDisabledStatus(id, status);
			if (!updateFlag) {
				result.put("success", false);
				result.put("msg", "数据库更新失败");
				return result;
			}
		} 
		result.put("success", true);
		
		return result;
	}
	
	
	/**
	 * 查询appid是否存在
	 * @param appid
	 * @return count  > 0 表示存在
	 */
	@ResponseBody
	@RequestMapping("/appidIsExist")
	public Map<String,Object> appidIsExist(String appid) {
		Map<String,Object> result = new HashMap<>();
		result.put("success", true); // 业务调用成功
		App app = appService.findByAppid(appid);
		if (app != null) {
			result.put("count", 1); // 数量
		} else {
			result.put("count", 0); // 数量
		}
		return result;
	}
	
	/**
	 * 跳转到添加产品页面
	 * @return
	 */
	@RequestMapping(path = "/add",method=RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/app_add");
		return mv;
	}
	
	
	/**
	 * 跳转到添加产品页面
	 * @return
	 */
	@RequestMapping(path = "/edit",method=RequestMethod.GET)
	public ModelAndView edit(Long id) {
		ModelAndView mv = new ModelAndView();
		
		App app = appService.findById(id);
		mv.addObject("app", app);
		mv.setViewName("app/app_edit");
		return mv;
	}
	
	/**
	 * 添加产品动作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/add",method=RequestMethod.POST)
	public Map<String,Object> addDo(String appid,String appName,String companyName,Integer priority,String whiteInvokeUrls) {
		Map<String,Object> result = new HashMap<String,Object>();
		// 检查参数
		if (StringUtils.isAnyBlank(appid,appName,companyName)) {
			result.put("success", false);
			result.put("msg", "参数不能为空");
			return result;
		}
		// 检查appid是否重复
		App app = appService.findByAppid(appid);
		if (app != null) {
			result.put("success", false);
			result.put("msg", "appid在系统中已经重复存在");
			return result;
		}
		
		Long appId = appService.insertApp(appid, appName, whiteInvokeUrls, companyName, priority);
		if (appId != null) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		
		return result;
	}
	
	/**
	 * 添加产品动作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/edit",method=RequestMethod.POST)
	public Map<String,Object> editDo(Long id,String appName,String companyName,Integer priority,String whiteInvokeUrls,Boolean disabled) {
		Map<String,Object> result = new HashMap<String,Object>();
		// 检查参数
		if (StringUtils.isAnyBlank(appName,companyName) || id == null) {
			result.put("success", false);
			result.put("msg", "参数不能为空");
			return result;
		}
		// 检查appid是否重复
		App app = appService.findById(id);
		if (app == null) {
			result.put("success", false);
			result.put("msg", "系统错误，请尝试重新进入此页面");
			return result;
		}
		
		boolean updateApp = appService.updateApp(id, appName, whiteInvokeUrls, companyName, priority, disabled == null?app.getDisabled():disabled);
		if (updateApp) {
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "系统错误，数据库更新失败");
		}
		
		return result;
	}
}
