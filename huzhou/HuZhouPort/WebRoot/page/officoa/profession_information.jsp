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

		<title>知识库</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/profession/profession_information.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/officoa/profession_information.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="<%=basePath%>js/common/paging.js"></script>




	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div class="profession_top">
					<img alt="新增" src="image/operation/add_know_normal.png" class="profession_addbutton" 
						onclick="addProfessionInfo()" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)"/>
					<img alt="搜索" src="image/operation/search_normal.png" class="profession_searchbutton" 
					onclick="showProfessionInfomation('findIndustryInfoList',1)" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
					<input type="text" id="profession_search"  name="Content" class="profession_search_input"/>
				</div>
				<table class="profession_table" cellpadding="0" cellspacing="0">
					<col width="40%" /><col width="30%" /><col width="30%" />
					<tr>
						<th>资讯标题</th>
						<th>通知对象</th>
						<th>操作</th>
					</tr>
				</table>
				<div class="User_S4" id="pageDiv">
							<p>
								共<span id="allTotal"></span>条
								<span class="firstBtnSpan"></span>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>页
								<span class="nextBtnSpan"></span>
								<span class="lastBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
				</div>
			</div>
		</div>
		
		

		
	</body>
</html>
