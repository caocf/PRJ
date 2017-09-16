<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>湖州APP内部版下载</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="js/common/jquery-1.10.2.min.js"></script>
</head>

<script type="text/javascript">
	$(document).ready(function() {
		if (!is_weixin()) {
			$("#upImg").hide();
			$("#updiv").hide();
		}
	});
	function downloadWharf() {
		window.location.href ="http://120.55.100.184:90/HuZhouPort/downLastApp?appid=1";
	}
	function is_weixin() {
		var ua = navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == "micromessenger") {
			return true;
		} else {
			return false;
		}
	}
</script>

<body style="width:100%;height:100%;margin:0px auto;text-align:center;">
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<img src="image/weixindown/pix.png" style><br>

	<img alt="" src="image/weixindown/btn_normal.png" onclick="downloadWharf()" style='position:absolute;margin-top:30px;margin-left:-450px;'/>
	<div style='float:left;width:100%;height:200px;'></div>
	<div style='width:100%;position: fixed;z-index: 2050;top:0px;'
		id="upImg">
		<img style="float:right;margin-right:70px;margin-top:50px;"
			src="image/weixindown/download-up.png">
	</div>
	<div id="updiv"
		style=" position: fixed;width: 100%;height: 100%;top: 0px;left: 0px;z-index: 2000;filter: Alpha(opacity=60);background: #000;	background-color: rgba(0,0,0,0.6);"></div>
</body>
</html>
