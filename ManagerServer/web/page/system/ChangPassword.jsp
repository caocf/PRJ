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

		<title>修改密码</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/changPassword.css" />
		<script src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/system/changPassword.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>
	</head>

	<body>
			<input type="hidden"
				value="<%=(String) session.getAttribute("username")%>" id="loginname" />
			<input type="hidden"
				value="<%=session.getAttribute("userId")%>" id="userId" />
			<input type="hidden" value="<%=basePath%>" id="basePath" />	
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
			<div id="content">
				<table id="dialogTable" cellpadding="0" cellspacing="0">
				<col width="80px"/><col/>
					<tr>
						<td>
							原密码：
						</td>
						<td>
							<input type="password" id="oldPwd" class="input_box"/>
							<font color="red">*</font>
							<label id="oldPwderr"></label>
						</td>
					</tr>
					<tr>
						<td>
							新密码：
						</td>
						<td>
							<input type="password" id="newPwd" class="input_box"/>
							<font color="red">*</font>
							<label id="newPwderr"></label>
						</td>
					</tr>
					<tr>
						<td>
							确认密码：
						</td>
						<td>
							<input type="password" id="repeatnewPwd" class="input_box"/>
							<font color="red">*</font>
							<label id="repeatnewPwderr"></label>
						</td>
					</tr>
					<tr>
						<td>&nbsp;
							
						</td>
						<td>
							<img src="image/operation/submit_normal.png" class="bt_submit"
								onclick="changeRepeatPWD(this)" onMouseOver="SubmitOver_img(this)"
									onmouseout="SubmitOut_img(this)"/>
							<img src="image/operation/reset_big_normal2.png"
								onclick="closeDialog()" id="bt_back"
								onmouseover="ResetBigOver2(this)" onMouseOut="ResetBigOut2(this)"
								title="重置" />
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>

