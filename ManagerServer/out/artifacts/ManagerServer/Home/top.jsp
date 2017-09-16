<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="Home/top.css" />
	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="Home/top.js"></script>
  </head>
  
  <body>
  <div id="top">
 		<div id="top1">
 			<input type="hidden" value="<%=basePath%>" id="basePath"/> 
	  		<div id="top2">
	 			<img src="<%=basePath%>image/top/nav_logo.png" id="iconImg"/>
	 		</div>
			 <div id="top3">

			 		<label><img src="image/top/user.png"/><font id="top_user"><%=(String)session.getAttribute("name")%></font></label>|<img src="image/top/muniu.png"/><a
			 		onclick="exitMain()" class="btExit"/>回首页</a>|<a onclick="exit()" class="btExit"/>退出</a><a style="display: none" href="<%=basePath%>page/downloadAPP.jsp"  target="mainLeft" id="downloadApp">APP管理</a>
			 </div>

			 <div id="top4" >
			<ul id="ul1">
				<li>
					<a href="<%=basePath%>page/left_office.jsp"  target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_office"><font>日常办公</font></a>
				</li>
				<li>
					<a href="<%=basePath%>page/left_business.jsp" target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_business"><font>业务处理</font></a>
				</li>
				<li>
				<a href="<%=basePath%>page/AddressList/left_user.jsp" target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_addressShow"><font>公众用户管理</font></a>
				</li>
				<li>
					<a href="<%=basePath%>page/statistic/statistic.jsp" target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_statistic"><font>综合查询与统计</font></a>
				</li>
				<li>
					<a href="<%=basePath%>page/left_integratedquery.jsp" target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_integratedquery"><font>系统管理</font></a>
				</li>
				<li>
					<a href="<%=basePath%>orgnize/orgnize.jsp" target="contentframe" onfocus="this.blur();" onclick="a_top_onclick(this.id)" id="top_system"><font>组织架构</font></a>
				</li>
			</ul>
		</div>
 		</div>
 		
 		<div >
	<img src="image/top/topline.png" width="100%" ></div>
	</div>
	
  </body>
</html>
