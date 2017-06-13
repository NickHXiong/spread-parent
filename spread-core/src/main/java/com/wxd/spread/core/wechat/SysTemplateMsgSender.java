package com.wxd.spread.core.wechat;

import com.wxd.spread.core.model.SysTemplateMessage;

/**
 * 系统模板消息处理类，此类的实现通过责任链模式链接并做出操作
 * @author wangxiaodan
 *
 */
public interface SysTemplateMsgSender {
	/**
	 * 发送微信模板消息
	 * @param msg
	 * @param openid
	 */
	public abstract void sendWechatTemplateMsg(SysTemplateMessage msg,String openid);
	
	/**
	 * 通过菜单事件的key查找系统消息模板
	 * @param menuKey
	 * @return
	 */
	public abstract SysTemplateMessage findByMenuKey(String menuKey);
	
}
