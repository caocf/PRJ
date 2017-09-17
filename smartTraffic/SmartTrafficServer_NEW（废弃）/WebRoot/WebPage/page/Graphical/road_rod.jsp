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

		<title>智慧出行-热点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/graphical/pot.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/graphical/parking.css" />
		
		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
				<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/Graphical/pot.js"></script>
		
	</head>

	<body>
	<h1>道路数据</h1>
  </body>
</html>
