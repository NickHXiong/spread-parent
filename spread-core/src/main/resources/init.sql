DROP TABLE IF EXISTS `sys_config` ;
CREATE TABLE `sys_config` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(45) NOT NULL COMMENT '配置键',
  `value_type` INT(1) NULL DEFAULT 1 COMMENT '值类型，1：string 2:integer 3:float',
  `value` VARCHAR(45) NULL COMMENT '配置值',
  `describe` VARCHAR(200) NULL COMMENT '配置描述',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC))
COMMENT = '系统配置表';
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('defaultUserLevelNum', '2', '1', '系统配置的默认用户渠道');
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('defaultAppChannelPercentage', '2', '80', '默认产品渠道分成百分比');
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('defaultAdminPassword', '1', 'e10adc3949ba59abbe56e057f20f883e', '配置新增管理员的默认密码');
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('openAutoWechatTransfer', '2', '0', '系统配置是否开通自动转账给推广人员（用于提现操作）,1:开通  0：未开通');
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('scanAppMaxNum', '2', '5', '同一个产品对一个扫码用户的最多推送渠道次数');
INSERT INTO `sys_config` (`key`, `value_type`, `value`, `describe`) VALUES ('defaultAppPriority', '2', '50', '默认优先级，这里配置适用于app、app_channel的默认优先级');



DROP TABLE IF EXISTS `admin` ;
CREATE TABLE `admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(45) NULL,
  `true_name` VARCHAR(45) NULL,
  `mobile` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `account` VARCHAR(45) NOT NULL COMMENT '账号',
  `super_admin` BIT not null default 0 comment '是否是超级管理员 1：超级管理员（超级管理员只能自己修改自己） 0：普通管理员（可增删改查）',
  `password` VARCHAR(45) NOT NULL COMMENT '密码',
  `disabled` BIT NULL DEFAULT 0 COMMENT 'false/null:正常   true：停用',
  `disabled_time` timestamp NULL COMMENT '停用时间',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `account_UNIQUE` (`account` ASC))
COMMENT = '管理员用户表';
INSERT INTO `spread`.`admin` (`nickname`, `true_name`, `mobile`, `email`, `account`, `super_admin`, `password`, `disabled`, `create_time`) VALUES ('王晓丹', '王晓丹', '13120178521', '573977818@qq.com', '123456', 1, 'e10adc3949ba59abbe56e057f20f883e', 0, now());


DROP TABLE IF EXISTS `admin_logger` ;
CREATE TABLE `admin_logger` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` INT(2) NOT NULL COMMENT '日志类型1：登录2：登出 ',
  `content` VARCHAR(200) NULL COMMENT '日志内容',
  `admin_id` INT NOT NULL COMMENT '管理员ID',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '管理员日志表';

drop table if exists `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NULL,
  `nickname` VARCHAR(45) NULL,
  `sex` INT(1) NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `city` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `province` VARCHAR(45) NULL,
  `language` VARCHAR(45) NULL,
  `headimgurl` VARCHAR(200) NULL,
  `subscribe` BIT(1) NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。',
  `subscribe_time` INT NULL COMMENT '订阅时间',
  `create_time` timestamp NULL DEFAULT now(),
  `groupid` INT(2) NULL COMMENT '微信分组',
  `remark` VARCHAR(45) NULL COMMENT '微信备注',
  `balance` INT NULL DEFAULT 0 COMMENT '当前可用余额，单位分',
  `wait_balance` INT NULL DEFAULT 0 COMMENT '待结算金额，单位分',
  `user_level_id` INT NULL COMMENT '用户等级ID',
  `integral` INT NULL DEFAULT 0 COMMENT '用户积分',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `openid_UNIQUE` (`openid` ASC))
COMMENT = '用户表';

DROP TABLE IF EXISTS `user_channel` ;
CREATE TABLE `user_channel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL COMMENT '用户表ID',
  `base` BIT(1) NULL DEFAULT 1 COMMENT '是否是基本渠道，生成用户就存在，true:不可修改和编辑 false:用户后来添加的渠道',
  `channel_code` VARCHAR(45) NOT NULL COMMENT '渠道码 uuid来生成',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `channel_code_UNIQUE` (`channel_code` ASC))
