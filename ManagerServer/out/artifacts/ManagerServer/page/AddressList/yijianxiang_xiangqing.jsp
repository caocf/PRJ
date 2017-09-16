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

		<title>意见箱详情</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/huzhoumain.css">
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/homePage.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<style type="text/css">
			label{
				cursor: pointer;
			}
			input[type=radio]{
				position: relative;
				top:3px;
			}
			textarea{
				float: left;
				padding: 10px;
				width: 90%;
				margin-left:10px;
				resize: none;
				height:100px;
				border: solid 1px #ccc;
			}
			table{
				width: 100%;
				float: left;
				border-collapse: collapse;
			}
			table td{
				text-align: left;
				padding-left: 10px;
				height:40px;
				line-height: 40px;
				border: solid 1px #cccccc;
			}
		</style>
	</head>

	<body style="overflow-x: hidden">
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="style_one" class="style_top">
			<button class="btn-pt" onclick="javascript:history.go(-1);location.reload()" style="margin-top:10px;">&lt;&lt;&nbsp;返回</button>
			<button class="btn-pt"  style="margin-top:10px;">删除</button>
			<span style="float: right;line-height: 36px;margin-right:20px;color: #666;">
				意见箱&nbsp;&gt;&nbsp;详情
			</span>
		</div>
		<div style="float:left;width: 100%;">
			<table cellpadding="0" cellspacing="0" >
				<col width="20%" /><col width="80%" />
				<tr>
					<td><b>城市：</b></td>
					<td>杭州</td>
				</tr>
				<tr>
					<td><b>联系电话：</b></td>
					<td>杭州</td>
				</tr>
				<tr>
					<td><b>内容：</b></td>
					<td>杭州</td>
				</tr>
				<tr>
					<td><b>时间：</b></td>
					<td>杭州</td>
				</tr>
				<tr>
					<td><b>反馈：</b></td>
					<td>杭州</td>
				</tr>
			</table>
		</div>
	</body>
</html>
