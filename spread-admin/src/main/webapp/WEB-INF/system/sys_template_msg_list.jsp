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
<title>系统模板列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
<div>
	<button class="layui-btn">新增模板</button>
</div>
<div>
	<table class="layui-table">
	  <thead>
	    <tr>
	    	<th>模板KEY</th>
	    	<th>模板标题</th>
	    	<th>模板内容</th>
			<th>微信模板</th>
			<th>微信模板ID</th>
			<th>是否禁用</th>
			<th>创建时间</th>
			<th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${list }" var="msg">
	  		<tr>
		      <td>${msg.templateKey }</td>
		      <td>${msg.title }</td>
		      <td>${msg.content }</td>
		      <td>${msg.template }</td>
		      <td>${msg.templateId }</td>
		      <td>${msg.disabled }</td>
		      <td><fmt:formatDate value="${msg.createTime }" type="both" /></td>
		      <td>
		      	<c:if test="${msg.disabled }">
		      		<button class="layui-btn">启用</button>
		      	</c:if>
		      	<c:if test="${!msg.disabled }">
		      		<button class="layui-btn">禁用</button>
		      	</c:if>
		      	<a href="${basePath }/sysTplMsg/edit?id=${msg.id}" class="layui-btn">编辑</a>
		      </td>
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