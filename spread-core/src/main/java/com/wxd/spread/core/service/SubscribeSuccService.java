package com.wxd.spread.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxd.spread.core.mapper.SubscribeSuccMapper;
import com.wxd.spread.core.model.SubscribeSucc;

@Service
public class SubscribeSuccService {
	
	@Autowired
	private SubscribeSuccMapper subscribeSuccMapper;

	
	/**
	 * 通过主键查找订阅记录信息
	 * @param id
	 * @return
	 */
	public SubscribeSucc findById(Long id) {
		if (id != null) {
			return subscribeSuccMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 查看某用户是否订阅了此产品
	 * @param app_id
	 * @param openid
	 * @return
	 */
	public SubscribeSucc findByAppIdAndOpenid(Long app_id,String openid) {
		if (app_id != null && StringUtils.isNotBlank(openid)) {
			return subscribeSuccMapper.selectByAppIdAndOpenid(app_id,openid);
		}
		return null;
	}
	
	/**
	 * 通过条件查询订阅记录列表
	 * @param subscribeSucc.openid
	 * @param subscribeSucc.appId
	 * @param subscribeSucc.success
	 * @param subscribeSucc.pushLimit
	 * @param startDate	时间过滤条件
	 * @param endDate	时间过滤条件
	 * @return
	 */
	public PageInfo<SubscribeSucc> findListByCriteria(int page,int pageSize,SubscribeSucc subscribeSucc,Date startDate,Date endDate) {
		PageHelper.startPage(page, pageSize);
		SubscribeSucc ss = null;
		if (subscribeSucc == null) {
			ss = new SubscribeSucc();
		} else {
			ss = subscribeSucc;
		}
		List<SubscribeSucc> list =  subscribeSuccMapper.selectListByCriteria(ss, startDate, endDate);
		
		PageInfo<SubscribeSucc> result = new PageInfo<>(list);
		return result;
	}
	
	
	/**
	 * 插入订阅记录信息
	 * @param subscribeSucc
	 * @return
	 */
	public Long insert(SubscribeSucc subscribeSucc) {
		if (subscribeSucc != null) {
			int insertNum = subscribeSuccMapper.insert(subscribeSucc);
			if (insertNum > 0) {
				return subscribeSucc.getId();
			}
		}
		return null;
	}
	
	/**
	 * 产品成功订阅（不包含超限的）的数量
	 * @param appId
	 * @return
	 */
	public long succCount(Long appId) {
		return subscribeSuccMapper.countSelective(null,appId,null,true,null);
	}
	
	/**
	 * 产品订阅的数量
	 * @param openid
	 * @param appId
	 * @param appid
	 * @param success
	 * @param pushLimit
	 * @return
	 */
	public long countSelective(String openid, Long appId,String appid,Boolean success, Boolean pushLimit) {
		return subscribeSuccMapper.countSelective(null,appId,null,true,null);
	}
	
	/**
	 * 更新为订阅成功
	 * @param openid
	 * @param appid
	 */
	public boolean updateSuccByOpenidAndAppid(String openid, String appid) {
		return subscribeSuccMapper.updateSuccByOpenidAndAppid(openid,appid) > 0;
	}
}
