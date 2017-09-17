<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-铁路-余票查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/publicTraffic/ticket.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/CRselectBox.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
			<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript"
			src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebPage/js/publicTraffic/airline/airlineinfo.js"></script>
<style type="text/css">
.a_href:hover{
text-decoration: underline;
}</style>
	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9,6" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
					<!--<a href="WebPage/page/publicTraffic/airline/ticket.jsp"
					class="layout1_a">班线查询</a>
					<a href="WebPage/page/publicTraffic/airline/importport.jsp"
					class="layout1_a">进出港实况信息查询</a>-->
					<a href="<%=basePath%>WebPage/page/publicTraffic/railway/airlinefo.jsp"
					class="a_select">订票热线</a>
					<label class="pagepath"><script>document.write(PATH_GRAPHICAL_PUBLICTRAFFIC_AIRLINE_INFO)</script></label>
				</div>
				  <div class="layout">
				       <b >订票地址:</b><a href="http://flights.ctrip.com/" target="_blank" style="margin-left: 15px;color: #5e79ec;" class="a_href">携程</a>
				    </div>
					<div class="layout6">
					
					</div>
					<div id="pageDiv">
							<p>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>
								<span class="nextBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>
			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
