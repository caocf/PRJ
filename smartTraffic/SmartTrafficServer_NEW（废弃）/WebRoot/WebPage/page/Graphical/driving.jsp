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

		<title>智慧出行-行车诱导</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/graphical/driving.css">
		
		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script language="javascript" src="http://115.231.73.253/zhjtapi/js/web-t/WebTHelper.js"></script>
		 <script type="text/javascript" src="WebPage/js/Graphical/driving.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,4"/> 
		</jsp:include>
		
		
		<!-- 左边内容 -->
				<div class="main_left">
				
					<!-- 头部标题 -->
					<div class="main_left_top">
						<div class="main_left_top_1">
							<a class="top_select_no" style="width: 80px"
								onClick="SelectItem_top(this,'WebPage/page/Graphical/self_driving.jsp')">停车诱导</a>
							<a class="top_select" style="width: 80px"
								onClick="SelectItem_top(this,'WebPage/page/Graphical/self_driving.jsp')">行车诱导</a>
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
										<input type="hidden" class="input_text2 " id="staPoint" readonly="readonly"/>
										<input type="text" class="input_text2 " id="startPointLonLat"/>
									</td>
									<td>
										<img src="WebPage/image/driving/map_point_start.png"
											style="width: 27px;" onClick="switchPoint('start')" />
									</td>
								</tr>
								<tr>
									<td>
										终点
									</td>
									<td>
										<input type="hidden" class="input_text2" id="endPoint" readonly="readonly"/>  
										<input type="text" class="input_text2" id="endPointLonLat" />
									</td>
									<td>
										<img src="WebPage/image/driving/map_point_end.png"
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
										<img src="WebPage/image/driving/map_point_pass.png"
											style="width: 25px;" onClick="switchPoint('pass')"/>
									</td>
								</tr>
							</table>
						</div>

					<div class="main_left_down" align="center">
						<div class="div_consider">
							<img class="img_consider" alt=""
								src="WebPage/image/driving/icon_survey.png" />
							<span class="span_consider_left">交通动态路径规划概况</span>
						</div>
						<div class="main_left_down_1">
							<div class="main_left_down_2">
								<a class="select_1 main_left_select_no"
									onClick="executeSearch(1,this)">推荐路线</a>
							</div>
							<div class=" main_left_down_2">
								<a class="select_2 main_left_select_no"
									onClick="executeSearch(2,this)">最短路线</a>
							</div>
							<div class="main_left_down_2">
								<a class="select_4 main_left_select_no"
									onClick="executeSearch(4,this)">避开高速</a>
							</div>
						</div>
						<div class="main_left_middle" id="infoMenu">
						</div>
					</div>
				</div>
			</div>
				<!-- 左边内容 -->
				<!-- 右边图形内容 -->
				<div class="layout1_right">
					<div id="simpleMap" class="simpleMap_top"></div>
				</div>
				<div id="routeSearchPoint">
					<img id="routeSearchPointImg">
				</div>
				<!-- 右边图形内容 -->
			<!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
