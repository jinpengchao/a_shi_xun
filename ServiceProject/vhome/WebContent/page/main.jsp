<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>首页--微家后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
	<link rel="stylesheet" href="../css/main.css" media="all" />
</head>
<body class="childrenBody">
	<div class="panel_box row">
		<div class="panel col">
			<a href="${ctx}/ControlUserReposted">
				<div class="panel_icon" style="background-color:#FF5722;">
					<i class="iconfont icon-dongtaifensishu" data-icon="icon-dongtaifensishu"></i>
				</div>
				<div class="panel_word userAll">
					<span>${ usersReported}</span>
					<cite>待审核用户</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="${ctx}/ShowUserList">
				<div class="panel_icon" style="background-color:#009688;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div class="panel_word userAll">
					<span>${ total}</span> <cite>所有用户</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="/vhome/ShowExamine">
				<div class="panel_icon" style="background-color:#F7B824;">
					<i class="iconfont icon-wenben" data-icon="icon-wenben"></i>
				</div>
				<div style="margin-top: 40px">
					<cite>帖子审核情况</cite>
				</div>
			</a>
		</div>
		<div class="panel col max_panel">
			<a href="javascript:;" data-url="/vhome/PostReport">
				<div class="panel_icon" style="background-color:#FF4040;">
					<i class="iconfont icon-text" data-icon="icon-text"></i>
				</div>
				<div style="margin-top: 40px">
					<span></span>
					<cite>帖子举报</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="https://kefu.easemob.com/mo/agent/webapp/chat">
				<div class="panel_icon" style="background-color:#9c9c9;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div style="margin-top: 40px">
					<span></span>
					<cite>客服系统</cite>
				</div>
			</a>
		</div>
	</div>

	<div class="row">
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">更新日志</blockquote>
			<div class="layui-elem-quote layui-quote-nm">
				<p style="color:#f00;"># v1.0.0 - 2020-05-25</p>
				<p>* 姬文斌-完成用户全局搜索的功能</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-24</p>
				<p>* 章鹏-网页上帖子审核的功能完全实现完毕</p>
				<p>* 靳朋朝-实现了帖子审核消息的安卓端与后台的交互</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-22</p>
				<p>* 章鹏-在安卓端能够上传视频之后，开始研究网页显示视频的问题</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-21</p>
				<p>* 姬文斌-开始编写用户搜索页面</p>
				<p>* 章鹏-接收帖子举报模块完成</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-20</p>
				<p>* 姬文斌-开始编写用户搜索页面</p>
				<p>* 章鹏-在安卓端能够上传视频之后，开始研究网页显示视频的问题</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-19</p>
				<p>* 靳朋朝-后台完成回复和处理用户发来的反馈的功能，并添加了JPush工具类，实现为用户发送一条处理情况通知</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-17</p>
				<p>* 章鹏-开始编写接收帖子举报消息模块</p>
				<p>* 靳朋朝-开始添加后台接收用户反馈消息的模块</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-16</p>
				<p>* 章鹏-完成Debug安卓端发送数据和后台获取数据的不一致导致的数据冲突问题</p>
				<p>* 姬文斌-开始编写用户搜索页面</p>
				<p>* 靳朋朝-添加了管理员进入后台的账号功能（含登录注册）</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-13</p>
				<p>* 章鹏-完成帖子内容在后台显示</p>
				<p>* 姬文斌-完成用户信息分页功能</p>
				<br />
				<p style="color:#f00;"># v1.0.0 - 2020-05-12</p>
				<p>* 姬文斌-完成在后台显示全部用户信息</p>
				<br />
				<p style="color:#f00;"># v1.0.0（编码后台） - 2020-05-11</p>
				<p>* 姬文斌-修改html文件为jsp文件，使之成为可运行的java-web程序</p>
				<p>* 章鹏-开始添加帖子审核模块</p>
				<p>* 姬文斌-开始添加用户管理模块</p>
				<br />
				<p style="color:#f00;"># v1.0.0（创建后台） - 2020-05-10</p>
				<p>* 姬文斌-创建后台页面</p>
				<p>* 姬文斌-更改后台参数</p>
			</div>
		</div>
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">系统基本参数</blockquote>
			<table class="layui-table">
				<colgroup>
					<col width="150">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<td>当前版本</td>
						<td>v1.0.0</td>
					</tr>
					<tr>
						<td>管理团队全称</td>
						<td>微家-借光读书小组</td>
					</tr>
					<tr>
						<td>网站首页</td>
						<td>mainPage.jsp</td>
					</tr>
					<tr>
						<td>服务器环境</td>
						<td class="server"></td>
					</tr>
					<tr>
						<td>数据库版本</td>
						<td>10.0.10</td>
					</tr>
					<tr>
						<td>最大上传限制</td>
						<td>20M</td>
					</tr>
					<tr>
						<td>当前用户权限</td>
						<td class="userRights"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<script type="text/javascript" src="../layui/layui.js"></script>
	<script type="text/javascript" src="../js/main.js"></script>
</body>
</html>