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

		<title>详情查看</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/huzhoumain.css">
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/AddressList/neihe_xiangqing.js"></script>
		<script type="text/javascript" src="js/homePage.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<style type="text/css">
			.shenpiwordspan{
				line-height: 36px;
				text-align: left;
				float: left;
				margin-left:20px;
			}
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
			.listTable th{
				background-color: rgb(240,245,248)!important;
			}

		</style>
	</head>

	<body style="overflow-x: hidden">
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="style_one" class="style_top">
			<button class="btn-pt" onclick="javascript:history.go(-1);location.reload()" style="margin-top:10px;">&lt;&lt;&nbsp;返回</button>
			<button class="btn-pt" onclick="changestatus()" style="margin-top:10px;" id="statusbtn" >禁用</button>
			<span style="float: right;line-height: 36px;margin-right:20px;color: #666;" id="xingqingtitle">
				注册审批&nbsp;&gt;&nbsp;内河用户&nbsp;&gt;&nbsp;审批
			</span>
		</div>
		<div style="float:left;width: 100%;">
			<div class="style_top" style="float:left;background-color:rgb(246,247,251);border-top: solid 1px #ccc;">
				<span style="float: left;line-height: 36px;margin-left:10px;color: #333;">
					用户基本信息
				</span>
			</div>
			<span class="shenpiwordspan">
				区域：<br>
				用户名：<br>
				手机号：<br>
				用户类型：<br>
				所在城市：<br>
				帐号状态：
			</span>
			<span class="shenpiwordspan" style="margin-left: 30px;" id="xqspan">
			</span>
			<div class="style_top" style="float:left;background-color:rgb(246,247,251);border-top: solid 1px #ccc;">
				<span style="float: left;line-height: 36px;margin-left:10px;color: #333;">
					绑定的船舶
				</span>
			</div>
			<div style="padding: 20px 10px;float: left;">
				<table class='listTable' cellpadding="0" cellspacing="0" style="border:solid 1px #ccc;border-collapse: collapse;">
					<col width="10%" /><col width="25%" /><col width="35%" /><col width="30%" />
					<tr>
						<th class="tdborder firsttdorth">序号</th>
						<th class="tdborder ">船名号</th>
						<th class="tdborder ">船舶登记号</th>
						<th class="tdborder ">附件</th>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
