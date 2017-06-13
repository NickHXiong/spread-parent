package com.wxd.spread.core.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.WechatEventMessage;

public interface WechatEventMessageMapper {

	/**
	 * 保存微信事件消息到系统数据库
	 * @param weMessage
	 * @return 返回影响的行数
	 */
	int insert(WechatEventMessage weMessage);

	/**
	 * 管理员查看用户消息，并设置为管理员已读（已处理）
	 * @param id	消息ID
	 * @param adminId	管理员ID
	 * @return
	 */
	int read(@Param("id") Long id, @Param("id") Long adminId);

	/**
	 * 通过主键查询用户消息
	 * @param id
	 * @return
	 */
	WechatEventMessage selectById(@Param("id") Long id);

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
	List<WechatEventMessage> selectListByCriteria(@Param("record")WechatEventMessage wechatEventMessage, @Param("startDate")Date startDate,@Param("endDate") Date endDate);
}