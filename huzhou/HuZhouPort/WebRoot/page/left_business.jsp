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

		<title>业务处理</title>

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
					<li class="perssion5">
						<a href="<%=basePath%>page/publicuser/shipuser_list.jsp" 
						target="mainRight" onClick="a_left_onclick(this)">用户船舶注册受理</a>
					</li>
					<li class="perssion6">
						<a href="<%=basePath%>page/publicuser/wharfuser_list.jsp" 
						target="mainRight" onClick="a_left_onclick(this)">用户码头注册受理</a>
					</li>
					<li class="perssion15">
						<a href="<%=basePath%>page/electric/electricReportList.jsp" target="mainRight"
						 onclick="a_left_onclick(this)">航行电子报告</a>
					</li>
					<li class="perssion16">
						<a href="<%=basePath%>page/business/dangerousGoodsPortln.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">危险品进港审批</a>
					</li>
					<li class="perssion17">
						<a href="<%=basePath%>page/business/dangerousGoodsJob.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">危险货物码头作业</a>
					</li>
					<li class="perssion18">
						<a href="<%=basePath%>page/business/law.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">电子信息采集管理</a>
					</li>
					<li class="perssion19">
						<a href="<%=basePath%>page/cruiselog/cruiselog.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">巡航</a>
					</li>
					
					<li class="perssion21">
						<a href="<%=basePath%>page/wharfwork/wharfNameList.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">码头作业报告</a>
					</li>
					<li class="perssion21">
						<a href="<%=basePath%>page/ais/ais.jsp"  onclick="a_left_onclick(this)"
							target="mainRight">九位码补录信息</a>
					</li>
					
				</ul>

			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/ais/ais.jsp" height="100%" class="rightpage"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
			</div>
	</body>
</html>
