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

		<title>系统维护</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/main/left.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/main/left.js"></script>

	</head>

	<body><input type="hidden" value="<%=session.getAttribute("roleId")%>" id="roleId" />
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="container">
			<div id="left">
				<ul>
					<li class="perssion1">
						<a href="<%=basePath%>page/system/person_center.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">个人信息</a>
					</li>
					<li class="perssion2">
						<a href="<%=basePath%>page/system/user_show.jsp"
							target="mainRight" onclick="a_left_onclick(this)">用户管理</a>
					</li>
					<li class="perssion3">
						<a href="<%=basePath%>page/system/organization_show.jsp"
							target="mainRight" onclick="a_left_onclick(this)">组织管理</a>
					</li>
					<li class="perssion4">
						<a href="<%=basePath%>page/system/role_show.jsp"
							target="mainRight" onclick="a_left_onclick(this)">角色管理</a>
					</li>
					
					<li class="perssion7">
						<a href="<%=basePath%>page/system/log.jsp" 
						target="mainRight" onclick="a_left_onclick(this)">系统日志</a>
					</li>
					<li class="perssion_child">
						<a href="javascript:void(0);"  onclick="a_left_onclick(this,'divl8')">数据字典维护</a>
						<div id="divl8">
								<a class="perssion8" href="<%=basePath%>page/electric/channelList.jsp"
									onclick="a_left_onclick(this)" target="mainRight">航道信息</a>
								<a class="perssion9" href="<%=basePath%>page/dictionary/BoatmanList.jsp"
									onclick="a_left_onclick(this)" target="mainRight">船员职位信息</a>
								<a class="perssion36" href="<%=basePath%>page/dictionary/BoatcardList.jsp"
									onclick="a_left_onclick(this)" target="mainRight">船员证书类型</a>
						</div>
					</li>

				</ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/system/person_center.jsp" height="100%" class="rightpage3"
					width="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
			</div>
	</body>
</html>
