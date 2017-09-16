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

		<title>用户管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/user_list.css" />
		<link rel="stylesheet" type="text/css" href="css/common/smallDialog.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>
		<script type="text/javascript" src="js/system/userValidator.js"></script>
		<script type="text/javascript" src="js/system/user_add.js"></script>
        <script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="js/common/Operation.js"></script>
        <script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<form id="actionName">
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
			<div class="totaldiv">
				<table cellspacing="10" cellpadding="0">
					<tr>
						<td>
							用户名：
						</td>
						<td>
							<input type="text" id="userName" name="userName"  class="input_box"/>
							<input type="hidden" id="userId" name="userId" />
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="userNameerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" name="password" id="password"  class="input_box"/>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="passworderr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							再次输入：
						</td>
						<td>
							<input type="password" id="confirmPassword"  class="input_box"/>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="confirmPassworderr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							用户部门：
						</td>
						<td colspan="2">
							<input type="hidden" value="" id="partOfDepartmentId"
								name="partOfDepartment" />
							<input type="text" value="" disabled="disabled"  class="input_box"
								id="partOfDepartmentName" name="partOfDepartmentName" />
							<img title="选择部门" src="image/operation/choose_normal.png" class="choose_img" 
							 onclick="openDepartmentTree2()"  onmouseover="ChooseOver(this)" onMouseOut="ChooseOut(this)"/>
						<font color="#FF0000">*</font>
							<span id="partOfDepartmenterr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							用户角色：
						</td>
						<td>
							<select id="partOfRole" name="partOfRole"></select>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="partOfRoleerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							真实姓名：
						</td>
						<td>
							<input type="text" value="" id="realname" name="name"  class="input_box"/>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="nameerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							职务：
						</td>
						<td>
							<select id="partOfPost" name="partOfPost"></select>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="partOfPosterr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							执法证编号：
						</td>
						<td>
							<input type="text" id="lawId" name="lawId"  class="input_box"/>
							
						</td>
						<td>
							<span id="lawIderr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							状态：
						</td>
						<td>
							<select id="StatusType" >
								<option value="1">
									在职
								</option>
								<option value="0">
									离职
								</option>
							</select>
							<div id="Status1"></div>
							<div id="Status2"></div>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="userStatuserr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							联系电话：
						</td>
						<td>
							<input type="text" value="" name="tel" id="tel"  class="input_box"/>
							<font color="#FF0000">多个号码请用逗号隔开</font>
						</td>
						<td>
							<span id="telerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>

						<td>
							电子邮件：
						</td>
						<td>
							<input type="text" value="" name="email" id="email"  class="input_box"/>
							
						</td>
						<td>
							<span id="emailerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>&nbsp;
							
						</td>
						<td>	
							<img src="image/operation/submit_normal.png" class="bt_submit"
							onclick="addUserInfo()" onMouseOver="SubmitOver_img(this)"
								onmouseout="SubmitOut_img(this)"/>
							<img src="image/operation/reset_big_normal2.png" onclick="javascript:window.location.reload();" id="bt_back" onmouseover="ResetBigOver2(this)" onmouseout="ResetBigOut2(this)" title="重置">
							
						</td>
					</tr>
				</table>
				</div>
		<!-- 部门树 -->
		<div id="cresteTree" style="display: none;">
			<div class="div_tree">
				<ul id='tree'></ul>
			</div>
			<div id="div_tree_operation">
				<img src="image/operation/sure_normal.png"
					onclick="changeUserOfDepartment()" onMouseOver="SureOver(this)"
					onmouseout="SureOut(this)" />
				<img src="image/operation/reset_small_normal.png"
					onclick="hidenDepartmentTree()" onMouseOver="ResetSmallOver(this)"
					onmouseout="ResetSmallOut(this)" title="取消" />
				<input type="hidden" id="hideDepartmentId" value="" />
				<input type="hidden" id="hideDepartmentName" value="" />
			</div>
		</div>
		</form>
	</body>
</html>
