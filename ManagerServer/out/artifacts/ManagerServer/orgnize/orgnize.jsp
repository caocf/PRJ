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

		<title>组织架构</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="orgnize/orgnize.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="orgnize/orgnize.js"></script>

	</head>

	<body>
<input type="hidden" value="<%=session.getAttribute("roleId")%>" id="roleId" />
<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="container">
			<div id="left">




			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/huzhoumain.jsp"
					width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight">
				</iframe>
			</div>
		</div>

	</body>
</html>