COMMENT = '用户渠道表（默认每个用户就一个，一个的时候不显示给用户，当有多个时候显示给用户，需要和用户等级配合）';

DROP TABLE IF EXISTS `user_income`;
CREATE TABLE `user_income` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL COMMENT '用户表ID',
  `user_channel_id` INT NOT NULL COMMENT '用户渠道标识',
  `app_id` INT NOT NULL COMMENT '对应的app表ID',
  `app_name` VARCHAR(45) NULL COMMENT '对应的app表的名称',
  `app_channel_id` INT NOT NULL COMMENT '对应的渠道ID',
  `sub_openid` VARCHAR(45) NOT NULL COMMENT '关注用户的openId',
  `sub_nickname` VARCHAR(45) NULL COMMENT '关注用户的昵称',
  `fee` INT NOT NULL COMMENT '金额，单位分',
  `subscribe_time` INT NOT NULL comment '用户什么时候关注后来的收入',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '用户收入表';

DROP TABLE IF EXISTS `user_level` ;
CREATE TABLE `user_level` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `level` INT(1) NOT NULL COMMENT ' 等级数值',
  `level_name` VARCHAR(45) NOT NULL COMMENT '等级名称',
  `integral` INT NOT NULL COMMENT '所需积分',
  `percentage` INT(2) NOT NULL COMMENT '得到渠道金额的百分比，例如：50，就是得到渠道金额的50%',
  `multi_channel` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否可以存在多个用户渠道，默认false,等级在90，可以多渠道',
  PRIMARY KEY (`id`))
COMMENT = '用户等级表（不变，暂定6个等级，分别为50、60、70、80、90、100）';
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('1', '倔强青铜', '0', '50', 0);
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('2', '秩序白银', '1000', '60', 0);
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('3', '荣耀黄金', '3000', '70', 0);
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('4', '尊贵铂金', '7000', '80', 0);
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('5', '永恒钻石', '15000', '90', 1);
INSERT INTO `user_level` (`level`, `level_name`, `integral`, `percentage`, `multi_channel`) VALUES ('6', '最强王者', '31000', '100', 1);

DROP TABLE IF EXISTS `app` ;
CREATE TABLE `app` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appid` VARCHAR(45) NOT NULL COMMENT '微信公众号appid',
  `app_name` VARCHAR(45) NULL COMMENT '产品名称',
  `white_invoke_urls` VARCHAR(100) NULL COMMENT '回调地址白名单，多个用英文逗号分隔',
  `company_name` VARCHAR(45) NULL COMMENT '公司名称',
  `priority` INT(3) NULL DEFAULT '50' COMMENT '优先级，用于本系统渠道推广时的顺序，越大优先级越高，默认50',
  `disabled` BIT(1) NULL DEFAULT 1 COMMENT ' true:不可用  false:可用',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '产品表（微信公众号表)';

DROP TABLE IF EXISTS `app_channel` ;
CREATE TABLE `app_channel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `app_id` INT NOT NULL COMMENT '产品ID',
  `scene_value` VARCHAR(45) NOT NULL COMMENT '设置的二维码渠道值',
  `channel_url` VARCHAR(45) NOT NULL COMMENT '二维码图片解析后的地址',
  `ticket` VARCHAR(45) NOT NULL COMMENT '获取的二维码ticket',
  `price` INT NOT NULL COMMENT '推广金额，单位分',
  `percentage` INT(2) NULL DEFAULT 80 COMMENT '系统用户各渠道的百分比，整数，不超过100',
  `priority` INT(3) NULL DEFAULT 50 COMMENT '产品内的优先级，用于本系统渠道推广时的顺序，越大优先级越高，默认50',
  `expire_date` timestamp NULL COMMENT '过期时间，如果null，标识永不过期，过期后不生成二维码',
  `max_num` INT NULL DEFAULT 0 COMMENT '最多推广关注人数，0表示无上限，超过此人数不生成二维码',
  `filter_sex` VARCHAR(45) NULL COMMENT '性别过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔,只有存在此字段的信息才能返回推广码',
  `filter_city` VARCHAR(100) NULL COMMENT '城市过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔,只有存在此字段的信息才能返回推广码',
  `filter_country` VARCHAR(45) NULL COMMENT '国家过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔,只有存在此字段的信息才能返回推广码',
  `filter_province` VARCHAR(100) NULL COMMENT '省份过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔,只有存在此字段的信息才能返回推广码',
  `filter_language` VARCHAR(45) NULL COMMENT '语言过滤字段，如果存在则需要符合此字段规则，多个用英文逗号分隔,只有存在此字段的信息才能返回推广码',
  `disabled` BIT(1) NULL DEFAULT 1 COMMENT ' true:不可用  false:可用',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '产品渠道表（需要推广的微信的不同场景渠道）';

