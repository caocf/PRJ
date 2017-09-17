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

		<title>智慧出行-电子站牌-公交线路详情</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebSit/css/publicTraffic/line_info.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/map.css" />
			

		<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
			<script type="text/javascript" src="WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>	
		<script type="text/javascript" src="WebSit/js/common/json2.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/publicTraffic/wisdom/line_infor.js"></script>


	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
			<input type="hidden" value="<%=request.getParameter("routeId") %>" id="routeId"/>
			<input type="hidden" value="<%=request.getParameter("busStationId")%>" id="busStationId"/>
			<input type="hidden" value="<%=request.getParameter("direct")%>" id="direct"/>
				<div class="layout1">
					<div class="layout1_left" >

					</div>
                    <div class="layout1_right">
						<div class="layout1_right_layout1">
							<div class="layout1_right_layout1_layout1">
							<div id="simpleMap" class="simpleMap_notop"></div>
							</div>
						</div>
					<div class="layout1_right_layout2">
					</div>
					</div>
				</div>
			</div>
		
				<!-- 页面内容-end -->
				<jsp:include page="../../../../WebSit/page/main/foot.jsp" />
			</div>
	</body>
</html>
