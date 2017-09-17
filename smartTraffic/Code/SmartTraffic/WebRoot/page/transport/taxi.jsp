<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>智慧运管-出租车监测</title>    
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
	<link rel="stylesheet" type="text/css" href="css/main/transport.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">
	<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
	<script type="text/javascript" src="js/system/top.js"></script>
	<script type="text/javascript" src="js/system/link.js"></script>
	<script type="text/javascript" src="js/transport/taxi.js"></script>
  </head>
  
   <body  style="overflow-x:hidden;overflow-y:hidden">
    <div id="fullbg"></div>
   <div class="loadingDiv">
		<img  src="image/system/loading.gif">
	</div>
	   <div class="common_c0">
       <input type="hidden" value="<%=basePath%>" id="basePath" />
       <div class="top_u1_u3">
			<div>
			 <img class="top_u2" id="transportguid_back" src="image/system/back_normal.png" />
			  <span class="top_text1" id="top_text">智慧运管</span>
		   </div>
			</div>
			<div id="taxi_map_1" class="traffic_map_back">
			<div id="taxi_map" class="traffic_map"></div>
		</div>
   </div>


  <div class="stateguid"  id="taxi_stateguid"  style="width:160px;right: 50px;">
   <span>空车</span><div class="leaveoff_colorshow"  style='background:#01B448;'></div> <div class="stateguid_split"></div>
   <span>重车</span><div class="leaveoff_colorshow" style='background:#FF0707;'></div>
   
   </div>
   
     <!-- 区域选择 -->
<!--      <select class="regionselect">
     <option value="请选择">请选择</option>        
      <option value="1" selected="selected">南湖区  </option> 
      <option value="2" selected="selected">秀洲区  </option> 
      </select> -->



	<div class="sizeguid">
		<div id="zoomLarge" style="cursor: pointer;">
			<img class="sizeguid_img1" src="image/main/icon_add.png" />
		</div>
		<div class="sizeguid_line"></div>
		<div id="zoomSmall" style="cursor: pointer;">
			<img class="sizeguid_img2" src="image/main/icon_reduce.png" />
		</div>
	</div>

 
      <!-- 底部导航栏 -->
      <img  style='height:68px;position:absolute;cursor:pointer;border:1px solid #ccc;;' id="link_Left" src="image/map/linkleft_on.png"> 
    <div class="linkline" id="linklineid">
		<div class="linkline_img" id="mainguid_tpbus" style="background:#2abb5d;behavior: url(ie-css3.htc);">
			<img src="image/link_icon/icon_gjjc.png"> <label>公交监测</label>
		</div>
		<div class="linkline_img" id="mainguid_tpdanger" style="background:#26aa55;">
			<img src="image/link_icon/icon_lkyw.png" style="margin-left:15px;"> <label style="margin-left:8px;">两客一危监测</label>
		</div>
		<div class="linkline_img" id="mainguid_tptaxi" style="background:#239b4d;">
			<img src="image/link_icon/icon_czcjc.png" > <label style="margin-left:10px;">出租车监测</label>
		</div>
		<div class="linkline_img" id="mainguid_tpvideo" style="background:#208c46;">
			<img src="image/link_icon/icon_spjk.png"> <label>视频监控</label>
		</div>
		<div class="linkline_img"  id="mainguid_tpanalyse" style="background:#1d8140;
		-moz-border-radius:0 4px 4px 0 ;-webkit-border-radius:0 4px 4px 0 ;
		border-radius:0 4px 4px 0;behavior: url(ie-css3.htc);">
			<img src="image/link_icon/icon_hyfx.png"> <label>行业分析</label>
		</div>
	</div>
    
    <!--  车辆信息 需要定位-->
   
   <!--  <div class="carinfodiv" id="cardinfo">
      <div class="carinfodiv_title">车辆信息</div>
      <div class="carinfodiv_close">关闭</div>
      <div class="carinfodiv_left">车辆车牌号&nbsp;：</div>
      <input class="carinfodiv_right" type="text" value="浙F12345" />
      <div class="carinfodiv_left">当前车速&nbsp;：</div>
      <input class="carinfodiv_right" type="text" value="35.8Km/h" />
       <div class="carinfodiv_left">所属路线&nbsp;：</div>
      <input class="carinfodiv_right" type="text" value="85路" />
       <div class="carinfodiv_left">所处站点&nbsp;：</div>
      <input class="carinfodiv_right" type="text" value="红星路聚贤路" />
       <div class="carinfodiv_left">运行状态&nbsp;：</div>
      <input class="carinfodiv_right" type="text" value="车辆偏离轨迹" />
    </div>
     <img class="ic_rightdiv" src="image/system/popover_arrow.png"/> -->
     
     
   <!-- 出租车统计信息div -->
      <div class="runstate" id="taxi_runstate">
      <div class="tip_top_style3" style="width:160px\9;">
			<div class="tip_top_textleft">运行状况</div>
		</div>
   
	<table class="tip_content_style3" id="runstateTb" cellpadding="2px" cellspacing="0">
		<col width="60%" />
		<col width="40%" />
	
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">在线车辆数</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" id="onlineTaxinum">
   <!--  <input style="border:0" readOnly="readonly" id="onlineTaxinum" value=""> -->
    </td>
	<td class="tip_line1_right" >辆</td>
	</tr>	
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">总车辆数</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" id="allTaxinum">
   <!--  <input style="border:0" readOnly="readonly" id="allTaxinum" value=""> -->
    </td>
	<td class="tip_line1_right" >辆</td>
	</tr>		
 <tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">当前均速</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" id="avgSpeed" >
	<!--  <input style="border:0" readOnly="readonly" id="avgSpeed" value=""> -->
	</td>
	<td class="tip_line1_right" >Km/h</td>
	</tr>	
	</table> 
   </div> 
   </body>
   </html>