package com.wxd.spread.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.SysTemplateMessage;

public interface SysTemplateMessageMapper {

	/**
	 * 通过主键查找实体信息
	 * @param id
	 * @return
	 */
	SysTemplateMessage selectById(Long id);

	/**
	 * 查询列表
	 * @param disabled true:只查询禁言的   false:只查询正常的   null:查询所有列表
	 * @return
	 */
	List<SysTemplateMessage> selectByDisabled(Boolean disabled);
	
	/**
	 * 插入系统模板消息
	 * @param sysTemplateMessage
	 * @return
	 */
	int insert(SysTemplateMessage sysTemplateMessage);

	/**
	 * 系统模板的启用/禁用
	 * @param id
	 * @param disabled
	 * @return
	 */
	int updateDisabledStatus(@Param("id")Long id,@Param("disabled") boolean disabled);

	/**
	 * 更新系统消息
	 * @param sysTemplateMessage
	 * 			更新的内容如果不为空有 receivedMsg,receivedEvent, title, content
	 * @return
	 */
	int update(SysTemplateMessage sysTemplateMessage);

	/**
	 * 通过系统模板消息键查找消息信息
	 * @param templateKey
	 * @return
	 */
	SysTemplateMessage findByTemplateKey(@Param("templateKey") String templateKey);
}