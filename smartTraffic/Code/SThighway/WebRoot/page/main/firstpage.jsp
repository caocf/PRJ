<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公路概况</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/main/firstpage.js"></script>
	

  </head>
  
  <body>
    <div class="common_c0" style="min-height:782px;">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
			<div class="common_c2" id="route_info">
				<div class="route_intro">
					<div class="route_title">嘉兴市公路概况</div>
					<div class="route_left"><div class="left_text" id="left_text"></div>
					
					<div></div><div class="left_image" style="margin-top:30px;"><img src="image/main/route_left.png" /></div></div>
					<div class="route_right"><div class="left_image"><img src="image/main/route_right.png" /></div><div class="right_text" id="right_text"></div>
						
						<div></div></div>
				</div>
			</div>
    </div>
  </body>
</html>
