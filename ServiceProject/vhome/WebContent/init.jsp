<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

</head>
<body>
<script type="text/javascript">  
			(function(){
				$.ajax({  
            		url:"${ctx}/ShowUserList",
            		type:"POST",
            		success:function(e){
            			if(e){
            			window.location="page/login/login.jsp"
            			}
            		}
            	});
			})();
</script>
</body>
</html>