package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.UserWithdrawalMapper;
import com.wxd.spread.core.model.Admin;
import com.wxd.spread.core.model.User;
import com.wxd.spread.core.model.UserWithdrawal;
import com.wxd.spread.core.model.UserWithdrawal.OperationRoleEnum;

@Service
public class UserWithdrawalService {
	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserWithdrawalMapper userWithdrawalMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private WechatTransferLogService wechatTransferLogService;
	
	
	/**
	 * 通过主键查找提现记录
	 * @param id
	 * @return
	 */
	public UserWithdrawal findById(Long id) {
		if (id != null) {
			return userWithdrawalMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 插入提现记录信息到数据库
	 * @param userWithdrawal
	 * @return
	 */
	public Long insert(UserWithdrawal userWithdrawal) {
		if (userWithdrawal != null) {
			int insertNum = userWithdrawalMapper.insert(userWithdrawal);
			if (insertNum > 0) {
				 return userWithdrawal.getId();
			}
		}
		return null;
	}
	
	/**
	 * 处理提现
	 * @return	false:处理失败    true:处理成功
	 */
	public boolean handleWithdrawals(Long adminId,Long userWithdrawalId,String desc) {
		logger.info("管理员处理用户的提现申请{adminId:"+adminId+",userWithdrawalId:"+userWithdrawalId+",desc:"+desc+"}");
		if (adminId == null || userWithdrawalId == null ) {
			return false;
		}
		// 获取管理员和提现申请，并做各种状态条件检查
		Admin admin = adminService.findById(adminId);
		UserWithdrawal userWithdrawal = userWithdrawalMapper.selectById(userWithdrawalId);
		if (admin == null || userWithdrawal == null) { // 对象不存在错误
			return false;
		}
		// 管理员被禁用
		if (admin.getDisabled() != null && admin.getDisabled()) {
			return false;
		}
		// 提现申请不是待处理状态
		if (UserWithdrawal.StatusEnum.getEnum(userWithdrawal.getStatus()) != UserWithdrawal.StatusEnum.WHAIT) {
			return false;
		}
		
		// 设置
		userWithdrawal.setOperationRole(OperationRoleEnum.ADMIN.getValue());
		userWithdrawal.setAdminId(adminId);
		userWithdrawal.setDescribe(desc);
		userWithdrawal.setHandleTime(new Date());
		userWithdrawal.setStatus(UserWithdrawal.StatusEnum.SUCC.getValue());
		int updateNum = userWithdrawalMapper.updateSelectiveById(userWithdrawal);
		if (updateNum <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 申请提现
	 * @param userId	申请提现的用户ID
	 * @param fee	提现金额
	 * @return
	 */
	public Long applyWithdrawals(Long userId,final long fee,final String clientIp) throws Exception{
		logger.info("用户申请提现{userId:"+String.valueOf(userId)+",fee:"+String.valueOf(fee)+"}");
		if (userId == null || fee <= 0) {
			return null;
		}
		// 查询提现用户并判断是否存在
		final User user = userService.findById(userId);
		if (user == null) {
			return null;
		}
		if (user.getBalance() < fee) {
			logger.info("用户申请提现失败，余额不足{userId:" + String.valueOf(userId) + ",fee:" + String.valueOf(fee) + ",balance:"
					+ user.getBalance() + "}");
			return null;
		}
		
		UserWithdrawal userWithdrawal = new UserWithdrawal();
		userWithdrawal.setUserId(userId);
		userWithdrawal.setCostFee(fee);
		userWithdrawal.setStatus(UserWithdrawal.StatusEnum.WHAIT.getValue());
		userWithdrawal.setCreateTime(new Date());
		int insertNum = userWithdrawalMapper.insert(userWithdrawal);
		if (insertNum > 0) {
			Long withdrawalId = userWithdrawal.getId();
			wechatTransferLogService.wechatTransfer(withdrawalId,user.getOpenid(),fee,clientIp);
			return withdrawalId;
		}
		return null;
	}
	
	
	/**
	 * 根据条件查询推送记录列表
	 * @param pushLog.openid
	 * @return
	 */
	public PageInfo<UserWithdrawal> findListByCriteria(int page,int pageSize,UserWithdrawal userWithdrawal) {
		PageHelper.startPage(page, pageSize);
		UserWithdrawal uw = null;
		if (userWithdrawal == null) {
			uw = new UserWithdrawal();
		} else {
			uw = userWithdrawal;
		}
		List<UserWithdrawal> list =  userWithdrawalMapper.selectListByCriteria(uw);
		
		PageInfo<UserWithdrawal> result = new PageInfo<>(list);
		return result;
	}


}
