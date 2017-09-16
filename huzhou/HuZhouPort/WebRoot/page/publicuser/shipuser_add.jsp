<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<link rel="stylesheet" type="text/css"
			href="css/system/publicuser_add.css" />
		<link rel="stylesheet" type="text/css"
			href="css/common/middelDialog2.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js">

</script>
		<script type="text/javascript" src="js/common/jquery.easyui.min.js">

</script>
<script src="js/main/iframe.js">
</script>

		<script type="text/javascript" src="js/common/inputValidator.js">

</script>
		<script type="text/javascript" src="js/publicuser/shipuser_add.js">

</script>
		<script type="text/javascript" src="js/common/Operation.js">

</script>
		<script type="text/javascript" src="js/common/CheckLogin.js">

</script>
	</head>

	<body>

		<div id="p1">
			<input type="hidden" value="<%=basePath%>" id="basePath" />
			<input type="hidden" value="<%=session.getAttribute("username")%>"
				id="LoginUser" />
			<input type="hidden" value="<%=basePath%>" id="basePath" />

			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:window.history.go(-1);"
					onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
					title="返回" />
			</div>
			<form action="AddPublicByShip" encType="multipart/form-data"
				method="post" id="add_form">
				<div class="totaldiv">
					<table cellspacing="0" cellpadding="0" id="dateList">
						<col width="80px" />
						<col width="230px" />
						<col width="*" />
						<tr>
							<th colspan="3">
								新客户注册
							</th>
						</tr>
						<tr>
							<td>
								用户名：
							</td>
							<td>
								<input type="text" id="publicUserName" name="userName"
									class="input_box" />
								<font color="#FF0000">*</font>
							</td>
							<td>
								<font color="#666">手机号码</font>
								<span id="userNameerr" class="promptSpan"></span>
								<span class="error" style="font-family:华文中宋; color:red; "> <s:if test="exception.message!=null">
										<s:property value="exception.message" />
									</s:if> <br> <s:if test="#session.loginInfo.error!=null">
										<s:property value="#session.loginInfo.error" />
									</s:if> <s:if test="#session.error!=null">
										<s:property value="#session.error" />
									</s:if> <s:if test="#session.functions.error!=null">
										<s:property value="#session.functions.error" />
									</s:if> <s:if test="errorMessages[0]!=null">
										<s:property value="errorMessages[0]" />
									</s:if> <s:else>
									</s:else> </span>
							</td>
						</tr>
						<tr>
							<td>
								密码：
							</td>
							<td>
								<input type="password" name="psd" id="password"
									class="input_box" />
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
								<input type="password" id="confirmPassword" class="input_box" />
								<font color="#FF0000">*</font>
							</td>
							<td>
								<span id="confirmPassworderr" class="promptSpan"></span>
							</td>
						</tr>
						<tr>
							<td>

							</td>
							<td>

							</td>
							<td>

							</td>
						</tr>
						<tr>
							<td class="style_top1" colspan="3">
								绑定的船舶
							</td>

						</tr>
						<tr>
							<td colspan="3">
								<img src="image/operation/btn_add-ship_normal.png"
									onclick="addShipFIle()" onMouseOver="SubmitaddInfo_img(this)"
									onmouseout="SubmitaddInfoOut_img(this)" />
								<span id="shipNameerr" class="promptSpan"></span>
								<span id="infoerr" class="promptSpan"></span>
							</td>
						</tr>
						<tr>
							<td>

							</td>
						</tr>

						<tr>
							<th colspan="3" class="th1">
								<table border="0" cellspacing="0" cellpadding="0"
									class="listTable1" id="usertable">
									<col width="20%" />
									<col width="20%" />
									<col width="30%" />
									<col width="20%" />
									<col width="10%" />
									<tr>

										<th>
											船名号
										</th>
										<th>
											材料
										</th>
										<th>
											附件
										</th>
										<th>
											<label class="operation">
												操作
											</label>
										</th>
									</tr>
								</table>
							</th>
						</tr>
						<tr>
							<td>
								&nbsp;
						</tr>
						<tr>

							<td colspan="2">
								<%--<input type="submit" value="" class="input_submit"
								onmouseover="SubmitOver_img(this)"
								onMouseOut="SubmitOut_img(this)" />
								--%>
								<img src="image/operation/submit_normal.png" class="bt_submit"
									onclick="AddPublicByShip()" onMouseOver="SubmitOver_img(this)"
									onmouseout="SubmitOut_img(this)" />
								<img src="image/operation/reset_big_normal2.png"
									onclick="javascript:window.location.reload();" id="bt_back"
									onmouseover="ResetBigOver2(this)" onMouseOut="ResetBigOut2(this)"
									title="重置" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div id="cresteData" style="display: none;">
			<div style="height: 500px;">
				<table>
					<tr>
						<td colspan="3">
							绑定船舶所需签证：
							<input type="checkbox" id="totalcheckbox"
								onclick="SelectAllBox()" />
							全选
							<font color="#FF0000">*</font>
							<span id="booklisterr" class="promptSpan"></span>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="shipbooklist">
						</td>
					</tr>
					<tr>

						<td colspan="2">
							<img src="image/operation/submit_normal.png" class="bt_submit"
								onclick="AddPublicByShip1();" onMouseOver="SubmitOver_img(this)"
								onmouseout="SubmitOut_img(this)" />
							<img src="image/operation/reset_big_normal.png"
								onclick="AddShip();" id="bt_back"
								onmouseover="ResetBigOver(this)" onMouseOut="ResetBigOut(this)"
								title="取消" />
						</td>
					</tr>
				</table>
			</div>
		</div>

	</body>
</html>
