package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.ScanUserMapper;
import com.wxd.spread.core.model.ScanUser;
import com.wxd.spread.core.utils.ExecutorServiceUtil;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class ScanUserService {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private ScanUserMapper scanUserMapper;
	
	/**
	 * 通过主键查找扫码用户
	 * @param id
	 * @return
	 */
	public ScanUser findById(Long id) {
		if (id != null) {
			return scanUserMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 通过openid获取扫码用户信息
	 * @param openid
	 * @return
	 */
	public ScanUser findByOpenid(String openid) {
		if (StringUtils.isNotBlank(openid)) {
			return scanUserMapper.selectByOpenid(openid);
		}
		return null;
	}
	
	/**
	 * 插入扫码用户信息到数据库
	 * 系统调用
	 * @param scanUser
	 * @return
	 */
	public Long insert(WxMpUser wxMpUser) {
		ScanUser scanUser = new ScanUser();
		scanUser.setOpenid(wxMpUser.getOpenId());
		scanUser.setNickname(wxMpUser.getNickname());
		scanUser.setSex(wxMpUser.getSexId());
		scanUser.setProvince(wxMpUser.getProvince());
		scanUser.setCity(wxMpUser.getCity());
		scanUser.setCountry(wxMpUser.getCountry());
		scanUser.setHeadimgurl(wxMpUser.getHeadImgUrl());
		scanUser.setCreateTime(new Date());
		int insertNum = scanUserMapper.insert(scanUser);
		if (insertNum > 0) {
			 return scanUser.getId();
		}
		return null;
	}
	
	/**
	 * 异步插入扫码用户信息到数据库
	 * 系统调用
	 * @param scanUser
	 * @return
	 */
	public void insertAsync(final WxMpUser wxMpUser) {
		ExecutorServiceUtil.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String openId = wxMpUser.getOpenId();
					ScanUser scanUser = scanUserMapper.selectByOpenid(openId);
					if (scanUser == null) {
						insert(wxMpUser);
					}
				}catch(Exception e) {
					logger.error("插入扫码用户失败[err:"+e.getMessage()+",wxMpUser:"+String.valueOf(wxMpUser)+"]");
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * 根据条件查询扫码用户列表
	 * @param nickname
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PageInfo<ScanUser> findListByCriteria(int page,int pageSize,String nickname, Date startDate,
			Date endDate) {
		PageHelper.startPage(page, pageSize);
		List<ScanUser> list =  scanUserMapper.selectListByCriteria(nickname, startDate, endDate);
		
		PageInfo<ScanUser> result = new PageInfo<>(list);
		return result;
	}
}
