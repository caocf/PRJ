<%@page import="com.channel.model.user.CXtUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	CXtUser user = ((CXtUser) session.getAttribute("user"));
	String username = null;
	int userid = -1;
	if (user != null) {
		username = user.getUsername();
		userid = user.getId();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css"
	href="common/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="common/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="page/common/css/common.css">

<script type="text/javascript" language="javascript"
	src="common/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript"
	src="common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/common.js"></script>
<script type="text/javascript" language="javascript"
	src="page/common/js/constants.js"></script>


</head>

<body>
	<input type="hidden" id="username" value="<%=username%>">
	<input type="hidden" id="userid" value="<%=userid%>">
	<input type="hidden" id="basePath" value="<%=basePath%>">

</body>
</html>
