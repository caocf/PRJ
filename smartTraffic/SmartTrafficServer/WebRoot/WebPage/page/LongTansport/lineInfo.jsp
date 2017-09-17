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

		<title>智慧出行-班线查询</title>
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
			src="WebPage/js/tansport/lineInfo.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		

	</head>

	<body>
		<div id="page_content" >
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content" >
				<div class="layout1">
				<a href="<%=basePath%>WebPage/page/LongTansport/LongDistancePassengerTansport.jsp"
					class="layout1_a">客运概况</a>
					<a class="a_select">班线查询</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/ticket.jsp"
					class="layout1_a">余票查询</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/PassengerStation.jsp"
					class="layout1_a">客运枢纽</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/agentInfo.jsp"
					class="layout1_a">代售点</a>
					<a href="<%=basePath%>WebPage/page/LongTansport/arriveInfo.jsp"
					class="layout1_a">到站信息查询</a>
				</div>
				<div class="layout2">
					<label class="layout2_label1">
						车次：
					</label>
					<input class="input_text3" value="中文/拼音/首字母" onBlur="TextBlur(this)"
						onFocus="TextFocus(this)"  id="busCode"/>
					
					<img src="WebPage/image/search_normal.png"
						onclick="ShowDataBySearch('GetBusCodeByBusCode', 1)"
						onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />

				</div>
				<%--<div class="layout3" id="TimeItem">
				</div>
				--%><div class="layout4">
				<label class="layout4_label1"></label><label class="layout4_label2">共<a id="alltotal"></a>个班线</label>
				</div>
				<div class="layout6">
					<table cellpadding="0" cellspacing="0" border="0"
						class="listTable2">
						<col width="5%" />
						<col width="7%" />
						<col width="13%" />
						<col width="13%" />
						<col width="8%" />
						<col width="12%" />
						<col width="12%" />
						<col width="12%" />
						<col width="12%" />
						<col width="6%" />
						<tr>
							<th>&nbsp;
								
							</th>
							<th>
								车次
							</th>
							<th> 
								起始站 
							</th>
							<th> 
								终点站 
							</th>
							<th> 
							         公司
							 </th>
							<th> 
								计划出发 
							</th>
							<th> 
								计划到达
							</th>
							<th> 
								实际出发 
							</th>
							<th> 
								实际到达 
							</th>
							<th> 
								状态
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
