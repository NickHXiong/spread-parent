package com.wxd.spread.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wxd.spread.core.model.SysConfig;
import com.wxd.spread.core.model.SysConfig.ValueTypeEnum;
import com.wxd.spread.core.service.SysConfigService;

/**
 * 系统配置控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 获取系统所有配置
	 * @return
	 */
	@RequestMapping("/info")
	public ModelAndView info() {
		ModelAndView mv = new ModelAndView();
		
		List<SysConfig> sysConfigs = sysConfigService.findAll();
		ValueTypeEnum[] valueTypes = SysConfig.ValueTypeEnum.values();
		
		mv.addObject("valueTypes", valueTypes);
		mv.addObject("sysConfigs", sysConfigs);
		mv.setViewName("system/sys_config");
		
		return mv;
	}
	
	/**
	 * 修改系统配置参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path= "/config",method=RequestMethod.POST)
	public Map<String,Object> config(String key,String value) {
		Map<String,Object> result = new HashMap<>();
		if (StringUtils.isAnyBlank(key,value)) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		
		SysConfig sysConfig = sysConfigService.findByKey(key);
		
		if (sysConfig == null) {
			result.put("success", false);
			result.put("msg", "键在系统中不存在");
			return result;
		}
		
		boolean updateFlag = sysConfigService.updateValueById(sysConfig.getId(), value);
		if (!updateFlag) {
			result.put("success", false);
			result.put("msg", "值在类型中不匹配");
			return result;
		}
		
		result.put("success", true);
		return result;
	}
}
