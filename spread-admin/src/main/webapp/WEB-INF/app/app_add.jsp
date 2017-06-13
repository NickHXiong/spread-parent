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
<title>产品增加</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<form class="layui-form">
		<div class="layui-form-item">
		    <label class="layui-form-label">产品appid</label>
		    <div class="layui-input-inline">
		      <input type="text" name="appid" required lay-verify="appid" placeholder="请输入产品appid" autocomplete="off" class="layui-input" >
		    </div>
		</div>
		<div class="layui-form-item">
	    <label class="layui-form-label">产品名称</label>
	    <div class="layui-input-inline">
	      <input type="text" name="appName" required lay-verify="required" placeholder="请输入产品名称" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">公司名称</label>
	    <div class="layui-input-inline">
	    	<input type="text" name="companyName" required lay-verify="required" placeholder="请输入公司名称" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">优先级</label>
	    <div class="layui-input-inline">
	      <input type="number" name="priority" placeholder="请输入优先级" autocomplete="off" class="layui-input" value="50">
	    </div>
	  </div>
	  <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">回调URL</label>
	    <div class="layui-input-inline">
	      <textarea placeholder="请输入回调URL" name="whiteInvokeUrls" class="layui-textarea"></textarea>
	    </div>
	    <div class="layui-form-mid layui-word-aux">多个用英文逗号分隔</div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit lay-filter="formApp">立即提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
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
		 appid: function(value){
			 if ($.trim(value) == "") {
				 return "必填项不能为空";
			 }
			 var result = "";
			 $.ajax({  
		         type : "post",  
		         dataType:"json",
		          url : "${basePath}/app/appidIsExist",  
		          data : "appid=" + value,  
		          async : false,  
		          success : function(data){  
		            if (data.success && data.count > 0) {
		            	result = "appid在系统中已经存在，请更换";
		            }
		          }  
		     }); 
	      	if(result != ""){
	        	return result;
	      	}
	    }
	  });
	
	//监听提交
	form.on('submit(formApp)', function(data){
		$.post("${basePath}/app/add",data.field,function(data){
	  		if (data.success) {
				layer.open({
					closeBtn: 0
					,title: '添加成功'
					,content: "添加成功，是否继续添加"
					,btn: ['继续添加', '返回产品列表']
					,yes: function(index, layero) {
						window.location.href="${basePath}/app/add";
					}
					,btn2: function(index, layero){
						window.location.href="${basePath}/app/list";
					}
				});
	  		} else {
				layer.open({
					title: '添加失败'
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