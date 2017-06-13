<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<title>用户消息列表</title>
<link rel="stylesheet" href="${basePath }/layui/css/layui.css"
	media="all" />
<link rel="stylesheet" href="${basePath }/css/main.css" media="all" />
<script src="${basePath }/js/common.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<div>
		<div>
			<table class="layui-table">
				<tr>
					<td>openid</td>
					<td>${msg.openid }</td>
				</tr>
				<tr>
					<td>消息时间</td>
					<td><fmt:formatDate value="${msg.eventDate }" type="both" /></td>
				</tr>
				<tr>
					<td>消息类型</td>
					<td><c:forEach items="${msgTypes}" var="mt">
							<c:if test="${msg.msgType == mt.value }">${mt.desc }</c:if>
						</c:forEach></td>
				</tr>
				
				<c:if test="${msg.msgType == 'text' }">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>消息描述</td>
						<td>${msg.content }</td>
					</tr>
				</c:if>


				<c:if test="${msg.msgType == 'image' }">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>图片链接</td>
						<td>${msg.picUrl }</td>
					</tr>
					<tr>
						<td>媒体id</td>
						<td>${msg.mediaId }</td>
					</tr>
				</c:if>
				<c:if test="${msg.msgType == 'voice' }">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>媒体id</td>
						<td>${msg.mediaId }</td>
					</tr>
					<tr>
						<td>语音格式</td>
						<td>${msg.format }</td>
					</tr>
					<tr>
						<td>语音识别结果</td>
						<td>${msg.recognition }</td>
					</tr>
				</c:if>
				<c:if test="${msg.msgType == 'video' || msg.msgType == 'shortvideo'}">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>媒体id</td>
						<td>${msg.mediaId }</td>
					</tr>
					<tr>
						<td>视频消息缩略图的媒体id</td>
						<td>${msg.thumbMediaId }</td>
					</tr>
				</c:if>
				<c:if test="${msg.msgType == 'location' }">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>纬度</td>
						<td>${msg.latitude }</td>
					</tr>
					<tr>
						<td>经度</td>
						<td>${msg.longitude }</td>
					</tr>
					<tr>
						<td>地理位置精度</td>
						<td>${msg.precision }</td>
					</tr>
					<tr>
						<td>地理位置信息</td>
						<td>${msg.label }</td>
					</tr>
				</c:if>
				<c:if test="${msg.msgType == 'link' }">
					<tr>
						<td>消息id</td>
						<td>${msg.msgId }</td>
					</tr>
					<tr>
						<td>消息标题</td>
						<td>${msg.title }</td>
					</tr>
					<tr>
						<td>消息描述</td>
						<td>${msg.content }</td>
					</tr>
					<tr>
						<td>消息链接</td>
						<td>${msg.url }</td>
					</tr>
				</c:if>
				<c:if test="${msg.msgType == 'event' }">
					<tr>
						<td>事件类型</td>
						<td>
							<c:forEach items="${wechatEvents}" var="we">
								<c:if test="${msg.wechatEvent == we.value }">${we.desc }</c:if>
							</c:forEach>
						</td>
					</tr>
					<c:if test="${msg.wechatEvent == 'subscribe'|| msg.wechatEvent == 'SCAN' || msg.wechatEvent == 'CLICK' || msg.wechatEvent == 'VIEW'}">
						<tr>
							<td>事件KEY值</td>
							<td>${msg.wechatEventKey }</td>
						</tr>
						<c:if test="${msg.wechatEvent == 'subscribe'|| msg.wechatEvent == 'SCAN'}">
							<tr>
								<td>二维码的ticket</td>
								<td>${msg.ticket }</td>
							</tr>
						</c:if>
					</c:if>
					<c:if test="${msg.wechatEvent == 'LOCATION'}">
						<tr>
							<td>纬度</td>
							<td>${msg.latitude }</td>
						</tr>
						<tr>
							<td>经度</td>
							<td>${msg.longitude }</td>
						</tr>
						<tr>
							<td>地理位置精度</td>
							<td>${msg.precision }</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td>read</td>
					<td>${msg.read }</td>
				</tr>
				<tr>
					<td>readAdminId</td>
					<td>${msg.readAdminId }</td>
				</tr>
				<tr>
					<td>createTime</td>
					<td><fmt:formatDate value="${msg.createTime }" type="both" /></td>
				</tr>
			</table>
		</div>
	</div>
	<script src="${basePath }/layui/layui.js"></script>
	<script>
		layui.use([ 'layer' ], function() {
		});
	</script>
</body>
</html>