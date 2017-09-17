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
    
    <title>智慧出行-驾校详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

	<link rel="stylesheet" href="<%=basePath%>WebPage/css/common/main.css" type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css" type="text/css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/TrainingFunctionDetail.js"></script>
</head>

	<body>
		<input type="hidden" value="<%=request.getParameter("id")%>" id="id" />
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top1.jsp" />

			<!--界面内容-->
			<div id="main_content">
				<div class="top">
					<a href="WebPage/page/carTraining/dynamic.jsp"><span
						style="font-size: 14px; color: #999; text-decoration: none;margin-bottom: 10px;display: block;width: 70px;">返回&raquo;</span>
					</a>
				</div>
				<div class="bottom">
					<h1 align="center" style="margin-top: 30px;margin-bottom: 10px" id="title"></h1>
					<p align="center" id="time">
					</p>
					<p align="center">
						<img id=""
							src="WebPage/image/trafficNews_icon_top.png" />
					</p>
					<p
						style="font-size: 14px; font-weight: normal; letter-spacing: 2px; color: rgb(51, 51, 51);line-height: 18px;text-indent: 2em;margin-left: 20px;margin-right: 20px;"
						id="news_content">
					</p>
					<p align="center" id="imageSrc"></p>

				</div>
			</div>
			
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>

	</body>
</html>
