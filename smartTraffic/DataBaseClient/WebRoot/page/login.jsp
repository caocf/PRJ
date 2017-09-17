<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			//清空session
session.removeAttribute("username"); session.invalidate(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>综合数据库数据维护库客户端_登陆</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
		
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/login.js"></script>

		
		
	</head>

	<body  style="height: 100%; background-color: #C5E9EB;">
	<div id="page_content" style="height: 100%;"><input type="hidden" value="<%=basePath%>" id="basePath" />

		<!-- 页面内容 -->
		<div id="login_content" style="position: relative;top: 35%;left: 36%;border: solid 1px rgb(245, 226, 226);width: 350px;
    									background-color: #0972F8;color: white;height: 215px;padding: 0 0 10px 10px;font-size: 14px;">
				<table border="0" cellpadding="0" cellspacing="20">
					<tbody><tr>
						<td colspan="2">
  							<h3>登录</h3>
						</td>
					</tr>

					<tr>
						<td>
							用户名：
						</td>
						<td>
							<input type="text" id="username" class="input_login">
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" id="password" class="input_login">
						</td>
					</tr>
					<tr>
					<td ></td>
						<td >
							<input type="button" value="登录" onclick="Login()" style="width: 210px;height: 30px;">
						</td>
					</tr>
				</tbody></table>

			</div>
		<!-- 页面内容-end -->
		</div>
		
	</body>
</html>
