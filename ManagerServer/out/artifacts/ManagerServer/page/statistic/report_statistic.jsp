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

		<title>统计分析</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/statistic/statistic.css" />
			
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/statistic/report_statistic.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="report_totalDiv">
	<div id="title_line"><input type="hidden" value="<%=basePath%>" id="basePath"/>
			<b>定期签证航次报告数据统计</b>
		</div>
	<div id="report_content">
			<div id="level1">
			<label>选择年份：</label>
				<select id="year_select">
				</select>
				<label>按</label>
				<select id="partoftime_select">
				<option value="1">季</option>
				<option value="2">月</option>
				</select>
				<label>统计</label>
				<img src="image/operation/query_normal.png" onclick="CreateReport()"
					onmouseover="QueryOver(this)" onmouseout="QueryOut(this)" />
				<img src="image/statistic/exp_normal.png" onclick="GetExcel()"
					onmouseover="ExpOver(this)" onmouseout="ExpOut(this)" />

				<img src="image/statistic/yes_report_choose.png"
					onclick="ChangeDiv(this)" id="bt_ChangePage"/>
		</div>
		
		<div id="report_imgdiv">
			</div>
			<div id="report_datediv">
			<table cellpadding="0" cellspacing="0" id="dataTable" >
			<tr><td>统计年份：</td><td colspan="3" class="parttime"></td></tr>
			<tr><td>统计内容：</td><td colspan="3" class="contentRange"></td></tr>
			<tr><td>统计总数：</td><td colspan="3" class="sum"></td></tr>
			<tr><td>统计数据表：</td><td class='td1'>周期</td><td class='td1'>数量</td><td class='td2'>百分比</td></tr>			
			</table>
			</div>			
	</div>
	</div>
	</body>
</html>
