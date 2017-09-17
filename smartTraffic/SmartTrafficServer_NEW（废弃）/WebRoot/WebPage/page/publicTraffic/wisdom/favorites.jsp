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

		<title>智慧出行-公交-收藏夹</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/wisdom.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/map_bus_popoup.css" />


		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/common/json2.js"></script>
		<script type="text/javascript" src="WebPage/js/publicTraffic/wisdom/favorites.js"></script>
		<script type="text/javascript" src="WebPage/js/publicTraffic/wisdom/way_search.js"></script>
		<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>


	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,1,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content2">
				<div class="layout1">
					<div class="layout1_left">
						<div class="layout1_left_layout1">	
								<a class="layout1_left_layout1_li" href="WebPage/page/publicTraffic/wisdom/road_search.jsp">
									线路查询
								</a>
								<a class="layout1_left_layout1_li" href="WebPage/page/publicTraffic/wisdom/bus_station.jsp">
									站点查询
								</a>
								<a class="layout1_left_layout1_li" href="WebPage/page/publicTraffic/wisdom/bus_transfer.jsp">
									换乘查询
								</a>
								<a class="layout1_left_layout1_li_yes"  href="WebPage/page/publicTraffic/wisdom/favorites.jsp">
									收藏夹
								</a>	
						</div>
						<div class="Favorites_left_layout1">
							<span onClick="FavoritesList(this,1)" class="layout3_left_layout2_label_select">线路</span>
							<span onClick="FavoritesList(this,2)" class="layout3_left_layout2_label">站点</span>
							<span onClick="FavoritesList(this,3)" class="layout3_left_layout2_label">换乘</span>
						</div>
							<div class="Favorites_datalist_1">
								<label class="loadingData">
									数据加载中，请稍候&hellip;
								</label>
							</div>
							<div class="Favorites_datalist_2">
								<label class="loadingData">
									数据加载中，请稍候&hellip;
								</label>
							</div>
							<div class="Favorites_datalist_3">
								<label class="loadingData">
									数据加载中，请稍候&hellip;
								</label>
							</div>

						</div>
						<div id="div_left_close" onclick="toggleFooter();"></div>
						<div id="div_left_open" onclick="toggleFooter();"></div>
						<div class="layout1_right">
							<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top"> <label class="simpleMap_top_location">嘉兴市</label>
							<div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 页面内容-end -->
				<jsp:include page="../../../../WebPage/page/main/foot.jsp" />
			</div>
	</body>
</html>
