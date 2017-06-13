<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta name="format-detection" content="telephone=no" />
<title>推广产品列表</title>
</head>
<body>
	<h2>推广产品列表</h2>
	<table border="1">
		<tr>
			<th>产品名称</th>
			<th>产品公司名称</th>
			<th>推广价格（单位分）</th>
		</tr>
		<c:if test="${fn:length(list) > 0}">
			<c:forEach items="${list }" var="app">
				<tr>
					<td>${app.appName }</td>
					<td>${app.companyName }</td>
					<td>${app.price }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td colspan="3">暂无推广产品</td>
			</tr>
		</c:if>
	</table>
</body>
</html>