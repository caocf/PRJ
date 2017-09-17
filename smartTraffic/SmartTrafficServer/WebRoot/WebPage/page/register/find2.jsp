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
    
    <title>智慧出行-找回密码</title>
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
	<script type="text/javascript" src="WebPage/js/register/FindPwd2.js"></script>
	<style type="text/css">
	.sendMsgBt1 {
		height: 40px;
		text-align: center;
		background-color: #f3f3f3;
		border: 0px;
		outline: none;
		vertical-align: middle;
		font-size: 12px;
		font-weight: bold;
		color: #a3a3a3;
		width: 115px;
	}
	
	.sendMsgBt2 {
		height: 40px;
		text-align: center;
		background-color: #f3f3f3;
		border: 0px;
		outline: none;
		vertical-align: middle;
		font-size: 12px;
		font-weight: bold;
		color: #a3a3a3;
		width: 115px;
		border: solid 1px #a3a3a3;
		cursor:pointer;
	}
	
	.sendMsgBt2:HOVER {
		background-color: #a3a3a3;
		color: white;
	}
</style>
  </head>
  
  <body>
  <jsp:include page="../../../WebPage/page/main/top.jsp" />
  <input type="hidden" value="<%=session.getAttribute("register_temp_info_phone") %>" id="phone"/>
     <div id="content1" style="background-image: url('WebPage/image/register/find2.png')"><p class="cont_wz">找回密码</p>
        <div class="find2_cont">
           <div style="height:25px; font-size: 14px;line-height: 25px;font-weight:bold;color: #333333;">系统已经发送一条短信到您的绑定手机<label><%=phone %></label>,请输入短信的验证码</div>
           <div style="font-size: 14px;font-weight: bold;margin:35px auto auto 35px;">验证码
           <input type="text" class="text" value="验证码" id="code" style="width:225px;" onBlur="TextBlur(this)" onFocus="TextFocus(this)"/>
            <input type="button" id="SendMessageAgain"  class="sendMsgBt1" value="60秒后重新发送" />
            <img src="WebPage/image/register/next23.png" onClick="ChackViricodeForWeb()" style="cursor:pointer;width:350px;margin:30px auto auto 52px;display: block;outline: none;" />
           </div><label style="display:none" id="codeerr"></label>
        </div>
     </div>
     
      <jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
