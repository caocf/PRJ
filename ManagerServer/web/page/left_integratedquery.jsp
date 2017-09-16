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

		<title>综合查询</title>

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
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
	<input type="hidden" value="<%=session.getAttribute("roleId")%>" id="roleId" />
		<div id="container">
			<div id="left">
				<ul>
					<li class="perssion25">
						<a href="<%=basePath%>page/integratedquery/search_cbjbxx.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">船舶基本信息</a>
					</li>
					<li class="perssion26">
						<a href="<%=basePath%>page/integratedquery/search_cbzsxx.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">船舶证书信息</a>
					</li>
					<li class="perssion27">
						<a href="<%=basePath%>page/integratedquery/search_cbwzxx.jsp"
							target="mainRight" onclick="a_left_onclick(this)">船舶违章信息</a>
					</li>
					<li class="perssion28">
						<a href="<%=basePath%>page/integratedquery/search_cbqzxx.jsp"
							target="mainRight" onclick="a_left_onclick(this)">船舶签证信息</a>
					</li>
					<li class="perssion29">
						<a href="<%=basePath%>page/integratedquery/search_cbjfxx.jsp"
							target="mainRight" onclick="a_left_onclick(this)">船舶缴费信息</a>
					</li>
					<li class="perssion30">
						<a href="<%=basePath%>page/integratedquery/search_cbjyxx.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">船舶检验信息</a>
					</li>
					<!--<li>
						<a href="<%=basePath%>page/integratedquery/search_mtzyxx.jsp"
						 target="mainRight" onclick="a_left_onclick(this)">码头作业信息</a>
					</li>-->
					<li class="perssion31">
						<a href="<%=basePath%>page/integratedquery/search_syqyxx.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">航运企业信息</a>
					</li>
					<!--<li>
						<a href="<%=basePath%>page/integratedquery/search_mtqyxx.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">码头企业信息</a>
					</li>
				--></ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/integratedquery/search_cbjbxx.jsp" height="100%"  class="rightpage2"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
			</div>
	</body>
</html>
