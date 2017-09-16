<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'law.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/law.css" />
	
	<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/business/law.js"></script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>

  </head>
  
  <body>
  <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
  <input type="hidden" value="<%=basePath%>" id="basePath" />
   <input type="hidden" value="<%=request.getParameter("lawPart")%>" id="lawPart" />
		<div id="content">
			<div id="layer">
				<ul id="layer1_menu">
					<li>
						<a href="<%=basePath%>page/business/illegal_show.jsp" target="law_right"
						onclick="a_menu_onclick_first(this)" id="law_right_part1">违章取证</a>
					</li>
					<!--
					<li>
						<a href="<%=basePath%>page/business/permit_show.jsp" target="law_right"
						onclick="a_menu_onclick(this)" id="law_right_part2">许可踏勘</a>
					</li>
					-->
					<li>
						<a href="<%=basePath%>page/business/patrol_show.jsp" target="law_right" 
						onclick="a_menu_onclick(this)" id="law_right_part3">码头巡查</a>
					</li><!--
					<li>
						<a href="<%=basePath%>page/empty.jsp" target="law_right" 
						onclick="a_menu_onclick(this)" id="law_right_part4">航道巡查</a>
					</li>
				--></ul>


			</div>
			<div id="layer2">
				<iframe src="<%=basePath%>page/business/illegal_show.jsp" height="100%"
					width="100%" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"
					name="law_right" id="law_right"></iframe>
			</div>

		</div>

  </body>
</html>
