<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cameraId = request.getParameter("cameraId");
String cameraName = request.getParameter("cameraName");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>综合交通</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=8"></meta>
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<link rel="stylesheet" type="text/css" href="css/system/top.css">
	<link rel="stylesheet" type="text/css" href="css/system/style.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">
		<link rel="stylesheet" type="text/css" href="css/map/map.css">
	<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/videomap.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
<!-- 	<script type="text/javascript" src="js/map/Mapbarurl.js"></script> -->
	<script type="text/javascript" src="js/system/top.js"></script>
	<script type="text/javascript" src="js/system/link.js"></script>
	<script type="text/javascript" src="js/map/videoplay.js"></script>
  </head>
<script type="text/javascript">

</script>


</HEAD>

<body style="overflow-x:hidden;overflow-y:hidden">
<div class="highway_out">
<input type="hidden" value="<%=cameraId%>" id="cameraId">
<input type="hidden" value="<%=cameraName%>" id="cameraName">
<input type="hidden" value="<%=basePath%>" id="basePath" />
<div class="top_u1">
			<div>
				<img class="top_u2" id="videoguid_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="top_text">视频监控</span>
					<a href="downloadexe/setup.exe" style="height:64px;line-height:64px;color:white;
					float:right;padding-right:100px;">控件下载</a>
					
			</div>
</div>
<div id="video_play" style="height:100%;">
<OBJECT ID="DemoCtrl"  name= "devread" WIDTH=100% HEIGHT=100% classid="CLSID:D74575FC-EE89-4b05-8851-1A0C417038B9" 
    codebase="/downloadexe/setup.exe">
    <PARAM NAME="_ExtentX" VALUE="12806">
    <PARAM NAME="_ExtentY" VALUE="1747">
    <PARAM NAME="_StockProps" VALUE="0">
    <div class="download-control" id="loadDiv" >
       <span>请<a href="downloadexe/setup.exe">下载</a>并安装控件</span>
					</div>
</OBJECT>
</div>
</div>
</body>
</HTML>