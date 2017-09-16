<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//清空session
session.removeAttribute("username"); 
session.removeAttribute("userId"); 
session.invalidate(); 
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>湖州港航登陆</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css" href="css/login/login.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/login.js"></script>
		
	</head>

	<body>
	<div id="content">
		<div id="l1">
		<div id="l3">
			<div id="l2">
				<img src="image/login/login_icon.png" />
			</div>
			<div id="l4">
				<input type="hidden" value="<%=basePath%>" id="basePath" />
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="txt2">
							<label class="txt">
								用户名:</label><input type="text" id="username" class="textLogin"
								onfocus="this.style.outline='none'"/>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="txt2">
							<label class="txt">
								密&nbsp;码:</label><input type="password" id="password" class="textLogin" onfocus="NoBorder(this)" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
						<div class="txt4">
							<label class="txt">
								验证码:</label><input type="text" id="email" class="textLogin2"
								onclick="this.value=clear1(this);"/>
						</div>
						</td>
					</tr>
					<tr>
						<td class="tishi" valign="middle">
							输入下图中的字符，不区分大小写
						</td>
					</tr>
					<tr>

						<td class="txt3" valign="top">
							<img src="validateCode.action?s=0.9667190614914191"
								class="loginImg" id="randImage" onClick="changImage()"
								onerror="this.onerror=null;this.src='validateCode.action?s='+Math.random();" />
							<a onClick="changImage()" class="changeImage">看不清换一张</a>
						</td>
					</tr>
					<tr>
						<td>
							<img src="image/login/bt_login_normal.png" class="btnLogin"
								onclick="Login()" onmouseover="LoginOver(this)"
								onmouseout="LoginOut(this)" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		</div>
</div>
	</body>
</html>
