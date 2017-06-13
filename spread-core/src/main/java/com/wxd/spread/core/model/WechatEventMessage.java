package com.wxd.spread.core.model;

import java.util.Date;

/**
 * 微信事件消息模板信息（用户在微信中的操作记录）
 * 
 * @author wangxiaodan
 *
 */
public class WechatEventMessage {
	private Long id;
	private String openid;
	/**
	 * 长整型
	 */
	private Long eventTime;
	/**
	 * 消息类型 event、text、image、voice、video、location、link 见枚举类MsgTypeEnum
	 */
	private String msgType;
	/**
	 * 事件类型,当msg_type=event时此时的事件类型，
	 * 见枚举类WechatEventEnum
	 */
	private String wechatEvent;
	/**
	 * 事件key
	 */
	private String wechatEventKey;
	/**
	 * 事件Ticket
	 */
	private String ticket;
	/**
	 * 上报地理位置事件的纬度
	 */
	private Double latitude;
	/**
	 * 上报地理位置事件的经度
	 */
	private Double longitude;
	/**
	 * 上报地理位置事件的精度
	 */
	private Double precision;
	/**
	 * 文本消息内容/链接消息的消息描述
	 */
	private String content;
	/**
	 * 消息id，64位整型
	 */
	private Long msgId;
	/**
	 * 图片链接
	 */
	private String picUrl;
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String mediaId;
	/**
	 * 语音格式，如amr，speex等
	 */
	private String format;
	/**
	 * 语音识别结果
	 */
	private String recognition;
	/**
	 * 视频消息缩略图的媒体id,可以调用多媒体文件下载接口拉取数据。
	 */
	private String thumbMediaId;
	/**
	 * 地理位置信息
	 */
	private String label;

	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息链接
	 */
	private String url;
	/**
	 * 是否已读，true:已读  false/null:未读
	 */
	private Boolean read;
	/**
	 * 已读的管理员ID
	 */
	private Long readAdminId;
	
	private Date createTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getEventTime() {
		return eventTime;
	}
	
	public Date getEventDate() {
		if (eventTime != null) {
			return new Date(eventTime * 1000);
		}
		return null;
	}

	public void setEventTime(Long eventTime) {
		this.eventTime = eventTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getWechatEvent() {
		return wechatEvent;
	}

	public void setWechatEvent(String wechatEvent) {
		this.wechatEvent = wechatEvent;
	}

	public String getWechatEventKey() {
		return wechatEventKey;
	}

	public void setWechatEventKey(String wechatEventKey) {
		this.wechatEventKey = wechatEventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Long getReadAdminId() {
		return readAdminId;
	}

	public void setReadAdminId(Long readAdminId) {
		this.readAdminId = readAdminId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 微信消息类型 event、text、image、voice、video、shortvideo、location、link
	 * 
	 * @author wangxiaodan
	 *
	 */
	public static enum MsgTypeEnum {
		EVENT("event", "事件消息"), TEXT("text", "文本消息"), IMAGE("image", "图片消息"), VOICE("voice", "语音消息"), VIDEO("video",
				"视频消息"), SHORTVIDEO("shortvideo", "小视频消息"), LOCATION("location", "地理位置消息"), LINK("link", "链接消息");
		private String value;
		private String desc;

		private MsgTypeEnum(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public String getValue() {
			return value;
		}

		public static MsgTypeEnum getEnum(String value) {
			if (value == null) {
				return null;
			}
			MsgTypeEnum[] values = MsgTypeEnum.values();
			for (MsgTypeEnum vt : values) {
				if (vt.value.equals(value)) {
					return vt;
				}
			}
			return null;
		}
	}

	/**
	 * 微信事件类型
	 * 
	 * @author wangxiaodan
	 *
	 */
	public static enum WechatEventEnum {
		SUBSCRIBE("subscribe", "订阅"), UNSUBSCRIBE("unsubscribe", "取消订阅"), SCAN("SCAN", "扫码"), LOCATION("LOCATION",
				"上报地理位置"), CLICK("CLICK", "菜单拉取消息"), VIEW("VIEW", "点击菜单跳转链接");
		private String value;
		private String desc;

		private WechatEventEnum(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public String getValue() {
			return value;
		}

		public static WechatEventEnum getEnum(String value) {
			if (value == null) {
				return null;
			}
			WechatEventEnum[] values = WechatEventEnum.values();
			for (WechatEventEnum vt : values) {
				if (vt.value.equals(value)) {
					return vt;
				}
			}
			return null;
		}
	}
}
