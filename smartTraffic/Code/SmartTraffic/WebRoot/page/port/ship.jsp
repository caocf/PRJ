<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>智慧港航--船舶动态监测</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/system/top.css">
<link rel="stylesheet" type="text/css" href="css/system/style.css">
<link rel="stylesheet" type="text/css" href="css/main/port.css">
<link rel="stylesheet" type="text/css" href="css/map/common.css">
<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">

<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
<script type="text/javascript" src="js/system/top.js"></script>
<script type="text/javascript" src="js/system/link.js"></script>
<script type="text/javascript" src="js/port/ship.js"></script>
</head>
<body  style="overflow-x:hidden;overflow-y:hidden">
<form name='form1' action='http://172.20.24.48:7103/sso/login?service=http%3A%2F%2F172.20.24.48%2Flogin%2Flogin%21main.action' method='POST'>  
		<input type='hidden' name='username' value='admin'/>  
		<input type='hidden' name='password' value='123'/>  	 
	</form> 
    <div id="fullbg"></div>
   <div class="loadingDiv">
		<img  src="image/system/loading.gif">
	</div>
	<div class="common_c0">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1_u4">
			<div>
				<img class="top_u2" id="portguid_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="top_text">智慧港航</span>
			</div>
		</div>
		<div id="ship_map_1" class="traffic_map_back">
			<div id="ship_map" class="traffic_map"></div>
		</div>
	</div>
	
	<div class="stateguid" style="display:none;">
   <span>正常</span><div class="normal_colorshow" ></div> <div class="stateguid_split"></div>
   <span>危险品</span><div class="danger_colorshow"></div><div class="stateguid_split"></div>
   <span>违章</span><div class="unlaw_colorshow" ></div>
   </div>
	

	
	
	    
    <div class="sizeguid" >
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
			<img src="image/link_icon/icon_zhgh.png"  style="margin-left:15px;">
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
