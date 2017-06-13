package com.wxd.spread.core.mapper;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wxd.spread.core.model.User;

public class UserMapperTest{
	private static UserMapper userMapper;
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-core.xml");
		userMapper = context.getBean(UserMapper.class);
		
		System.out.println(userMapper == null);
		
		User user = new User();
		user.setOpenid("oA3KowBwWsedw6RERcYKUSGulWjw");
		user.setNickname("飘渺星辰凯");
		user.setSex(1);
		user.setCountry("中国");
		user.setProvince("北京");
		user.setCity("朝阳");
		user.setLanguage("zh_CN");
		user.setHeadimgurl("http://wx.qlogo.cn/mmopen/ZKaZgmCO5QsTZ9RepV3B5v5SVcyP83fraSLicCHmdNgvicHKKDtvI3X78NJXHXmt3zjxQIG8msrTfU06ibdUkzLd5tNIZJIwv4X/0");
		user.setSubscribe(true);
		user.setSubscribeTime(1495601740L);
		user.setGroupid(0);
		user.setRemark("");
		user.setBalance(0L);
		user.setWaitBalance(0L);
		user.setUserLevelId(1L);
		user.setIntegral(0L);
		user.setCreateTime(new Date());
		
		Long userId = testInsert(user);
		//testSelectById(userId);
		
	}
	
	public static Long testInsert(User user) {
		userMapper.insert(user);
		System.out.println(user);
		return user.getId();
	}
	
	
	public static void testSelectById(Long id) {
		User user = userMapper.selectById(id);
		System.out.println(user);
	}
	public static void testSelectByOpenid(String openid) {
		User user = userMapper.selectByOpenid(openid);
		System.out.println(user);
	}
}
