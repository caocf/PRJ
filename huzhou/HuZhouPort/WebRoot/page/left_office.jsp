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

		<title>首页</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/main/left.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/main/left.js"></script>

	</head>

	<body>
<input type="hidden" value="<%=session.getAttribute("roleId")%>" id="roleId" />
<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="container">
			<div id="left">
				<ul>
					<li id="first">
						<a href="<%=basePath%>page/huzhoumain.jsp" target="mainRight" onClick="a_left_onclick(this)">首页</a>
					</li>
					<li >
						<a href="<%=basePath%>page/LeaveAndOvertime/LeaveAndOvertime.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 考勤管理</a>
							</li>
					<li >
						<a href="<%=basePath%>page/officoa/schedule_show.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 日程安排</a>
					</li>
					
					<li >
						<a href="<%=basePath%>page/noticeforsee/Knowledge_tree.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 通知公告</a></li>
					
					<li >
						<a href="<%=basePath%>page/officoa/profession_information.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 行业资讯</a></li>
						<li >
						<a href="<%=basePath%>page/officoa/Knowledge_tree.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 通知管理</a></li>
							<li class="perssion36">
						<a href="<%=basePath%>page/shiplog/shiplog.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 船舶日志</a></li>
							
						<li >
							<a href="<%=basePath%>page/meetingroom/roomapply.jsp" target="mainRight" onClick="a_left_onclick(this)"> 会议室申请</a>
							<ul>
								<li>
									<a href="<%=basePath%>page/meetingroom/roomapply.jsp" target="mainRight" onClick=""> 新建申请</a>
								</li>
							</ul>
						</li>
						
							<li >
						<a href="<%=basePath%>page/shiplog/shiplog.jsp"
							target="mainRight" onClick="a_left_onclick(this)"> 用车管理</a></li>
					
				</ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/huzhoumain.jsp" height="100%" 
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
		</div>

	</body>
</html>
