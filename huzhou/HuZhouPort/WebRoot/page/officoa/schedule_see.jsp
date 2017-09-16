<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'schedule.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_see.css" />
		<link rel="stylesheet" type="text/css" href="css/schedule.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/officoa/schedule_see.js"></script>
		<script type="text/javascript" src="js/officoa/schedule.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>



	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" />
	<div id="totaldiv">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					 onclick="goBackSchedule()" onMouseOver="BackSmallOver(this)" 
					 onmouseout="BackSmallOut(this)" title="返回日程页面"/>
					<div id="clock_div"> <a onClick="openClockPage()" class="operation">提醒设置</a></div>
			</div>
			<input type="hidden" value="<%=basePath%>" id="basePath" />
			<input type="hidden" id="eventId"
				value="<%=request.getParameter("eventId") %>" />
				<div id="content">
				<table id="EventList" class="table_see" cellpadding="0" cellspacing="0">
				<col width="84px"/><col/>
					<tr>
						<td>
							日程名称:
						</td>
						<td>
							<label id="sName"></label>
						</td>
					</tr>
					<tr>
						<td>
							日程类型:
						</td>
						<td>
							<label id="sKind"></label>
						</td>
					</tr>
					<tr>
						<td>
							开始时间:
						</td>
						<td>
							<label id="sTime"></label>
						</td>
					</tr>
					<tr>
						<td>
							提醒时间:
						</td>
						<td>
							<label id="sRemind"></label>
						</td>
					</tr>
					<tr>
						<td>
							提醒方式:
						</td>
						<td>
							<label id="sRemindType"></label>
						</td>
					</tr>
					<tr>
						<td>
							地点:
						</td>
						<td>
							<label id="sLocation"></label>
						</td>
					</tr>
					<tr>
						<td>
							发起人:
						</td>
						<td>
							<label id="sFirstUser"></label>
						</td>
					</tr>
					<tr>
						<td>
							相关人员:
						</td>
						<td>
							<label id="sUser"></label>
						</td>
					</tr>
					<tr>
						<td>
							详细内容:
						</td>
						<td>
							<div  id="sContent" class="table_see_div"></div>
						</td>
					</tr>
				</table>
				<div class="content_kind" id="evidenceDiv_title">
					附件
				</div>
				<div id="evidenceDiv">
				</div>
				<table id="ReplyTable" cellpadding="0" cellspacing="0">
				<col width="80px" />
					<col />
				<tr><th colspan="2">
				<div class="content_kind" id="Reply_title">
					回复记录
				</div>
				</th></tr>			
				</table>
			</div>
		</div>
	</body>
</html>
