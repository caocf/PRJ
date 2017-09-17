<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>智慧港航</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<link rel="stylesheet" type="text/css" href="css/system/top.css">
<link rel="stylesheet" type="text/css" href="css/system/style.css">
<link rel="stylesheet" type="text/css" href="css/main/port.css">
<link rel="stylesheet" type="text/css" href="css/map/common.css">

<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
<script type="text/javascript" src="js/system/top.js"></script>
<script type="text/javascript" src="js/system/link.js"></script>
<script type="text/javascript" src="js/main/port.js"></script>
</head>
<body style="overflow-x:hidden;overflow-y:hidden">
<form name='form1' action='http://172.20.24.48:7103/sso/login?service=http%3A%2F%2F172.20.24.48%2Flogin%2Flogin%21main.action' method='POST'>  
		<input type='hidden' name='username' value='admin'/>  
		<input type='hidden' name='password' value='123'/>  	 
	</form>  


	<div class="common_c0">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1_u4">
			<div>
				<img class="top_u2" id="portguid_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="top_text">智慧港航</span>
			</div>
		</div>
		<div id="port_map_1" class="traffic_map_back">
			<div id="port_map" class="traffic_map"></div>
		</div>
	</div>
	 <!-- 左侧浮动层 -->
   <div class="serach">
   <input class="search_input" type="text" id="trafficsearch" />
   <div class="serach_right">搜索</div>
   </div>
   
   <!-- 运行状况 -->
   <div class="runstate">
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
	
	<div class="stateguid">
   <span>正常</span><div class="normal_colorshow" ></div> <div class="stateguid_split"></div>
   <span>危险品</span><div class="danger_colorshow"></div><div class="stateguid_split"></div>
   <span>违章</span><div class="unlaw_colorshow" ></div>
   </div>
	
	  <!-- 区域选择 -->
     <select class="regionselect">
     <option value="请选择">请选择</option>        
      <option value="1" selected="selected">南湖区  </option> 
      <option value="2" selected="selected">秀洲区  </option> 
      </select>
	
	
	    
    <div class="sizeguid">
    <div id="zoomLarge" style="cursor: pointer;">
    <img class="sizeguid_img1" src="image/main/icon_add.png" /></div>
    <div class="sizeguid_line"></div>
    <div id="zoomSmall" style="cursor: pointer;">
    <img class="sizeguid_img2" src="image/main/icon_reduce.png" /></div>
    </div>
	
	
	  <!-- 底部导航栏 -->
 <img  style='height:68px;position:absolute;cursor:pointer;border:1px solid #ccc;' id="link_Left" src="image/map/linkleft_on.png"> 
    <div class="linkline" id="linklineid" >
		<div class="linkline_img" id="mainguid_pboat" style="background:#2a8eff;behavior: url(ie-css3.htc);">
			<img src="image/link_icon/icon_zhgh.png">
			<label style="margin-left:8px;">船舶动态监控</label>
		</div>
		<div class="linkline_img" id="mainguid_pvideo" style="background:#257ee3;">
			<img src="image/link_icon/icon_spjk.png">
			<label>视频监控</label>
		</div>
		
		<div class="linkline_img" id="mainguid_pchannel" style="background:#2170c8;">
		<a onclick='document.form1.submit();' target="_blank">
			<img src="image/link_icon/icon_hdjc.png">
			<label>航道监测</label>
			</a>
		</div>
		<div class="linkline_img" id="mainguid_panalyse" style="background:#1e65b5; 
		-moz-border-radius:0 4px 4px 0 ;-webkit-border-radius:0 4px 4px 0 ;
		border-radius:0 4px 4px 0;behavior: url(ie-css3.htc);">
			<img src="image/link_icon/icon_hyfx.png">
			<label>行业分析</label>
		</div>
	</div>
    
	
	
	
</body>
</html>
