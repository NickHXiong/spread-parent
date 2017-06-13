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
<title>结算管理</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#settle_add_btn").click(function(){
		window.location.href="${basePath}/appSettlement/add";
	});
})
</script>
</head>
<body>
<div>
<div>
	<button class="layui-btn" id="settle_add_btn">新增结算</button>
</div>
<div>
	<form class="layui-form" method="post" action="${basePath }/appSettlement/list">
		<div class="layui-form-item">
			<div class="layui-inline">
		      <label class="layui-form-label">产品</label>
		      <div class="layui-input-inline">
		      	<select name="appId" id="appId">
			        <option value="-1">全部</option>
			        <c:forEach items="${appList }" var="app">
			        	<option value="${app.id }" <c:if test="${_params.appId == app.id}">selected</c:if>>${app.appName }</option>
			        </c:forEach>
			      </select>
		      </div>
		    </div>
			<div class="layui-inline">
				<label class="layui-form-label">结算日期</label>
		      	<div class="layui-input-inline" style="width: 100px;">
		        	<input type="text" name="dateFrom" id="dateFrom" value="${_params.dateFrom }" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      	</div>
		      	<div class="layui-form-mid">-</div>
		      	<div class="layui-input-inline" style="width: 100px;">
		        	<input type="text" name="dateTo" id="dateTo" value="${_params.dateTo }" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      	</div>
			</div>
			<div class="layui-inline">
				<button class="layui-btn" lay-submit="">查询</button>
			</div>
		</div>
	</form>
</div>
<div>
	<table class="layui-table">
	  <thead>
	    <tr>
	    	<th>产品ID</th>
			<th>渠道ID</th>
			<th>结算金额(分)</th>
			<th>结算开始日期</th>
			<th>结算结束日期</th>
			<th>说明</th>
			<th>管理员ID</th>
			<th>创建时间</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="settlement">
	  		<tr>
		      <td>${settlement.appId }</td>
		      <td>${settlement.appChannelId }</td>
		      <td>${settlement.amountFee }</td>
		      <td><fmt:formatDate value="${settlement.dateFrom }" pattern="yyyy-MM-dd" /></td>
		      <td><fmt:formatDate value="${settlement.dateEnd }" pattern="yyyy-MM-dd" /></td>
		      <td>${settlement.describe }</td>
		      <td>${settlement.adminId }</td>
		      <td><fmt:formatDate value="${settlement.createTime }" type="both" /></td>
		    </tr>
	  	</c:forEach>
	  </tbody>
	</table>
</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form', 'laydate'], function(){
	var form = layui.form()
	  ,layer = layui.layer
	  ,laydate = layui.laydate;
});
</script>
</body>
</html>