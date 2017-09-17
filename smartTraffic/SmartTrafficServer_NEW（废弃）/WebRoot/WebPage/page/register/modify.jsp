<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-修改密码</title>
   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/register/style.css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	<script type="text/javascript" src="WebPage/js/register/ChangePwd.js"></script>
  </head>
  
  <body>
  <jsp:include page="../../../WebPage/page/main/top.jsp" />
  
    <div id="content1" style="background-image: url('WebPage/image/register/login_bg.png')"><p class="cont_wz">修改密码</p>
        <div class="mod_mima">
          <div class="mod_mima_li" >原密码&nbsp;<input type="password" class="text" style="font-size: 15px;width:335px;"
               id="oldPwd"/><label id="oldPwderr"  style="font-size:12px;font-weight: normal;color: red;margin-left: 10px; "></label>
          </div>
          <div class="mod_mima_li" style="margin-top:20px;">新密码&nbsp;<input type="password" class="text" style="font-size: 15px;width:335px;"   id="newPwd"/>
          <label id="newPwderr"  style="font-size:12px;font-weight: normal;color: red;"></label></div>
          <div class="mod_mima_li" style="margin:20px auto auto -135px;width:700px;">确认密码&nbsp;<input type="password" class="text" style="font-size: 15px;width:335px;"  id="newPwd2"/>
           <label id="newPwd2err" style="font-size:12px;font-weight: normal;color: red; "></label></div>
          <div class="mod_mima_li" style="margin-top:30px;">
          <img src="WebPage/image/register/submit.png" class="text2" style="cursor:hand;width:350px;margin-left:60px;" onClick="SavePwd()" />
          </div>
        
        </div>
    </div>
    
     <jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
