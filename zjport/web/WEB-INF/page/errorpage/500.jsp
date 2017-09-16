<%@ page language="java" import="org.slf4j.Logger" pageEncoding="UTF-8"%>
<%@ page import="org.slf4j.LoggerFactory" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
	//记录日志
	Logger logger = LoggerFactory.getLogger("myException.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>500</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<center style="color:red;font-size:30px;">500</center>
	<hr />
</body>
<script>
	//500页面跳转使用
	window.location="http://localhost:8080/ZjPort/login/personal";
</script>
</html>
