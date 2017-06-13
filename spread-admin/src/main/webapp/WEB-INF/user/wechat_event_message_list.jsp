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
<title>用户消息列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
<div>
	<form class="layui-form" method="post" action="${basePath }/wechatEventMessage/list">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">openid</label>
			<div class="layui-input-inline">
				<input type="text" name="openid" autocomplete="off" class="layui-input">  
			</div>
		</div> 
		<div class="layui-inline">
			<label class="layui-form-label">消息类型</label>
			<div class="layui-input-inline">
				<select name="msgType">
			        <option value="">全部</option>
			        <c:forEach items="${msgTypes}" var="mt">
			        	<option value="${mt.value }">${mt.desc }</option>
			        </c:forEach>
		        </select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">事件类型</label>
			<div class="layui-input-inline">
				<select name="wechatEvent">
			        <option value="">全部</option>
			        <c:forEach items="${wechatEvents}" var="we">
			        	<option value="${we.value }">${we.desc }</option>
			        </c:forEach>
		        </select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">是否已读</label>
			<div class="layui-input-inline">
				<select name="read">
			        <option value="">全部</option>
			        <option value="1">已读</option>
			        <option value="0">未读</option>
		        </select>
			</div>
		</div>
		<div class="layui-inline">
	      <label class="layui-form-label">时间</label>
	      <div class="layui-input-inline" style="width: 100px;">
	        <input type="text" name="startDate" id="startDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
	      </div>
	      <div class="layui-form-mid">-</div>
	      <div class="layui-input-inline" style="width: 100px;">
	        <input type="text" name="endDate" id="endDate" placeholder="yyyy-mm-dd" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
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
	    	<th>openid</th>
			<th>消息时间</th>
			<th>消息类型</th>
			<th>事件类型</th>
			<th>是否已读</th>
			<th>管理员ID</th>
			<th>创建时间</th>
			<th>查看</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="msg">
	  		<tr>
		      <td>${msg.openid }</td>
		      <td><fmt:formatDate value="${msg.eventDate }" type="both" /></td>
		      <td>
		      		<c:forEach items="${msgTypes}" var="mt">
		      			<c:if test="${msg.msgType == mt.value }">${mt.desc }</c:if>
			        </c:forEach>
		      </td>
		      <td>
		      	<c:forEach items="${wechatEvents}" var="we">
		      		<c:if test="${msg.wechatEvent == we.value }">${we.desc }</c:if>
			   	</c:forEach>
		      </td>
		      <td>${msg.read }</td>
		      <td>${msg.readAdminId }</td>
		      <td><fmt:formatDate value="${msg.createTime }" type="both" /></td>
		      <td>
		      	<a href="${basePath }/wechatEventMessage/detail?id=${msg.id}" class="layui-btn">详情</a>
		      </td>
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