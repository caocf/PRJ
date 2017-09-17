     <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  
  <link rel="stylesheet" href="<%=basePath%>WebPage/css/Individual/change.css" type="text/css"></link>
 <link rel="stylesheet" href="<%=basePath%>WebPage/css/common/main.css" type="text/css"></link></head>
  
  <body>
  	<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top1.jsp" />
		
	<%-- 界面内容 --%>
	<div id="main">
	<div class="top" style="margin-top:5px;">
	 <img alt="" src="WebPage/image/person_changePwd.png">
	</div>
	
     <div class="center">
   <form action="#" method="post">
   <table align="center">
   <tr align="right" height="60px">
   <td width="200px"><span class="span">原密码&nbsp;</span></td>
   <td colspan="2" style="line-height: 36px">
   <input name="textfield"type="text"value="原密码"onfocus="if (value =='原密码'){value 
=''}"onblur="if (value 
==''){value='原密码'} " class="span2" />
   </td>
    </tr>
   <tr align="right" height="60px">
   <td style="margin-right: 5px"><span class="span">新密码&nbsp;</span></td>
   <td colspan="2" >
   <input name="textfield"type="text"value="设置密码"onfocus="if (value =='设置密码'){value 
=''}"onblur="if (value 
==''){value='设置密码'} "  class="span2"/>
   </td>
   </tr>
   <tr align="right" height="60px">
   <td><span class="span">确认密码&nbsp;</span></td>
   <td colspan="2">
    <input name="textfield"type="text"value="确认密码"onfocus="if (value =='确认密码'){value 
=''}"onblur="if (value 
==''){value='确认密码'} "  class="span2"/>
   </td>
   </tr>
   <tr>
   <td></td>
   <td>提交</td>
   <td>取消</td>
   </tr>
   </table>
   </form>
   </div>
   </div >
   
   <%-- 界面内容 --%>
	<jsp:include page="../../../WebPage/page/main/foot.jsp" />

</div>
  </body>
</html>
