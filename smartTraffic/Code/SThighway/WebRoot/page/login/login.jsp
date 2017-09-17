<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <body>
  <head>
    <base href="<%=basePath%>">
    
    <title>登陆页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login/login.css">	
	<script src="<%=basePath%>js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/login/login.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/common/cookie.js"></script>	
  </head>

<div class="mainall">
  <div class="loginstyle">
<input type="hidden" value="<%=basePath%>" id="basePath"/>
 <div class="logintop">
  <div class="p1">
 <%--  <div style="float:left">
  <img  src="<%=basePath%>image/login/logo1.png" /> </div> --%>
  <div style="float:left;margin-top:9px;margin-left:5px;">
  <img src="<%=basePath%>image/login/logo2.png"/></div>
  <div style="height:65px;inline-height:65px;margin-left:15px;float:left;" > 
  <label>嘉兴公路综合数据交换平台</label></div></div>
  </div> 
<div class="logindiv" id="loimg">
   <div class="logsign">
             用户登录 <label>Sign In</label>
   </div>
   <div style="display:none;height:30px;line-height:30px;clear:both;text-align:left;margin-left:180px;*margin-left:0;*padding-left:180px;" id="errordiv" >
     <input type="text" id="errormessage"/>
   </div>
   <div class="logininput">
   <div class="logininput_image">
   <img  src="<%=basePath%>image/login/user.png" style="margin-top:5px;"></div>
   <input type="text"  id="username" value="请输入用户名" onkeydown="EnterPress(event)"/>
   </div>
   <div class="diatanctdiv"></div>
  <div class="logininput">
  <div class="logininput_image">
   <img  src="<%=basePath%>image/login/key.png" style="margin-top:4px;height:32px;" ></div>
   <input type="text"  id="showpassword" value="请输入密码"/>
   <input type="password" name="password" style="display:none;color:black;" value="" id="password"
  onkeydown="EnterPress(event)"/>  
   </div>
 
    <div class="diatanctdiv"></div>
   <div class="logincheck">
   <input type="checkbox" id="isremember" /> 
   <label >记住密码</label>
   </div>
    <div class="diatanctdiv"></div>
   <div class="loginbutton">
   <button id="login" name="login" onclick="usersubmit()">登录</button>
   
   </div>
   <div class="diatantdiv2"></div>
  </div>   
  </div>
     <div class="footerdiv" id="footer">
     <br><label onclick="frontdownLoad()" style="cursor:pointer;">为了更好的体验效果,单击此处下载微软雅黑字体</label>
		 <div class="versiondiv" id="footer" >
          <label>浙江省嘉兴市公路管理局   版权所有 &nbsp;&nbsp;&nbsp;<span id="version"></span></label> 
          
          </div>
          <div class="footerblack"></div>
        </div> 
        </div>
<!--    <div id="footerdiv">
   <label >浙江省嘉兴市公路管理局  版权所有  地址：嘉兴市经济开发区开禧路46号  版本号<span id="version"></span></label>

   </div>  -->
  </body>
</html>
