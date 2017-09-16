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

		<title>首页</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/huzhoumain.css">
		<script src="js/common/jquery-1.10.2.min.js">
		</script>
		<script type="text/javascript" src="js/homePage.js">
</script>

<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script src="js/officoa/knowledgelist.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div class="panel panel-default">
    		<div class="panel-body">
       			 这是一个基本的面板
	    	</div>
		</div>
	</body>
</html>
