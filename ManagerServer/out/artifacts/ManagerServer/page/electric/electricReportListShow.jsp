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

		<title>查看详情</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		
		<script type="text/javascript"
			src="js/electric/electricReportListShow.js"></script>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=1.4"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>"
			id="LoginUser" />
		<div id="see_container">
			<div id="layer1" style="position: static; float: none; clear: both">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:window.history.go(-1);"
					onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
					title="返回" />
			</div>
			<div style="position: static; float: none; clear: both">
				<table cellspacing="0" cellpadding="0" class="listTable1" border="0">
				<col width="150px"/><col width="*"/>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">船舶名称：</font>
						</td>
						<td>
							<input id="reportID" type="hidden" name="reportID"
								value="<%=request.getParameter("reportID")%>" />
							<label id="shipName" ></label>
						</td>

					</tr>
					<tr>
						<td>

							<font color="red" style="font-weight: bold">上报用户：</font>
						</td>
						<td>

							<label  id="shipUserName"  ></label>
						</td>

					</tr>
					<tr>
						<td>

							<font color="red" style="font-weight: bold">上报类型：</font>
						</td>
						<td>

							<label id="reportKind"  ></label>
						</td>

					</tr>
					<tr>
						<td>

							<font color="red" style="font-weight: bold">进出港标志：</font>
						</td>
						<td>

							<label id="outOrIn" ></label>
						</td>

					</tr>
					<tr>
						<td>

							<font color="red" style="font-weight: bold">起始港：</font>
						</td>
						<td>

							<label id="startingPort" ></label>
						</td>

					</tr>
					<tr>
						<td>

							<font color="red" style="font-weight: bold">目的港：</font>
						</td>
						<td>

							<label id="arrivalPort"></label>
						</td>

					</tr>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">上一航次加油量：</font>
						</td>
						<td>

							<label id="draft" ></label>
						</td>

					</tr>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">上一航次加油时间：</font>
						</td>
						<td>

							<label id="drafttime" ></label>
						</td>

					</tr>
					<tr id="hiddentr1">
						<td>
							<font color="red" style="font-weight: bold">载货种类：</font>
						</td>
						<td>
							<label id="cargoType" ></label>
						</td>

					</tr>
					<tr id="hiddentr2">
						<td>
							<font color="red" style="font-weight: bold">载货数量：</font>
						</td>
						<td>
							<label id="cargoNumber" ></label>
						</td>

					</tr>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">拟停靠码头：</font>
						</td>
						<td>

							<label id="toBeDockedAtThePier"  ></label>
						</td>

					</tr>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">预计进出港时间：</font>
						</td>
						<td>

							<label id="estimatedTimeOfArrival"></label>
						</td>

					</tr>
					<tr>
						<td>
							<font color="red" style="font-weight: bold">报告时间：</font>
						</td>
						<td>

							<label id="reportTime" ></label>
						</td>

					</tr>
				</table>
			</div>
		</div>
</html>
