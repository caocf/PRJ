<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校培训</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>WebPage/js/carTraining/dynamic_left.js"></script>
	<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/TrainingFunction.js"></script>
	
	 	  
	
  </head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="4,1" />
			</jsp:include>

			<div id="main_content" style="height: 750px">
				<div class="main_content_title">
					<div class="main_content_title_divselect"
						onclick="SelectItem_top(this,'WebPage/page/carTraining/dynamic.jsp')">
						驾培动态
					</div>
					<div class="main_content_title_divnoselect"
						onclick="SelectItem_top(this,'WebPage/page/carTraining/drivingIntSec.jsp')">
						驾校网点查询
					</div>
				</div>
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
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
