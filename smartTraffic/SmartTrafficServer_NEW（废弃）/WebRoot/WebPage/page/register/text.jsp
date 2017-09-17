<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-登录</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebPage/css/register/logindailog.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebPage/js/common/inputValidator.js"></script>
		<script type="text/javascript" src="WebPage/js/main/Login.js"></script>
		<script type="text/javascript" src="WebPage/js/common/cookie.js"></script>
	</head>

	<body>

		<div id="dialog"><a  onClick="closeWindow()" class="log_close_img">关闭</a>
			<div class="log_top">
				登录智慧交通出行网
			</div>
			<label
				style="color: red; display: block; height: 20px; text-align: center; margin-top: 10px;"
				id="loginerr"></label>
			<div style="margin-top: -38px">
				<div class="log_txt1">
					&nbsp;
					<img style="margin-top: 10px"
						src="WebPage/image/register/text_bg1.png" />
					<input type="text" class="log_txt11" id="userName"
						value="请输入用户名" onBlur="TextBlur(this)"
						onFocus="TextFocus(this)" />
				</div>
				<div class="log_txt2">
					&nbsp;
					<img src="WebPage/image/register/text_bg2.png" />
						<input type="text"  id="passText" value="请输入密码" 
						onfocus="changeTip(this)" class="password_input" style="color: #a3a3a3;"/>
						<input type="password" id="password" class="password_input" style="display:none" onBlur="changeBack(this)"/>
				</div>
			</div>
			<div>
				<input type="checkbox" class="log_che" id="CookieSaveOrDelete"/>
				
				<p
					style="float: left; margin: 20px auto auto 10px; font-size: 12px; ">
					两周内自动登录
				</p>
				<a href="WebPage/page/register/find1.jsp" target="_parent"
					style="display: block; float: left; margin: 20px auto auto 150px; color: #2547a0; font-size: 12px;">忘记密码？</a>
			</div>
			<div class="clear"></div>
			<div>
				<img src="WebPage/image/register/login_sub.png" class="text2"
					style="cursor: auto; width: 320px; margin: 20px auto auto 50px; font-weight: normal;"
					onClick="Login()"/>
			</div>
			<a href="WebPage/page/register/register.jsp" target="_parent"
				style="display: block; float: right; margin: 25px 50px auto auto; color: #2547a1;">立即注册</a>
			<p style="float: right; margin: 25px 10px auto auto; color: #5c5c5c;">
				没有账号？
			</p>
			<div class="clear"></div>
		</div>
	</body>
</html>
