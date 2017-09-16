<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<base href="<%=basePath1%>">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css"
			href="css/user_department_show.css" />
		<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script src="js/noticeforsee/knowledgelist.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>

	</head>
	<body>
		<input type="hidden" value="<%=basePath1%>" id="basePath" />
		<input id="kindID" name="kindID" type="hidden"
			value="<%=request.getParameter("kindID") %>" />
		<input type="hidden" value="<%=session.getAttribute("userId")%>"
			id="LoginUser" />

		<div class="u2_top">
			<div id="u2_top_right">
				<input type="text" id="Content" name="Content"
					class="search_input_box" />
				<img alt="搜索" src="image/operation/search_normal.png" class="u3_img"
					onclick="selectKnowledgelist('selectKnowLedgedian',1)" onMouseOver="SearchOver(this)"
					onMouseOut="SearchOut(this)" />
			</div>
		</div>
		<table width="100%" border="0" cellspacing="0" class="listTable"
			id="Knowledge">
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<tr>
				<th>
					通知名称
				</th>
				<th>
					通知时间
				</th>
				<th>
					通知对象
				</th>
				<th>
					操作
				</th>
			</tr>


		</table>

		<div id="pageDiv">
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
	</body>
</html>