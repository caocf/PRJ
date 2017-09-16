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

		<title>个人信息修改</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/person_update.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>
		<script type="text/javascript" src="js/system/publicValidator.js"></script>
		<script type="text/javascript" src="js/system/person_update.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
         <script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<form id="actionName">
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=request.getParameter("UserId")%>"
			id="UserId" />
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
			<div class="totaldiv">
				<table cellspacing="10" cellpadding="0">
				<tr>
						<td colspan="2">
						<img class="imgIcon" src="<%=basePath%>image/person_pic.png"/>
						</td>
					</tr>
					<tr>
						<td>
							用户名：
						</td>
						<td>
							<label class="userName_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							部门：
						</td>
						<td>
							<label class="deparment_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							状态：
						</td>
						<td>
							<label class="userStatus_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							职务：
						</td>
						<td>
							<label class="post_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					
					<tr>
						<td>
							角色：
						</td>
						<td>
							<label class="role_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							执法证编号：
						</td>
						<td>
							<label class="lawId_text" ></label>													
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							密码：
						</td>
						<td>
							<input type="password" name="password" id="password" class="input_box"/>
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
							<input type="password" id="confirmPassword" class="input_box"/>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="confirmPassworderr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							真实姓名：
						</td>
						<td>
							<input type="text" value="" id="realname" name="name" class="input_box"/>
							<font color="#FF0000">*</font>
						</td>
						<td>
							<span id="nameerr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td>
							联系电话：
						</td>
						<td>
							<input type="text" value="" name="tel" id="tel" class="input_box"/>
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
							<input type="text" value="" name="email" id="email" class="input_box"/>
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
								onclick="updateUserInfo()" onMouseOver="SubmitOver_img(this)"
									onmouseout="SubmitOut_img(this)"/>
								<img src="image/operation/reset_big_normal2.png"
							onclick="javascript:window.location.reload();" id="bt_back"
								onmouseover="ResetBigOver2(this)" onMouseOut="ResetBigOut2(this)"
								title="重置" />
						</td>
					</tr>
				</table>
				<input type="hidden" class="userName" name="userName" />
				<input type="hidden" id="userId" name="userId" />
				<input type="hidden" id="partOfDepartmentId" name="partOfDepartment" />
				<input type="hidden" id="lawId" name="lawId"/>
				<input type="hidden" id="userStatus" name="userStatus"/>
				<input type="hidden" id="partOfPost" name="partOfPost"/>
				<input type="hidden" id="partOfRole" name="partOfRole"/>
				</div>

		</form>
	</body>
</html>
