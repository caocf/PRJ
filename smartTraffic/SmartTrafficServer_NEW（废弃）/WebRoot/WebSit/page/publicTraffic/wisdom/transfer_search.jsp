<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	String endPoint = null;
	if (request.getParameter("endPoint") != null) {
		endPoint = new String(request.getParameter("endPoint").getBytes(
				"ISO-8859-1"), "utf-8");
	}
	String startPoint = null;
	if (request.getParameter("startPoint") != null) {
		startPoint = new String(request.getParameter("startPoint").getBytes(
				"ISO-8859-1"), "utf-8");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-公交-换乘查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebSit/css/publicTraffic/wisdom.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebSit/css/common/map_bus_popoup.css" />
		<link href="WebSit/js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />
	
		<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>	
		<script type="text/javascript" src="WebSit/js/common/json2.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>
		<script src="WebSit/js/common/jquery-ui-1.8.16.custom.min.js"
			language="javascript"></script>
			
		<script type="text/javascript" src="WebSit/js/publicTraffic/wisdom/transfer_search.js"></script>
		<script type="text/javascript" src="WebSit/js/common/div_left_handle.js"></script>


	</head>

	<body>
		<div id="page_content">
			<input type="hidden" id="sc_startPoint" value="<%=startPoint %>"/>
			<input type="hidden" id="sc_endPoint" value="<%=endPoint %>"/>
			<input type="hidden" id="sc_startPointLonLat" value="<%=request.getParameter("startPointLonLat") %>"/>
			<input type="hidden" id="sc_endPointLonLat" value="<%=request.getParameter("endPointLonLat") %>"/>
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content2">
				<div class="layout1">
					<div class="layout1_left">
						<div class="layout1_left_layout1">
								<a class="layout1_left_layout1_li" href="WebSit/page/publicTraffic/wisdom/road_search.jsp">
									线路查询
								</a>
								<a class="layout1_left_layout1_li" href="WebSit/page/publicTraffic/wisdom/bus_station.jsp">
									站点查询
								</a>
								<a class="layout1_left_layout1_li_yes" href="WebSit/page/publicTraffic/wisdom/bus_transfer.jsp">
									换乘查询
								</a>
								<a class="layout1_left_layout1_li"  href="WebSit/page/publicTraffic/wisdom/favorites.jsp">
									收藏夹
								</a>	
						</div>
						<!--起点 终点 搜索 -->
						<div class="layout3_left">
							<div class="layout3_left_layout1">
								<div class="layout3_left_layout1_left">
									<div class="qizhongdian" style="border-bottom: 1px dashed #ccc">起点&nbsp;
									   <input type="text" class="qztext" id="startPoint"  onkeyup="startPointSelect()" onKeyDown="startPointSelect()" onFocus="startPointFocus();"/>
									</div>
									   
								    <div class="qizhongdian">终点
								      <input type="text" style="margin-left: 6px" class="qztext" id="endPoint"  onkeyup="endPointSelect()" onKeyDown="endPointSelect()" onFocus="endPointFocus();"/>    
								    </div>
									<img  src="WebSit/image/graphical/btn_change_normal.png" onMouseOver="ChangeTextOver(this)"
									 onMouseOut="ChangeTextOut(this)" onClick="ChangeTextOnClick()"/>
								</div>
								<img  src="WebSit/image/graphical/btn_search_normal.png" onMouseOver="BarChangeOver(this)"
								 onMouseOut="BarChangeOut(this)" class="layout3_left_layout1_right" onClick="SearchBusTransfer()"/>
								</div>
							</div>
							<div class="layout3_left_layout2">
							<span onClick="TransferOder(this,1)" class="layout3_left_layout2_label_select">少换乘</span><span onClick="TransferOder(this,2)" class="layout3_left_layout2_label">时间短</span>
							<span onClick="TransferOder(this,3)" class="layout3_left_layout2_label">少步行</span>
							<span onClick="TransferOder(this,4)" class="layout3_left_layout2_label">价格少</span><span onClick="TransferOder(this,5)" class="layout3_left_layout2_label">总距离短</span>
							</div>
							<div class="layout1_left_datalist_title">
								<img src="WebSit/image/graphical/select.png" style="float: left" />
								<span style="float: left;">公交概况</span>
								<img src="WebSit/image/common/collect.png" onClick="SaveTransferFavor()" />
								<span style="float: right;">共<label
										id="searchReasultCount"></label>条搜索结果</span>
							</div>
								<div class="layout1_left_datalist3">
									
								</div>
								<div id="pageDiv">
									<p>
										<span class="prevBtnSpan"></span>
										<span class="pageNoSpan"></span>
										<span class="nextBtnSpan"></span>
										<span class="gotoPageSpan"></span>
									</p>
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
				<jsp:include page="../../../../WebSit/page/main/foot.jsp" />
			</div>
			<div id="autocomplete_div1" style="z-index: 1; top: 290px; left: 30px;position: absolute;width: 330px; "></div>
			<div id="autocomplete_div2" style="z-index: 1; top: 324px; left: 30px;position: absolute;width: 330px; "></div>
	</body>
</html>
