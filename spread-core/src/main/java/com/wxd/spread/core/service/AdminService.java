package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.mapper.AdminMapper;
import com.wxd.spread.core.model.Admin;
import com.wxd.spread.core.model.SysConfig;


@Service
public class AdminService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 通过主键查询管理员详情信息
	 * @param id
	 * @return
	 */
	public Admin findById(Long id) {
		return adminMapper.selectById(id);
	}
	
	/**
	 * 通过关键字等条件分页查询管理员列表
	 * @param page
	 * @param pageSize
	 * @param nickname
	 * @param trueName
	 * @param mobile
	 * @param email
	 * @param account
	 * @return
	 */
	public PageInfo<Admin> list(int page,int pageSize, String nickname,String trueName,String mobile,String email,String account) {
		PageHelper.startPage(page, pageSize);
		
		List<Admin> list = adminMapper.selectListByCondition(nickname, trueName, mobile, email, account);
		
		PageInfo<Admin> result = new PageInfo<>(list);
		return result;
	}
	
	/**
	 * 管理员修改自身密码
	 * 注意业务：管理员的密码没有任何其他人可以修改（包括超级管理员），只有自己能修改
	 * @param id 管理员ID
	 * @param oldPassword 管理员旧密码
	 * @param newPassword 管理员新密码
	 * @return
	 */
	public boolean updatePassword(Long id,String oldPassword,String newPassword) {
		if (id == null || StringUtils.isAnyBlank(oldPassword,newPassword)) {
			return false;
		}
		logger.info("管理员修改自身密码 id:" + id );
		
		return adminMapper.updatePassword(id,oldPassword,newPassword) > 0;
	}
	
	/**
	 * 修改管理员信息
	 * 这里修改的只是普通信息，没有密码
	 * @param id
	 * @param nickname
	 * @param trueName
	 * @return
	 */
	public boolean updateAdminInfo(Long id,String nickname,String trueName) {
		if (id == null) {
			return false;
		}
		return adminMapper.updateNickNameAndTruename(id, nickname, trueName) > 0;
	}
	
	/**
	 * 启用/禁用普通管理员状态
	 * @param id
	 * @param disabled	false:启用  true:禁用
	 * @return
	 */
	public boolean updateNormalAdminDisabledStatus(Long id,boolean disabled) {
		if (id == null) {
			return false;
		}
		return adminMapper.updateNormalAdminDisabledStatus(id, disabled) > 0;
	}
	
	/**
	 * 新增普通管理员
	 * @param account	必须且唯一
	 * @param mobile	必须且唯一
	 * @param email	必须且唯一
	 * @param nickname	可选，昵称
	 * @param trueName	可选，真实姓名
	 * @return
	 */
	public Long insertNormalAdmin(String account,String mobile,String email,String nickname,String trueName) {
		Admin admin = adminMapper.selectByAccountOrMobileOrEmail(account, mobile, email);
		if (admin != null) { // 说明已经存在相同的账号或者电话号码或者邮箱
			return null;
		}
		admin = new Admin();
		admin.setNickname(nickname);
		admin.setTrueName(trueName);
		admin.setMobile(mobile);
		admin.setEmail(email);
		admin.setAccount(account);
		admin.setSuperAdmin(false);
		SysConfig sysConfig = sysConfigService.findByKey(Constant.SYS_CONFIG_ADMIN_PASSWORD_KEY);
		if (sysConfig != null) {
			admin.setPassword(sysConfig.getValue()); // 根据系统的配置信息设置新增管理员的默认密码
		} 
		admin.setDisabled(false);
		admin.setCreateTime(new Date());
		
		int insertNum = adminMapper.insert(admin);
		if (insertNum > 0 ) {
			return admin.getId();
		}
		return null;
	}
	
	/**
	 * 用于用户登录，通过账号或者电话或者邮箱同时和密码匹配查找管理员
	 * @param account
	 * @param mobile
	 * @param email
	 * @param password
	 * @return
	 */
	public Admin findByAccountOrMobileOrEmailAndPwd(String account,String mobile,String email,String password) {
		Admin admin = adminMapper.selectByAccountOrMobileOrEmail(account, mobile, email);
		if (admin != null && StringUtils.equalsIgnoreCase(admin.getPassword(), password)) { 
			// 如果能查找到管理员信息，且密码相同
			return admin;
		}
		return null;
	}
}
