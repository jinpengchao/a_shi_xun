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
	jiwenbin cnm
	<c:forEach items="${examine }" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		${p.examineString }&nbsp&nbsp<br><br>
		<a href="/ShowExamine?examineString=已审核">批准</a>&nbsp&nbsp
		<a href="/ShowExamine?examineString=审核失败">不批准</a>&nbsp&nbsp
	</c:forEach>
	<br><br><br>
	<c:forEach items="${examine1 }" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		${p.examineString }&nbsp&nbsp
	</c:forEach>
	<br><br><br>
	<c:forEach items="${examine2}" var="p">
		发帖子的人：${p.nickName } &nbsp&nbsp
		帖子内容：${p.postContent} &nbsp&nbsp
		${p.examineString }&nbsp&nbsp
	</c:forEach>
</body>
</html>