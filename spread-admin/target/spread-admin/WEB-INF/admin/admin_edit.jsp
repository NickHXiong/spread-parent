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
<title>管理员列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<form class="layui-form">
		<c:if test="${flag == 'edit' }">
			<input type="hidden" required lay-verify="required" name="id" id="id" class="layui-input" value="${admin.id}" />
		</c:if>
		<c:if test="${flag == 'add' }">
			<div class="layui-form-item">
			    <label class="layui-form-label">账号</label>
			    <div class="layui-input-inline">
			      <input type="text" name="account" required lay-verify="account|number" placeholder="请输入账号" autocomplete="off" class="layui-input" value="${admin.account}">
			    </div>
			</div>
			<div class="layui-form-item">
		    <label class="layui-form-label">手机号码</label>
		    <div class="layui-input-inline">
		      <input type="tel" name="mobile" required lay-verify="phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input" value="${admin.mobile}">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">邮箱</label>
		    <div class="layui-input-inline">
		    	<input type="text" name="email" lay-verify="email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" value="${admin.email}">
		    </div>
		  </div>
	  </c:if>
	  <div class="layui-form-item">
	    <label class="layui-form-label">昵称</label>
	    <div class="layui-input-inline">
	      <input type="text" name="nickname" required lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input" value="${admin.nickname}">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">真实姓名</label>
	    <div class="layui-input-inline">
	      <input type="text" name="trueName" required lay-verify="required" placeholder="请输入真实姓名" autocomplete="off" class="layui-input" value="${admin.trueName}">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit lay-filter="formAdmin">立即提交</button>
	    </div>
	  </div>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form'], function(){
	var layer = layui.layer,form = layui.form();
	  
	 //自定义验证规则
	 form.verify({
	    account: function(value){
	      if(value.length < 5){
	        return '账号至少得8个字符啊';
	      }
	    }
	  });
	
	
	//监听提交
	form.on('submit(formAdmin)', function(data){
		var m ;
		<c:if test="${flag == 'edit' }">
			m = "修改";
		</c:if>
		<c:if test="${flag == 'add' }">
			m = "添加";
		</c:if>
		
	  	$.post("${basePath}/admin/${flag}",data.field,function(data){
	  		if (data.success) {
				layer.open({
					closeBtn: 0
					,title: '操作成功'
					,content: "操作成功，是否继续" + m
					,btn: ['继续' + m, '返回列表']
					,yes: function(index, layero){
						var o = $("#id").val();
						window.location.href="${basePath}/admin/${flag}"+(o?"?id="+o:"")
					}
					,btn2: function(index, layero){
						window.location.href="${basePath}/admin/list";
					}
				});
	  		} else {
				layer.open({
					title: '操作失败'
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