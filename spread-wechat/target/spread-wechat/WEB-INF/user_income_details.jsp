<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta name="format-detection" content="telephone=no" />
<title>收入详情列表</title>
</head>
<body>
	<h2>收入详情列表</h2>
	<table border="1">
		<tr>
			<th>产品名称</th>
			<th>关注人</th>
			<th>推广时间</th>
			<th>收入金额（单位分）</th>
		</tr>
		<c:if test="${pageInfo.list != null && fn:length(pageInfo.list) > 0}">
			<c:forEach items="${pageInfo.list }" var="income">
				<tr>
					<td>${income.appName }</td>
					<td>${income.subNickname }</td>
					<c:if test="${income.subscribeDate != null}">
						<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${income.subscribeDate}" /></td>
					</c:if>
					<c:if test="${income.subscribeDate == null}">
						<td> - </td>
					</c:if>
					<td>${income.fee }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${pageInfo.list == null || fn:length(pageInfo.list) == 0}">
			<tr>
				<td colspan="4">暂无收入，赶快去<a href="/user/channel_qr">推广</a></td>
			</tr>
		</c:if>
	</table>
</body>
</html>