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
<!-- layui组件对Layui会对select、checkbox、radio等原始元素隐藏，
从而进行美化修饰处理。但这需要依赖于form组件，所以你必须加载 form，并且执行一个实例。
注意：导航的Hover效果、Tab选项卡等同理（它们需依赖 element 模块） -->
<script type="text/javascript" src="../../layui/layui.js"></script>
<script type="text/javascript" src="linksList.js"></script>
<script src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript">
//判断字符是否为空的方法
	function isEmpty(obj) {
		if (typeof obj == "undefined" || obj == null || obj == "") {
			return true;
		} else {
			return false;
	}
};
	function check(phone){
		var select0 = document.getElementsByName(phone+"nameMark")[0];
		var index0 = select0.selectedIndex; 
		var nameMark = select0.options[index0].value;
		var select1 = document.getElementsByName(phone+"pwMark")[0];
		var index1 = select1.selectedIndex; 
		var pwMark = select1.options[index1].value;
		var select2 = document.getElementsByName(phone+"headMark")[0];
		var index2 = select2.selectedIndex; 
		var headMark = select2.options[index2].value;
		var select3 = document.getElementsByName(phone+"hours")[0];
		var index3 = select3.selectedIndex; // 选中索引
		var time = select3.options[index3].value; // 选中值
		$.ajax({  
    		url:"${ctx}/ControlUserReposted",
    		data:{"nameCheck":nameMark,"pwCheck":pwMark,"headerCheck":headMark,"time":time,
    			"phone":phone},
    		type:"POST",
    		success:function(e){
    				if(e){
    					setTimeout(function(){
    						alert("用户审核完成！");
    			        },1000);
    				}
    		}
    	});
	};
	function judge() {
		var inputValue = $("input[name='param']").val();
		if (isEmpty(inputValue)) {
			alert("请输入搜索的内容!");
		}else{
			$.ajax({
				url : "${ctx}/FindUserReported",
				data : {"param" : inputValue},
				type:"POST",
				success : function(e) {
					if (e) {
						location.reload();
					}
				}
			});
		}
	};
</script>
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
			<div class="layui-input-inline">
				<input type="text" name="param" placeholder="请输入关键字"
					class="layui-input search_input">
			</div>
			<a class="layui-btn search_btn" onclick="judge()">查询</a>
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
				<tr id="${parents.phone }">
					<form class="layui-form" style="">
						<td>
							<div class="layui-inline">
								<label class="layui-form-label">${parents.nikeName }</label>
								<div style="width: 65px; font-size: 10px"
									class="layui-input-block">
									<select name="${parents.phone}nameMark" lay-filter="name">
										<option value="T">通过</option>
										<option value="F">禁止</option>
									</select>
								</div>
							</div>
						</td>
						<td><div class="layui-inline">
								<label class="layui-form-label">${parents.personalWord }</label>
								<div style="width: 65px; font-size: 10px;"
									class="layui-input-block">
									<select name="${parents.phone}pwMark" lay-filter="pw">
										<option value="T">通过</option>
										<option value="F">禁止</option>
									</select>
								</div>
							</div></td>
						<td>
							<div class="layui-inline">
								<label class="layui-form-label"> <img
									src="${ctx}/images/${ parents.headerImg}" />
								</label>
								<div style="width: 65px; font-size: 10px"
									class="layui-input-block">
									<select name="${parents.phone}headMark" lay-filter="head">
										<option value="T">通过</option>
										<option value="F">禁止</option>
									</select>
								</div>
							</div>
						</td>
						<td>
							<div class="layui-inline">
								<select name="${parents.phone}hours" lay-filter="hours"
									style="font-size: 10px">
									<option value="0">选择套餐</option>
									<option value="1">1小时</option>
									<option value="3">3小时</option>
									<option value="8">8小时</option>
									<option value="24">24小时</option>
								</select>
							</div>
						</td>
						<td><div class="layui-form-item">
								<img src="${ctx}/page/img/success.png"
									onclick="check(${parents.phone})">
							</div></td>
					</form>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="page"></div>
</body>
</html>