<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>智慧出行-代售点</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/publicTraffic/ticket3.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/CRselectBox.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebPage/js/tansport/agentInfo.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>


	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
				<a href="<%=basePath%>WebPage/page/LongTansport/LongDistancePassengerTansport.jsp"
					class="layout1_a">客运概况</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/lineInfo.jsp"
					class="layout1_a">班线查询</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/ticket.jsp"
					class="layout1_a">余票查询</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/PassengerStation.jsp"
					class="layout1_a">客运枢纽</a>
					<a class="a_select">代售点</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/arriveInfo.jsp"
					class="layout1_a">到站信息查询</a>
					</div>
				<div style="width:100%;height:15px"></div>
				<div class="layout6">
					<table cellpadding="0" cellspacing="0" border="0"
						class="listTable2">
						<col width="5%" />
						<col width="20%" />
						<col width="20%" />
						<col width="27%" />
						<col width="28%" />
						<tr>
							<th>&nbsp;
								
							</th>
							<th>
								代售点名称
							</th>
							<th> 
								代售点联系方式
							</th>
							<th> 
								代售点介绍
							</th>
							<th> 
							         代售点地址
							 </th>
						</tr>
					</table>
					
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
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