DROP TABLE IF EXISTS `push_statistics` ;
CREATE TABLE `push_statistics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NOT NULL COMMENT '要扫码的用户的微信openid',
  `app_id` INT NOT NULL COMMENT ' 产品ID',
  `num` INT(2) NOT NULL DEFAULT 0 COMMENT '推送次数',
  `pre_push_time` DATETIME NOT NULL COMMENT '最近一次推送时间，用于判断5分钟内部给此用户推送此产品',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间（第一次推送时间）',
  PRIMARY KEY (`id`))
COMMENT = '推送统计表（系统配置表中配置最高推送次数，推送了max次后还没有关注app，说明此用户已经关注，不在推送）';
ALTER TABLE `push_statistics` 
ADD INDEX `push_statistics_openid_appId` USING BTREE (`openid` ASC, `app_id` ASC);

drop table if exists `push_log`;
CREATE TABLE `push_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `nickname` VARCHAR(45) COMMENT 'user_id的昵称',
  `user_channel_id` INT NOT NULL,
  `app_id` INT NOT NULL,
  `app_name` VARCHAR(45) comment '推送的产品的名称',
  `app_channel_id` INT NOT NULL,
  `success` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否订阅成功，默认false，如果有回调，且回调的时间和这个时间距离最近，同时此字段为false，则设置true，并给用户分成',
  `create_time` timestamp NULL DEFAULT now(),
  PRIMARY KEY (`id`))
COMMENT = '推送日志表（公众号方回调后，通过比对这个表中的时间来分成渠道）';

drop table if exists `subscribe_succ`;
CREATE TABLE `subscribe_succ` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NOT NULL COMMENT '微信订阅用户',
  `app_id` INT NOT NULL COMMENT '订阅的产品ID',
  `appid` VARCHAR(45) NOT NULL COMMENT '订阅的产品微信appid',
  `success` BIT(1) NULL DEFAULT 0 COMMENT 'true:订阅成功  false:未订阅',
  `push_limit` BIT(1) NULL DEFAULT 0 COMMENT 'true:推送超限（猜测此微信用户已经订阅此公众号）   false:未超限',
  `describe` VARCHAR(100) NULL COMMENT '其他描述',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '订阅记录表（在此表中的记录都不在推送给用户二维码）';
ALTER TABLE `subscribe_succ` 
ADD INDEX `openid_appId` USING BTREE (`openid` ASC, `app_id` ASC);

DROP TABLE IF EXISTS `scan_user` ;
CREATE TABLE `scan_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NOT NULL COMMENT '用户的唯一标识',
  `nickname` VARCHAR(45) NULL,
  `sex` INT(1) NULL DEFAULT 0,
  `province` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `headimgurl` VARCHAR(200) NULL,
  `create_time` timestamp NULL DEFAULT now() COMMENT '扫码时间（创建时间）',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `openid_UNIQUE` (`openid` ASC))
COMMENT = '扫码用户表（扫码授权就获取）';

DROP TABLE IF EXISTS `app_invoke_log` ;
CREATE TABLE `app_invoke_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `appid` VARCHAR(45) NOT NULL COMMENT '对应的产品appid',
  `openid` VARCHAR(45) NOT NULL COMMENT '微信openid',
  `scene_value` VARCHAR(45) NOT NULL COMMENT '场景ID',
  `invoke_time` INT NULL COMMENT '整型，产品方接受到事件创建的时间',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间（调用时间）',
  PRIMARY KEY (`id`))
COMMENT = '第三方系统回调表';

