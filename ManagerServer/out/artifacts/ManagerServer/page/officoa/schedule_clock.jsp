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
		<script type="text/javascript"	src="js/officoa/schedule_clock.js"></script>
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
		</div>
			<input type="hidden" id="eventId" name="scheduleEventUser.eventId"
				value="<%=request.getParameter("eventId") %>"/>
			<input type="hidden"  name="scheduleEventUser.userId" id="userId"
				value="<%=session.getAttribute("userId")%>"/>
			<input type="hidden" id="agreeReason" name="scheduleEventUser.agreeReason"/>
			<input type="hidden"  name="scheduleEventUser.userAgree" id="userAgree"/>
		<div id="content">	
			<table id="EventList"  class="table_see" cellpadding="0" cellspacing="0">
			<col width="80px"/><col/>
				<tr>
					<td>
						提醒时间:
					</td>
					<td>
						<select name="scheduleEventUser.eventRemind" id="eventRemind"  class="select_box">
							<option value="0">
								无提醒
							</option>
							<option value="5">
								提前5分钟提醒
							</option>
							<option value="10">
								提前10分钟提醒
							</option>
							<option value="15">
								提前15分钟提醒
							</option>
							<option value="30">
								提前30分钟提醒
							</option>
							<option value="60">
								提前1个小时提醒
							</option>
							<option value="120">
								提前2个小时提醒
							</option>
							<option value="240">
								提前4个小时提醒
							</option>
							<option value="720">
								提前12个小时提醒
							</option>
							<option value="1440">
								提前24个小时提醒
							</option>
						</select>
						<font color="#a2a2a2">*只在手机端提醒</font>
					</td>
				</tr>
				<tr>
					<td>
						提醒方式:
					</td>
					<td>
						<select name="scheduleEventUser.eventRemindType"
							id="eventRemindType"  class="select_box">
							<option value="1">
								铃声
							</option>
							<option value="2">
								震动
							</option>
							<option value="3">
								铃声和震动
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td>
						<img alt="提交" src="image/operation/submit_normal.png"
							class="u3_img" onClick="AddColck()"
							onMouseOver="SubmitOver_img(this)"
							onMouseOut="SubmitOut_img(this)" />
					</td>
				</tr>
			</table>
			</div>
			</div>
			</form>
	</body>
</html>
