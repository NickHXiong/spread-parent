package com.wxd.spread.core.utils;

import com.wxd.spread.core.model.WechatEventMessage;
import com.wxd.spread.core.model.WechatEventMessage.MsgTypeEnum;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public class WxMpXmlMessage2WechatEventMessageUtil {
	
	
	/**
	 * 将微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decode(WxMpXmlMessage wxMessage){
		if (wxMessage == null) {
			return null;
		}
		WechatEventMessage weMessage = null;
		MsgTypeEnum msgType = WechatEventMessage.MsgTypeEnum.getEnum(wxMessage.getMsgType());
		if (MsgTypeEnum.TEXT == msgType) {
			weMessage = decodeTextMsgType(wxMessage);
		} else if (MsgTypeEnum.IMAGE == msgType)  {
			weMessage = decodeImageMsgType(wxMessage);
		} else if (MsgTypeEnum.VOICE == msgType) {
			weMessage = decodeVoiceMsgType(wxMessage);
		} else if (MsgTypeEnum.VIDEO == msgType || MsgTypeEnum.SHORTVIDEO == msgType) {
			weMessage = decodeVideoMsgType(wxMessage);
		} else if (MsgTypeEnum.LOCATION == msgType ) {
			weMessage = decodeLocationMsgType(wxMessage);
		} else if (MsgTypeEnum.LINK == msgType) {
			weMessage = decodeLinkMsgType(wxMessage);
		} else if (MsgTypeEnum.EVENT == msgType) {
			weMessage = decodeEventMsgType(wxMessage);
		}
		
		return weMessage;
	}
	
	
	/**
	 * 将文本微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeTextMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(WechatEventMessage.MsgTypeEnum.TEXT.getValue());
		weMessage.setContent(wxMessage.getContent());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	
	/**
	 * 将图片微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeImageMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(WechatEventMessage.MsgTypeEnum.IMAGE.getValue());
		weMessage.setPicUrl(wxMessage.getPicUrl());
		weMessage.setContent(wxMessage.getContent());
		weMessage.setMediaId(wxMessage.getMediaId());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	
	
	/**
	 * 将语音微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeVoiceMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(WechatEventMessage.MsgTypeEnum.VOICE.getValue());
		weMessage.setMediaId(wxMessage.getMediaId());
		weMessage.setFormat(wxMessage.getFormat());
		weMessage.setRecognition(wxMessage.getRecognition());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	/**
	 * 将视频(小视频)微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeVideoMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(wxMessage.getMsgType());
		weMessage.setMediaId(wxMessage.getMediaId());
		weMessage.setThumbMediaId(wxMessage.getThumbMediaId());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	
	/**
	 * 将地理位置微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeLocationMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(wxMessage.getMsgType());
		weMessage.setLatitude(wxMessage.getLocationX());
		weMessage.setLongitude(wxMessage.getLocationY());
		weMessage.setPrecision(wxMessage.getScale());
		weMessage.setLabel(wxMessage.getLabel());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	/**
	 * 将链接微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeLinkMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(wxMessage.getMsgType());
		weMessage.setTitle(wxMessage.getTitle());
		weMessage.setContent(wxMessage.getDescription());
		weMessage.setUrl(wxMessage.getUrl());
		weMessage.setMsgId(wxMessage.getMsgId());
		return weMessage;
	}
	/**
	 * 将事件微信消息解码为系统微信事件消息
	 * @param wxMessage
	 * @return
	 */
	public static WechatEventMessage decodeEventMsgType(WxMpXmlMessage wxMessage) {
		WechatEventMessage weMessage = new WechatEventMessage();
		weMessage.setOpenid(wxMessage.getFromUser());
		weMessage.setEventTime(wxMessage.getCreateTime());
		weMessage.setMsgType(WechatEventMessage.MsgTypeEnum.EVENT.getValue());
		weMessage.setWechatEvent(wxMessage.getEvent());
		weMessage.setWechatEventKey(wxMessage.getEventKey());
		weMessage.setTicket(wxMessage.getTicket());
		weMessage.setLatitude(wxMessage.getLatitude());
		weMessage.setLongitude(wxMessage.getLongitude());
		weMessage.setPrecision(wxMessage.getPrecision());
		return weMessage;
	}
	
	/**
	 * 将系统微信事件消息编码为微信消息
	 * TODO 暂未用到未实现
	 * @param weMessage
	 * @return
	 */
	public static WxMpXmlMessage encode(WechatEventMessage weMessage) {
		return null;
	}
}
