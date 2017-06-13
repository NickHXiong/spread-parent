package com.wxd.spread.wechat.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wxd.spread.core.model.AppChannel;
import com.wxd.spread.core.service.AppChannelService;
import com.wxd.spread.core.utils.QrCodeUtil;

/**
 * 二维码图片控制器
 * @author wangxiaodan
 *
 */
@Controller
@RequestMapping("/qr")
public class CodeImageController {
	@Autowired
	private AppChannelService appChannelService;
	
	/**
	 * 用户的推广二维码图片
	 * @param request
	 * @param response
	 * @param uccd	用户渠道code
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping("/user")
	public void user(HttpServletRequest request, HttpServletResponse response, String uccd)
			throws IOException, WriterException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		String url = null;
		if (StringUtils.isNotBlank(uccd)) {
			url =USER_QR_CONTENT_URL + "?uccd=" + uccd;
		} else {
			url = USER_QR_CONTENT_URL;
		}

		BitMatrix bitMatrix = QrCodeUtil.genQR(url);
		ServletOutputStream responseOutputStream = response.getOutputStream();
		QrCodeUtil.writeToStream(bitMatrix, "jpeg", responseOutputStream);

		responseOutputStream.flush();
		responseOutputStream.close();
	}
	
	
	/**
	 * 产品的推广二维码图片
	 * @param request
	 * @param response
	 * @param acid	产品渠道的id
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping("/app")
	public void app(HttpServletRequest request, HttpServletResponse response, Long acid)
			throws IOException, WriterException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		if (acid == null) {
			return;
		}
		AppChannel appChannel = appChannelService.findById(acid);
		if (appChannel == null || (appChannel.getDisabled() != null && appChannel.getDisabled() )) {
			return;
		}
		
		BitMatrix bitMatrix = QrCodeUtil.genQR(appChannel.getChannelUrl());
		ServletOutputStream responseOutputStream = response.getOutputStream();
		QrCodeUtil.writeToStream(bitMatrix, "jpeg", responseOutputStream);

		responseOutputStream.flush();
		responseOutputStream.close();
	}
	
	
	private static final String USER_QR_CONTENT_URL = "http://5936863d.ngrok.io/app/channel_qr";
}
