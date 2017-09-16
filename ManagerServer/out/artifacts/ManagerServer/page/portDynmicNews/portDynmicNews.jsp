<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.huzhouport.portDynmicNews.action.ModelActionPortDynmicNews"%>
<%@page import="com.huzhouport.portDynmicNews.model.ModelPortDynmicNews"%>
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

		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="JSP,keyword2,keyword3">
		<meta http-equiv="description" content="">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<script type="text/javascript" src="js/common/paging.js"></script>
		 <script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/portDynmicNews/portDynmicNewsList.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<center>
			<h2>
				港航动态
			</h2>
		</center>
		<table id="protDynmicNews_table">
		</table>
		 <label class="loadingData" style="display:none">数据加载中·····</label>
       <!-- 用于显示页码的div -->
	<div id="pageDiv">
		<p>
		共<span id="allTotal"></span>条
			<span class="firstBtnSpan"></span>&nbsp;
			<span class="prevBtnSpan"></span>&nbsp;
			<span class="pageNoSpan"></span>页&nbsp;
			<span class="nextBtnSpan"></span>&nbsp;
			<span class="lastBtnSpan"></span>&nbsp;
			<span class="gotoPageSpan"></span>
		</p>
	</div>
	</body>
</html>
