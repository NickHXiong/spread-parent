package com.wxd.spread.wechat.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wxd.spread.core.service.WechatService;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@RestController
@RequestMapping("/wechat/portal")
public class WxMpPortalController {

	@Autowired
	private WechatService wechatService;

	private final Logger logger = Logger.getLogger(this.getClass());

	@ResponseBody
	@GetMapping(produces = "text/plain;charset=utf-8")
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {
		this.logger.info("接收到来自微信服务器的认证消息：{signature:" + signature + ",timestamp:" + timestamp + ", nonce:" + nonce
				+ ", echostr:" + echostr + "}");

		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实!");
		}

		if (this.getWechatService().checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}

		return "非法请求";
	}

	@ResponseBody
	@PostMapping(produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
		this.logger.info("接收微信请求：[signature=[" + signature + "], encType=[" + encType + "], msgSignature=["
				+ msgSignature + "]," + " timestamp=[" + timestamp + "], nonce=[" + nonce + "], requestBody=[\n"
				+ requestBody + "\n] ");

		if (!this.wechatService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		String out = null;
		if (encType == null) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			WxMpXmlOutMessage outMessage = this.getWechatService().route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toXml();
		} else if ("aes".equals(encType)) {
			// aes加密的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
					this.getWechatService().getWxMpConfigStorage(), timestamp, nonce, msgSignature);
			this.logger.debug("消息解密后内容为：\n" + inMessage.toString());
			WxMpXmlOutMessage outMessage = this.getWechatService().route(inMessage);
			if (outMessage == null) {
				return "";
			}
			out = outMessage.toEncryptedXml(this.getWechatService().getWxMpConfigStorage());
		}
		this.logger.debug("组装回复信息：" + out);
		return out;
	}

	protected WechatService getWechatService() {
		return this.wechatService;
	}

}
