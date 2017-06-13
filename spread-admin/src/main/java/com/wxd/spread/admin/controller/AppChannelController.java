package com.wxd.spread.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.admin.util.RequestUtils;
import com.wxd.spread.core.model.App;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.service.AppChannelService;
import com.wxd.spread.core.service.AppService;

@Controller
@RequestMapping("/appChannel")
public class AppChannelController {
	

	@Autowired
	private AppChannelService appChannelService;
	@Autowired
	private AppService appService;
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, Integer page, Integer pageSize, Long appId, String sceneValue,
			String channelUrl, String ticket, Integer disabled, Integer priceMin, Integer priceMax) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		AppChannel criteria = new AppChannel();
		if (appId != null && appId > 0) {
			criteria.setAppId(appId);
		}
		criteria.setSceneValue(StringUtils.defaultIfBlank(sceneValue, null));
		criteria.setChannelUrl(StringUtils.defaultIfBlank(channelUrl, null));
		criteria.setTicket(StringUtils.defaultIfBlank(ticket, null));
		if (disabled != null) {
			if (disabled == 1) {
				criteria.setDisabled(true);
			} else if (disabled == 0) {
				criteria.setDisabled(false);
			}
		}
		
		PageInfo<AppChannel> pageInfo = appChannelService.findByCriteria(pageInt, pageSizeInt,criteria,priceMin,priceMax);
		
		List<App> apps = appService.listAbledOrderName();

		mv.addObject("appList", apps);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("app/channel_list");
		return mv;
	}
	
	/**
	 * 修改产品的状态
	 * @param id
	 * @param status	true:禁用产品渠道  false:启用产品渠道
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
		
		AppChannel appChannel = appChannelService.findById(id);
		if (appChannel == null) {
			result.put("success", false);
			result.put("msg", "对应产品渠道不存在");
			return result;
		}
		
		// 状态不相同，直接修改
		if (appChannel.getDisabled() != status) {
			boolean updateFlag = appChannelService.updateDisabledStatus(id, status);
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
	 * 获取产品下的渠道列表
	 * @param appId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getChannelListByAppId")
	public Map<String,Object> getChannelListByAppId(Long appId) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		List<AppChannel> list = appChannelService.list(appId);
		result.put("list", list);
		
		return result;
	}
	
	
	/**
	 * 跳转到添加产品渠道页面
	 * @return
	 */
	@RequestMapping(path = "/add",method=RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		
		List<App> apps = appService.listAbledOrderName();

		mv.addObject("appList", apps);
		mv.setViewName("app/channel_add");
		return mv;
	}

	
	/**
	 * 跳转到修改产品渠道页面
	 * @return
	 */
	@RequestMapping(path = "/edit",method=RequestMethod.GET)
	public ModelAndView edit(Long id) {
		ModelAndView mv = new ModelAndView();
		
		AppChannel appChannel = appChannelService.findById(id);
		App app = appService.findById(appChannel.getAppId());

		mv.addObject("app", app);
		mv.addObject("appChannel", appChannel);
		mv.setViewName("app/channel_edit");
		return mv;
	}
	
	/**
	 * 查询产品下是否存在此场景值
	 * @param appId
	 * @param sceneValue
	 * @return exist:true  存在   false:不存在
	 */
	@ResponseBody
	@RequestMapping("/isExistAppIdAndSceneValue")
	public Map<String,Object> isExistAppIdAndSceneValue(Long appId,String sceneValue) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("exist", false);
		
		AppChannel appChannel = appChannelService.findByAppIdAndSceneValue(appId,sceneValue);
		if (appChannel != null) {
			result.put("exist", true);
		}
		
		return result;
	}
	
	/**
	 * 添加产品渠道动作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/add",method=RequestMethod.POST)
	public Map<String,Object> addDo(HttpServletRequest request,AppChannel channel) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		String[]  filterSexArr = RequestUtils.getParametersStartingWithValuesList(request, "filterSex");
		if (filterSexArr != null) {
			channel.setFilterSex(StringUtils.join(filterSexArr));
		}
		
		// 检查参数
		if (StringUtils.isAnyBlank(channel.getSceneValue(),channel.getChannelUrl(),channel.getTicket()) || channel.getPrice() == null || channel.getAppId() == null || channel.getPrice() <= 0) {
			result.put("success", false);
			result.put("msg", "新增失败，参数错误");
			return result;
		}
		
		Long channelId = appChannelService.insertAppChannel(channel);
		if (channelId != null) {
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "新增失败，数据库操作错误");
		}
		
		return result;
	}
	
	/**
	 * 修改产品渠道动作
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/edit",method=RequestMethod.POST)
	public Map<String,Object> editDo(HttpServletRequest request,AppChannel channel) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		String[]  filterSexArr = RequestUtils.getParametersStartingWithValuesList(request, "filterSex");
		if (filterSexArr != null) {
			channel.setFilterSex(StringUtils.join(filterSexArr));
		}
		
		// 检查参数
		if (channel.getId() == null) {
			result.put("success", false);
			result.put("msg", "修改失败，参数错误");
			return result;
		}
		
		// 查找产品渠道是否存在，并检查优先级是否修改
		AppChannel appChannel = appChannelService.findById(channel.getId());
		if (appChannel == null) {
			result.put("success", false);
			result.put("msg", "修改失败，渠道不存在");
			return result;
		}
		
		if (channel.getPriority() != null && (appChannel.getPriority() == null || channel.getPriority().intValue() != appChannel.getPriority() )) {
			// 更新优先级
			appChannelService.updatePriority(channel.getId(), channel.getPriority());
		}
		
		// 更新无关紧要的过滤字段
		boolean updateFlag = appChannelService.updateFilterField(channel);
		if (updateFlag) {
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "修改失败，数据库操作错误");
		}
		return result;
	}
	
}
