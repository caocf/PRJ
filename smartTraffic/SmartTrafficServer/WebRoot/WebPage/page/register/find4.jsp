<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-找回密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/register/style.css"/>
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	<script type="text/javascript" src="WebPage/js/register/FindPwd4.js"></script>

  </head>
  
  <body>
  <jsp:include page="../../../WebPage/page/main/top.jsp" />
  
     <div id="content1" style="background-image:url('WebPage/image/register/find4.png');"><p class="cont_wz">找回密码</p>
        <div class="find4_cont"><img style="float:left;" src="WebPage/image/register/res.png"/><p style="float:left;margin-top:2px;">&nbsp;成功找回密码，<label id="seconds">5</label>秒后将返回首页&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: #2a44a1;" href="WebPage/page/firstpage/FirstPage.jsp">立即返回&raquo;</a></p>
        <div class="clear"></div>
     </div>
</div>   
  
      <jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
