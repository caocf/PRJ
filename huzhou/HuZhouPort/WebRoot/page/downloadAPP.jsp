<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    
    <title>湖州港航综合管理平台</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/main/main.css" />
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/main/main.js"></script>
		<script type="text/javascript" src="js/main/download.js"></script>
		

     
  </head>
  
  <body>
  	<input type="hidden" value="<%=basePath%>" id="basePath"/>
	<div id="container">
		<div id="content">
			<div id="childContent">
				<div style="height:50px;width:100%;line-height: 50px;vertical-align: middle;text-align: center;
					font-size: 16px;font-weight: bold;">App下载</div>
				<div style="width:50%;float:left;height:50%;margin-top:80px;">
						<img style="margin-left:23%;" src="image/app/inner.png"/>
						<div style="width:100%;text-align: center;margin-top:15px;">
							<a style="text-decoration: underline;" class="AHOVER" onclick="innerDownload()">内部版App下载</a>
						</div>
				</div>
				<div style="width:50%;float:left;height:50%;margin-top:80px;">
					<img style="margin-left:23%;" src="image/app/public.png"/>
					<div style="width:100%;text-align: center;margin-top:15px;">
						<a style="text-decoration: underline;" class="AHOVER" onclick="publicDownload()">外部版App下载</a>
					</div>
				</div>			
			</div>	
	   </div>
     
   </div>
 
  </body>
</html>
