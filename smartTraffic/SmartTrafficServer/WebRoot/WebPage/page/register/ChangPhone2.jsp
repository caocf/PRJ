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
    
    <title>智慧出行-绑定手机</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/register/ChangPhone.css" />
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	<script type="text/javascript" src="WebPage/js/register/ChangPhone2.js"></script>
	
  </head>
  
  <body>
  	<div id="page_content">
	  <jsp:include page="../../../WebPage/page/main/top.jsp" />
	  <input type="hidden" value="<%=session.getAttribute("register_temp_info_phone") %>" id="phone"/>
	     <div id="content1"  style="background-image: url('WebPage/image/register/find2.png')"><p class="content_title">绑定手机</p>
	        <div class="find_content">
	           <div style="height:25px; font-size: 14px;line-height: 25px;font-weight:bold;color: #333333;text-align: center;">
	           		系统已经发送一条短信到您的绑定手机<label><%=phone %></label>,请输入短信的验证码</div>
	           <div style="font-size: 14px;font-weight: bold;margin:35px 0 0 280px;">验证码
	           <input type="text" class="find_text"  id="code" style="width:225px;" />
	            <input type="button" id="SendMessageAgain"  class="sendMsgBt1" value="60秒后重新发送" />
	            <img src="WebPage/image/register/next23.png" onClick="ChackViricodeForWeb()" style="cursor:pointer;width:350px;margin:30px 0 0 62px;display: block;outline: none;" />
	           </div><label style="display:none" id="codeerr"></label>
	        </div>
	     </div>
	     
	      <jsp:include page="../../../WebPage/page/main/foot.jsp" />
      </div>
  </body>
</html>
