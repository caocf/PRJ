     <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入长途客运信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" href="<%=basePath%>WebPage/css/taxiInformation/taxiInformation.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/taxiInformation/taxiOverview.css" type="text/css"></link>
 <script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>WebPage/js/taxi/taxiInfor.js"></script>
 <script type="text/javascript" src="WebPage/js/common/Operation.js"></script>


</head>
   <body>
   	<input type="hidden" value="<%=basePath%>" id="basePath"/>
  	<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,1"/> 
		</jsp:include>
		
	<%-- 界面内容 --%>
	<div id="main">
	<div class="top">
	<div class="top_1"><a class="top_select" onclick="SelectItem_top(this,'WebPage/page/LongTansport/LongDistancePassengerTansport.jsp')">客运概况</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/LongTansport/lineInfo.jsp')">班线查询</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/LongTansport/ticket.jsp')">余票查询</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/LongTansport/PassengerStation.jsp')">客运枢纽</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/LongTansport/agentInfo.jsp')">代售点</a></div>
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/LongTansport/arriveInfo.jsp')">到站信息查询</a></div>
	 </div>
    <div class="page_dowm">
    <div class="middle">
    <div class="main_left">
    <div class="main_left_one"><a class="main_left_select" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_JX.jsp')" >嘉兴市</a></div>
    <div class="main_left_2" ><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_JS.jsp')">嘉善</a></div>
    <div class="main_left_2"><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_TX.jsp')">桐乡</a></div>
    <div class="main_left_2"><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_PH.jsp')">平湖</a></div>
    <div class="main_left_2"><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_HN.jsp')">海宁</a></div>
    <div class="main_left_2"><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/PassengerSurvey_HY.jsp')">海盐</a></div>
    </div>
    <div class="main_right" >
     <iframe class="right" src="WebPage/page/LongTansport/PassengerSurvey_JX.jsp" width="800px" height="100%" scrolling="no" frameborder="0""></iframe>
    </div>
   </div>
     </div>
   </div>
   <%-- 界面内容 --%>
	<jsp:include page="../../../WebPage/page/main/foot.jsp" />

</div>
  </body>
</html>
