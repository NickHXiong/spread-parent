package com.wxd.spread.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxd.spread.core.mapper.SysTemplateMessageMapper;
import com.wxd.spread.core.model.SysTemplateMessage;

/**
 * 系统消息服务类
 * @author wangxiaodan
 *
 */
@Service
public class SysTemplateMessageService {
	
	@Autowired
	private SysTemplateMessageMapper sysTemplateMessageMapper;
	
	/**
	 * 通过模板的key查找系统消息模板
	 * @return
	 */
	public SysTemplateMessage findByTemplateKey(String templateKey) {
		if (StringUtils.isNotBlank(templateKey)) {
			return sysTemplateMessageMapper.findByTemplateKey(templateKey);
		}
		
		return null;
	}
	
	/**
	 * 通过唯一主键查找系统模板消息
	 * @param id
	 * @return
	 */
	public SysTemplateMessage findById(Long id) {
		if (id != null) {
			return sysTemplateMessageMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 查找系统模板消息列表
	 * @param disabled	true:只查询禁用的   false:只查询正常的   null:查询所有列表
	 * @return
	 */
	public List<SysTemplateMessage> list(Boolean disabled) {
		List<SysTemplateMessage> list = sysTemplateMessageMapper.selectByDisabled(disabled);
		if (list == null) {
			return new ArrayList<SysTemplateMessage>();
		}
		return list;
	}
	
	/**
	 * 插入系统模板
	 * @param sysTemplateMessage
	 * @return
	 */
	public Long insert(SysTemplateMessage sysTemplateMessage) {
		if (sysTemplateMessage != null) {
			int insertNum = sysTemplateMessageMapper.insert(sysTemplateMessage);
			if (insertNum > 0) {
				return sysTemplateMessage.getId();
			}
		}
		return null;
	}
	
	/**
	 * 系统模板的启用/禁用
	 * @param id
	 * @param disabled	true:禁用  false:启用
	 * @return
	 */
	public boolean updateDisabledStatus(Long id,boolean disabled) {
		if (id != null) {
			return sysTemplateMessageMapper.updateDisabledStatus(id,disabled) > 0;
		}
		return false;
	}
	
	/**
	 * 
	 * @param id
	 * @param receivedMsg
	 * @param receivedEvent
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean update(Long id,String title,String content) {
		if (id != null) {
			SysTemplateMessage sysTemplateMessage = new SysTemplateMessage();
			sysTemplateMessage.setId(id);
			sysTemplateMessage.setTitle(title);
			sysTemplateMessage.setContent(content);
			return sysTemplateMessageMapper.update(sysTemplateMessage) > 0;
		}
		return false;
	}
	
}
