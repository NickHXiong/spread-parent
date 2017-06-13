<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta name="format-detection" content="telephone=no" />
<title>公众号二维码</title>
</head>
<body>
	<c:if test="${app != null}" >
		<div>《${app.appName}》二维码</div>
		<img src="http://5936863d.ngrok.io/qr/app?acid=${appChannel.id }">
	</c:if>
	<c:if test="${app == null}" >
		<div>没有可推广产品，休息一下！</div>
	</c:if>
</body>
</html>