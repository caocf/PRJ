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
		<link rel="stylesheet" href="<%=basePath%>WebPage/css/graphical/FreightTraffic.css" type="text/css"></link>
		
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
	<div class="main_left_top_1"> <a class="top_select_no" onClick="SelectItem_top(this,'WebPage/page/Graphical/multiple.jsp')"><span>综合出行</span></a></div>
	<div class="main_left_top_1" > <a class="top_select" onClick="SelectItem_top(this,'#')"><span>货运出行</span></a></div>
	</div>
	<!-- 头部标题 -->
		
	<!-- 货车信息内容 -->
	<div class="main_left_top_second" >
	<table width="340px" height="100px">
	<tr>
	<td width="170px"><span class="span_table">是否装载危险物品</span></td>
	<td width="170px"><label><input type="radio" value="是"/><span class="span_table">&nbsp;是</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="是"/><span class="span_table" >&nbsp;否</span></label></td>
	</tr>
	<tr>
	<td align="right"><span class="span_table">限载（吨）</span><input type="text" class="input_table"/></td>
	<td align="right"><span class="span_table">长（吨）</span><input type="text" class="input_table" /></td>
	</tr>
	<tr>
	<td align="right"><span class="span_table">宽（吨）</span><input type="text" class="input_table"/></td>
	<td align="right"><span class="span_table">高（吨）</span><input type="text" class="input_table"/></td>
	</tr>
	</table>
	</div>
	<!-- 货车信息内容 -->
	
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
	<a href="WebPage/page/Graphical/multiple_group_search.jsp">
	<img alt="" src="WebPage/image/graphical/btn_search_normal.png" onMouseOver="this.src='WebPage/image/graphical/btn_search_hover.png'" onMouseOut="this.src='WebPage/image/graphical/btn_search_normal.png'"  class="img_search"/>
	</a>
	</div>
	<!-- 搜索按钮 -->
	<p class="p_choose"><img alt="" src="WebPage/image/graphical/icon_real-timel-traffic_not-chose.png" class="img_no_choice"></img><span class="span_choose">&nbsp;考虑实时路况</span></p>
	</div>
	<!-- 上半部分 -->	
	
	
	<!-- 下半部分 -->	
	<div class="main_left_down" align="center">
	<!-- 搜索结果文字 -->
	<div  class="div_consider"><img class="img_consider" alt="" src="WebPage/image/graphical/icon_survey.png"/ ><span class="span_consider_left">综合出行概况</span></div>
	<!-- 搜索结果文字 -->
	
	<!-- 中间搜索正文 -->
	<div class="main_left_middle">
	<!-- 文字简介 -->
城市综合交通涵盖了存在于城市中及与城市有关的各种交通形式。从地域关系上，城市综合交通大致分为城市对外交通和城市交通两大部分。
城市对外交通，泛指本城市与其他城市间的交通，及城市行政区范围内的城区与周围城镇、乡村间的交通。其主要交通形式有公路交通、铁路交通、航空交通和水运交通。
城市交通是指城市（城区）内的交通，包括城市道路交通、城市轨道交通和城市水上交通等。其中，以城市道路交通和城市轨道交通为主体。
从形式上，城市综合交通可以分为地上交通、地下交通、路面交通、轨道交通、水上交通等。

	<!--文字简介 -->
	
	</div>
	<!-- 中间搜索正文 -->
	
	
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
