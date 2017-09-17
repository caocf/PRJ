 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-投诉意见受理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/suggest/style.css" />
    <link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>
		
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	<script type="text/javascript" src="WebPage/js/suggest/adviceList.js"></script>
	<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>

 </head>
  <body>
   <jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="9,2"/> 
		</jsp:include>
  
  
   <div id="content1">
   <table cellpadding="0" cellspacing="0">
      <tr>
      <td valign="top">
      <div class="leftnew1" >
          <a href="WebPage/page/suggest/suggest.jsp" class="left2" style="margin-top: 0px" >投诉受理</a>
          <a class="left1"style="margin-top: 10px;">意见建议受理</a>
      </div>
      </td>
      <td>
      <div class="rightnew1">
           <div class="right_top">意见建议受理</div>
              <div id="NewsList" class="main_right_lili">
              </div>				        	
      </div> 
      </td>
      </tr>
      </table> 
   </div>
   
   <jsp:include page="../../../WebPage/page/main/foot.jsp" />
   
  </body>
</html>
