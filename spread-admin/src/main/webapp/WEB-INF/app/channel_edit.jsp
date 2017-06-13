<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<title>产品渠道编辑</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${basePath }/css/main.css" media="all"/>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<div>
	<form class="layui-form">
		<input type="hidden" name="id" required lay-verify="required" value="${appChannel.id }"/>
		<div class="layui-form-item">
		    <label class="layui-form-label">产品</label>
		    <div class="layui-input-inline">
		    	<input type="text" value="${app.appName }" disabled="disabled" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid layui-word-aux">不可编辑项</div>
		</div>
		
	  <div class="layui-form-item">
	    <label class="layui-form-label">场景值</label>
	    <div class="layui-input-inline">
	      <input type="text" name="sceneValue" value="${appChannel.sceneValue }" disabled="disabled" placeholder="请输入渠道场景值" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">不可编辑项</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道URL</label>
	    <div class="layui-input-inline">
	      <input type="text" name="channelUrl" value="${appChannel.channelUrl }" disabled="disabled" placeholder="请输入渠道URL" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">不可编辑项</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道TICKET</label>
	    <div class="layui-input-inline">
	      <input type="text" name="ticket" value="${appChannel.ticket }" disabled="disabled" placeholder="请输入渠道TICKET" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">不可编辑项</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广价格</label>
	    <div class="layui-input-inline">
	      <input type="number" name="price" value="${appChannel.price }" disabled="disabled" placeholder="请输入推广价格" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">不可编辑项，单位分</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广分成</label>
	    <div class="layui-input-inline">
	      <input type="number" name="percentage" value="80" placeholder="请输入渠道推广分成" value="${appChannel.percentage }" disabled="disabled" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">推广分成百分比，不可编辑项</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道优先级</label>
	    <div class="layui-input-inline">
	      <input type="number" name="priority" value="50" placeholder="请输入渠道优先级" value="${appChannel.priority }" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-form-mid layui-word-aux">产品中渠道的优先级，默认50</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道过期日</label>
	    <div class="layui-input-inline">
	    	<input type="text" class="layui-input" name="expireDateStr" value="<fmt:formatDate value="${appChannel.expireDate}" pattern="yyyy-MM-dd" />" placeholder="请选择渠道过期日" onclick="layui.laydate({elem: this, festival: true, min: laydate.now(+1)})">
	    </div>
	    <div class="layui-form-mid layui-word-aux">到此日期后渠道不在推送给用户</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">渠道上限</label>
	    <div class="layui-input-inline">
	      <input type="number" name="maxNum" placeholder="请输入渠道推广上限" value="${appChannel.maxNum }" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">推广性别</label>
	    <div class="layui-input-block">
	        <input type="checkbox" name="filterSex01" value="1" title="男" <c:if test='${fn:indexOf(appChannel.filterSex,"1") > 0}'>checked</c:if>>
	        <input type="checkbox" name="filterSex02" value="2" title="女" <c:if test='${fn:indexOf(appChannel.filterSex,"2") > 0}'>checked</c:if>>
	        <input type="checkbox" name="filterSex00" value="0" title="未知" <c:if test='${fn:indexOf(appChannel.filterSex,"0") > 0}'>checked</c:if>>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit lay-filter="formAppChannel">立即提交</button>
	    </div>
	  </div>
	</form>
</div>
<script src="${basePath }/layui/layui.js"></script>
<script>
layui.use(['layer','form','laydate'], function(){
	var layer = layui.layer,form = layui.form();
	var laydate = layui.laydate;
	
	//监听提交
	form.on('submit(formAppChannel)', function(data){
		$.post("${basePath}/appChannel/edit",data.field,function(data){
	  		if (data.success) {
				layer.open({
					closeBtn: 0
					,title: '修改成功'
					,content: "修改成功，是否继续修改"
					,btn: ['继续修改', '返回产品渠道列表']
					,yes: function(index, layero) {
						window.location.href="${basePath}/appChannel/edit?id=${appChannel.id}";
					}
					,btn2: function(index, layero) {
						window.location.href="${basePath}/appChannel/list";
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