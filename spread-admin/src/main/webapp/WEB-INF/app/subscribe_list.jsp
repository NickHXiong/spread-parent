<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>订阅记录列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
<div>
	<table class="layui-table">
	  <thead>
	    <tr>
	    	<th>openid</th>
			<th>产品ID</th>
			<th>产品appid</th>
			<th>订阅成功</th>
			<th>推送超限</th>
			<th>描述</th>
			<th>创建时间</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="user">
	  		<tr>
		      <td>${user.openid }</td>
		      <td>${user.appId }</td>
		      <td>${user.appid }</td>
		      <td>${user.success }</td>
		      <td>${user.pushLimit }</td>
		      <td>${user.describe }</td>
		      <td><fmt:formatDate value="${user.createTime }" type="both" /></td>
		    </tr>
	  	</c:forEach>
	  </tbody>
	</table>
</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer'], function(){
	var layer = layui.layer;
});
</script>
</body>
</html>