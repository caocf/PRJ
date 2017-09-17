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

		<title>智慧出行-自驾出行</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<link rel="stylesheet" href="<%=basePath%>WebPage/css/graphical/multiple.css" type="text/css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/wisdom.css" />
		<script type="text/javascript" src="WebPage/js/taxi/taxiInfor.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/Graphical/multiple.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,2"/> 
		</jsp:include>

		<!-- 页面内容 -->
		<div id="main_content1">
			<!-- 左边内容 -->
		<div class="main_left">
		
	<div class="main_left_top_2">
		<!-- 头部标题 -->
	<div class="main_left_top">
	<div class="main_left_top_1"><a class="top_select_no" onClick="SelectItem_top(this,'WebPage/page/Graphical/self_driving.jsp')">自驾出行</a></div>
	<div class="main_left_top_1"> <a class="top_select" onClick="SelectItem_top(this,'WebPage/page/Graphical/multiple.jsp')"><span>综合出行</span></a></div>
	<div class="main_left_top_1" > <a class="top_select_no" onClick="SelectItem_top(this,'WebPage/page/Graphical/FreightTraffic.jsp')"><span>货运出行</span></a></div>
	</div>
	
	<!-- 二级标题 -->
	<div class="main_left_top_second">
	<div class="main_left_top_second_1"><a class="main_left_select_no" onClick="SelectItem_main_left(this,'WebPage/page/Graphical/multiple.jsp')">公共交通</a></div>
	<div class="main_left_top_second_2"><a class="main_left_select_no" href="WebPage/page/Graphical/self_driving.jsp" >自驾</a></div>
	<div class="main_left_top_second_2"><a class="main_left_select" href="WebPage/page/Graphical/multiple_group.jsp">组合</a></div>
	</div>
	<!-- 二级标题 -->
	 
	<!--起点 终点 搜索 -->
	<div class="main_left_search_left">
	<table class="table_start_end">
	<tr>
	<td align="left" >
	<span class="span_start_end">&nbsp;起点</span> <input type="text" class="input_start_end" /><br>
	    <hr class="hr_start_end">
    <span class="span_start_end">&nbsp;终点&nbsp;</span><input type="text" class="input_start_end" />
	
	
	</td>
	<td width="50px">
	<a href="#">
	<img alt="" src="WebPage/image/graphical/btn_change_normal.png" onMouseOver="this.src='WebPage/image/graphical/btn_change_hover.png'" onMouseOut="this.src='WebPage/image/graphical/btn_change_normal.png'"  style="float: right;"/>
	</a>
	</td>
	</tr>
	</table>
	</div>
	<div class="main_left_search_right">
	<a href="#">
	<img alt="" src="WebPage/image/graphical/btn_search_normal.png" onMouseOver="this.src='WebPage/image/graphical/btn_search_hover.png'" onMouseOut="this.src='WebPage/image/graphical/btn_search_normal.png'"  class="img_search"/>
	</a>
	</div>
	<p class="p_choose"><img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_not-chose.png" class="img_no_choice"></img><span class="span_choose">&nbsp;考虑实时路况</span></p>
	</div>
	<div class="main_left_down" align="center">
	<div class="div_consider"><img class="img_consider" alt="" src="WebPage/image/graphical/icon_survey.png"/ ><span class="span_consider_left">综合出行概况</span></div>
	<div class="main_left_down_1">
	<div class="main_left_down_2"><a class="main_left_select" onClick="SelectItem_main_left(this,'#')">时间短</a></div>
	<div class="main_left_down_2"><a class="main_left_select_no" onClick="SelectItem_main_left(this,'#')">路程短</a></div>
	<div class="main_left_down_2"><a class="main_left_select_no" onClick="SelectItem_main_left(this,'#')">路况好</a></div>
	</div>
	<div class="main_left_middle">
	<div class="main_left_middle_1">
	<div class="main_left_middle_2">
	<h4 align="center" style="color: white;">1</h4>
	</div>
	<h3 class="h3_search_result_1" >共5.2公里 行程16分钟</h3>
	<img class="img_phone" alt="" src="WebPage/image/graphical/btn_send-to-mobile.png">
	</div>
	</div>
	<div class="main_left_bottom">
	 <h3 class="h3_search_taxi" >打车20约元，10分钟</h3>
	 <a href="#"><span class="span_reserve" >立即预约</span></a>
	</div>
	</div>
	</div>
	<!-- 左边内容 -->
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
