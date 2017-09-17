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

		<title>智慧出行-铁路-余票查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/publicTraffic/ticket.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/CRselectBox.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebPage/js/publicTraffic/wisdom/businfo.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content_businfo" >
				<div class="layout1_businfo">
					<a href="<%=basePath%>WebPage/page/publicTraffic/wisdom/road_search.jsp"
					class="layout1_a">线路查询</a>
					<a href="<%=basePath%>WebPage/page/publicTraffic/wisdom/bus_station.jsp"
					class="layout1_a">站点查询</a>
					<a href="<%=basePath%>WebPage/page/publicTraffic/wisdom/bus_transfer.jsp"
					class="layout1_a">换乘查询</a>
					<a href="<%=basePath%>WebPage/page/publicTraffic/railway/railwayinfo.jsp"
					class="a_select">公交概况</a>
					<a href="<%=basePath%>WebPage/page/publicTraffic/wisdom/favorites.jsp"
					class="layout1_a">收藏夹</a>
					<label class="pagepath"><script>document.write(PATH_GRAPHICAL_PUBLICTRAFFIC_BUS_INFO)</script></label>
				</div>
				    <div class="layout_businfo">
				       <b style="display:block;font-size: 16px;margin-top: 10px;">公交IC卡办理充值网点</b>
				    </div>
					<div class="layout6_businfo">
					
					</div>
			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
