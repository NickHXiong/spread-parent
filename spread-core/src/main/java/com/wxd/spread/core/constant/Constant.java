package com.wxd.spread.core.constant;

/**
 * 系统常量类
 * @author wangxiaodan
 *
 */
public final class Constant {
	/**
	 * 系统配置：同一个产品对一个扫码用户的最多推送渠道次数
	 */
	public static final String SYS_CONFIG_ACAN_MAXNUM_KEY = "scanAppMaxNum";
	/**
	 * 系统配置：系统配置是否开通自动转账给推广人员（用于提现操作）,1:开通  0：未开通
	 */
	public static final String SYS_CONFIG_AUTO_TRANSFER_KEY = "openAutoWechatTransfer"; 
	/**
	 * 系统配置：新增管理员的默认密码
	 */
	public static final String SYS_CONFIG_ADMIN_PASSWORD_KEY = "defaultAdminPassword"; 
	/**
	 * 系统配置：默认产品渠道分成百分比
	 */
	public static final String SYS_CONFIG_APPCHANNEL_PERCENTAGE_KEY = "defaultAppChannelPercentage"; 
	/**
	 * 系统配置：系统配置的默认用户渠道
	 */
	public static final String SYS_CONFIG_USER_LEVEL_NUM_KEY = "defaultUserLevelNum"; 
	
	/**
	 * 系统配置：默认优先级，这里配置适用于app、app_channel的默认优先级
	 */
	public static final String SYS_CONFIG_APP_PRIORITY_KEY = "defaultAppPriority"; 
	
	/**
	 * 微信菜单:微信菜单我的余额按钮消息key
	 */
	public static final String WECHAT_MENU_MY_BALANCE_KEY = "spread_click_key_001";
	
	/**
	 * 微信菜单:微信菜单提现说明按钮消息key
	 */
	public static final String WECHAT_MENU_WITHDRAW_KEY = "spread_click_key_002";
	
	/**
	 * 微信菜单:微信菜单关于我们按钮消息key
	 */
	public static final String WECHAT_MENU_ABOUT_US_KEY = "spread_click_key_003";
	
	/**
	 * 系统模板：用户查询余额消息模板
	 */
	public static final String SYS_TEMPLATE_SEARCH_BALANCE_KEY = "searchBalance";
	
	/**
	 * 系统模板：关于我们系统模板消息
	 */
	public static final String SYS_TEMPLATE_ABOUT_US_KEY = "aboutUs";
	/**
	 * 系统模板：提现说明系统模板消息
	 */
	public static final String SYS_TEMPLATE_WITHDRAW_KEY = "withdraw";
	
	
	
}
