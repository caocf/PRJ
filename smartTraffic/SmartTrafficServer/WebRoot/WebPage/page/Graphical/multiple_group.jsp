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
		<link rel="stylesheet" href="<%=basePath%>WebPage/css/graphical/multiple_group.css" type="text/css"></link>
		
		<!-- 默认背景选中的JS -->
		<script type="text/javascript" src="WebPage/js/taxi/taxiInfor.js"></script>
		
		<!-- 地图的JS引用 -->
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
		
		<!-- 综合出行的JS -->
		<script type="text/javascript" src="WebPage/js/Graphical/multiple.js"></script>
		
		<!-- 右边的样式 -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/map.css" />
		
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
	<div class="div_consider"><img class="img_consider" alt="" src="WebPage/image/graphical/icon_survey.png"/ ><span class="span_consider_left">综合出行概况</span></div>
	<!-- 搜索结果文字 -->
	
	<!-- 中间搜索正文 -->
	<div class="main_left_middle">
	<!-- 文字简介 -->
城市综合交通涵盖了存在于城市中及与城市有关的各种交通形式。从地域关系上，城市综合交通大致分为城市对外交通和城市交通两大部分。
城市对外交通，泛指本城市与其他城市间的交通，及城市行政区范围内的城区与周围城镇、乡村间的交通。其主要交通形式有公路交通、铁路交通、航空交通和水运交通。
城市交通是指城市（城区）内的交通，包括城市道路交通、城市轨道交通和城市水上交通等。其中，以城市道路交通和城市轨道交通为主体。
从形式上，城市综合交通可以分为地上交通、地下交通、路面交通、轨道交通、水上交通等。
从运输性质上，城市综合交通又可以分为客运交通和货运交通两大类型。
从交通的位置上，城市综合交通又可分为道路上的交通和道路外的交通。
城市综合交通又可以按交通性质与交通方式进行分类。各类城市对外交通的规划决定于相关的行业规划和城镇体系规划；各类城市交通又与城市的运输系统，道路系统和城市交通管理系统密切相关。
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
						
						<!-- <div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div> -->
					</div>
						</div>
	<!-- 右边图形内容 -->
		
		
		</div>
		<!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
