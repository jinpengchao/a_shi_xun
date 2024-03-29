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
	<link rel="stylesheet" href="../../css/user.css" media="all" />
</head>
<body class="childrenBody">
	<div class="layui-form users_list">
	  	<table>
		    <colgroup>
				<col width="150">
				<col width="20%">
				<col width="30%">
				<col width="20%">
				<col width="9%">
				<col width="9%">
				<col width="5%">
		    </colgroup>
		    <thead>
				<tr bgcolor="#efefef">
					<th><h3>发布人</h3></th>
					<th><h3>帖子内容</h3></th>
					<th><h3>帖子图片</h3></th>
					<th><h3>帖子视频</h3></th>
					<th><h3>审核状态</h3></th>
					<th><h3>发布时间</h3></th>
					<th><h3>操作</h3></th>
				</tr> 
				<c:forEach items="${examine }" var="p">
				<tr bgcolor="#efefef">
					<th><p style=" font-weight:lighter;">${p.nickName }</p></th>
					<th><p style=" font-weight:lighter;">${p.postContent}</p></th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传照片</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 100px;height: 100px;"  src="/imageUrl/${i}">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传视频</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autoplay="false">
								</c:if>
								
							</c:forEach>
						</c:if>
					
					</th>
					<th><span style="color:orange;">${p.examine }</span></th>
					<th><p style=" font-weight:lighter;">${p.time}</p></th>
					<td>
					<div style="text-align:center; vertical-align:middel;">
					<input name="注册" type="button" id="btn1" title=" " value="批准"  onclick="location.href='/vhome/UpdateExamine?examineString=已审核&id=${p.id}&currentrId=${p.rId}&currentPhone=${p.phone}&currentPersonId=${p.personId}'" />
					<input style="margin-top:10px" name="注册" type="button" id="btn1" title=" " value="不批准"  onclick="location.href='/vhome/UpdateExamine?examineString=审核失败&id=${p.id}&currentrId=${p.rId}&currentPhone=${p.phone}&currentPersonId=${p.personId}'" />
					</div>
				</tr>
				</c:forEach>
				<c:forEach items="${examine1 }" var="p">
				<tr bgcolor="#efefef">
					<th><p style=" font-weight:lighter;">${p.nickName }</p></th>
					<th><p style=" font-weight:lighter;">${p.postContent}</p></th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传照片</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 100px;height: 100px;"  src="/imageUrl/${i}">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传视频</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autoplay="false">
								</c:if>
							</c:forEach>
						</c:if>
					
					</th>
					<th><span style="color:green;">${p.examine }</span></th>
					<th><p style=" font-weight:lighter;">${p.time}</p></th>
					<td>
					<div style="text-align:center; vertical-align:middel;">
					<input name="注册" type="button" id="btn1" title=" " value="移除"  onclick="location.href='/vhome/DeleteExamine?id1=${p.id}'" />
					</div>
				</tr>
				</c:forEach>
				<c:forEach items="${examine2}" var="p">
				<tr bgcolor="#efefef">
					<th><p style=" font-weight:lighter;">${p.nickName }</p></th>
					<th><p style=" font-weight:lighter;">${p.postContent}</p></th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传照片</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.jpg')}">
									<img style="width: 100px;height: 100px;"  src="/imageUrl/${i}">
								</c:if>
							</c:forEach>
						</c:if>
					</th>
					<th>
						<c:if test="${p.imgs eq ''}">
							<p style=" font-weight:lighter;">该帖子未上传视频</p>
						</c:if>
						<c:if test="${p.imgs ne ''}">
							<c:forEach items="${fn:split(p.imgs,',')}" var="i">
								<c:if test="${fn:endsWith(i,'.mp4')}">
									<embed src="/imageUrl/${i}" width="300" height="300" autoplay="false">
								</c:if>
							</c:forEach>
						</c:if>
					
					</th>
					<th><span style="color:red;">${p.examine }</span></th>
					<th><p style=" font-weight:lighter;">${p.time}</p></th>
					<td>
					<div style="text-align:center; vertical-align:middel;">
					<input name="注册" type="button" id="btn1" title=" " value="移除"  onclick="location.href='/vhome/DeleteExamine?id1=${p.id}'" />
					</div>
				</tr>
				</c:forEach>
		    </thead>
		    <tbody class="news_content"></tbody>
		</table>
	</div>
	<div id="page"></div>
	<script type="text/javascript" src="/vhome/layui/layui.js"></script>
	<script type="text/javascript" src="page/news/newsList.js"></script>
</body>
</html>