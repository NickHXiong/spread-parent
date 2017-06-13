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
<title>产品渠道列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$("button[d]").click(function(){
		var self = $(this);
		var id = self.attr("key");
		var status = self.attr("d") == "false"?true:false; 
		$.post("${basePath}/appChannel/changeDisableStatus",{"id":id,"status":status},function(data){
			var msg ;
			if (data.success) { // 修改成功
				// 修改相关条目的数据
				self.attr("d",String(status));
				self.html(status?"启用":"禁用");
				if (status) {
					self.removeClass("layui-btn-danger");
				} else {
					self.addClass("layui-btn-danger");
				}
				msg = (status?"禁用":"启用") + "操作成功";
				$("#status_show_" + id).html(status?"禁用":"启用");
			} else { // 修改失败提示
				msg = (status?"禁用":"启用") + "操作失败";
			}
			layer.msg(msg);
			
		},"json")
	});
	
	// 编辑按钮修改
	$("button[id^=edit_btn_]").click(function(){
		var id = $(this).attr("key");
		window.location.href="${basePath}/appChannel/edit?id=" + id;
	});
})
</script>
</head>
<body>
<div>
<div>
	<a href="${basePath }/appChannel/add" class="layui-btn">新增产品渠道</a>
</div>
<div>
	<form class="layui-form" method="post" action="${basePath }/appChannel/list">
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
		      <label class="layui-form-label">场景值</label>
		      <div class="layui-input-inline">
		        <input type="text" name="sceneValue" autocomplete="off" class="layui-input" value="${_params.sceneValue }">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">URL</label>
		      <div class="layui-input-inline">
		        <input type="text" name="channelUrl" autocomplete="off" class="layui-input" value="${_params.channelUrl }">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">ticket</label>
		      <div class="layui-input-inline">
		        <input type="text" name="ticket" autocomplete="off" class="layui-input" value="${_params.ticket }">
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">状态</label>
		      <div class="layui-input-inline">
		     	 <select name="disabled">
			        <option value="-1">全部</option>
			        <option value="0" <c:if test="${_params.disabled == 0 }">selected</c:if>>启用</option>
			        <option value="1" <c:if test="${_params.disabled == 1 }">selected</c:if>>禁用</option>
			      </select>
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">价格</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="number" name="priceMin" placeholder="￥" autocomplete="off" value="${_params.priceMin }" class="layui-input">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="number" name="priceMax" placeholder="￥" autocomplete="off" value="${_params.priceMax }" class="layui-input">
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		    	<button class="layui-btn" lay-submit="" lay-filter="searchBtn">查询</button>
		    </div>
  		</div>
  	</form>
</div>
<div>
	<table class="layui-table">
	  <thead>
	    <tr>
	    	<th>产品ID</th>
	    	<th>场景值</th>
	    	<th>二维码URL</th>
	    	<th>二维码ticket</th>
	    	<th>推广价格</th>
	    	<th>推广分成%</th>
	    	<th>优先级</th>
	    	<th>过期时间</th>
	    	<th>状态</th>
			<th>创建时间</th>
			<th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="user">
	  		<tr>
		      <td>${user.appId }</td>
		      <td>${user.sceneValue }</td>
		      <td>${user.channelUrl }</td>
		      <td>${user.ticket }</td>
		      <td>${user.price }</td>
		      <td>${user.percentage }</td>
		      <td>${user.priority }</td>
		      <td><fmt:formatDate value="${user.expireDate }" pattern="yyyy-MM-dd" /></td>
		      <td>
		      	<font id="status_show_${user.id }">
			      	<c:if test="${!user.disabled }">启用</c:if>
			      	<c:if test="${user.disabled }">禁用</c:if>
		      	</font>
		      </td>
		      <td><fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
		      <td>
		      	<c:if test="${user.disabled }">
			      	<button class="layui-btn" d="${user.disabled }" key="${user.id }">启用</button>
		      	</c:if>
		      	<c:if test="${!user.disabled }">
			      	<button class="layui-btn layui-btn-danger" d="${user.disabled }" key="${user.id }">禁用</button>
		      	</c:if>
		      	<button class="layui-btn layui-btn-warm" id="edit_btn_${app.id }" key="${user.id }">编辑</button>
		      </td>
		    </tr>
	  	</c:forEach>
	  </tbody>
	</table>
</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form'], function(){
	var layer = layui.layer;
	var form = layui.form();
});
</script>
</body>
</html>