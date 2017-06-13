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
<title>产品回调日志列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
<div>
	<input type="file" id="importIpt" name="settleFile" lay-title="导入推广成功EXCEL" class="layui-upload-file" />
</div>
<div>
	<table class="layui-table">
	  <thead>
	    <tr>
	    	<th>appid</th>
	    	<th>openid</th>
	    	<th>场景值</th>
	    	<th>回调时间</th>
			<th>创建时间</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${pageInfo.list }" var="invoke">
	  		<tr>
		      <td>${invoke.appid }</td>
		      <td>${invoke.openid }</td>
		      <td>${invoke.sceneValue }</td>
		      <td><fmt:formatDate value="${invoke.invokeDate }" type="both" /></td>
		      <td><fmt:formatDate value="${invoke.createTime }" type="both" /></td>
		    </tr>
	  	</c:forEach>
	  </tbody>
	</table>
</div>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','upload'], function(){
	var layer = layui.layer;
	layui.upload({
		url: '${basePath}/invoke/upload'
		,type:'file'
		,ext: 'xlsx'
		,elem: '#importIpt' //指定原始元素，默认直接查找class="layui-upload-file"
		,method: 'post' //上传接口的http类型
		,before: function(input){
			layer.msg("正在导入。。。");
		}
		,success: function(res) {
			if (res.success) {
				layer.alert('导入推广文件成功', {icon: 1});
			} else {
				layer.alert(res.msg, {icon: 2});
			}
		}
	});
});
</script>
</body>
</html>