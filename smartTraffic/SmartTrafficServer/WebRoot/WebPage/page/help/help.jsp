<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

		<title>智慧出行-帮助</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	    <link rel="stylesheet" href="<%=basePath%>WebPage/css/help/help.css" type="text/css"></link>

	    <script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	    <script type="text/javascript" src="WebPage/js/other/help.js"/></script>

	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="0"/> 
		</jsp:include>
		<!-- 页面内容 -->
		<div class="layout1" align="center">
		<h1>使用帮助</h1>
		<p align="center">
			<img src="WebPage/image/trafficNews_icon_top.png" />
		</p>
		<div id="help_content" style="font-size: 16px;"></div>
		</div>
		<!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
