<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校报名</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	
	
	
  </head>
  
  <body>
    <jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
	<jsp:param name="menu_number" value="4,4"/> 
		</jsp:include>
  
   <div id="content1" style="margin-top:27px;">
     <div class="select_top"><p>账号：</p>
      <input class="select_text" type="text" value="请输入身份证号或报名时登记的手机号码" onBlur="TextBlur(this)" onFocus="TextFocus(this)"/>
     <p style="margin-left: 30px">密码：</p>
      <input class="select_text" type="text" value="请输入密码；初始密码为身份证后六位" onBlur="TextBlur(this)" onFocus="TextFocus(this)"/>
      <a class="select_buttom"> 查询 </a>
     </div>
     <div class="clear"></div>
     <div class="select_bot"> 
       <p style="margin-left: 12px">教学内容</p><p style="margin-left: 490px">学时</p><p style="margin-left: 155px">学习时间</p><p style="margin-left: 140px">状态</p>
     </div>
   </div>
   <jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
