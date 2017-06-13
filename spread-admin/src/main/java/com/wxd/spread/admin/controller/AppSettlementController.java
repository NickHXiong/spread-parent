package com.wxd.spread.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppSettlement;
import com.wxd.spread.core.service.AppService;
import com.wxd.spread.core.service.AppSettlementService;
import com.wxd.spread.core.utils.DateUtils;

@Controller
@RequestMapping("/appSettlement")
public class AppSettlementController {
	
	@Autowired
	private AppSettlementService appSettlementService;
	@Autowired
	private AppService appService;
	
	@RequestMapping("/list")
	public ModelAndView list(Integer page,Integer pageSize,Long appId,String dateFrom,String dateTo) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		if (appId != null && appId <= 0 ) {
			appId = null;
		}
		
		Date fDate = DateUtils.format(dateFrom, DateUtils.DATE);
		Date tDate = DateUtils.format(dateTo, DateUtils.DATE);
		AppSettlement criteria = new AppSettlement();
		criteria.setAppId(appId);
		criteria.setDateFrom(fDate);
		criteria.setDateEnd(tDate);
		
		PageInfo<AppSettlement> pageInfo = appSettlementService.findListByCriteria(pageInt, pageSizeInt, criteria);
		List<App> appList = appService.listAbledOrderName();
		
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("appList", appList);
		
		mv.setViewName("system/settlement_list");
		return mv;
	}
	
	
	/**
	 * 跳转到添加系统结算页面
	 * @return
	 */
	@RequestMapping(path="/add",method=RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		
		List<App> appList = appService.listAllOrderName();
		mv.addObject("appList", appList);
		mv.setViewName("system/settlement_add");
		return mv;
	}
	
	/**
	 * 添加结算操作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/add",method=RequestMethod.POST)
	public Map<String,Object> addDo(AppSettlement appSettlement) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		if (appSettlement.getAppId() == null || appSettlement.getAppId()<= 0|| appSettlement.getAppChannelId() == null || appSettlement.getAppChannelId()<= 0 || appSettlement.getAmountFee() == null || appSettlement.getAmountFee() <= 0 || appSettlement.getDateFrom() == null || appSettlement.getDateEnd() == null || (appSettlement.getDateFrom().after(appSettlement.getDateEnd()))) {
			result.put("success", false);
			result.put("msg", "结算失败，参数错误");
			return result;
		}
		
		appSettlement.setAdminId(-1L);
		
		try {
			boolean settlement = appSettlementService.settlement(appSettlement);
			if (!settlement) {
				result.put("success", false);
				result.put("msg", "结算失败，请联系管理员");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "结算失败，数据库操作异常");
			return result;
		}
		
		result.put("success", true);
		return result;
	}
}
