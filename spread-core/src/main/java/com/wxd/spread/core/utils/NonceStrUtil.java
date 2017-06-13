package com.wxd.spread.core.utils;

import java.util.Random;
/**
 * 生成随机长度的字符串工具类
 * @author wangxiaodan
 *
 */
public class NonceStrUtil {
	private static final Random random = new Random();
	private static final int RANDOM_NUM =  62 ;
	public static String nonceStr(int len) {
		// a -> 97 z -> 122 A -> 65 Z -> 90 '0' -> 48 '9' -> 57
		StringBuilder sBuilder = new StringBuilder();
		for (int i=0;i<len;i++) {
			int r = random.nextInt(RANDOM_NUM);
			r += 48;
			if (r <= 57) {
				sBuilder.append((char)r);
				continue;
			}
			r += 7;
			if (r <= 90) {
				sBuilder.append((char)r);
				continue;
			}
			r += 6;
			sBuilder.append((char)r);
		}
		return sBuilder.toString();
	}
	
	
	
	public static void main(String[] args) {
		for (int i=0;i<10;i++) {
			System.out.println(nonceStr(32));
		}
	}
}
