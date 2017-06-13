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
<title>产品渠道增加</title>
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
		    	<select name="appId" id="appId" lay-verify="required">
			        <option value=""></option>
			        <c:forEach items="${appList }" var="app">
			        	<option value="${app.id }">${app.appName }</option>
			        </c:forEach>
			      </select>
		    </div>
		    <div class="layui-form-mid layui-word-aux">必选</div>
		</div>
		
	  <div class="layui-form-item">
	    <label class="layui-form-label">场景值</label>
	    <div class="layui-input-inline">
	      <input type="text" name="sceneValue" required lay-verify="required|sceneValue" placeholder="请输入渠道场景值" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">必填</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道URL</label>
	    <div class="layui-input-inline">
	      <input type="text" name="channelUrl" required lay-verify="required|url" placeholder="请输入渠道URL" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">必填</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道TICKET</label>
	    <div class="layui-input-inline">
	      <input type="text" name="ticket" required lay-verify="required" placeholder="请输入渠道TICKET" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">必填</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广价格</label>
	    <div class="layui-input-inline">
	      <input type="number" name="price" required lay-verify="required" placeholder="请输入推广价格" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">必填且必须大于0，单位分</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广分成</label>
	    <div class="layui-input-inline">
	      <input type="number" name="percentage" value="80" placeholder="请输入渠道推广分成" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">推广分成百分比，0-100，默认80</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道优先级</label>
	    <div class="layui-input-inline">
	      <input type="number" name="priority" value="50" placeholder="请输入渠道优先级" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">产品中渠道的优先级，默认50</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道过期日</label>
	    <div class="layui-input-inline">
	    	<input type="text" class="layui-input" name="expireDateStr" placeholder="请选择渠道过期日" onclick="layui.laydate({elem: this, festival: true, min: laydate.now(+1)})">
	    </div>
	    <div class="layui-form-mid layui-word-aux">到此日期后渠道不在推送给用户</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道上限</label>
	    <div class="layui-input-inline">
	      <input type="number" name="maxNum" placeholder="请输入渠道推广上限" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广性别</label>
	    <div class="layui-input-block">
	        <input type="checkbox" name="filterSex01" value="1" title="男">
	        <input type="checkbox" name="filterSex02" value="2" title="女">
	        <input type="checkbox" name="filterSex00" value="0" title="未知">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit lay-filter="formAppChannel">立即提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
	  </div>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form','laydate'], function(){
	var layer = layui.layer,form = layui.form();
	var laydate = layui.laydate;

	 //自定义验证规则
	 form.verify({
		 sceneValue: function(value){
			 var appId = $("#appId").val();
			 if (appId != "") {
				 var result = "";
				 $.ajax({  
			         type : "post",  
			         dataType:"json",
			          url : "${basePath}/appChannel/isExistAppIdAndSceneValue",  
			          data : "appId=" + value + "&sceneValue=" + value,  
			          async : false,  
			          success : function(data){
			            if (data.exist) {
			            	result = "产品的场景值在系统中已经存在，请更换";
			            }
			          }  
			     }); 
		      	if(result != ""){
		        	return result;
		      	}
			}
	    }
	  });
	
	//监听提交
	form.on('submit(formAppChannel)', function(data){
		$.post("${basePath}/appChannel/add",data.field,function(data){
	  		if (data.success) {
				layer.open({
					closeBtn: 0
					,title: '添加成功'
					,content: "添加成功，是否继续添加"
					,btn: ['继续添加', '返回产品渠道列表']
					,yes: function(index, layero) {
						window.location.href="${basePath}/appChannel/add";
					}
					,btn2: function(index, layero){
						window.location.href="${basePath}/appChannel/list";
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