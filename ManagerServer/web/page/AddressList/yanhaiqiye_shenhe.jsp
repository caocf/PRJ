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

		<title>内河码头审核</title>

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

		</style>
	</head>

	<body style="overflow-x: hidden">
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="style_one" class="style_top">
			<button class="btn-pt" onclick="javascript:history.go(-1);location.reload()" style="margin-top:10px;">&lt;&lt;&nbsp;返回</button>
			<span style="float: right;line-height: 36px;margin-right:20px;color: #666;">
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
			</span>
			<span class="shenpiwordspan" style="margin-left: 30px;">
				讷河<br>
				pkaqu<br>
				11012011911<br>
				船户<br>
				杭州<br>
			</span>
			<div class="style_top" style="float:left;background-color:rgb(246,247,251);border-top: solid 1px #ccc;">
				<span style="float: left;line-height: 36px;margin-left:10px;color: #333;">
					审批内容
				</span>
			</div>
			<span class="shenpiwordspan">
				企业名称：<br>
				工商营业执照号：<br>
				工商营业执照：<br>
			</span>
			<span class="shenpiwordspan" style="margin-left: 30px;">
				11012011911<br>
				船户<br>
				<div style="width: 20px;height:20px;background-color: red;float: left;margin-top:5px;"></div>
				<span style="float: left;line-height: 20px;margin-left: 10px;">
					船舶证书<br>
					<span class="clickword">预览</span>
					<span class="clickword">下载</span>
				</span>
			</span>
			<div class="style_top" style="float:left;background-color:rgb(246,247,251);border-top: solid 1px #ccc;">
				<span style="float: left;line-height: 36px;margin-left:10px;color: #333;">
					审批
				</span>
			</div>
			<div style="float: left;width: 100%;padding: 10px;">
				<label for="passradio">
					<input type="radio" name="radiobutton" id="passradio"  />&nbsp;通过
				</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="nopassradio">
					<input type="radio" name="radiobutton" id="nopassradio" />&nbsp;驳回
				</label>
			</div>
			<textarea placeholder="请输入意见说明"></textarea>
			<div style="float: left;width: 100%;margin-top:10px;">
				<button class="btn-pt" onclick="" style="float: left;margin-left:10px;width: 80px;height:30px;background-color: #0099CC;color: white;">提交</button>
				<button class="btn-pt" onclick="javascript:history.go(-1);location.reload()" style="float: left;margin-left:10px;width: 80px;height:30px;">取消</button>
			</div>
		</div>
	</body>
</html>
