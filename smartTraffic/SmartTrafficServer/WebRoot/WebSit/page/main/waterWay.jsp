<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-水路交通</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	

 <link rel="stylesheet" href="<%=basePath%>WebSit/css/trafficNews/trafficNews.css" type="text/css"></link> 
<link rel="stylesheet" href="<%=basePath%>WebSit/css/taxiInformation/taxiInformation.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebSit/css/main/add_2.css" type="text/css"></link>

<script type="text/javascript" src="<%=basePath%>WebSit/js/XMap/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
<script type="text/javascript" src="<%=basePath%>WebSit/js/taxi/taxiInfor.js"></script>


</head>
   <body>
  	<div id="page_content">
		<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true" >
			<jsp:param name="menu_number" value="5"/> 
		</jsp:include>
		
		
		
	<%-- 界面内容 --%>
	<div id="main">
	<div class="top">
	<div class="top_1"><a class="top_select" onclick="SelectItem_top1(this,'WebSit/page/main/TrafficPerformanceIndex.jsp')">交通指数</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top1(this,'WebSit/page/main/TrafficInformation.jsp')">路况信息</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top1(this,'WebSit/page/main/RoadVideo.jsp')">道路视频</a></div>
	<!-- <div class="top_2"><a class="top_select_no" onclick="SelectItem_top(this,'#')">预约拼车电招</a></div>
	 --></div>
     <div class="page_dowm">
     <iframe class="down" src="WebSit/page/main/TrafficPerformanceIndex.jsp" width="1000px" height="100%" scrolling="no" frameborder="0"></iframe>
     </div>
   </div>
   <%-- 界面内容 --%>
	<jsp:include page="../../../WebSit/page/main/foot.jsp" />

</div>
  </body>
</html>



