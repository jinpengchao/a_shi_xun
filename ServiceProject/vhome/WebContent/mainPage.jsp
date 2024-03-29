<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>微家后台管理</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="favicon.ico">
	<link rel="stylesheet" href="layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
	<link rel="stylesheet" href="css/main.css" media="all" />
</head>
<body class="main_body">
	<div class="layui-layout layui-layout-admin">
		<!--顶部 -->
		<div class="layui-header header">
			<div class="layui-main">
				<a href="#" class="logo">微家后台管理</a>
				<!-- 显示/隐藏菜单 -->
				<a href="javascript:;" class="iconfont hideMenu icon-menu1"></a>
				<!-- 搜索 -->
				<div class="layui-form component">
			        <select name="modules" lay-verify="required" lay-search="">
						<option value="">直接选择或搜索选择</option>
						<option value="1">layer</option>
						<option value="2">form</option>
						<option value="3">layim</option>
						<option value="4">element</option>
						<option value="5">laytpl</option>
						<option value="6">upload</option>
						<option value="7">laydate</option>
						<option value="8">laypage</option>
						<option value="9">flow</option>
						<option value="10">util</option>
						<option value="11">code</option>
						<option value="12">tree</option>
						<option value="13">layedit</option>
						<option value="14">nav</option>
						<option value="15">tab</option>
						<option value="16">table</option>
						<option value="17">select</option>
						<option value="18">checkbox</option>
						<option value="19">switch</option>
						<option value="20">radio</option>
			        </select>
			        <i class="layui-icon">&#xe615;</i>
			    </div>
			    <!-- 天气信息 -->
			    <div class="weather" pc>
			    	<div id="tp-weather-widget"></div>
					<script>(function(T,h,i,n,k,P,a,g,e){
						g=function(){
							P=h.createElement(i);
							a=h.getElementsByTagName(i)[0];
							P.src=k;P.charset="utf-8";
							P.async=1;a.parentNode.insertBefore(P,a)
							};
							T["ThinkPageWeatherWidgetObject"]=n;
							T[n]||(T[n]=function(){
								(T[n].q=T[n].q||[]).push(arguments)
								});
							T[n].l=+new Date();
							if(T.attachEvent)
							{T.attachEvent("onload",g)
								}else{
									T.addEventListener("load",g,false)
									}
							}(window,document,"script","tpwidget","//widget.seniverse.com/widget/chameleon.js")
							)</script>
					<script>tpwidget("init", {
					    "flavor": "slim",
					    "location": "WX4FBXXFKE4F",
					    "geolocation": "enabled",
					    "language": "zh-chs",
					    "unit": "c",
					    "theme": "chameleon",
					    "container": "tp-weather-widget",
					    "bubble": "disabled",
					    "alarmType": "badge",
					    "color": "#FFFFFF",
					    "uid": "U9EC08A15F",
					    "hash": "039da28f5581f4bcb5c799fb4cdfb673"
					});
					tpwidget("show");</script>
			    </div>
			    <!-- 顶部右侧菜单 -->
			    <ul class="layui-nav top_menu">
			    	<li class="layui-nav-item showNotice" id="showNotice" pc>
						<a href="javascript:;"><i class="iconfont icon-gonggao"></i><cite>系统公告</cite></a>
					</li>
			    	<li class="layui-nav-item" mobile>
			    		<a href="javascript:;" class="mobileAddTab" data-url="page/user/changePwd.jsp"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><span>设置</span></a>
			    	</li>
			    	<li class="layui-nav-item" mobile>
			    	
			    		<a href="page/login/login.jsp" class="signOut"><i class="iconfont icon-loginout"></i>退出</a>
			    	</li>
					<li class="layui-nav-item lockcms" pc>
						<a href="javascript:;"><i class="iconfont icon-lock1"></i>锁屏</a>
					</li>
					<li class="layui-nav-item" pc>
						<a href="javascript:;">
							<img src="images/${sessionScope.headerImage }" class="layui-circle" width="35" height="35">
							<cite>${sessionScope.nickName }</cite>
						</a>
						<dl class="layui-nav-child">
							<dd><a href="javascript:;" data-url="page/user/userInfo.jsp"><i class="iconfont icon-zhanghu" data-icon="icon-zhanghu"></i><span>个人资料</span></a></dd>
							<dd><a href="javascript:;" data-url="page/user/changePwd.jsp"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><span>修改密码</span></a></dd>
							<dd><a href="page/login/login.jsp" class="signOut"><i class="iconfont icon-loginout"></i><span>退出</span></a></dd>
						</dl>
					</li>
				</ul>
			</div>
		</div>
		<!-- 左侧导航 -->
		<div class="layui-side layui-bg-black">
			<div class="user-photo">
				<a class="img" title="我的头像" ><img src="images/${sessionScope.headerImage }"></a>
				<p><span class="userName">【${sessionScope.nickName }】你好！</span>欢迎登陆</p>
			</div>
			<div class="navBar layui-side-scroll"></div>
		</div>
		<!-- 右侧内容 -->
		<div class="layui-body layui-form">
			<div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
				<ul class="layui-tab-title top_tab" id="top_tabs">
					<li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>后台首页</cite></li>
				</ul>
				<ul class="layui-nav closeBox">
				  <li class="layui-nav-item">
				    <a href="javascript:;"><i class="iconfont icon-caozuo"></i><span>页面操作</span></a>
				    <dl class="layui-nav-child">
					  <dd><a href="" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i><span>刷新当前</span></a></dd>
				      <dd><a href="" class="closePageOther"><i class="iconfont icon-prohibit"></i><span>关闭其他</span></a></dd>
				      <dd><a href="" class="closePageAll"><i class="iconfont icon-guanbi"></i><span>关闭全部</span></a></dd>
				    </dl>
				  </li>
				</ul>
				<div class="layui-tab-content clildFrame">
					<div class="layui-tab-item layui-show">
						<iframe src="page/main.jsp"></iframe>
					</div>
				</div>
			</div>
		</div>
		<!-- 底部 -->
		<div class="layui-footer footer">
			<p>copyright @2020 微家开发团队</p>
		</div>
	</div>
	
	<!-- 移动导航 -->
	<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
	<div class="site-mobile-shade"></div>

	<script type="text/javascript" src="layui/layui.js"></script>
	<script type="text/javascript" src="js/leftNav.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>