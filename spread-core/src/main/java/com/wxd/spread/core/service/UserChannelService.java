package com.wxd.spread.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxd.spread.core.mapper.UserChannelMapper;
import com.wxd.spread.core.model.UserChannel;

@Service
public class UserChannelService {
	@Autowired
	private UserChannelMapper userChannelMapper;
	
	
	public UserChannel findById(Long id) {
		if (id != null) {
			return userChannelMapper.selectById(id);
		}
		return null;
	}
	
	/**
	 * 查询用户的所有渠道
	 * @param userId
	 * @return
	 */
	public List<UserChannel> findByUserId(Long userId){
		List<UserChannel> userChannels = userChannelMapper.selectByUserId(userId);
		if (userChannels == null) {
			return new ArrayList<>();
		}
		return userChannels;
	}
	
	
	/**
	 * 查找用户的最基本渠道
	 * @param userId
	 * @return
	 */
	public UserChannel findBaseChannel(Long userId) {
		return userChannelMapper.selectBaseChannelByUserId(userId);
	}
	
	/**
	 * 通过推广码查找推送记录
	 * @param channelCode	推广码
	 * @return
	 */
	public UserChannel findByChannelCode(String channelCode) {
		if (StringUtils.isNotBlank(channelCode)) {
			return userChannelMapper.selectByChannelCode(channelCode);
		}
		return null;
	}
	
	
	/**
	 * 创建新用户渠道，如果是第一个渠道，则是基本渠道（永远不可修改和编辑），否则是普通渠道
	 * 这里只对用户标识做不为空判断，不判断用户是否真实存在，要求调用者必须确保用户存在
	 * @param userId	用户唯一ID
	 * @return	正常返回新增渠道的id,如果返回null表示创建失败
	 */
	public Long createNewUserChannel(Long userId) {
		if (userId != null) { // 这里只做不为空判断，不判断用户是否真实存在，要求调用者必须确保用户存在
			UserChannel uc = new UserChannel();
			uc.setUserId(userId);
			if (userChannelMapper.selectCountByUserId(userId) > 0) {
				uc.setBase(false); // 如果已经存在至少一个渠道，则此新增渠道是普通渠道
			} else {
				uc.setBase(true); // 如果是第一个渠道，则此新增渠道是基本渠道
			}
			
			// 确保渠道码唯一
			String channelCode = UUID.randomUUID().toString();
			while (userChannelMapper.selectCountByChannelCode(channelCode) > 0) {
				channelCode = UUID.randomUUID().toString();
			}
			uc.setChannelCode(channelCode);
			uc.setCreateTime(new Date());
			
			int insertNum = userChannelMapper.insert(uc);
			if (insertNum > 0) {
				return uc.getId();
			}
		}
		return null;
	}
}
