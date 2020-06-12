<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>404--微家后台管理模板</title>
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
				<col width="150">
				<col width="20%">
				<col width="30%">
				<col width="20%">
				<col width="9%">
				<col width="5%">
		    </colgroup>
		    <thead>
				<tr bgcolor="#efefef">
					<th><h3>发布人</h3></th>
					<th><h3>帖子内容</h3></th>
					<th><h3>帖子图片</h3></th>
					<th><h3>帖子视频</h3></th>
					<th><h3>发布时间</h3></th>
					<th><h3>操作</h3></th>
				</tr> 
				<c:forEach items="${report }" var="p">
				<tr bgcolor="#efefef">
					<th><p style=" font-weight:lighter;">${p.nickName }</p></th>
					<th><p style=" font-weight:lighter;">${p.postContent}</p> </th>
					<th>
						<c:if test="${p.imgs eq ''}">
							该帖子未上传照片
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 100px;height: 100px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.jpeg')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.gif')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								<c:if test="${fn:endsWith(i,'.png')}">
									<img style="width: 50px;height: 50px;" src="/imageUrl/${i}">
								</c:if>
								
							</c:forEach>
						</c:if>
					</th>
					<th>
						<c:if test="${p.imgs eq ''}">
							该帖子未上传照片
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autoplay="false">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					
					<th><p style=" font-weight:lighter;">${p.time}</p></th>
					<td><div style="text-align:center; vertical-align:middel;">
					<input name="注册" type="button" id="btn1" title=" " value="删除"  onclick="location.href='/vhome/DeleteReport?id=${p.id}&currentrId=${p.rId}&currentphone=${p.phone}&currentcontent=${p.postContent}'" />
					<input name="注册" type="button" id="btn1" title=" " value="忽略"  onclick="location.href='/vhome/IgnoreReport?id=${p.id}'" />
					</div>
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