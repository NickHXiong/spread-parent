<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path ;
request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
<title>产品编辑</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<form class="layui-form">
		<input type="hidden" name="id" required lay-verify="required" value="${app.id }"/>
		<div class="layui-form-item">
		    <label class="layui-form-label">产品appid</label>
		    <div class="layui-input-inline">
		      <input type="text" name="appid" required lay-verify="required" disabled="disabled" value="${app.appid }" autocomplete="off" class="layui-input" >
		    </div>
		</div>
		<div class="layui-form-item">
	    <label class="layui-form-label">产品名称</label>
	    <div class="layui-input-inline">
	      <input type="text" name="appName" required lay-verify="required" placeholder="请输入产品名称" value="${app.appName }" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">公司名称</label>
	    <div class="layui-input-inline">
	    	<input type="text" name="companyName" required lay-verify="required" placeholder="请输入公司名称" value="${app.companyName }" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">优先级</label>
	    <div class="layui-input-inline">
	      <input type="number" name="priority" required lay-verify="required" placeholder="请输入优先级" autocomplete="off" value="${app.priority }" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">回调URL</label>
	    <div class="layui-input-inline">
	      <textarea placeholder="请输入回调URL" name="whiteInvokeUrls" class="layui-textarea">${app.whiteInvokeUrls }</textarea>
	    </div>
	    <div class="layui-form-mid layui-word-aux">多个用英文逗号分隔</div>
	  </div>
	  <div class="layui-form-item">
    	<label class="layui-form-label">是否禁用</label>
	    <div class="layui-input-block">
	      <input type="radio" name="disabled" value="1" title="禁用" <c:if test="${app.disabled }">checked</c:if>>
	      <input type="radio" name="disabled" value="0" title="启用" <c:if test="${!app.disabled }">checked</c:if>>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit lay-filter="formApp">立即提交</button>
	    </div>
	  </div>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form'], function(){
	var layer = layui.layer,form = layui.form();
	//监听提交
	form.on('submit(formApp)', function(data){
		$.post("${basePath}/app/edit",data.field,function(data){
	  		if (data.success) {
				layer.open({
					closeBtn: 0
					,title: '修改成功'
					,content: "修改成功，是否继续修改"
					,btn: ['继续修改', '返回产品列表']
					,yes: function(index, layero) {
						window.location.href="${basePath}/app/edit?id=${app.id}";
					}
					,btn2: function(index, layero){
						window.location.href="${basePath}/app/list";
					}
				});
	  		} else {
				layer.open({
					title: '修改失败'
					,content: data.msg
				});  
	  		}
	  	},"json");
		
		return false;
	});
});
</script>
</body>
</html>