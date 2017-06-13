package com.wxd.spread.admin.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wxd.spread.admin.util.ExcelReader;
import com.wxd.spread.admin.util.PageUtils;
import com.wxd.spread.core.model.AppInvokeLog;
import com.wxd.spread.core.service.AppInvokeLogService;

/**
 * 产品回调日志记录控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/invoke")
public class InvokeController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AppInvokeLogService appInvokeLogService;
	
	/**
	 * 产品回调记录列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Integer page,Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		
		int pageInt = PageUtils.getPage(page);
		int pageSizeInt = PageUtils.getPageSize(pageSize);
		
		PageInfo<AppInvokeLog> pageInfo = appInvokeLogService.findListByCriteria(pageInt, pageSizeInt, null,null,null);
		mv.addObject("pageInfo", pageInfo);
		
		mv.setViewName("system/invoke_list");
		return mv;
	}
	
	/**
	 * 上传推广成功的文件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/upload",method=RequestMethod.POST)
	public Map<String,Object> uploadInvokeFile(@RequestParam("settleFile") MultipartFile file) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		if (!file.isEmpty()) {
			try {
				ExcelReader reader = new ExcelReader();
				String[] title = reader.readExcelTitle(file.getInputStream());
				logger.info("导入回调信息。 title -> " + StringUtils.join(title,','));
				
				Map<Integer, String> mapContent = reader.readExcelContent(file.getInputStream());
				String appid = null;
				String openid = null;
				String sceneValue = null;
				Long invokeTime = null;
				for (Entry<Integer, String> entry:mapContent.entrySet()) {
					String value = entry.getValue();
					logger.info("导入回调信息。 value ->" + value);
					
					String[] split = value.split("\\s+");
					if (split.length < 4) {
						result.put("success", false);
						result.put("msg", "导入失败，内容信息不完整");
					}
					
					try {
						appid = split[0];
						openid = split[1];
						sceneValue = split[2];
						BigDecimal bd = new BigDecimal(split[3]);
						invokeTime = bd.longValue();
					} catch(Exception e) {
						e.printStackTrace();
						result.put("success", false);
						result.put("msg", "导入失败，内容信息不完整");
					}
					appInvokeLogService.invokeCallAsyncHandle(appid, openid, sceneValue, invokeTime);
				}
			}catch (Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("msg", "导入失败，格式有误");
			}
		} else {
			result.put("success", false);
			result.put("msg", "导入失败，未发现文件");
		}
		result.put("success", true);
		
		return result;
	}
	
	
}
