package com.wxd.spread.core.service;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.bean.request.WxEntPayRequest;
import com.github.binarywang.wxpay.bean.result.WxEntPayResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.mapper.UserWithdrawalMapper;
import com.wxd.spread.core.mapper.WechatTransferLogMapper;
import com.wxd.spread.core.model.SysConfig;
import com.wxd.spread.core.model.UserWithdrawal;
import com.wxd.spread.core.model.UserWithdrawal.OperationRoleEnum;
import com.wxd.spread.core.model.WechatTransferLog;
import com.wxd.spread.core.utils.ExecutorServiceUtil;
import com.wxd.spread.core.utils.NonceStrUtil;

import me.chanjar.weixin.common.exception.WxErrorException;

@Service
public class WechatTransferLogService {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private WechatTransferLogMapper wechatTransferLogMapper;
	@Autowired
	private UserWithdrawalMapper userWithdrawalMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private WxPayService wxPayService;
	
	public void wechatTransfer (final Long withdrawalId,final String openid,final long fee,final String clientIp) throws Exception {
		// 插入数据库提现申请成功,则异步查看系统是否支持自动支付
		ExecutorServiceUtil.execute(new Runnable() {
			@Override
			public void run() {
				SysConfig sysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_AUTO_TRANSFER_KEY);
				Integer value = sysConfig.getIntValue();
				if (value != null && value == 1) { // 系统支持自动转账功能，则自动将提现表中的申请转到用户微信余额中
					// 生成唯一商户号
					String partnerTradeNo = UUID.randomUUID().toString();
					while (wechatTransferLogMapper.countByPartnerTradeNo(partnerTradeNo) > 0) {
						partnerTradeNo = UUID.randomUUID().toString();
					}
					WechatTransferLog wechatTransferLog = new WechatTransferLog();
					wechatTransferLog.setUserWithdrawalId(withdrawalId);
					wechatTransferLog.setNonceStr(NonceStrUtil.nonceStr(32));
					wechatTransferLog.setPartnerTradeNo(partnerTradeNo);
					wechatTransferLog.setOpenid(openid);
					wechatTransferLog.setAmount(fee);
					wechatTransferLog.setDesc("提现");
					wechatTransferLog.setSpbillCreateIp(clientIp);
					try {
						WxEntPayRequest request = new WxEntPayRequest();
						request.setNonceStr(wechatTransferLog.getNonceStr());
						request.setPartnerTradeNo(partnerTradeNo);
						request.setOpenid(openid);
						request.setAmount((int)fee);
						request.setDescription(wechatTransferLog.getDesc());
						request.setSpbillCreateIp(wechatTransferLog.getSpbillCreateIp());
						// 请求微信企业转账支付
						WxEntPayResult entPayResult = wxPayService.entPay(request);
						wechatTransferLog.setReturnCode(entPayResult.getReturnCode());
						wechatTransferLog.setReturnMsg(entPayResult.getReturnMsg());
						wechatTransferLog.setResultCode(entPayResult.getResultCode());
						wechatTransferLog.setErrCode(entPayResult.getErrCode());
						wechatTransferLog.setErrCodeDes(entPayResult.getErrCodeDes());
						wechatTransferLog.setPaymentNo(entPayResult.getPaymentNo());
						wechatTransferLog.setPaymentTime(entPayResult.getPaymentTime());
						wechatTransferLog.setCreateTime(new Date());
						wechatTransferLogMapper.insert(wechatTransferLog);
						// 微信转账成功，修改提现记录的状态
						if ("SUCCESS".equals(entPayResult.getReturnCode()) && "SUCCESS".equals(entPayResult.getResultCode())) {
							UserWithdrawal userWithdrawal = userWithdrawalMapper.selectById(withdrawalId);
							userWithdrawal.setOperationRole(OperationRoleEnum.SYSTEM.getValue());
							userWithdrawal.setDescribe("系统调用微信API支付");
							userWithdrawal.setHandleTime(new Date());
							userWithdrawal.setStatus(UserWithdrawal.StatusEnum.SUCC.getValue());
							int updateNum = userWithdrawalMapper.updateSelectiveById(userWithdrawal);
							if (updateNum < 0) {
								logger.error("给用户通过企业转账成功,但是提现记录标志设置失败，重要。" + wechatTransferLog);
							}
						}
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
