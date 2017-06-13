package com.wxd.spread.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.wxd.spread.core.model.WechatTransferLog;

public interface WechatTransferLogMapper {
	
	/**
	 * 插入自动微信转账记录记录
	 * 此插入未返回插入的记录ID
	 * @param wechatTransferLog
	 * @return
	 */
	public int insert(WechatTransferLog wechatTransferLog);
	
	
	/**
	 * 查询商户订单号数量
	 * @param partnerTradeNo
	 * @return
	 */
	public int countByPartnerTradeNo(@Param("partnerTradeNo") String partnerTradeNo);
}