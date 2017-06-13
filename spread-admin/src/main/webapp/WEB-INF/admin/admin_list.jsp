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
<title>管理员列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
function statusChange(id) {
	var status = $("#disabled_" + id).attr("d");
	var disabled = 1;
	if (status == "true") { // 当前禁用状态，做启用操作
		disabled = 0;
	} 
	
	$.post("${basePath}/admin/changeDisabledStatus",{"id":id,"status":disabled},function(data){
		var msg ;
		if (data.success) { // 修改成功
			// 修改相关条目的数据
			var showObj = $("#disabled_show_" + id);
			var timeObj = $("#disabled_time_" + id);
			var btnObj = $("#disabled_" + id);
			if (disabled) { // 禁用成功
				showObj.html("true");
				timeObj.html(new Date().Format("yyyy-MM-dd hh:mm:ss"));
				btnObj.attr("d","true");
				btnObj.html("启用");
				btnObj.removeClass("layui-btn-danger");
			} else { // 启用成功
				showObj.html("false");
				timeObj.html("");
				btnObj.attr("d","false");
				btnObj.html("禁用");
				btnObj.addClass("layui-btn-danger");
			}
			msg = (disabled == 1?"禁用":"启用") + "操作成功";
		} else { // 修改失败提示
			msg = (disabled == 1?"禁用":"启用") + "操作失败";
		}
		layer.msg(msg);
	},"json");
}
</script>
</head>
<body>
<div>
<div>
	<a href="${basePath }/admin/add" class="layui-btn">新增管理员</a>
</div>
<div>
	<form class="layui-form" method="post" action="${basePath }/admin/list">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">昵称</label>
			<div class="layui-input-inline">
				<input type="text" name="nickname" placeholder="管理员昵称" autocomplete="off" class="layui-input">  
			</div>
		</div> 
		<div class="layui-inline">
			<label class="layui-form-label">真实姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="trueName" placeholder="管理员真实姓名" autocomplete="off" class="layui-input">  
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">电话号码</label>
			<div class="layui-input-inline">
				<input type="text" name="mobile" placeholder="管理员电话号码" autocomplete="off" class="layui-input">  
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">邮箱</label>
			<div class="layui-input-inline">
				<input type="text" name="email" placeholder="管理员邮箱" autocomplete="off" class="layui-input">  
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">账号</label>
			<div class="layui-input-inline">
				<input type="text" name="account" placeholder="管理员账号" autocomplete="off" class="layui-input">  
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
	    	<th>昵称</th>
			<th>真实姓名</th>
			<th>电话号码</th>
			<th>Email</th>
			<th>账号</th>
			<th>超级管理员</th>
			<th>创建时间</th>
			<th>禁用</th>
			<th>禁用时间</th>
			<th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="admin">
	  		<tr>
		      <td>${admin.nickname }</td>
		      <td>${admin.trueName }</td>
		      <td>${admin.mobile }</td>
		      <td>${admin.email }</td>
		      <td>${admin.account }</td>
		      <td>${admin.superAdmin }</td>
		      <td><fmt:formatDate value="${admin.createTime }" type="both" /></td>
		      <td id="disabled_show_${admin.id }">${admin.disabled }</td>
		      <td id="disabled_time_${admin.id }">
		      	<c:if test="${admin.disabled }">
		      		<fmt:formatDate value="${admin.disabledTime }" type="both" />
		      	</c:if>
		      </td>
		      <td>
		      	<c:if test="${admin.disabled && !admin.superAdmin}">
		      		<button class="layui-btn" id="disabled_${admin.id }" d="${admin.disabled }" onclick="statusChange('${admin.id}')">启用</button>
		      	</c:if>
		      	<c:if test="${!admin.disabled && !admin.superAdmin}">
		      		<button class="layui-btn layui-btn-danger" id="disabled_${admin.id }" d="${admin.disabled }" onclick="statusChange('${admin.id}')">禁用</button>
		      	</c:if>
		      	<a href="${basePath }/admin/edit?id=${admin.id}" class="layui-btn layui-btn-warm">编辑</a>
		      </td>
		    </tr>
	  	</c:forEach>
	  </tbody>
	</table>
</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
var layer;
layui.use(['layer','form'], function(){
	layer = layui.layer;
	var form = layui.form();
});
</script>
</body>
</html>