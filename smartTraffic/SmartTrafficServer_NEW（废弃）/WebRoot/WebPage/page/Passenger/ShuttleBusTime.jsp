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

		<title>智慧出行-班线查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	    <link rel="stylesheet" href="<%=basePath%>WebPage/css/taxiInformation/passenger.css" type="text/css"/>
	

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/taxi/taxiInfor.js"></script>

	</head>

	<body>
		<div id="page_content" >
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,1,3" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content" >
				<div class="layout1">
					<a href="<%=basePath%>WebPage/page/Passenger/TicketOutlet.jsp"
						class="layout1_a">售票网点</a>
					<a class="a_select">班车时刻</a>
					<a class="layout1_a" href="http://keyun.96520.com/bus/querybus.htm">余票查询</a>
					
				</div>
				<div class="layout2">	
					<div class="main_left">
						<a class="main_left_select"
							onclick="SelectItem_main_left(this,'WebPage/page/Passenger/PassengerSurvey_ZX.jsp')" >客运中心班车时刻</a>
						<a class="main_left_select_no"
							onclick="SelectItem_main_left(this,'WebPage/page/Passenger/PassengerSurvey_BZ.jsp')" >北站客运时刻</a>
					</div>
					<div class="main_right">
						<iframe class="right"
							src="WebPage/page/Passenger/PassengerSurvey_ZX.jsp"
							width="800px" height="100%" scrolling="no" frameborder="0"></iframe>
					</div>
						
				</div>
				
			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
