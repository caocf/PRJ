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
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/video/VideoMonitor.css">
	
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/videomonitor/webVideoCtrl.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/videomonitor/VideoMonitor.js"></script>
	

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/top.jsp" flush="true" />
	    	<div style="width:100%;height:798px;">
		    	<div class="common_c2">
		    			<div id="videolist" style="width:300px;float:left;height:798px;">
		    				<div id="department_left_select_child2" class="department_left_select_child">
							</div>
		    			</div>
		    			<div class="plugin">
		    				<div class="stop_button"><button class="video_button_stop" onclick="stopVideo()">停止预览</button>&nbsp;&nbsp;&nbsp;<a id="downLoad" onclick="downLoad()">点击下载插件</a></div>
		    				<div id="divPlugin"></div>
		    			</div>
				</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
  </body>
</html>