DROP TABLE IF EXISTS `app_settlement` ;
CREATE TABLE `app_settlement` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `app_id` INT NOT NULL,
  `app_channel_id` INT NOT NULL COMMENT '产品渠道ID',
  `amount_fee` INT NOT NULL COMMENT '结算金额，单位分',
  `date_from` DATETIME NOT NULL COMMENT '结算开始日期（包含当天的，yyyy-MM-dd）',
  `date_end` DATETIME NOT NULL COMMENT '结算结束日期（包含当天的，yyyy-MM-dd）',
  `describe` VARCHAR(100) NOT NULL COMMENT '结算描述',
  `admin_id` INT NOT NULL COMMENT '管理员ID',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建日期',
  PRIMARY KEY (`id`))
COMMENT = '第三方产品结算表';

DROP TABLE IF EXISTS `user_withdrawal`;
CREATE TABLE `user_withdrawal` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `cost_fee` INT NOT NULL COMMENT '提现金额，单位分',
  `operation_role` INT(1) NOT NULL DEFAULT 2 COMMENT '1:系统（待系统开通支付功能字段转账用）  2：管理员(必须存在管理员ID，且前期都是手工提现)',
  `admin_id` INT NULL COMMENT '管理员ID',
  `describe` VARCHAR(45) NULL COMMENT '管理员或系统提现说明',
  `handle_time` DATETIME NULL COMMENT '提现成功时间（管理员处理时间或系统处理时间）',
  `status`  int(1) NOT NULL DEFAULT 0 comment '提现状态 0：申请提现，待处理  1：提现成功，已处理',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '用户提现表';

DROP TABLE IF EXISTS `sys_template_message`;
CREATE TABLE `sys_template_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `template_key` VARCHAR(45) NOT NULL comment '系统模板消息的键',
  `title` VARCHAR(100) NULL COMMENT '模板标题',
  `content` VARCHAR(200) NOT NULL COMMENT '模板内容',
  `template` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否是微信模板 true:微信模板   false:用户事件回复消息',
  `template_id` VARCHAR(45) NULL COMMENT '模板ID',
  `disabled` BIT(1) NOT NULL DEFAULT 0 COMMENT 'true:禁用   false:正常（默认）',
  `create_time` timestamp NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '系统模板表（当用户发送指定消息或事件后发送消息给用户）';

DROP TABLE IF EXISTS `wechat_event_message`;
CREATE TABLE `wechat_event_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(45) NOT NULL,
  `event_time` INT NOT NULL COMMENT '长整型',
  `msg_type` VARCHAR(45) NOT NULL COMMENT '消息类型 event、text、image、voice、video、shortvideo、location、link',
  `wechat_event` VARCHAR(45) NULL COMMENT '事件类型  ',
  `wechat_event_key` VARCHAR(45) NULL COMMENT '事件key',
  `ticket` VARCHAR(45) NULL COMMENT '事件Ticket',
  `latitude` DOUBLE NULL COMMENT '上报地理位置事件的纬度',
  `longitude` DOUBLE NULL COMMENT '上报地理位置事件的经度',
  `precision` DOUBLE NULL COMMENT '上报地理位置事件的精度',
  `content` VARCHAR(200) NULL COMMENT '文本消息内容/消息描述',
  `msg_id` INT NULL COMMENT '消息id，64位整型',
  `pic_url` VARCHAR(45) NULL COMMENT '图片链接',
  `media_id` VARCHAR(45) NULL COMMENT '图片消息媒体id，可以调用多媒体文件下载接口拉取数据。',
  `format` VARCHAR(45) NULL COMMENT '语音格式，如amr，speex等',
  `recognition` VARCHAR(200) NULL COMMENT '语音识别结果',
  `thumb_media_id` VARCHAR(45) NULL COMMENT '视频消息缩略图的媒体id,可以调用多媒体文件下载接口拉取数据。',
  `label` VARCHAR(45) NULL COMMENT '地理位置信息',
  `title` VARCHAR(45) NULL COMMENT '消息标题',
  `url` VARCHAR(45) NULL COMMENT '消息链接',
  `read` BIT NOT NULL DEFAULT 0 comment '管理员是否已读，0：未读  1：已读',
  `read_admin_id` INT NULL COMMENT '已读消息的管理员ID',
  `create_time` timestamp NULL DEFAULT now(),
  PRIMARY KEY (`id`))
COMMENT = '微信事件消息模板表（用户在微信中的操作记录）';

