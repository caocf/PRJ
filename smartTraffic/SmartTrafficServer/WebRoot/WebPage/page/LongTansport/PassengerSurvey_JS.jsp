<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入行业基本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/PassengerSuvey.css"/>
  </head>
  
  <body>
   <h4 style="color: rgb(92,92,92);">嘉善客运概况</h4>
   <hr style="color: rgb(192,202,229);">
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    沪杭城际高速铁路嘉善南站是沿线7个车站中距离中心城区最近的车站（3km），站址设在嘉善县大云镇江家村，按一般中间站设计，站台长450米，站场规模为2台4线，站房主体为1层，两侧局部为2层，总建筑面积为4998平方米，其中，建筑主体高度为23米，总长148米，总宽39米。   
   </p>
   <h4 style="color: rgb(92,92,92);">嘉善分布站简图</h4>
   <p align="center"><img style="height: 450px;" alt="" src="WebPage/image/LongTansport/PassengerSurvey_JS.png"/></p>
  </body>
</html>
