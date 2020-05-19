<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>图片总数--微家后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="../../css/images.css" media="all" />
</head>
<body class="childrenBody">
	<form class="layui-form">
		<blockquote class="layui-elem-quote news_search">
			<div class="layui-inline">
				<input type="checkbox" name="selectAll" id="selectAll" lay-filter="selectAll" lay-skin="primary" title="全选">
			</div>
			<div class="layui-inline">
				<a class="layui-btn layui-btn-danger batchDel">批量删除</a>
			</div>
			<div class="layui-inline">
				<div class="layui-form-mid layui-word-aux">本页所有数据均为静态，刷新后所有操作无效</div>
			</div>
		</blockquote>
		<ul id="Images"></ul>
	</form>
	<script type="text/javascript" src="../../layui/layui.js"></script>
	<script type="text/javascript" src="images.js"></script>
</body>
</html>