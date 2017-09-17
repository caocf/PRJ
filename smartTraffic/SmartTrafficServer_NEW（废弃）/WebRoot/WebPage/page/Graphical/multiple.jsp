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

		<title>智慧出行-综合出行</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<!-- 综合出行的样式 -->
		<link rel="stylesheet" href="<%=basePath%>WebPage/css/graphical/multiple.css" type="text/css"></link>
		<!-- 默认背景选中的JS -->
		<script type="text/javascript" src="WebPage/js/taxi/taxiInfor.js"></script>
		
		<!-- 地图的JS引用 -->
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
		
		<!-- 综合出行的JS -->
		<script type="text/javascript" src="WebPage/js/Graphical/multiple.js"></script>
		
		<!-- 右边的样式 -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/wisdom.css" />
	    </head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,2"/> 
		</jsp:include>
		<!-- 页面内容 -->
		<div id="main_multiple" > 
	<div class="main_left" >
	<!-- 上半部分 -->	
	<div class="main_left_top_2" align="center" >
	<!-- 头部标题 -->
	<div class="main_left_top">
	<div class="main_left_top_1"><a class="top_select_no" onClick="SelectItem_top(this,'WebPage/page/Graphical/self_driving.jsp')">自驾出行</a></div>
	<div class="main_left_top_1"> <a class="top_select" onClick="SelectItem_top(this,'WebPage/page/Graphical/multiple.jsp')"><span>综合出行</span></a></div>
	<div class="main_left_top_1" > <a class="top_select_no" onClick="SelectItem_top(this,'WebPage/page/Graphical/FreightTraffic.jsp')"><span>货运出行</span></a></div>
	</div>
	<!-- 头部标题 -->
		
	<!-- 二级标题 -->
	<div class="main_left_top_second" >
	<div class="main_left_top_second_1"><a class="main_left_select" onClick="SelectItem_main_left(this,'WebPage/page/Graphical/multiple.jsp')">公共交通</a></div>
	<div class="main_left_top_second_2"><a class="main_left_select_no" href="WebPage/page/Graphical/self_driving.jsp" >自驾</a></div>
	<div class="main_left_top_second_2"><a class="main_left_select_no" href="WebPage/page/Graphical/multiple_group.jsp">组合</a></div>
	</div>
	<!-- 二级标题 -->
	
	
	<!-- 工具选择 -->
	<div class="main_left_top_tra">
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_not-chose.png" class="img_tra" ><span class="span_tra">火车</span>
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_chose.png" class="img_tra" ><span class="span_tra">高铁</span>
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_chose.png" class="img_tra" ><span class="span_tra">动车</span>
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_chose.png" class="img_tra" ><span class="span_tra">长途客运</span>
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_chose.png" class="img_tra" ><span class="span_tra">公交</span>
	<img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_not-chose.png" class="img_tra" ><span class="span_tra">自行车</span>
	</div>
	<!-- 工具选择 -->
	
	
	
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
	<!--起点 终点 搜索 -->
	
	<!-- 搜索按钮 -->
	<div class="main_left_search_right">
	<a href="#">
	<img alt="" src="WebPage/image/graphical/btn_search_normal.png" onMouseOver="this.src='WebPage/image/graphical/btn_search_hover.png'" onMouseOut="this.src='WebPage/image/graphical/btn_search_normal.png'"  class="img_search"/>
	</a>
	</div>
	<!-- 搜索按钮 -->
	<div class="p_choose"><img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_not-chose.png" class="img_no_choice"></img><span class="span_choose">&nbsp;考虑实时路况</span></div>
	</div>
	<!-- 上半部分 -->	
	
	
	<!-- 下半部分 -->	
	<div class="main_left_down" align="center">
	<!-- 搜索结果文字 -->
	<div class="div_consider"><img class="img_consider" alt="" src="WebPage/image/graphical/icon_survey.png"/ ><span class="span_consider_left">自驾出行概况</span><span class="span_consider_right">当前区域共4个搜索结果</span></div>
	<!-- 搜索结果文字 -->
	
	<!-- 选择标题 -->
	<div class="main_left_down_1">
	<div class="main_left_down_2" style="margin-left: 50px"><a class="main_left_select" onClick="SelectItem_main_left(this,'#')">时间短</a></div>
	<div class="main_left_down_2"><a class="main_left_select_no" onClick="SelectItem_main_left(this,'#')">少换乘</a></div>
	<div class="main_left_down_2"><a class="main_left_select_no" onClick="SelectItem_main_left(this,'#')">少步行</a></div>
	</div>
	<!-- 选择标题 -->
	
	<!-- 中间搜索正文 -->
	<div class="main_left_middle">
	<!-- 搜索结果1 -->
	<div class="main_left_middle_1">
	<div class="main_left_middle_2">
	<h4 align="center" style="color: white;">1</h4>
	</div>
	<table width="300px" height="50px">
	<tr>
	<td align="left"><span class="span_result">93路~98路</span></td>
	<td align="right"><span class="span_result_1">约35分钟</span></td>
	</tr>
	<tr>
	<td align="left"> <span class="span_result_2"">6.5公里 换乘一次 步行210米</span></td>
	<td align="right"><img class="img_phone_1" alt="" src="WebPage/image/graphical/btn_send-to-mobile.png"></td>
	</tr>
	</table>
	</div>
	<!-- 搜索结果1 -->
	
	</div>
	<!-- 中间搜索正文 -->
	
	<!-- 搜索结果2 -->
	<div class="main_left_middle_3">
	<div class="main_left_middle_2">
	<h4 align="center" style="color: white;">2</h4>
	</div>
	<h3 class="h3_search_result_2" >14路-自行车</h3><h4 align="center" class="h4_search_result">全程约40分钟</h4>
	</div>
	<!-- 搜索结果2 -->
	
	<!-- 搜索内容底部 -->
	<div class="main_left_bottom">
	 <h3 class="h3_search_taxi" >打车20约元，10分钟</h3>
	 <a href="#"><span class="span_reserve" >立即预约</span></a>
	</div>
	<!-- 搜索内容底部 -->
	</div>
	<!-- 下半部分 -->	
	</div>
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
