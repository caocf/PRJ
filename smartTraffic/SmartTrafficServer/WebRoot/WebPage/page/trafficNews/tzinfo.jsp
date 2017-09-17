<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-交通快讯_通阻信息详情</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/common/table.css"/>
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/time/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/trafficNews/tzinfo.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="5,1" />
			</jsp:include>
			<%-- 界面内容 --%>
			<input value="<%=request.getParameter("xxbh") %>" id="xxbh" type="hidden">
			<div id="main_content">
				<div class="back_button" onclick="goback()" >返回</div>
			</div>
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>

	</body>
</html>
