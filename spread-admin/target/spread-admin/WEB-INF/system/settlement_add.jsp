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
<title>添加结算单</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<form class="layui-form">
		<div class="layui-form-item">
		    <label class="layui-form-label">产品</label>
		    <div class="layui-input-inline">
		    	<select name="appId" required lay-verify="required" lay-filter="appSelect">
			        <option value=""></option>
			        <c:forEach items="${appList }" var="app">
			        	<option value="${app.id }">${app.appName }</option>
			        </c:forEach>
			    </select>
		    </div>
		    <div class="layui-form-mid layui-word-aux">必选，请选择要结算的产品</div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">产品渠道</label>
		    <div class="layui-input-inline">
		    	<select name="appChannelId" id="appChannelId" required lay-verify="required">
			        <option value=""></option>
			    </select>
		    </div>
		    <div class="layui-form-mid layui-word-aux">必选，请选择要结算的产品渠道</div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">结算金额</label>
		    <div class="layui-input-inline">
		      <input type="number" name="amountFee" required lay-verify="required|amountFee" placeholder="请输入结算金额" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid layui-word-aux">必填，请填写要结算的金额，单位分</div>
		</div>
		<div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">结算日期</label>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" name="dateFrom" id="dateFrom" required lay-verify="required|date" placeholder="开始日" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      </div>
		      <div class="layui-form-mid">-</div>
		      <div class="layui-input-inline" style="width: 100px;">
		        <input type="text" name="dateEnd" id="dateEnd" required lay-verify="required|date" placeholder="截止日" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
		      </div>
			    <div class="layui-form-mid layui-word-aux">必选，请选择结算日期</div>
		    </div>
		</div>
	  	<div class="layui-form-item layui-form-text">
	  		<div class="layui-inline">
			    <label class="layui-form-label">结算说明</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入结算说明" name="describe" class="layui-textarea"></textarea>
			    </div>
		    </div>
		</div>
		
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit lay-filter="formSettlement">立即提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		</div>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form','laydate'], function(){
	var layer = layui.layer,form = layui.form(),laydate = layui.laydate;

	 //自定义验证规则
	 form.verify({
		amountFee: function(value){
			var num = Number(value);
			if (isNaN(num) || num <= 0) {
				return "请填写正确的结算金额";
			}
	    }
	  });
	 
	var start = {
  		max: '2099-06-16 23:59:59'
  		,istoday: false
  		,choose: function(datas){
    		end.min = datas; //开始日选好后，重置结束日的最小日期
    		end.start = datas //将结束日的初始值设定为开始日
  		}
	};
			  
	var end = {
		max: '2099-06-16 23:59:59'
		,istoday: false
		,choose: function(datas){
			start.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	
	$("#dateFrom").click(function(){
		start.elem = this;
		laydate(start);
	});
	
	$("#dateEnd").click(function(){
		end.elem = this
	    laydate(end);
	});
	 
	//根据选择不同的产品来加载不同的渠道
	form.on('select(appSelect)', function(data){
		$("#appChannelId").html("<option value=''></option>");
		$.post("${basePath}/appChannel/getChannelListByAppId",{"appId":data.value},function(data){
			if (data.success) {
				var strBuilder = "<option value=''></option>";
				$.each(data.list, function(index, value, array) {
					strBuilder += "<option value='"+value.id+"'>"+value.sceneValue+"</option>";
				});
				$("#appChannelId").html(strBuilder);
				form.render('select');
			}
		},"json");
		form.render('select');
	});
	 
	//监听提交
	form.on('submit(formSettlement)', function(data){
		$.post("${basePath}/appSettlement/add",data.field,function(data){
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