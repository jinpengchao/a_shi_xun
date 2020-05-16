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
	<c:forEach items="${report }" var="p">
		<img src="/imageUrl/a.png"/>
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		<a href="/vhome/DeleteReport?id=${p.id}">删除帖子</a>&nbsp&nbsp
		<a href="/vhome/IgnoreReport?id=${p.id}">忽略</a><br><br>
		
	</c:forEach>
</body>
</html>