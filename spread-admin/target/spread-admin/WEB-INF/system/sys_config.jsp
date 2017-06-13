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
<title>系统参数配置</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
var num = 0;
var oldValue01 ;
var oldValue02 ;
$(function(){
	$("input[id^='listen_c_']").change(function(){
		var oldV = (num == 1?oldValue01:oldValue02);
		var th = $(this);
		var value = th.val();
		if ($.trim(value) == "") {
			layer.msg('配置参数不能为空');
			th.val(oldValue01);
		}
		$.post("${basePath}/sysConfig/config",{"key":th.attr("name"),"value":value},function(data){
			if (data.success) {
				layer.msg('配置成功');
			} else {
				layer.msg('配置失败，' + data.msg);
				th.val(oldValue01);
			}
		},"json");
	});
	
	$("input[id^='listen_c_']").focus(function(){
		num = (num + 1) % 2;
		if (num == 1) {
			oldValue01 = $(this).val();
		} else {
			oldValue02 = $(this).val();
		}
	});
})
</script>
</head>
<body>
<div>
	<form class="layui-form layui-form-pane" action="">
		<c:forEach items="${sysConfigs}" var="config">
			<div class="layui-form-item">
			    <label class="layui-form-label">${config.key }</label>
			    <div class="layui-input-inline">
			      <input type="text" name="${config.key }" id="listen_c_${config.key }" placeholder="请输入" value="${config.value }" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">
			    （
			    <c:forEach items="${valueTypes }" var="vt">
			    	<c:if test="${vt.type == config.valueType }">${vt.desc}</c:if>
			    </c:forEach>
			    ）${config.describe }</div>
	  		</div>
		</c:forEach>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
var layer;
layui.use(['layer','form'], function(){
	var form = layui.form();
	layer = layui.layer;
});
</script>
</body>
</html>