package com.wxd.spread.core.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.WechatEventMessageMapper;
import com.wxd.spread.core.model.WechatEventMessage;
import com.wxd.spread.core.utils.WxMpXmlMessage2WechatEventMessageUtil;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Service
public class WechatEventMessageService {
	@Autowired
	private WechatEventMessageMapper wechatEventMessageMapper;
	
	/**
	 * 记录微信发送的所有消息到数据库
	 * @param wxMessage
	 * @return	返回消息在系统中的ID,如果null则记录失败
	 */
	public Long recordMessage(WxMpXmlMessage wxMessage) {
		// 将微信消息转换为系统消息
		WechatEventMessage weMessage = WxMpXmlMessage2WechatEventMessageUtil.decode(wxMessage);
		if (weMessage == null) {
			return null;
		}
		weMessage.setRead(false);
		weMessage.setCreateTime(new Date());
		int effectNum = wechatEventMessageMapper.insert(weMessage);
		if (effectNum > 0) {
			return weMessage.getId();
		}
		return null;
	}
	
	/**
	 * 管理员查看用户消息，并设置为管理员已读（已处理）
	 * @param id	消息ID
	 * @param adminId	管理员ID
	 * @return
	 */
	public boolean read(Long id,Long adminId){
		if (id != null && adminId != null) {
			return wechatEventMessageMapper.read(id,adminId) > 0;
		}
		return false;
	}
	
	/**
	 * 通过主键查询用户消息
	 * @param id
	 * @return
	 */
	public WechatEventMessage findById(Long id) {
		if (id != null) {
			return wechatEventMessageMapper.selectById(id);
		}
		return null;
	}
	
	
	/**
	 * 根据条件查询用户消息列表
	 * @param wechatEventMessage.openid
	 * @param wechatEventMessage.msgType
	 * @param wechatEventMessage.wechatEvent
	 * @param wechatEventMessage.read
	 * @param wechatEventMessage.readAdminId
	 * @param startDate	时间过滤条件
	 * @param endDate	时间过滤条件
	 * @return
	 */
	public PageInfo<WechatEventMessage> findListByCriteria(int page,int pageSize,WechatEventMessage wechatEventMessage, Date startDate,
			Date endDate) {
		PageHelper.startPage(page, pageSize);
		WechatEventMessage wem = null;
		if (wechatEventMessage == null) {
			wem = new WechatEventMessage();
		} else {
			wem = wechatEventMessage;
		}
		List<WechatEventMessage> list =  wechatEventMessageMapper.selectListByCriteria(wem, startDate, endDate);
		
		PageInfo<WechatEventMessage> result = new PageInfo<>(list);
		return result;
	}
}
