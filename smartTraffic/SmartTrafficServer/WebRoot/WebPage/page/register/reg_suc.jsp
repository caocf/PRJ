<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String phone="";
if(session.getAttribute("register_temp_info_phone")!=null){
phone=session.getAttribute("register_temp_info_phone").toString();
}
if(phone!=""){
StringBuffer str=new StringBuffer(phone);
phone=str.replace(3, 10, "***").toString();
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-注册成功</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" type="text/css" href="WebPage/css/register/style.css"/>
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	

  </head>
  
  <body>
  <jsp:include page="../../../WebPage/page/main/top.jsp" />
  
   <div id="content1" style="background-image: url('WebPage/image/register/login_bg.png')"><p class="cont_wz">欢迎注册</p>
      <div class="reg_suc_cont">
         <div class="reg_suc_cont_top"><img style="vertical-align: middle;" src="WebPage/image/register/res.png"/>&nbsp;恭喜，<%=phone %>已注册成功。
             
         </div>
         <!--，请激活你的账号<div class="reg_suc_cont_top_bottom">
            <div class="reg_suc_cont_top_bottom_p" style="margin-left:45px">系统已向你的邮箱 <a style="color: #ed9608;font-weight:bold;font-size: 14px;font-family: arial;" href="">hexingtong@163.com</a>
                                                                                                                         发送了一封验证邮件，请登录您的邮箱，点击邮件中的链接<br>完成邮箱验证，激活账号。如果您超过3分钟没有接到邮件，您可以<a style="color:#2746a0;font-weight: bold;" href="">重新发送</a>
            </div>
            <div><a href="WebPage/page/register/login.jsp"><input type="submit" class="text3" style="cursor:pointer;" value="登录邮箱"/></a>
            </div>
         </div>-->
      </div>
   
   </div>
   
    <jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
