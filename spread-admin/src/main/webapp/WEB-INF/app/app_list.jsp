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
<title>产品列表</title>
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
		$.post("${basePath}/app/changeDisableStatus",{"id":id,"status":status},function(data){
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
				$("#status_show_" + id).html(status?"已禁用":"未禁用");
			} else { // 修改失败提示
				msg = (status?"禁用":"启用") + "操作失败";
			}
			layer.msg(msg);
			
		},"json")
	});
	
	// 编辑按钮修改
	$("button[id^=edit_btn_]").click(function(){
		var id = $(this).attr("key");
		window.location.href="${basePath}/app/edit?id=" + id;
	});
	// 编辑按钮修改
	$("button[id^=channels_btn_]").click(function(){
		var id = $(this).attr("key");
		window.location.href="${basePath}/appChannel/list?appId=" + id;
	});
})
	
</script>
</head>
<body>
<div>
<div>
	<a href="${basePath }/app/add" class="layui-btn">新增产品</a>
</div>
<div>
	<form class="layui-form" method="post" action="${basePath }/app/list">
  		<div class="layui-form-item">
  			<div class="layui-inline">
		      <label class="layui-form-label">产品名称</label>
		      <div class="layui-input-inline">
		        <input type="tel" name="appName" autocomplete="off" class="layui-input" value="${_params.appName }">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">公司名称</label>
		      <div class="layui-input-inline">
		        <input type="text" name="companyName" autocomplete="off" class="layui-input" value="${_params.companyName }">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <label class="layui-form-label">是否禁用</label>
		      <div class="layui-input-inline">
		     	 <select name="disabled">
			        <option value="-1">全部</option>
			        <option value="0" <c:if test="${_params.disabled == 0 }">selected</c:if>>未禁用</option>
			        <option value="1" <c:if test="${_params.disabled == 1 }">selected</c:if>>已禁用</option>
			      </select>
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
	    	<th>appid</th>
	    	<th>产品名称</th>
	    	<th>公司名称</th>
	    	<th>优先级</th>
	    	<th>禁用</th>
			<th>创建时间</th>
			<th>回调白名单</th>
			<th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="app">
	  		<tr>
		      <td>${app.appid }</td>
		      <td>${app.appName }</td>
		      <td>${app.companyName }</td>
		      <td>${app.priority }</td>
		      <td>
		      	<font id="status_show_${app.id }">
			      	<c:if test="${app.disabled }">已禁用</c:if>
			      	<c:if test="${!app.disabled }">未禁用</c:if>
		      	</font>
		      </td>
		      <td><fmt:formatDate value="${app.createTime }" type="both" /></td>
		      <td>${app.whiteInvokeUrls }</td>
		      <td>
		      	<c:if test="${app.disabled }">
			      	<button class="layui-btn" d="${app.disabled }" key="${app.id }">启用</button>
		      	</c:if>
		      	<c:if test="${!app.disabled }">
			      	<button class="layui-btn layui-btn-danger" d="${app.disabled }" key="${app.id }">禁用</button>
		      	</c:if>
		      	<button class="layui-btn layui-btn-warm" id="edit_btn_${app.id }" key="${app.id }">编辑</button>
		      	<button class="layui-btn layui-btn-normal" id="channels_btn_${app.id }" key="${app.id }">渠道信息</button>
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