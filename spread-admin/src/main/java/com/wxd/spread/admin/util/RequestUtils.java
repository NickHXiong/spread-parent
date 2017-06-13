package com.wxd.spread.admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.web.util.WebUtils;

public class RequestUtils {
	
	/**
	 * 获取所有request的参数构造成map
	 * @param request
	 * @return
	 */
	public static Map<String,Object> getAllParams(ServletRequest request) {
		if (request == null) {
			return new HashMap<String,Object>();
		}
		return WebUtils.getParametersStartingWith(request, null);
	}
	
	/**
	 * 获取以某些前缀开头的所有request的参数值构造成数值返回
	 * @param request
	 * @return
	 */
	public static String[] getParametersStartingWithValuesList(ServletRequest request, String prefix) {
		if (request == null) {
			return null;
		}
		Map<String, Object> parametersStartingWith = WebUtils.getParametersStartingWith(request, prefix);
		if (parametersStartingWith.size() > 0) {
			Collection<Object> values = parametersStartingWith.values();
			List<String> list = new ArrayList<String>();
			for (Object obj : values) {
				if (obj instanceof String) {
					list.add((String) obj);
				} else {
					Collections.addAll(list, (String[]) obj);
				}
			}
			return list.toArray(new String[list.size()]);
		}
		return null;
	}
}
