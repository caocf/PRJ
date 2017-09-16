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
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js">

</script>
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css" />
		<script type="text/javascript" src="js/electric/channelListShow.js">
</script>

		<script type="text/javascript" src="js/common/CheckLogin.js">
</script>
	</head>

	<body>

		<input type="hidden" value="<%=basePath%>" id="basePath" />
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
				<table cellspacing="5" class="listTable1">
					<col width="10px" />
					<col width="10px" />
					<col width="*" />

					<tr>
						<td>
							航道名称：
						</td>
						<td>
							<input id="channelName" readonly="readonly" name="channelName"
								style="border: 0" />
							<input id="channelId" type="hidden" name="channelId"
								value="<%=request.getParameter("channelId")%>" />
						</td>

					</tr>
					<tr>
						<td>
							航道编码：
						</td>
						<td>
							<input type="text" readonly="readonly" id="channelCoding"
								name="channelCoding" style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							河流名称：
						</td>
						<td>
							<input id="riverName" readonly="readonly" name="riverName"
								style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							河流代码：
						</td>
						<td>
							<input id="riversCode" readonly="readonly" name="riversCode"
								style="border: 0" />
						</td>

					</tr>

					<tr>
						<td>
							起点名称：
						</td>
						<td>
							<input type="text" readonly="readonly" id="startingName"
								name="startingName" style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							终点名称：
						</td>
						<td>
							<input id="endingName" readonly="readonly" name="endingName"
								style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							航段里程：
						</td>
						<td>
							<input id="segmentmileage" readonly="readonly"
								name="segmentmileage" style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							航道水深：
						</td>
						<td>
							<input id="channelDepth" readonly="readonly" name="channelDepth"
								style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							航道宽度：
						</td>
						<td>
							<input id="channelWidth" readonly="readonly" name="channelWidth"
								style="border: 0" />
						</td>

					</tr>
					<tr>
						<td>
							航段现状技术等级：
						</td>
						<td>
							<input id="segmentTechnology" readonly="readonly"
								name="segmentTechnology" style="border: 0" />
						</td>

					</tr>
				</table>
			</div>
		</div>
</html>
