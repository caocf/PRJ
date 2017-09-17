<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-实时路况</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" type="text/css" href="WebSit/css/common/map.css" />
	<link rel="stylesheet" type="text/css" href="WebSit/css/common/CRselectBox_live.css" />
	
	<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebSit/js/common/formatConversion.js"></script>
	<script type="text/javascript" src="WebSit/js/XMap/XMapHelper.js"></script>
	<script type="text/javascript" src="WebSit/js/XMap/WebTHelper.js"></script>
	<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>	

	<script type="text/javascript" src="WebSit/js/Graphical/Visibility.js"></script>
<style type="text/css">
/*全路网指数*/
.sMap_network_div2 {
	position: absolute;
	top: 50px;
	right: 24%;
	border: solid #cbcccf;
	border-width: 2px;
	border-bottom-right-radius: 3px;
	border-bottom-left-radius: 3px;
	height: 40px;
	width: 130px;
	color: #666666;
	background-color: #fff;
}

#TrafficValue_value {
	float: left;
	line-height: 40px;
	font-size: 18px;
	font-weight: bold;
}
</style>

</head>
   <body>
  	<div id="page_content">
		<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true" >
			<jsp:param name="menu_number" value="7"/> 
		</jsp:include>
			<div id="main_content2" style="height: 700px;overflow: hidden;position: relative;">
				<div id="simpleMap" class="simpleMap_top"></div>
				<div id="simpleMap_top">
					<label class="simpleMap_top_location">
						嘉兴市
					</label>
				</div>
				<div class="sMap_network_div2">
					<div class="CRselectBox" id="TrafficValue_select">
						<input type="hidden" value="0"  class="abc" />
						<input type="hidden" value="全路网" class="abc_CRtext" />
						<a class="CRselectValue" href="#">全路网</a>
						<ul class="CRselectBoxOptions">
							<li class="CRselectBoxItem">
								<a  class="selected" rel="0">全路网</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="1">南湖区</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="2">秀洲区</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="3">内环内</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="4">中环内</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="5">三环内</a>
							</li>
						</ul>
					</div>
					<div id="TrafficValue_value"></div>
				</div>
			</div>
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />

</div>
  </body>
</html>



