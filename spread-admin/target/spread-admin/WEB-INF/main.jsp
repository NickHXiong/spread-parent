<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
<title>后台管理</title>
<link rel="stylesheet" href="${basePath}/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath}/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	function changeContent(src) {
		var oldSrc = $("#mainContent").attr("src");
		if (oldSrc != src) {
			document.getElementById("mainContent").src = src;
		}
	}
</script>
</head>
<body>
<!-- 顶部导航 -->
<div class="layui-layout layui-layout-admin">
	<div class="layui-header header">
		<ul class="layui-nav" lay-filter="">
		  <li class="layui-nav-item"><a href="javascript:void(0);">我的信息</a></li>
		  <li class="layui-nav-item"><a href="javascript:void(0);">修改密码</a></li>
		  <li class="layui-nav-item"><a href="javascript:void(0);">退出系统</a></li>
		</ul>
	</div>
	<!-- 侧边导航 -->
	<div class="layui-side layui-bg-black">
		<div class="layui-side-scroll">
			<ul class="layui-nav layui-nav-tree" lay-filter="test">
			  <li class="layui-nav-item layui-nav-itemed">
			    <a href="javascript:void(0);">用户管理</a>
			    <dl class="layui-nav-child">
			      <dd><a href="javascript:changeContent('${basePath}/user/list');">用户列表</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/userLevel/list');">用户等级列表</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/userWithdrawal/list');">提现记录</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/wechatEventMessage/list');">消息记录</a></dd>
			    </dl>
			  </li>
			  <li class="layui-nav-item">
			    <a href="javascript:;">产品管理</a>
			    <dl class="layui-nav-child">
			      <dd><a href="javascript:changeContent('${basePath}/app/list');">产品列表</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/appChannel/list');">渠道列表</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/push/log_list');">推送记录</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/push/statistics_list');">推送统计</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/subscribe/list');">订阅列表</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/scanUser/list');">扫码用户</a></dd>
			    </dl>
			  </li>
			  <li class="layui-nav-item">
			    <a href="javascript:;">系统管理</a>
			    <dl class="layui-nav-child">
			      <dd><a href="javascript:changeContent('${basePath}/sysTplMsg/list');">模板管理</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/appSettlement/list');">结算管理</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/invoke/list');">产品回调管理</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/sysConfig/info');">系统参数</a></dd>
			    </dl>
			  </li>
			  <li class="layui-nav-item">
			    <a href="javascript:;">管理员管理</a>
			    <dl class="layui-nav-child">
			      <dd><a href="javascript:changeContent('${basePath}/admin/list');">管理员管理</a></dd>
			      <dd><a href="javascript:changeContent('${basePath}/adminLogger/list');">日志列表</a></dd>
			    </dl>
			  </li>
			</ul>
		</div>
	</div>
	<!-- 内容 -->
	<div class="layui-body site-mine">
		<iframe src="${basePath}/admin/list" id="mainContent" width="100%" height="400" scrolling="auto" frameborder="0"></iframe>
	</div>
	<!-- 网站底部 -->
	<div class="layui-footer footer footer-mine">
	  <div class="layui-main">
	    <p>© 2017 <a href="/">layui.com</a> MIT license</p>
	    <p>
	      <a href="http://fly.layui.com/case/2017/" target="_blank">案例</a>
	      <a href="http://fly.layui.com/jie/3147.html" target="_blank">捐赠</a>
	      <a href="mailto:573977818@qq.com">联系</a>
	      <a href="http://fly.layui.com/jie/4281.html" target="_blank">Git仓库</a>
	      <a href="http://fly.layui.com/jie/2461.html" target="_blank">微信公众号</a>
	    </p>
	  </div>
	</div>
</div>
<script src="${basePath}/layui/layui.js"></script>
<script>
layui.use(['layer', 'laypage', 'element'], function(){
	  var layer = layui.layer
	  ,element = layui.element();
	  
	});
</script>
</body>
</html>   