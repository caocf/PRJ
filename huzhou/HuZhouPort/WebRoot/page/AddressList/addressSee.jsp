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

		<title>通讯录查看</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/leave/see.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		
		<script type="text/javascript" src="js/AddressList/addressSee.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
        <script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=request.getParameter("UserId")%>"
			id="UserId" />
			<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div >
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
	<div style="padding:44px 0 0 0" >
			<table width="100%" border="0" cellspacing="0" class="seenew"
	   id="addresssee">
	<col width="100px" /><col />
    </table></div>
	</body>
</html>
