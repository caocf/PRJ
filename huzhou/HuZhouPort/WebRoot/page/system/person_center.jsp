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

		<title>个人中心</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/person.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/system/person_center.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" />
		<div id="content">
			<img class="imgIcon" src="<%=basePath%>image/person_pic.png"/>
			<div id="imgIcon_right_div">
				<div id="imgIcon_right_div01">
					<label id="userName">
						<b>用户名：<%=(String)session.getAttribute("username")%></b>
					</label>
					<a onclick="updateUser()" class="operation">更改资料</a>
					<label  class="operation">|</label>
					<a onclick="ChangePassword()"  class="operation">修改密码</a>
				</div>
				
				<div class="txt1">			
					<label>
						姓名：
					</label>
					<label id="name"></label>&nbsp;
					<label>
						部门：
					</label>
					<label id="department"></label>&nbsp;
					<label>
						职位：
					</label>
					<label id="post"></label>&nbsp;
					<label>
						状态：
					</label>
					<label id="StatusType"></label>
				</div>
			</div>
			<div class="txt3">
				<label>
					手机：
				</label>
				<label id="tel"></label>
			</div>
			<div class="txt2">
				<label>
					邮箱：
				</label>
				<label  id="email"></label>
			</div>
			<div class="txt2">
				<label>
					角色：
				</label>
				<label id="role"></label>
			</div>
			<div class="txt2">
				<label>
					执法证编号：
				</label>
				<label id="lawId"></label>
			</div>
		</div>
	</body>
</html>
