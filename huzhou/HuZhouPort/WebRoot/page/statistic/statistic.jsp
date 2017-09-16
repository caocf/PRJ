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
				<li  class="perssion14">
					<a href="<%=basePath%>page/statistic/app_statistic.jsp" target="mainRight"
						 onclick="a_left_onclick(this)">APP下载量统计</a>
				</li>
				<li  class="perssion20">
					<a href="<%=basePath%>page/statistic/draft_statistic.jsp" target="mainRight"
						 onclick="a_left_onclick(this)">油耗统计</a>
				</li>
				<li class="perssion22">
					 <a href="<%=basePath%>page/statistic/illegal_statistic.jsp" target="mainRight"
					 onclick="a_left_onclick(this)">违章取证统计</a>		
				</li>
				<li class="perssion23">
					<a href="<%=basePath%>page/statistic/elect_statistic.jsp" target="mainRight"
					 onclick="a_left_onclick(this)">航行电子报告统计</a>
				</li>
				<li  class="perssion33">
					<a href="<%=basePath%>page/statistic/leave_statistic.jsp" target="mainRight"
						 onclick="a_left_onclick(this)">考勤管理统计</a>
				</li>
			</ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/statistic/illegal_statistic.jsp" height="100%" class="rightpage1"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
		</div>	
		
	</body>
</html>
