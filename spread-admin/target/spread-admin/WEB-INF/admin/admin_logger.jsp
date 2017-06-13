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
<title>管理员日志</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<div>
		<form class="layui-form" action="${basePath }/adminLogger/list">
		 <div class="layui-form-item">
		 	<div class="layui-inline">
			    <label class="layui-form-label">多规则验证</label>
			    <div class="layui-input-block">
			      	<select name="type">
				        <option value="">所有</option>
				        <c:forEach items="${types }" var="type">
				        	<option value="${type.value }">${type.desc }</option>
				        </c:forEach>
					</select>
			    </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">开始日期</label>
		      <div class="layui-input-inline">
		        <input type="text" name="startDate" id="startDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">结束日期</label>
		      <div class="layui-input-inline">
		        <input type="text" name="endDate" id="endDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      </div>
		    </div>
		    <div class="layui-inline">
		    	<button class="layui-btn" lay-submit="" lay-filter="demo2">查询</button>
		  	</div>
		  </div>
		  
		</form>
	</div>
	<div>
		<table class="layui-table">
		  <thead>
		    <tr>
		    	<th>管理员ID</th>
				<th>日志类型</th>
				<th>日志内容</th>
				<th>创建时间</th>
		    </tr> 
		  </thead>
		  <tbody>
		  	<c:forEach items="${pageInfo.list }" var="adminLogger">
		  		<tr>
			      <td>${adminLogger.adminId }</td>
			      <td>
		      		<c:forEach items="${types }" var="type">
		      			<c:if test="${type.value == adminLogger.type }">${type.desc }</c:if>
			        </c:forEach>
			      </td>
			      <td>${adminLogger.content }</td>
			      <td><fmt:formatDate value="${adminLogger.createTime }" type="both" /></td>
			    </tr>
		  	</c:forEach>
		  </tbody>
		</table>
	</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form','laydate'], function(){
	var layer = layui.layer;
	var form = layui.form();
	var laydate = layui.laydate();
});
</script>
</body>
</html>