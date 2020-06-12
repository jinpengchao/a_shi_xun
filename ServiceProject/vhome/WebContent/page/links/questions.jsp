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
	  	<table>
		    <colgroup>
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="60%">
				<col width="10%">
		    </colgroup>
		    <thead>
				<tr bgcolor="#efefef">
					<th><h3>姓名</h3></th>
					<th><h3>电话</h3></th>
					<th><h3>主题</h3></th>
					<th><h3>问题反馈主要内容</h3></th>
					<th><h3>操作</h3></th>
				</tr>
				<c:forEach items="${all_questions }" var="questions">
				<tr bgcolor="#efefef">
					<th><p style=" font-weight:lighter;">${questions.creatorName }</p></th>
					<th><p style=" font-weight:lighter;">${questions.creatorPhone}</p> </th>
					<th><p style=" font-weight:lighter;">${questions.subject} </p></th>
					<th><p style=" font-weight:lighter;">${questions.content}</p></th>
					<th>
					<c:if test="${questions_status == 0 }">
					<div style="text-align:center; vertical-align:middel;">
						<input name="注册" type="button" id="btn1" title="登注册" value="回复"  onclick="location.href='page/links/answer.jsp?currentphone=${questions.creatorPhone}&currentname=${questions.creatorName}&currentid=${questions.id}&currentregisrationID=${questions.registrationId}'" />
						<input name="注册" type="button" id="btn1" title="登注册" value="忽略"  onclick="location.href='/vhome/IgnoreReport?id=${p.id}'" />
					</div>
					</c:if>
					<c:if test="${questions_status == 1 }">
					<div style="text-align:center; vertical-align:middel;">
						<input name="注册" type="button" id="btn1" title="登注册" value="查看回复"  onclick="location.href='/vhome/ShowAnswers?currentid=${questions.id}'" />
					</div>
					
					</c:if>
					</th>
				</tr>
				</c:forEach>
				
		    </thead>
		</table>
	</div>
<div id="page"></div>
<script type="text/javascript" src="../../layui/layui.js"></script>
</body>
</html>