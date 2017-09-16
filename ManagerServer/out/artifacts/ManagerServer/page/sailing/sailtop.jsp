<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>湖州港航公众服务系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="css/sailing/sailtop.css" />
	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/sailing/sailtop.js"></script>
  </head>
  
  <body>
  	<div id="fullbg"></div>
  	<div id="top">
 		<div id="top1">
 			<input type="hidden" value="<%=(String)session.getAttribute("pUserName") %>" id="shipusername">
 			<input type="hidden" value="<%=session.getAttribute("pUserId") %>" id="userId">
 			<input type="hidden" value="<%=(String)session.getAttribute("shipName") %>" id="shipName">
 			<input type="hidden" value="<%=basePath%>" id="basePath"/> 
	  		<div id="top2">
	 			<img src="<%=basePath%>image/top/sailtop_icon.png" id="iconImg"/>
	 		</div>
			 <div id="top3">
			 	<label><img src="image/top/user.png"/><font id="top_user"><%=(String)session.getAttribute("pUserName")%></font></label>|<a onclick="exit()" class="btExit"/>退出</a>|<a onclick="downloadApp()" id="downloadApp">APP下载</a>
			 </div>
 		</div>
 		
 		<div><img src="image/top/topline.png" width="100%"></div>
	</div>
	
  </body>
</html>
