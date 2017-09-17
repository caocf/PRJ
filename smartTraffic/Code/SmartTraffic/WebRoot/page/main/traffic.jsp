<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>综合交通</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<link rel="stylesheet" type="text/css" href="css/system/top.css">
	<link rel="stylesheet" type="text/css" href="css/system/style.css">
	<link rel="stylesheet" type="text/css" href="css/main/traffic.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">
	<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
	<script type="text/javascript" src="js/system/top.js"></script>
	<script type="text/javascript" src="js/system/link.js"></script>
	<script type="text/javascript" src="js/main/traffic.js"></script>
  </head>
  
   <body  style="overflow-x:hidden;overflow-y:hidden">
   <form name='form1'  target='_blank' action='http://172.20.24.48/sso/login?service=http%3A%2F%2F172.20.24.48%2Flogin%2Flogin%21main.action' method='POST'>  
		<input type='hidden' name='username' value='admin'/>  
		<input type='hidden' name='password' value='123'/>  	 
	</form>  
   <div id="fullbg"></div>
   <div class="loadingDiv">
		<img  src="image/system/loading.gif">
	</div>
	
	<div class="common_c0">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1">
			<div>
				<img class="top_u2" id="trafficguid_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="trfffic_top">综合交通</span>
			</div>
		</div>
		<div id="traffic_map_1" class="traffic_map_back">
			<div id="traffic_map" class="traffic_map"></div>
		</div>
	</div>

	<!-- 左侧浮动层 -->
   <div class="serach" id="taxi_search" style="display: none;">
   <input class="search_input" type="text" id="trafficsearch" />
   <div class="serach_right">搜索</div>
   </div>
   
   <!-- 运行状况 -->
   <div class="runstate" id="runstate" style="display: none;">
      <div class="tip_top">
			<div class="tip_top_textleft">运行状况</div>
		</div>
   
	<table class="tip_content" id="runstateTb" cellpadding="2px" cellspacing="0">
		<col width="60%" />
		<col width="40%" />
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">总站点总数</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" >3134</td>
	<td class="tip_line1_right" >站</td>
	</tr>	
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">在线车辆数</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" >806</td>
	<td class="tip_line1_right" >辆</td>
	</tr>	
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">在线线路数</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" >106</td>
	<td class="tip_line1_right" >条</td>
	</tr>		
	<tr class="tip_line">
	<td colspan="2"  >
	<div class="tip_line1_title_sign"></div>
	<div class="tip_line1_title_text">当前均速</div>
	</td>
	</tr>
	<tr class="tip_bottom">
	<td class="runstate_line1_left" >18.21</td>
	<td class="tip_line1_right" >Km/h</td>
	</tr>	
	</table>
   </div> 
   
   <div class="stateguid"  id="taxi_stateguid" style="display: none;">
   <span>正常</span><div class="normal_colorshow" ></div> <div class="stateguid_split"></div>
   <span>偏离轨迹</span><div class="leaveoff_colorshow"></div><div class="stateguid_split"></div>
   <span>故障</span><div class="fault_colorshow" ></div>
   </div>
   
   <div class="cartypeguid" style="display: none;">
   <div class="cartypeguid_left" id="typeic_bus">
   <img  id="typeic_bus_img" style="margin-top:8px;" src="image/traffic/typeic_bus_normal.png"></div>
   <div class="cartypeguid_center"  id="typeic_car">
   <img  id="typeic_car_img"  style="margin-top:10px;" src="image/traffic/typeic_car_normal.png"></div>
   <div class="cartypeguid_right" id="typeic_ship">
   <img  id="typeic_ship_img" style="margin-top:8px;" src="image/traffic/typeic_ship_normal.png"></div>
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
	 <img  style='height:68px;position:absolute;cursor:pointer;border:1px solid #ccc;' id="link_Left2" src="image/map/linkleft_on.png"> 
      <!-- 运行监测导航栏 -->
     <div class="list" id="runlist">
     <div class="list_item" style="border-top:0px;" id="tra_roadlink">路网监测</div>
     <div class="list_item" id="tra_buslink">公交监测</div>
     <div class="list_item" id="tra_taxilink">出租车监测 </div>
     <div class="list_item" id="tra_shiplink">船舶监测</div>
     <div class="list_item" id="tra_shiprunlink"><a onclick='document.form1.submit();' target="_blank">船舶运行监测</a></div>
     </div>
       <!-- 运行监测导航栏 -->
     <div class="list" id="carrunlist"  style='background:#316cbd;'>
      <div class="list_item" style="border-top:0px;" id="tra_sciencelink">交通指数</div>
      <div class="list_item"  id="tra_carrunlink">行车诱导</div>
      
     </div>
    <div class="linkline" id="linklineid" >
     
			<div class="linkline_img" id="change_trun" style="background:#1f5aa7;behavior: url(ie-css3.htc);">
				<img src="image/link_icon/icon_yxjc.png"> <label>运行监测 </label>
			</div>
			<div class="linkline_img" id="mainguid_tvideo" style="background:#2a68b9;">
				<img src="image/link_icon/icon_spjk.png"> <label>视频监控</label>
			</div>
			<div class="linkline_img" id="mainguid_tscience" style="background:#3679ce;">
				<img src="image/link_icon/icon_kjzd.png"> <label>科技治堵</label>
			</div>
			<div class="linkline_img" id="mainguid_twalkservice" style="background:#3982de;">
				<img src="image/link_icon/icon_cxfw.png"> <label>出行服务</label>
			</div>
			<div class="linkline_img" id="mainguid_tlogistics" style="background:#4e97f3;">
				<img src="image/link_icon/icon_wlfw.png"> <label>物流服务</label>
			</div>
			<div class="linkline_img" id="mainguid_tanalyse" style="background:#65a9ff;
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
      <div class="runstate" id="taxi_runstate" style="display: none;">
      <div class="tip_top" style="width:160px\9;">
			<div class="tip_top_textleft">运行状况</div>
		</div>
   
	<table class="tip_content" id="runstateTb" cellpadding="2px" cellspacing="0">
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