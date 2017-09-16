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
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css" />
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<script type="text/javascript"
			src="js/electric/electricVisaListShow.js">
</script>

<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="see_container">
		<div id="layer1" style="position: static;float: none;clear: both">
			<img src="<%=basePath%>image/operation/back_small_normal.png"
				onclick="javascript:window.history.go(-1);"
				onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
				title="返回" />
		</div>
		<div  style="position: static;float: none;clear: both">
		<table cellspacing="5" class="listTable1">

			<tr>
				<td>

					<input id="visaID" type="hidden" name="visaID"
						value="<%=request.getParameter("visaID")%>" />
				</td>

			</tr>
			<tr>
				<td>
					船舶名称：
				</td>
				<td>
					<%--<input id="signUser" type="hidden" name="signUser">
					--%>
					<input id="shipName" readonly="readonly" name="shipName"
						style="border: 0" />
				</td>

			</tr>
			<tr>
				<td>
					起运港：
				</td>
				<td>
					<input type="text" readonly="readonly" id="startingPort"
						name="startingPort" style="border: 0" />
				</td>

			</tr>
			<tr>
				<td>
					目的港：
				</td>
				<td>
					<%--<input id="signLocation" type="hidden" name="signLocation">
					--%>
					<input id="arrivalPort" readonly="readonly" name="arrivalPort"
						style="border: 0" />
				</td>

			</tr>
			<tr>
				<td>
					载货：
				</td>
				<td>
					<%--<input id="signLocation" type="hidden" name="signLocation">
					--%>
					<input id="cargoTypes" readonly="readonly" name="cargoTypes"
						style="border: 0" />
				</td>

			</tr>
			<tr>
				<td>
					进/出港：
				</td>
				<td>
					<%--<input id="signLocation" type="hidden" name="signLocation">
					--%>
					<input id="mark" readonly="readonly" name="mark" style="border: 0" />
				</td>

			</tr>
			<tr>
				<td>
					状态：
				</td>
				<td>
					<%--<input id="signLocation" type="hidden" name="signLocation">
					--%>
					<input id="visaStatus" readonly="readonly" name="visaStatus"
						style="border: 0" />
				</td>

			</tr>
		</table>
		<div id="layer2">
			<div>
				审核
			</div>
		</div>
		<table cellspacing="5" class="listTable" >
			<tr>
				<td>
					<input type="radio" name="visaStatus" id="visaStatus1" value="已签证" checked="checked" />签证
					&nbsp;&nbsp;&nbsp;
					
					<input type="radio" name="visaStatus" id="visaStatus2" value="未签证" align="right"/>拒绝签证
				</td>
				
			</tr>
			<tr>
				<td >
					<textarea rows="4" cols="30" id="visaContent" name="visaContent"></textarea>
				</td>
			</tr>		
				<tr>
				<td>
					<input type="button" value="提交" onClick="applyVisaInfo();"/>
					<input type="button" value="取消" onClick="javascript:window.history.go(-1);"/>
				</td>
			</tr>	
		</table>
		</div>
		</div>
</html>
