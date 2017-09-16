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
		<link rel="stylesheet" type="text/css"
			href="css/statistic/statistic.css" />

		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/statistic/illegal_statistic.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/time/WdatePicker.js"></script>
			<script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="report_totalDiv">
		<div id="title_line"><input type="hidden" value="<%=basePath%>" id="basePath"/>
			<b>违章取证数据统计</b>
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
				<select id="condition_select">
					<option value="1">
						全部
					</option>
					<option value="2">
						审核通过
					</option>
				</select>
				<img src="image/operation/query_normal.png" onclick="CreateReport()"
					onmouseover="QueryOver(this)" onmouseout="QueryOut(this)" />
				<img src="image/statistic/exp_normal.png" onclick="GetExcel()"
					onmouseover="ExpOver(this)" onmouseout="ExpOut(this)" />

				<img src="image/statistic/yes_report_choose.png"
					onclick="ChangePage(this)" id="bt_ChangePage"/>
			</div>
			<div id="report_imgdiv">
			</div>
			<div id="report_datediv">
			<table cellpadding="0" cellspacing="0" id="dataTable" >
			<tr><td>统计时间段：</td><td colspan="3" class="parttime"></td></tr>
			<tr><td>统计内容范围：</td><td colspan="3" class="contentRange"></td></tr>
			<tr><td>统计总数：</td><td colspan="3" class="sum"></td></tr>
			<tr><td>执法类别统计：</td><td colspan="3"></td></tr>
			<tr><td></td><td class='td1'>名称</td><td class='td1'>数量</td><td class='td2'>百分比</td></tr>				
			</table>
			</div>
		</div>
		</div>
	</body>
</html>
