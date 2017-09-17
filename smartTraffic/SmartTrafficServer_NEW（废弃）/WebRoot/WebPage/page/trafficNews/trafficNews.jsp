<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-交通快讯</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/trafficNews/TypeList.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="5,1" />
			</jsp:include>
			<%-- 界面内容 --%>
			<div id="main_content">
				<div style="width: 100%; height: 700px">
					<div class="main_left">
	
					</div>
					
					<div class="main_right">
						 <div id="NewsList" class="main_right_li"></div>
  		  
				        
				           
				            <div id="pageDiv">
									<p>
									<span class="prevBtnSpan"></span>
									<span class="pageNoSpan"></span>
									<span class="nextBtnSpan"></span>
									<span class="gotoPageSpan"></span>
									</p>
							</div>
				            
				         
					</div>
				</div>
			</div>
			
			
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>

	</body>
</html>
