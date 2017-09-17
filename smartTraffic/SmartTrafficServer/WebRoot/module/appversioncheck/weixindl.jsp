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

		<title>应用下载</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-control" content="no-cache">
		<meta http-equiv="Cache" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>

	</head>

	<script type="text/javascript">
	function dodownload() {
		window.location.href = $("#basePath").val()
				+ "downLastApp?equitment=weixin";
	}
</script>

	<body style="font-family: 微软雅黑;text-align:center">
		<input id="basePath" name="basePath" type="hidden"
			value="<%=basePath%>">

		<img src="WebPage/image/appversion/bgground.png" height="70%"
			width="100%" />

		<div style="margin:0 auto">
			<h1>
				请从右上角点击打开应用
			</h1>
			<h1>
				然后点击下方连接进行下载
			</h1>
		</div>
		<br>
		<br>
		<br>
		<button
			style="margin:0 auto;width:400px;height:100px;font-size:20px;background-image:url(<%=basePath%>WebPage/image/appversion/btn_bg_normal-.png);background-position:center;"
			onclick=
	dodownload();
>
			<font color="#ffffff">立即下载</font>
		</button>
	</body>
</html>
