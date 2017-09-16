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

		<title>通知公告</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/noticeforsee/Knowledge_tree.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>




	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("userId")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div id="u2_left">
					<p>
						分类
					</p>


					<div id="deparmentTreeDiv">
						<ul
							style="list-style-type: none; margin: 10px 0 5px 10px; padding: 0">
							<li class="li-1">
								<img src='image/common/minus.png' style='cursor: default' />
								<a onClick="firstDepartment(this)" id="bigtree">通知公告</a>
								<ul id='department_tree'></ul>
							</li>
						</ul>
					</div>
				</div>
				<div id="u2_right">
					<iframe src="<%=basePath%>page/noticeforsee/Knowledgelist.jsp?kindID=-1"
						height="100%" width="100%" frameborder="0" marginwidth="0"
						marginheight="0" scrolling="no" name="user_right" id="user_right"></iframe>
				</div>
			</div>
		</div>




	</body>
</html>
