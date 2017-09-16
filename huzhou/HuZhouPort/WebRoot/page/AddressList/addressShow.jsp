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

		<title>用户管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/address/address_list.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>

         <script type="text/javascript" src="js/AddressList/addressShow.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="content">
		
			<div id="u2">
			<div id="u2_left">
				<p>
				<a	onclick="firstDepartment(this)" id="organization">湖州港航管理局</a>
				</p>
				<div  id="deparmentTreeDiv">
				<ul id='department_tree'></ul></div>
			</div>
			<div id="u2_right">
				<iframe src="<%=basePath%>page/AddressList/addressList.jsp" height="100%"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					scrolling="no" name="user_right" id="user_right"></iframe>
			</div>
			</div>
		</div>
	</body>
</html>
