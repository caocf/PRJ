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
	<script type="text/javascript" src="js/main/trafficRoad.js"></script>
  </head>
  
   <body  style="overflow:auto;">
      <form name='form1'  target='_blank' action='http://172.20.24.48:7103/sso/login?service=http%3A%2F%2F172.20.24.48%2Flogin%2Flogin%21main.action' method='POST'>  
		<input type='hidden' name='username' value='admin'/>  
		<input type='hidden' name='password' value='123'/>  	 
	</form>  
	
	<div class="common_c0">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1">
			<div>
				<img class="top_u2" id="trafficguid_back_zh"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="trfffic_top">公路流量监测</span>
			</div>
		</div>
		<div id="traffic_map_1" class="traffic_map_back">
			<div id="traffic_map" class="traffic_map"></div>
		</div>
	</div>

	
    <img  style='height:41px;position:absolute;cursor:pointer;border:1px solid #ccc;top:80px;left:30px;' id="link_Left_other" src="image/map/linkleft_on.png"> 
<div class="travelcount" style="width:550px;display:none;" id="tjsj">
		<div class="tip_top_style3" style="background:#3982de;border: 1px solid #3982de;width:550px;border-radius:0;">
			<div class="tip_top_textleft" id="traffic_block" style="text-align:left;padding-left:20px;">交通量统计</div>
		</div>
		<div class="tip_content_styleroad" style="width:550px;" >
		<select class="travelcount_title" id="jtllxmc" style="width:550px;padding-left:20px;"> 
		</select>
		<div style="width:100%;height:260px;overflow-y:auto;">
		<table id="travelcountTb"  class="roadlistTb"
			cellpadding="4px" cellspacing="0">
			<tr><th width="30%" >统计名称</th>
			<th class="ytjsj">年统计</th>
			<th class="mtjsj">月统计</th>
			<th class="dtjsj">日统计</th>
			</tr>
			<tr class="tip_road_line">
				<td class="tip_road_left" >机动车当量数合计:</td>
				<td class="tip_road_right" id="jdcdl_y"></td>
				<td class="tip_road_right" id="jdcdl_m"></td>
				<td class="tip_road_right" id="jdcdl_d"></td>
			</tr>
            <tr class="tip_road_line">
				<td class="tip_road_left" >汽车当量数合计:</td>
				<td class="tip_road_right" id="qcdl_y" ></td>
				<td class="tip_road_right" id="qcdl_m" ></td>
				<td class="tip_road_right" id="qcdl_d" ></td>
			</tr>
			<tr class="tip_road_line">
				<td class="tip_road_left" >大型货车:</td>
				<td class="tip_road_right" id="dxhc_y" ></td>
				<td class="tip_road_right" id="dxhc_m" ></td>
				<td class="tip_road_right" id="dxhc_d" ></td>
			</tr>
			<tr class="tip_road_line">
				<td class="tip_road_left" >行驶量(万车公里):</td>
				<td class="tip_road_right"id="xsl_y" ></td>
				<td class="tip_road_right"id="xsl_m" ></td>
				<td class="tip_road_right"id="xsl_d" ></td>
			</tr>
			<tr>
				<td class="tip_road_left" >适应交通量(辆/日):</td>
				<td class="tip_road_right" id="syjtl_d" colspan='3'
				style='text-align:center;'/></td>	
			</tr>
			
			<tr class="tip_road_line">
				<td class="tip_road_left">拥挤度:</td>
				<td class="tip_road_right" id="yjd_y" /></td>	
				<td class="tip_road_right" id="yjd_m" /></td>	
				<td class="tip_road_right" id="yjd_d" /></td>	
			</tr>
		</table>
		</div>
		</div>
	</div>
<!--   <div class="stateguid"  id="taxi_stateguid" style="right:50px;width:430px;">
   <span>非常畅通</span><div class="normal_colorshow" style='background:green;'></div> <div class="stateguid_split" ></div>
   <span>畅通</span><div class="leaveoff_colorshow" style='background:blue;'></div><div class="stateguid_split"></div>
   <span>一般</span><div class="fault_colorshow" style='background:yellow;'></div><div class="stateguid_split"></div>
    <span>拥挤</span><div class="fault_colorshow" style='background:orange;'></div><div class="stateguid_split"></div>
    <span>拥挤</span><div class="fault_colorshow" style='background:red;'></div>
   </div> -->
   <div class="stateguid_yjd"  id="taxi_stateguid" style='right:50px;top:480px;'>
      <div class="normal_colorshow" style='background:green;'></div><div class="stateguid_right" style="margin-left:5px;margin-top:8px;">非常畅通</div>
      <div class="normal_colorshow" style='background:blue;'></div><div class="stateguid_right" style="margin-left:5px;margin-top:8px;">畅通</div>
       <div class="normal_colorshow" style='background:yellow;'></div><div class="stateguid_right" style="margin-left:5px;margin-top:8px;">一般</div>
       <div class="normal_colorshow" style='background:orange;'></div><div class="stateguid_right" style="margin-left:5px;margin-top:8px;">拥挤</div>
       <div class="normal_colorshow" style='background:red;'></div><div class="stateguid_right" style="margin-left:5px;margin-top:8px;">堵塞</div>
   </div>
   <div class="stateguid_yjd"  id="gsd_stateguid" style="top:650px;height:70px;width:130px;right:50px;">
   
   <div style="height:20px;margin-top:5px;margin-left:5px;">
   <div style='float:left;'>
   <img   src='image/mapicon/ic_map_traffic_gd.png' style="width:20px;heigt:28px;"/></div>
 
  <div class="stateguid_right" style="margin-left:5px;height:20px;line-height:20px;width:100px;">交调站(国道)</div> 
   </div>
   <div style="height:20px;margin-top:10px;clear:both;margin-left:5px;">
   <div style='float:left;'>
   <img   src='image/mapicon/ic_map_traffic_sd.png' style="width:20px;heigt:28px;"/></div>
 
  <div class="stateguid_right" style="margin-left:5px;height:20px;line-height:20px;width:100px;">交调站(省道)</div> 
   </div>
   </div>
     <!-- 右部浮动层 -->
  <!--   <div class="degreeguid">
    <span>拥堵</span><img src="image/main/guid_color.png"/><span>畅通</span>
    <img  src="image/main/icon_nodata.png"><span>无数据</span>
    </div> -->
   
   
     <!-- 区域选择 -->
<!--      <select class="regionselect">
     <option value="请选择">请选择</option>        
      <option value="1" selected="selected">南湖区  </option> 
      <option value="2" selected="selected">秀洲区  </option> 
      </select> -->





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
     
     
   
   </body>
   </html>