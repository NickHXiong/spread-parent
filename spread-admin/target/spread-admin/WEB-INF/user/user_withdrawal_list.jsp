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
<title>用户提现列表</title>
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
	    	<th>用户ID</th>
			<th>提现金额（分）</th>
			<th>处理角色</th>
			<th>管理员ID</th>
			<th>处理说明</th>
			<th>处理时间</th>
			<th>状态</th>
			<th>创建时间</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="userT">
	  		<tr>
		      <td>${userT.userId }</td>
		      <td>${userT.costFee }</td>
		      <td>
		      	<c:forEach items="operationRoles" var="oRole">
		      		<c:if test="${oRole.value == userT.operationRole}">${oRole.desc }</c:if>
		      	</c:forEach>
		      </td>
		      <td>${userT.adminId }</td>
		      <td>${userT.describe }</td>
		      <td><fmt:formatDate value="${userT.handleTime }" type="both" /></td>
		      <td>
		      	<c:forEach items="statusEnums" var="stat">
		      		<c:if test="${stat.value == userT.status}">${stat.desc }</c:if>
		      	</c:forEach>
		      </td>
		      <td><fmt:formatDate value="${userT.createTime }" type="both" /></td>
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