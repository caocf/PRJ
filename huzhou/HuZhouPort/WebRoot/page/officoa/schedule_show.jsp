<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'ss.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/schedule_show.css" />
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/officoa/schedule_show.js"></script>
		<script type="text/javascript" src="js/officoa/Operation_schedule.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>
	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="calendar"></div>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" />
		<input type="hidden" value="" id="EntTime" />
		<div id="middleLine">
			<label id="select_day"></label>
			<label id="select_time"></label>
		<img alt="添加日程" src="image/schedule/add_schedul_normal.png"
			class="u3_img" onclick="addEvent()"
			onMouseOver="AddScheduleOver(this)" onMouseOut="AddScheduleOut(this)" />
		</div>
		
		<div id="showEvent">
			<table id="EventList" cellpadding="0" cellspacing="0">
				<col width="7%" />
				<col width="13%" />
				<col width="10%" />
				<col width="15%" />
				<col width="15%" />
				<col width="10%" />
				<col width="10%" />
				<col width="20%" />
				<tr>
					<th>
						编号
					</th>
					<th>
						日程名称
					</th>
					<th>
						日程类型
					</th>
					<th>
						时间
					</th>
					<th>
						地点
					</th>
					<th>
						发起人
					</th>
					<th>
						附件
					</th>
					<th>
						<label class="operation">操作</label>
					</th>
				</tr>
			</table>
		</div>

	</body>
</html>
