<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>帖子列表--微家后台管理模板</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
<link rel="stylesheet"
	href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
<link rel="stylesheet" href="../../css/news.css" media="all" />
<script type="text/javascript">
	function check(phone){
		var nameMark = $("#nameMark option:selected").val();
		var pwMark = $("#pwMark option:selected").val();
		var headMark = $("#headMark option:selected").val();
		var time = $("#hours option:selected").val();
		$.ajax({  
    		url:"${ctx}/ControlUserReposted",
    		data:{"nameCheck":nameMark,"pwCheck":pwMark,"headCheck":headMark,"time":time,
    			"phone":phone},
    		type:"POST",
    		success:function(e){
    				if(e){
    					setTimeout(function(){
    						alert("用户审核完成！");
    			        },4000);
    				}
    		}
    	});
	}
</script>
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
			<div class="layui-input-inline">
				<input type="text" name="searchBug" placeholder="请输入关键字"
					class="layui-input search_input">
			</div>
			<a class="layui-btn search_btn" href="">查询</a>
		</div>
	</blockquote>
	<div class="layui-form links_list">
		<table class="layui-table" id="tableUser">
			<colgroup>
				<col width="20%">
				<col width="35%">
				<col width="25%">
				<col width="15%">
				<col width="5%">
			</colgroup>
			<thead>
				<tr>
					<th style="text-align: left;">用户昵称</th>
					<th>个性签名</th>
					<th>头像</th>
					<th>封禁时限</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:forEach items="${userReposted}" var="parents" varStatus="sta">
				<tr>
					<td>
						<div class="layui-inline">
							<label class="layui-form-label">${ parents.nikeName}</label>
							<div class="layui-input-block">
								<select id="nameMark">
									<option value="T">通过</option>
									<option value="F">禁止</option>
								</select>
							</div>
						</div>
					</td>
					<td>
						<div class="layui-inline">
							<label class="layui-form-label">${ parents.personalWord}</label>
							<div class="layui-input-block">
								<select id="pwMark">
									<option value="T">通过</option>
									<option value="F">禁止</option>
								</select>
							</div>
						</div>
					</td>
					<td>
						<div class="layui-inline">
							<label class="layui-form-label"> <img
								src="${ctx}/images/${ parents.headerImg}" />
							</label>
							<div class="layui-input-block">
								<select id="headMark">
									<option value="T">通过</option>
									<option value="F">禁止</option>
								</select>
							</div>
						</div>
					</td>
					<td><select id="hours">
							<option value="0">选择套餐</option>
							<option value="1">1小时</option>
							<option value="3">3小时</option>
							<option value="8">8小时</option>
							<option value="24">24小时</option>
					</select></td>
					<td><img src="${ctx}/page/img/success.png"
						onclick="check(${parents.phone})"></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="page"></div>
	<script type="text/javascript" src="../../layui/layui.js"></script>
	<script src="${ctx}/js/jquery.min.js"></script>
</body>
</html>