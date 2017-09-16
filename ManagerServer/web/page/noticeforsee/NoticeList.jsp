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
		<title>通知公告列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">


		<link rel="stylesheet" type="text/css" href="css/Notice_CSS/noticelist.css" />
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/notice_js/noticelist.js"></script>
		<script type="text/javascript" src="js/bootpaging.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("userId")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />


	<div id="condition">
			<ul id="nav" style="line-height: 35px;height: inherit; list-style-type: none;">
				<li class="con" style="left: 8%">
					<a id="region" class="a1">杭州</a>
					<ul class="ulchild">
						<li><a class="a2" onclick="onOptionClick(this,'region')">杭州</a></li>
						<li><a class="a2" onclick="onOptionClick(this,'region')">嘉兴</a></li>
						<li><a class="a2" onclick="onOptionClick(this,'region')">湖州</a></li>
						<li><a class="a2" onclick="onOptionClick(this,'region')">舟山</a></li>
					</ul>
				</li>
				<li class="con" style="left: 23%;">
					<a id="type"  class="a1">港航通知</a>
					<ul class="ulchild">
						<li><a class="a2" onclick="onOptionClick(this,'type')">港航通知</a></li>
						<li><a class="a2" onclick="onOptionClick(this,'type')">航行通告</a></li>
						<li><a class="a2" onclick="onOptionClick(this,'type')">行政通知</a></li>
					</ul>
				</li>
				<li class="con" style="left: 80%;">
					<table>
						<tr>
							<td>
								<input id="tip"  style="width: 100px;height: 36px;line-height: 35px;text-align: center;" />
							</td>
							<td>
								<button style=" width: 100px;height: 36px;line-height: 35px;" onclick="getnoticelist(1)">查询</button>
							</td>

						</tr>

					</table>
				</li>

			</ul>
		</div>
		<div style="height: 600px;width: 100%;">
			<table  id="list" align='center' style="line-height: 40px;" >
				<col width="10%" />
				<col width="35%" />
				<col width="20%"/>
				<col width="35%" />

				<tr style="background-color: #f5f5f5;text-align: center;height: 35px;width: 100%;">
					<th align="center" style="text-align: center;vertical-align: middle;">
						<input type="checkbox" class="checkInput" value="-1" id="checkSystem" onclick="checkAll(this)">
						<button style="height: 30px;line-height: 30px;" onclick="deleteAll()">删除选中</button>
					</th>
					<th align="center" style="text-align: center;">
						通知名称
					</th>
					<th align="center" style="text-align: center;">
						通知时间
					</th>
					<th align="center" style="text-align: center;">
						操作
					</th>
				</tr>
			</table>
		</div>
	<div class='bootpagediv' style='width:100%;display: inline-block;'>
		<nav style='float:right;display:none' id='pageshow'>
			<ul class="pagination" id='pagination'>
			</ul>
		</nav>
	</div>
	</body>
</html>
