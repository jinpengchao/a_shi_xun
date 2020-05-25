<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>会员添加--layui后台管理模板</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
<link rel="stylesheet" href="../../css/user.css" media="all" />
<style type="text/css">
.layui-form-item .layui-inline {
	width: 33.333%;
	float: left;
	margin-right: 0;
}

@media ( max-width :1240px) {
	.layui-form-item .layui-inline {
		width: 100%;
		float: none;
	}
}
</style>
</head>
<body class="childrenBody">
	<form class="layui-form" style="width: 80%;">
		<div class="layui-form-item">
			<label class="layui-form-label">用户ID</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input userId" lay-verify="required"
					value="${param.id}" name="userId" readonly="readonly">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">电话</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input userPhone" name="userPhone"
					value="${param.phone }">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block userSex">
					<c:choose>
						<c:when test="${param.sex=='女'    }">
							<input type="radio" name="sex" value="男" title="男">
							<input type="radio" name="sex" value="女" title="女" checked="checked">
							<input type="radio" name="sex" value="保密" title="保密">
						</c:when>
						<c:when test="${param.sex== '男'  }">
							<input type="radio" name="sex" value="男" title="男" checked="checked">
							<input type="radio" name="sex" value="女" title="女">
							<input type="radio" name="sex" value="保密" title="保密">
						</c:when>
						<c:otherwise>
							<input type="radio" name="sex" value="男" title="男">
							<input type="radio" name="sex" value="女" title="女">
							<input type="radio" name="sex" value="保密" title="保密" checked="checked">
    					</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">昵称</label>
					<div class="layui-input-block ">
						<input type="text" name="userNick" class="layui-input userName"
							value="${param.nikeName}">
					</div>
				</div>
				<div class="layui-form-item userAddress">
					<label class="layui-form-label">家庭住址</label>
					<div class="layui-input-inline">
						<select name="province" lay-filter="province">
							<option value="">请选择省</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<select name="city" lay-filter="city" disabled>
							<option value="">请选择市</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<select name="area" lay-filter="area" disabled>
							<option value="">请选择县/区</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">用户状态</label>
					<div class="layui-input-block">
						<select name="userStatus" class="userStatus"
							lay-filter="userStatus">
							<option value="自由">自由</option>
							<option value="封禁">封禁</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">头像上传</label>
					<div class="layui-input-block userSex">
						<img src="${ctx }/images/${param.headerImg}"> <input
							type="file" class="userImg" name="userImg">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">IMEI</label>
						<div class="layui-input-block userIMEI">
							<input type="text" class="layui-input userEmail" name="userImei"
								value="${param.imei}">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">个性签名</label>
						<div class="layui-input-block">
							<textarea placeholder="${param.personalWord }"
								class="layui-textarea linksDesc" name="userWord"></textarea>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="editUser">立即提交</button>
							<button type="reset" class="layui-btn layui-btn-primary">重置</button>
						</div>
					</div>
	</form>
	<script type="text/javascript" src="../../layui/layui.js"></script>
	<script type="text/javascript" src="editUser.js"></script>
	<script type="text/javascript" src="address.js"></script>
	<script type="text/javascript" src="user.js"></script>
</body>
</html>