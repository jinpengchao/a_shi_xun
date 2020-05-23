<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>帖子列表--微家后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
	<link rel="stylesheet" href="../../css/news.css" media="all" />
</head>
<body class="childrenBody">
	<div class="layui-form news_list">
	  	<table border="2px;">
		    <colgroup>
				<col width="150">
				<col>
				<col width="20%">
				<col width="9%">
				<col width="9%">
				<col width="9%">
				<col width="15%">
		    </colgroup>
		    <thead>
				<tr>
					<th>发布人</th>
					<th>帖子内容</th>
					<th>帖子图片</th>
					<th>审核状态</th>
					<th>发布时间</th>
					<th>操作</th>
				</tr> 
				<c:forEach items="${examine }" var="p">
				<tr>
					<th>${p.nickName }</th>
					<th>${p.postContent} </th>
					<th>
						<c:if test="${p.imgs eq ''}">
							该帖子未上传照片
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autostart="false">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th><span style="color:red;">${p.examine }</span></th>
					<th>${p.time}</th>
					<td><span style="margin-left: 15px;"><a href="/vhome/UpdateExamine?examineString=已审核&id=${p.id}">批准</a>
					<a href="/vhome/UpdateExamine?examineString=审核失败&id=${p.id}">不批准</a></span></td>
				</tr>
				</c:forEach>
				<c:forEach items="${examine1 }" var="p">
				<tr>
					<th>${p.nickName }</th>
					<th>${p.postContent} </th>
					<th>
						<c:if test="${p.imgs eq ''}">
							该帖子未上传照片
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autostart="false">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th><span style="color:green;">${p.examine }</span></th>
					<th>${p.time}</th>
					<th><a href="/vhome/DeleteExamine?id1=${p.id}">删除</a></th>
					</tr>
				</c:forEach>
				<c:forEach items="${examine2}" var="p">
				<tr>
					<th>${p.nickName }</th>
					<th>${p.postContent}</th>
					<th>
						<c:if test="${p.imgs eq ''}">
							该帖子未上传照片
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autostart="false">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th><span style="color:#FF0000;">${p.examine }</span></th>
					<th>${p.time}</th>
					<th><a href="/vhome/DeleteExamine?id1=${p.id}">删除</a></th>
					</tr>
				</c:forEach>
		    </thead>
		    <tbody class="news_content"></tbody>
		</table>
	</div>
	<div id="page"></div>
	<script type="text/javascript" src="../../layui/layui.js"></script>
</body>
</html>