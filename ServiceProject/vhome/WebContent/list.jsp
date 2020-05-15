<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${examine }" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		<span style="color:red;">${p.examineString }</span>
		<a href="/vhome/UpdateExamine?examineString=已审核&id=${p.id}">批准</a>&nbsp&nbsp
		<a href="/vhome/UpdateExamine?examineString=审核失败&id=${p.id}">不批准</a><br><br>
	</c:forEach>
	<br>
	<c:forEach items="${examine1 }" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		<span style="color:green;">${p.examineString }</span>
		<a href="/vhome/DeleteExamine?id1=${p.id}">删除</a><br><br>
	</c:forEach>
	<br>
	<c:forEach items="${examine2}" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		<span style="color:#FF0000;">${p.examineString }</span>
		<a href="/vhome/DeleteExamine?id1=${p.id}">删除</a><br><br>
	</c:forEach>
</body>
</html>