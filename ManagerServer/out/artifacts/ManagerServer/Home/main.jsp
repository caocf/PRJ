<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    
    <title>浙江港航综合管理平台</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/common/popup.css">
	<link rel="stylesheet" type="text/css" href="Home/main.css" />
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script src="js/common/jquery.popup.js"></script>
	<script type="text/javascript" src="Home/main.js"></script>
     
  </head>
  
  <body>
  <a href='' class='default_popup' style='color:red;display:none'></a>
  <input type="hidden" value='<%=basePath%>' id='basePath'/>

	<div id="mainview">
		<iframe id="mainTop" name="mainTop" src="<%=basePath%>Home/top.jsp" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
		<div id="maindown">
			<div id="content">
				<iframe id="contentframe" name="contentframe" src="<%=basePath%>Home/down.jsp" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
			</div>
   		</div>
   </div>
 
  </body>
</html>
