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

		<title>办公OA</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/main_page.css" />

	</head>

	<body>
		<div id="container">
			<div id="left">
				<ul>
					<li>
						<a href="<%=basePath%>page/LeaveAndOvertime/LeaveAndOvertime.jsp" target="officoa_right">考勤管理</a>
					</li>
					<li>
						<a href="page/attendace/signList.jsp" target="officoa_right">签到签退</a>
					</li>
					<li>
						<a href="<%=basePath%>page/officoa/schedule_show.jsp"
							target="officoa_right">日程安排</a>
					</li>
					<li>
						<a href=""
							target="officoa_right">信息发布</a>
					</li>
					<li>
						<a href="<%=basePath%>page/officoa/Knowledge.jsp"
							target="officoa_right">通知公告</a>
					</li>
					<li>
						<a href="<%=basePath%>page/officoa/profession_information.jsp"
							target="officoa_right">行业资讯</a>
					</li>
					
				</ul>


			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/officoa/schedule_show.jsp" height="100%"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="officoa_right" id="officoa_right"></iframe>
			</div>

		</div>

	</body>
</html>
