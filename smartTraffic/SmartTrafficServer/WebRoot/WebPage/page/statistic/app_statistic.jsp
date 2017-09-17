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

		<title>统计分析</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/style.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/statistic/statistic.css" />

		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/statistic/app_statistic.js"></script>
		<script type="text/javascript" src="WebPage/js/time/WdatePicker.js"></script>

	</head>

	<body>
		<div id="report_totalDiv">
		
			<div id="title_line">
				<input type="hidden" value="<%=basePath%>" id="basePath" />
				<b>APP下载量数据统计</b>
			</div>
			<div id="report_imgdiv">
			</div>
			
			
			<div id="report_content">
			
			<div id="level1">
				<label>
					时间段：
				</label>
				<input type="text" id="beginTime" readonly="readonly"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" />
				<label>
					至
				</label>
				<input type="text" id="endTime" readonly="readonly"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" />
					
				<img src="WebPage/image/search_normal.png" onclick="queryStatistic()">
			</div>
				<table cellpadding="0" cellspacing="0" id="dataTable">
					<tr class='addTr'>
						<td class='td1'>
							APP名称
						</td>
						<td class='td1'>
							APP版本号
						</td>
						<td class='td4'>
							下载量（次）
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
