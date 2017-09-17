<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>智慧公路</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<link rel="stylesheet" type="text/css" href="css/system/top.css">
	<link rel="stylesheet" type="text/css" href="css/system/style.css">
	<link rel="stylesheet" type="text/css" href="css/main/road.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">
		<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
	<script type="text/javascript" src="js/system/top.js"></script>
	<script type="text/javascript" src="js/system/link.js"></script>
	<script type="text/javascript" src="js/main/road.js"></script>

  </head>
  
  <body style="overflow-x:hidden;overflow-y:hidden">
  <div id="fullbg"></div>
   <div class="loadingDiv">
		<img  src="image/system/loading.gif">
	</div>
	
   <div class="common_c0">
       <input type="hidden" value="<%=basePath%>" id="basePath" />
       <div class="top_u1_u2">
			<div>
			 <img class="top_u2" id="roadguid_back" src="image/system/back_normal.png" />
			  <span class="top_text1" id="top_text">智慧公路</span>
		   </div>
			</div>
				<div id="road_map_1" class="traffic_map_back">
			<div id="road_map" class="traffic_map"></div>
		</div>
   </div>


	<div class="travelcount">
		<div class="tip_top_style2" >
			<div class="tip_top_textleft" id="traffic_block">日交通量统计</div>
			
			
		</div>
		<div class="tip_content_styleroad" >
		<select class="travelcount_title" id="jtllxmc"> 
		</select>
		<table id="travelcountTb" style="width:280px;"
			cellpadding="4px" cellspacing="0">
			<col width="60%" />
			<col width="40%" />
			<tr class="tip_road_line">
				<td class="tip_road_left">机动车当量数合计:</td>
				<td class="tip_road_right" id="jdcdl">
				</td>
			</tr>
            <tr class="tip_road_line">
				<td class="tip_road_left">汽车当量数合计:</td>
				<td class="tip_road_right" id="qcdl" /></td>
			</tr>
			<tr class="tip_road_line">
				<td class="tip_road_left">大型货车:</td>
				<td class="tip_road_right" id="dxhc" /></td>
			</tr>
			<tr class="tip_road_line">
				<td class="tip_road_left">行驶量:</td>
				<td class="tip_road_right"id="xsl" /></td>
			</tr><tr class="tip_road_line">
				<td class="tip_road_left">适应交通量:</td>
				<td class="tip_road_right" id="syjtl" /></td>
			</tr>
			<tr class="tip_road_line"  >
				<td class="tip_road_left"  >统计时间:</td>
				<td class="tip_road_right" id="tjsj" /></td>
			</tr>
		</table>
		</div>
	</div>


	<!-- 底部导航栏 -->
	 <img  style='height:68px;position:absolute;cursor:pointer;border:1px solid #ccc;' id="link_Left" src="image/map/linkleft_on.png"> 
    <div class="linkline" id="linklineid">
		<div class="linkline_img" id="mainguid_rroute" style="background:#008181;behavior: url(ie-css3.htc);">
				<img src="image/link_icon/icon_yxjc.png">
			<label>路网监测</label>
		</div>
		<div class="linkline_img" id="mainguid_rblock" style="background:#009292;">
				<img src="image/link_icon/icon_tzxx.png">
			<label>通阻信息</label>
		</div>
		<div class="linkline_img" id="mainguid_rvideo" style="background:#00a5a5;">
				<img src="image/link_icon/icon_spjk.png">
			<label>视频监控</label>
		</div>
		<div class="linkline_img" id="mainguid_ranalyse" style="background:#00b6b6;
		-moz-border-radius:0 4px 4px 0 ;-webkit-border-radius:0 4px 4px 0 ;
		border-radius:0 4px 4px 0;behavior: url(ie-css3.htc);">
				<img src="image/link_icon/icon_hyfx.png">
			<label>行业分析</label>
		</div>

	</div>
    
    <!-- 右部浮动层 -->
    <div class="degreeguid">
    <span>拥堵</span><img src="image/main/guid_color.png"/><span>畅通</span>
    <img  src="image/main/icon_nodata.png"><span>无数据</span>
    </div>
    <!-- 
    <select class="regionselect">
     <option value="请选择">请选择</option>        
      <option value="1" selected="selected">南湖区  </option> 
      <option value="2" selected="selected">秀洲区  </option> 
      </select> -->
      
    <div class="sizeguid">
    <div id="zoomLarge" style="cursor: pointer;">
    <img class="sizeguid_img1" src="image/main/icon_add.png" /></div>
    <div class="sizeguid_line"></div>
    <div id="zoomSmall" style="cursor: pointer;">
    <img class="sizeguid_img2" src="image/main/icon_reduce.png" /></div>
    </div>
  
  </body>
  </html>
  
  