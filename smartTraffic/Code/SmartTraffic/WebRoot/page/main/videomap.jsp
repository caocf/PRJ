<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
<base href="<%=basePath%>">

<title>视频监控</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<link rel="stylesheet" type="text/css" href="css/system/top.css">
<link rel="stylesheet" type="text/css" href="css/system/style.css">
<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
<link rel="stylesheet" type="text/css" href="css/main/videomap.css">


<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/map/Mapbarurl.js"></script>
<script type="text/javascript" src="js/system/top.js"></script>
<script type="text/javascript" src="js/system/link.js"></script>
<script type="text/javascript" src="<%=basePath%>js/map/videomap.js"></script>
</head>
<body style="overflow-x:hidden;overflow-y:hidden;"><!-- position:relative; -->

  
  <div id="fullbg" style="width:100%;height:100%;"></div>
   <div class="loadingDiv">
		<img  src="image/system/loading.gif">
	</div>

<div class="highway_out" id="video_out">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1">
			<div>
				<img class="top_u2" id="videoguid_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="top_text">视频监控</span>
			</div>
		
	
	</div>
	 <!-- 左侧栏 -->
	<div class="highway_left" id="video_left_info">
	 <div class="video_left" id="video_left" >
	 </div>
	</div>
	<!-- 树形菜单 -->
 <div class="image_size" onclick="showOrhideleft()">
<img class="video_image" id="arrowleft_" alt="" src="image/map/arrowleft_normal.png" >
</div> 
<div class="sizeguid">
		<div id="zoomLarge" style="cursor: pointer;">
			<img class="sizeguid_img1" src="image/main/icon_add.png" />
		</div>
		<div class="sizeguid_line"></div>
		<div id="zoomSmall" style="cursor: pointer;">
			<img class="sizeguid_img2" src="image/main/icon_reduce.png" />
		</div>
	</div>
<div class="highway_right" id="video_map_1">
<div class="highway_right_map" id="video_map">


</div>
</div>
</div>
<div class="stateguid"  id="taxi_stateguid" >
   <span>公管视频</span><div class="gl_colorshow" ></div> <div class="stateguid_split"></div>
   <span>港航视频</span><div class="gh_colorshow"></div><div class="stateguid_split"></div>
   <span>运管视频</span><div class="yg_colorshow" ></div><div class="stateguid_split"></div>
   <span>港务视频</span><div class="gw_colorshow" ></div>
   </div>
</body>
	</html>
	