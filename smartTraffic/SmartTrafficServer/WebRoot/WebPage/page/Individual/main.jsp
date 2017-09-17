<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-个人信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/main/foot.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/main/top.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/Individual/main.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>WebPage/css/common/style.css" type="text/css"></link></head>
  
  <body> 
  	<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top1.jsp" />
		
	<%-- 界面内容 --%>
	<div id="main">
	<div class="main_top">
     <img alt="" src="WebPage/image/person_top.png">
	</div>
	
    <div class="main_left">
    <img alt="" src="WebPage/image/person_info.png">
    </div>
    <div class="main_right">
    <div class="main_right_up">
     <table class="table">
     <tr height="45px">
     <td width="65px">用户名:</td>
     <td>多多</td>
     <td></td>
     </tr>
     <tr height="45px">
     <td width="65px">性&nbsp;&nbsp;别：</td>
     <td colspan="2"><label><input type="radio" name="男" value="1"/>男 <input type="radio" name="男" value="2"/>女</label></td>
 </tr>
     <tr height="45px">
     <td width="65px">密　码：</td>
     <td><a href="#" class="a">修改密码</a></td>
     <td></td>
     </tr >
     <tr height="45px">
     <td width="65px">所在地:</td>
     <td><select style="width: 180px;outline: none;" >
     <option value="#">
     </select></td>
     <td><select  style="width: 180px;outline: none;" >
     <option value="#">
     </select></td>
     </tr>
     <tr height="45px">
     <td width="65px">生　日：</td>
     <td>
     <select style="width: 180px;outline: none;" >
     <option value="#">
     </select>
     </td>
     <td><select style="width: 180px;outline: none;" >
     <option value="#">
     </select></td>
     </tr>
     <tr height="45px">
     <td width="65px">昵　称：</td>
     <td colspan="2" style="outline: none"><input type="text" style="width: 360px;height: 30px;outline: none;"/></td>
     
     
     </tr>
     <tr height="45px">
     <td></td>
     <td  colspan="2">
     <a href="#"><img src="WebPage/image/update_data_normal.png" border="0" onMouseOver="this.src='WebPage/image/update_data_hover.png'" onMouseOut="this.src='WebPage/image/update_data_click.png'" /></a>
     
     </td>
     </tr>
     
     
     </table>
   </div>
    <div class="main_right_down">
     <table>
     <tr height="106px">
     <td width="48px" class="td"><img src="WebPage/image/person_phone.png"/></td>
     <td width="640px" align="left"  style="line-height: 30px" >
     <span style="color: #333333; font-weight: bolder">绑定手机</span>  <br>
    <span style="color: gray;font-size: 12px">  绑定手机之后，您可以用使用手机找回密码</span>  
 </td>
     <td align="center">
     <span style="color: #2746a2">绑定手机</span>
     </td>
     </tr>
     <tr height="106px">
     <td width="48px"><img src="WebPage/image/person_email.png"/></td>
     <td width="640px" style="line-height: 30px">
     <span style="color: #333333; font-weight: bolder">绑定邮箱</span><br>
    <span style="color: gray;font-size: 12px">绑定邮箱之后，您可以用邮箱手机找回密码</span>   
     </td>
     <td align="center">
     <span style="color: #2746a2">  绑定邮箱</span>
   
     </td>
     </tr>
     <tr height="106px">
     <td width="48px"><img src="WebPage/image/person_idcard.png"/></td>
     <td  style="line-height: 30px">
     <span style="color: #333333; font-weight: bolder">实名认证</span>  <br>
    <span style="color: gray;font-size: 12px"> 实名认证后，您可以享受到更多的服务</span>  
    </td>
     <td align="center">
     <span style="color: #2746a2">立即认证</span>
     </td>
     </tr>
     </table>
   </div>
   </div>
   </div>
	 
	<%-- 界面内容 --%>
	
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
</div>
  </body>
</html>
