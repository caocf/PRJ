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

		<title>智慧出行-自驾出行</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />


		<link rel="stylesheet" type="text/css"
			href="WebPage/css/graphical/self_driving.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/publicTraffic/wisdom.css" />

		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/taxi/taxiInfor.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript"
			src="WebPage/js/Graphical/selfdriving_search.js"></script>
			<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>

	</head>

	<body>
	<input type="hidden" id="sc_startPoint" value="<%=startPoint %>"/>
			<input type="hidden" id="sc_endPoint" value="<%=endPoint %>"/>
			<input type="hidden" id="sc_startPointLonLat" value="<%=request.getParameter("startPointLonLat") %>"/>
			<input type="hidden" id="sc_endPointLonLat" value="<%=request.getParameter("endPointLonLat") %>"/>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,3" />
			</jsp:include>
			
			
			
			<!-- 页面内容 -->
			<div id="main_content1">
				<!-- 左边内容 -->
				<div class="main_left">
				
					<!-- 头部标题 -->
					<div class="main_left_top">
						<div class="main_left_top_1">
							<a class="top_select"
								onClick="SelectItem_top(this,'WebPage/page/Graphical/self_driving.jsp')">自驾出行</a>
						</div>
						<div class="main_left_top_1">
							<a class="top_select_no"
								onClick="SelectItem_top(this,'WebPage/page/Graphical/multiple.jsp')"><span>综合出行</span>
							</a>
						</div>
						<div class="main_left_top_1">
							<a class="top_select_no"
								onClick="SelectItem_top(this,'WebPage/page/Graphical/FreightTraffic.jsp')"><span>货运出行</span>
							</a>
						</div>
					</div>
						<!--起点 终点 搜索 -->
					<div style="overflow-y: auto;overflow-x: hidden;height: 710px;">
						<div class="main_left_search_left"><input type="hidden" class="type" >
							<table class="table_start_end">
							<col width="30px"/><col width="250px"/><col width="50px"/>
								<tr>
									<td>
										起点
									</td>
									<td>
										<input type="text" class="input_text2 " id="startPoint"  onfocus="startPointFocus();"/>
										<input type="hidden" class="input_text2 " id="startPointLonLat" readonly="readonly" />
									</td>
									<td>
										<img src="WebPage/image/common/map_point_start.png"
											style="width: 27px;" onClick="switchPoint('start')" />
									</td>
								</tr>
								<tr>
									<td>
										终点
									</td>
									<td>
										<input type="text" class="input_text2" id="endPoint" onFocus="endPointFocus();"/>  
										<input type="hidden" class="input_text2" id="endPointLonLat" readonly="readonly" />
									</td>
									<td>
										<img src="WebPage/image/common/map_point_end.png"
											style="width: 27px;" onClick="switchPoint('end')" />
									</td>
								</tr>
								<tr>
									<td>
										途径
									</td>
									<td id="byPassInput">
										
									</td>
									<td>
										<img src="WebPage/image/common/map_point_pass.png"
											style="width: 25px;" onClick="switchPoint('pass')"/>
									</td>
								</tr>
							</table>
						</div>

					<div class="main_left_down" align="center">
						<div class="div_consider">
							<img class="img_consider" alt=""
								src="WebPage/image/graphical/icon_survey.png" />
							<span class="span_consider_left">自驾出行概况</span>
						</div>
						<div class="main_left_down_1">
							<div class="main_left_down_2">
								<a class="main_left_select"
									onClick="searchCar(this,'1')">最短线路</a>
							</div>
							<div class="main_left_down_2">
								<a class="main_left_select_no"
									onClick="searchCar(this,'0')">最快路线</a>
							</div>
							<div class="main_left_down_2">
								<a class="main_left_select_no"
									onClick="searchCar(this,'2')">避开高速</a>
							</div>
							<div class="main_left_down_2">
								<a class="main_left_select_no"
									onClick="searchCar(this, '3')">步行</a>
							</div>
						</div>
						<div class="main_left_middle">
						</div>
					</div>
				</div>
			</div>
				<!-- 左边内容 -->
				<div id="div_left_close" onclick="toggleFooter('main_left');"></div>
						<div id="div_left_open" onclick="toggleFooter('main_left');"></div>
				<!-- 右边图形内容 -->
				<div class="layout1_right">
					<div id="simpleMap" class="simpleMap_top"></div>
					<div id="simpleMap_top">
						<label class="simpleMap_top_location">
							嘉兴市
						</label>
						
						<div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div>
					</div>
				</div>
				<!-- 右边图形内容 -->

			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
