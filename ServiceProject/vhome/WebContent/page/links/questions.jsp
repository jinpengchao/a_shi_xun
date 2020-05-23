<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>questions--微家后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../layui/css/layui.css" media="all" />
	
	<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
	<link rel="stylesheet" href="../../css/news.css" media="all" />
</head>
<body class="childrenBody">
<div class="layui-form news_list">
	  	<table border="2px;">
		    <colgroup>
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="60%">
				<col width="10%">
		    </colgroup>
		    <thead>
				<tr>
					<th>姓名</th>
					<th>电话</th>
					<th>主题</th>
					<th>问题反馈主要内容</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${all_questions }" var="questions">
				<tr>
					<th>${questions.creatorName }</th>
					<th>${questions.creatorPhone} </th>
					<th>${questions.subject} </th>
					<th>${questions.content}</th>
					
					<c:if test="${questions_status == 0 }">
						<td><span style="margin-left: 15px;">
							<a href="page/links/answer.jsp?currentphone=${questions.creatorPhone}&currentname=${questions.creatorName}&currentid=${questions.id}&currentregisrationID=${questions.registrationId}">回复</a>
							<a href="/vhome/IgnoreReport?id=${p.id}">忽略</a></span></td>
					</c:if>
					<c:if test="${questions_status == 1 }">
						<td><span style="margin-left: 15px;">
							<a href="/vhome/ShowAnswers?currentid=${questions.id}">查看回复内容</a></span></td>
					</c:if>
					
				</tr>
				</c:forEach>
				
		    </thead>
		</table>
	</div>
<div id="page"></div>
<script type="text/javascript" src="../../layui/layui.js"></script>
</body>
</html>