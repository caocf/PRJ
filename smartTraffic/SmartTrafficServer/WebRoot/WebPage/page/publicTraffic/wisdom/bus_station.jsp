<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("UTF-8");
	String searchcontent = null;
	if (request.getParameter("searchcontent") != null) {
		searchcontent = new String(request.getParameter("searchcontent").getBytes(
				"ISO-8859-1"), "UTF-8");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-公交-站点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebPage/css/publicTraffic/wisdom.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/map_bus_popoup.css" />

		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>	
		<script type="text/javascript" src="WebPage/js/common/json2.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/publicTraffic/wisdom/bus_station.js"></script>
		<script type="text/javascript" src="WebPage/js/publicTraffic/wisdom/way_search.js"></script>
		<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>


	</head>

	<body><input type="hidden" id="searchcontent" value="<%=searchcontent %>"/>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content2">
				<div class="layout1">
					<div class="layout1_left">
						<div class="map_menu_div">	
								<a class="layout1_a" href="WebPage/page/publicTraffic/wisdom/road_search.jsp">
									线路查询
								</a>
								<a class="a_select" href="WebPage/page/publicTraffic/wisdom/bus_station.jsp">
									站点查询
								</a>
								<a class="layout1_a" href="WebPage/page/publicTraffic/wisdom/bus_transfer.jsp">
									换乘查询
								</a>
								<a class="layout1_a" href="WebPage/page/publicTraffic/wisdom/businfo.jsp">
									公交概况
								</a>
								<a class="layout1_a"  href="WebPage/page/publicTraffic/wisdom/favorites.jsp">
									收藏夹
								</a>	
						</div>
						<div class="layout1_left_layout2">
						<input type="text" class="input_text5" value="请输入站点名称" id="stationName"
							 onBlur="TextBlur(this)" onFocus="TextFocus(this)" />
							<img src="WebPage/image/search_normal.png"
								onclick="queryBusStation(1)"
								onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />
						</div>
						<div class="layout1_left_datalist_title" style="visibility: hidden;">
							<img src="WebPage/image/graphical/select.png" style="float: left" />
							<span style="float: left;">搜索结果</span>
							<span style="float: right;">共<label
									id="searchReasultCount"></label>条</span>
						</div>
					
						<div class="layout1_left_datalist">
								
							</div>
						<div class="layout1_left2">
						<div id="pageDiv">
							<p>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>
								<span class="nextBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>
					</div>
						</div>
						<div id="div_left_close" onclick="toggleFooter();"></div>
						<div id="div_left_open" onclick="toggleFooter();"></div>
						<div class="layout1_right">
							<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top"> <label class="simpleMap_top_location">嘉兴市</label>
							<!-- <div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div> -->
							</div>
						</div>
					</div>
				</div>
				<!-- 页面内容-end -->
				<jsp:include page="../../../../WebPage/page/main/foot.jsp" />
			</div>
	</body>
</html>
