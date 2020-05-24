<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户总数--微家后台管理模板</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
<link rel="stylesheet" href="../../css/user.css" media="all" />
<script type="text/javascript">
	//判断字符是否为空的方法
	function isEmpty(obj){
    	if(typeof obj == "undefined" || obj == null || obj == ""){
        	return true;
    	}else{
        	return false;
    	}
	};
	function judge() {
		var inputValue = $("input[name='param']").val();
		if (isEmpty(inputValue)) {
			alert("请输入搜索的内容!");
		}
	};
	function del(id){
		$.ajax({  
    		url:"${ctx}/PerformUserInfo",//servlet文件的名称
    		data:{"id":id},
    		success:function(e){
    			if(e){
    				window.location="${ctx}/page/user/allUsers.jsp"
    			}
    		}
    	});
	};
	function jump(id,pw,name,img,ph,sex,area,imei,sta){
		window.location="${ctx}/page/user/editUser.jsp?id="+id+"&personalWord="+pw
				+"&nikeName="+name+"&headerImg="+img+"&phone="+ph
				+"&sex="+sex+"&area="+area+"&imei="+imei+"&status="+sta;
	};
</script>
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
			<form class="layui-input-inline" action="${ctx}/GetUserInfo"
				method="POST">
				<input type="text" name="param" placeholder="请输入关键字"
					class="layui-input search_input">
					<input type="submit" value="查询" onclick="judge()">
			</form>
		</div>
	</blockquote>
	<div class="layui-form users_list"
		style="text-align: center; width: 100%">
		<table class="layui-table">
			<colgroup>
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="15%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="5%">
			</colgroup>
			<thead>
				<tr>
					<th>用户ID</th>
					<th>个性签名</th>
					<th>用户昵称</th>
					<th>头像</th>
					<th>电话号码</th>
					<th>性别</th>
					<th>住址</th>
					<th>手机IMEI</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${userParents}" var="parents" varStatus="sta">
					<tr>
						<td>${ parents.id}</td>
						<td>${ parents.personalWord}</td>
						<td>${ parents.nikeName}</td>
						<td><img src="${ctx}/images/${ parents.headerImg}" /></td>
						<td>${ parents.phone}</td>
						<td>${ parents.sex}</td>
						<td>${ parents.area}</td>
						<td>${ parents.imei}</td>
						<td>${ parents.status}</td>
						<td><img
							onclick="jump('${ parents.id}','${ parents.personalWord}','${ parents.nikeName}','${ parents.headerImg}','${ parents.phone}','${ parents.sex}','${ parents.area}','${ parents.imei}','${ parents.status}')"
							style="width: 20px; height: 20px" src="${ctx}/page/img/edit.png" />
							<img onclick="del('${parents.id}')"
							style="width: 20px; height: 20px" src="${ctx}/page/img/del.png" />
						</td>
					</tr>
				</c:forEach>
			</thead>
		</table>
	</div>
	<div id="page"></div>
	<script type="text/javascript" src="../../layui/layui.js"></script>
	<script src="${ctx}/js/jquery.min.js"></script>
</body>
</html>