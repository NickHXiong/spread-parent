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
<title>用户等级列表</title>
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
	    	<th>等级</th>
			<th>等级名称</th>
			<th>所需积分</th>
			<th>分成比例%</th>
			<th>可多渠道</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${list }" var="userLevel">
	  		<tr>
		      <td>${userLevel.level }</td>
		      <td>${userLevel.levelName }</td>
		      <td>${userLevel.integral }</td>
		      <td>${userLevel.percentage }</td>
		      <td>${userLevel.multiChannel }</td>
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