DROP TABLE IF EXISTS `wechat_transfer_log` ;
CREATE TABLE `wechat_transfer_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_withdrawal_id` INT NOT NULL COMMENT '用户提现表ID',
  `nonce_str` VARCHAR(45) NULL COMMENT '微信转账请求参数--随机字符串',
  `partner_trade_no` VARCHAR(45) NOT NULL COMMENT '微信转账请求参数--商户订单号 ',
  `openid` VARCHAR(45) NOT NULL COMMENT '微信转账请求参数--用户openid',
  `re_user_name` VARCHAR(45) NULL COMMENT '微信转账请求参数--收款用户姓名  ',
  `amount` INT NULL COMMENT '微信转账请求参数--金额,单位分',
  `desc` VARCHAR(100) NULL COMMENT '微信转账请求参数--企业付款描述信息  ',
  `spbill_create_ip` VARCHAR(45) NULL COMMENT '微信转账请求参数--Ip地址 ',
  `return_code` VARCHAR(45) NULL COMMENT '微信转账返回参数--返回状态码 ',
  `return_msg` VARCHAR(45) NULL COMMENT '微信转账返回参数--返回信息',
  `result_code` VARCHAR(45) NULL COMMENT '微信转账返回参数--业务结果  ',
  `err_code` VARCHAR(45) NULL COMMENT '微信转账返回参数--错误代码  ',
  `err_code_des` VARCHAR(45) NULL COMMENT '微信转账返回参数--错误代码描述  ',
  `payment_no` VARCHAR(45) NULL COMMENT '微信转账返回参数--微信订单号 ',
  `payment_time` VARCHAR(45) NULL COMMENT '微信转账返回参数--微信支付成功时间 ',
  `create_time` timestamp NULL DEFAULT now() COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `partner_trade_no_UNIQUE` (`partner_trade_no` ASC))
COMMENT = '微信转账日志记录表';


DROP TABLE IF EXISTS `wechat_menu`;
CREATE TABLE `wechat_menu` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` INT NULL COMMENT '上级菜单',
  `order` INT(2) NULL COMMENT '菜单排序',
  `type` VARCHAR(45) NULL COMMENT '菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型',
  `name` VARCHAR(45) NOT NULL COMMENT '菜单标题，不超过16个字节，子菜单不超过60个字节',
  `url` VARCHAR(200) NULL COMMENT 'view、miniprogram类型必须，网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。',
  `key` VARCHAR(50) NULL COMMENT 'click等点击类型必须，菜单KEY值，用于消息接口推送，不超过128字节',
  `media_id` VARCHAR(45) NULL COMMENT 'media_id类型和view_limited类型必须，调用新增永久素材接口返回的合法media_id',
  `appid` VARCHAR(45) NULL COMMENT 'miniprogram类型必须，小程序的appid（仅认证公众号可配置）',
  `pagepath` VARCHAR(100) NULL COMMENT 'miniprogram类型必须，小程序的页面路径',
  `create_time` timestamp NULL DEFAULT now(),
  PRIMARY KEY (`id`))
COMMENT = '微信菜单表';


INSERT INTO `wechat_menu` (`order`, `name`) VALUES ('1', '推广');
INSERT INTO `wechat_menu` (`order`, `name`) VALUES ('2', '个人');
INSERT INTO `wechat_menu` (`order`, `name`) VALUES ('3', '帮助');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `url`) VALUES ('1', '1', 'view', '我的推广码', 'http://5936863d.ngrok.io/user/channel_qr');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `url`) VALUES ('1', '2', 'view', '产品大合集', 'http://5936863d.ngrok.io/app/list');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `key`) VALUES ('2', '1', 'click', '我的余额', 'spread_click_key_001');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `url`) VALUES ('2', '2', 'view', '收入详情', 'http://5936863d.ngrok.io/income/details');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `key`) VALUES ('2', '3', 'click', '提现说明', 'spread_click_key_002');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `key`) VALUES ('3', '1', 'click', '关于我们', 'spread_click_key_003');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `url`) VALUES ('3', '2', 'view', '产品说明', 'http://5936863d.ngrok.io/product_desc.html');
INSERT INTO `wechat_menu` (`parent_id`, `order`, `type`, `name`, `url`) VALUES ('3', '3', 'view', '常见问题', 'http://5936863d.ngrok.io/questions.html');

