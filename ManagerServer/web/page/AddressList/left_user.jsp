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

		<title>注册审批</title>

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
				<ul >
					<li class="line">
						<a  class="title1"
							target="mainRight" onClick="a_left_onclick(this)"> 内河船户
						</a>
						<div  class="title2">
							<a  class="child" href="<%=basePath%>page/AddressList/neibuuser_list.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">用户管理</a>
							<a  class="child"  href="<%=basePath%>page/AddressList/neibuuser_bangding_list.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">绑定信息审核</a>
						</div>
					</li>
					<li class="line">
						<a  class="title1"
							target="mainRight" onClick="a_left_onclick(this)"> 内河码头
						</a>
						<div  class="title2">
							<a  class="child" href="<%=basePath%>page/AddressList/neihematou_list.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">用户管理</a>
							<a  class="child"  href="<%=basePath%>page/AddressList/neibuuser_bangding_list.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">绑定信息审核</a>
						</div>
					</li>
					<li class="line">
						<a  class="title1"
							target="mainRight" onClick="a_left_onclick(this)"> 沿海企业
						</a>
						<div  class="title2">
							<a  class="child" href="<%=basePath%>page/AddressList/yanhaiqiye_list.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">用户管理</a>
							<a  class="child"  href="<%=basePath%>page/dictionary/BoatmanList.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">绑定信息审核</a>
						</div>
					</li>

					<li class="line">
						<a  class="title1" onClick="a_left_onclick(this)"href="<%=basePath%>page/AddressList/yijianxiang_list.jsp"
						   target="mainRight" > 意见箱
						</a>
					</li>
				</ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>page/huzhoumain.jsp"
					width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
		</div>

	</body>
</html>
