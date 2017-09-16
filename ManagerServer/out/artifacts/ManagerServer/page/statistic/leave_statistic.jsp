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
		<link rel="stylesheet" type="text/css" href="css/common/ShowDiv.css" />

		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/statistic/leave_statistic.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/time/WdatePicker.js"></script>
			<script type="text/javascript" src="js/common/CheckLogin.js"></script>
			<script type="text/javascript" src="js/statistic/departmentTree.js"></script>

	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=session.getAttribute("userId")%>" id="LoginUserid" />
		<div id="report_totalDiv">
			<div id="title_line"><input type="hidden" value="<%=basePath%>" id="basePath"/>
				<b>考勤管理统计</b>
			</div>
			<div id="report_content">
				<div id="level1">
					<label class="selectkindtitle">类型：</label>
					<select onchange="SelectKind()" id="puselect">
						<option value="-1">个人统计</option>
					</select>
					<input type="button" onclick="showDialog()" class="bt_department" style="display:none" value="选择部门"/>
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
					<img src="image/operation/query_normal.png" onClick="CreateReport()"
						onmouseover="QueryOver(this)" onMouseOut="QueryOut(this)" title="查询"/>
					<img src="image/statistic/exp_normal.png" onClick="GetExcel()"
						onmouseover="ExpOver(this)" onMouseOut="ExpOut(this)" title="导出"/>
					<img src="image/operation/print_normal.png" onClick="xlPrint()" id="printBt"
						onmouseover="PrintOver(this)" onMouseOut="PrintOut(this)" title="打印"/>
					
	
					<img src="image/statistic/yes_report_choose.png"
						onclick="ChangeDiv(this)" id="bt_ChangePage"/>
				</div>
				<div id="report_imgdiv">
				</div>
				<div id="report_datediv">
				<table cellpadding="0" cellspacing="0" id="dataTable" >
					
				</table>
				</div>
			</div>
		
			
		</div>
		<div id="departmentDIv" style="display:none;">
		<p><label class="title">部门选择</label><a class="close" onclick="claseDialog()">关闭</a></p>
				<div class="div_tree">
				<label onclick="SelectThisDepartment(0,'港航局统计')" id="ganhangju">湖州港航管理局</label>
				<ul id='tree'></ul></div>
			</div>
	</body>
</html>
