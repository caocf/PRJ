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

		<title>日常办公</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="Home/left.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="Home/left.js"></script>

	</head>

	<body>
<input type="hidden" value="<%=session.getAttribute("roleId")%>" id="roleId" />
<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="container">
			<div id="left">
				<ul >
					<li class="line">
						<a id="item1" class="title1"
							target="mainRight" onClick="a_left_onclick(this)"> 考勤管理
						</a>
						<div id="divl7" class="title2" style="height: 120px;">
							<a id="c1" class="child" href="<%=basePath%>page/LeaveAndOvertime/LeaveAndOvertime.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">假条审批</a>
							<a id="c2" class="child"  href="<%=basePath%>page/dictionary/BoatmanList.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">我的假条</a>
							<a id="c5" class="child"  href="<%=basePath%>page/dictionary/BoatmanList.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">出勤统计</a>
						</div>
					</li>
					
					<li class="line">
						<a id="item2" class="title1"
							target="mainRight" onclick="a_left_onclick(this)"> 信息发布
						</a>
						<div id="divl8" class="title2">
							<a id="c3" class="child"  href="<%=basePath%>page/noticeforsee/NoticeList.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">通告列表</a>
							<a id="c4" class="child" href="<%=basePath%>page/noticeforsee/NoticePush.jsp"
							   onclick="a_left_onclick1(this)" target="mainRight">发布通告</a>
						</div>
					</li>

					<li class="line">
						<a id="item4" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 公众用户管理
						</a>
					</li>
					<li class="line">
						<a id="item5" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> APP管理
						</a>
					</li>
					<li class="line">
						<a id="item6" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 业务审核
						</a>
					</li>
					<li class="line">
						<a id="item7" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 查询与统计
						</a>
					</li>
					<li class="line">
						<a id="item8" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 文书起草
						</a>
					</li>
					<li class="line">
						<a id="item9" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 通讯录
						</a>
					</li>
					<li class="line">
						<a id="item10" class="title1"
						   target="mainRight" onclick="a_left_onclick(this)"> 系统管理
						</a>
					</li>

				</ul>
			</div>
			<div id="right">
				<iframe src="<%=basePath%>Home/welcome.jsp"
					width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"
					name="mainRight" id="mainRight"></iframe>
			</div>
		</div>

	</body>
</html>
