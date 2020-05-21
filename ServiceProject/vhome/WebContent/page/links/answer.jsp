<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>questions--微家后台管理模板</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../layui/css/layui.css" media="all" />

<link rel="stylesheet"
	href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
<link rel="stylesheet" href="../../css/news.css" media="all" />
</head>
<body class="childrenBody">
	<form action="/vhome/SaveAnswer" method="post">
        <h2>您正在回复用户：${param.currentphone }</h2>
		<input type="hidden" id="phone" name="phone" value="${param.currentphone }">
	
		<textarea id="content" name="content" placeholder="输入内容以回复用户"
			rows="20" cols="100"></textarea>
		<input type="submit" value="发送">
		<a href="javascript:history.go(-1)">返回</a>
	</form>
	<div id="page"></div>
	<script type="text/javascript" src="../../layui/layui.js"></script>
</body>
</html>