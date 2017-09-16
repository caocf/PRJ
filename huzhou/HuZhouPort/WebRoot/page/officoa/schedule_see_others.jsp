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
			src="<%=basePath%>js/officoa/schedule_see_others.js"></script>
			<script type="text/javascript" src="js/officoa/schedule.js"></script>
			 <script type="text/javascript" src="js/common/Operation.js"></script>
			 <script type="text/javascript" src="js/common/CheckLogin.js"></script>
			


	</head>

	<body>
	<form id="actionName">
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
     <input type="hidden" value="<%=basePath%>" id="basePath"/>
    <div id="totaldiv">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					 onclick="goBackSchedule()" onMouseOver="BackSmallOver(this)" 
					 onmouseout="BackSmallOut(this)" title="返回日程页面"/>
					 <div id="clock_div"><a onClick="openClockPage()" class="operation">提醒设置</a></div>
			</div>
		<input type="hidden" id="eventId" name="scheduleEventUser.eventId"
			value="<%=request.getParameter("eventId") %>"/>
			<input type="hidden"  name="scheduleEventUser.userId" id="userId"
			value="<%=session.getAttribute("userId")%>"/>
			<input type="hidden" id="eventRemind" name="scheduleEventUser.eventRemind"/>
			<input type="hidden"  name="scheduleEventUser.eventRemindType" id="eventRemindType"/>
		<div id="content">	
			<table id="EventList"  class="table_see" cellpadding="0" cellspacing="0">
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
				<div class="content_kind" id="evidenceDiv_title" >
					附件
				</div>
				<div id="evidenceDiv">
				</div>
				
		</div>
	</div>
	</form>
	</body>
</html>
