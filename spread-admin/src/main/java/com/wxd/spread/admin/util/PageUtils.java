package com.wxd.spread.admin.util;

import com.wxd.spread.admin.constant.Constant;

public class PageUtils {
	
	/**
	 * 检查页码数，同时判断返回默认还是给定页码数
	 * @param page	页码
	 * @return
	 */
	public static int getPage(Integer page) {
		if (page == null) {
			return Constant.DEFAULT_PAGE_NUM;
		} else if (page <= 0) {
			return Constant.DEFAULT_PAGE_NUM;
		}
		return page;
	}
	
	/**
	 * 检查分页大小，同时判断返回默认还是给定页大小
	 * @param pageSize	分页大小
	 * @return
	 */
	public static int getPageSize(Integer pageSize) {
		if (pageSize == null) {
			return Constant.DEFAULT_PAGE_SIZE;
		} else if (pageSize <= 0) {
			return Constant.DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}
}
