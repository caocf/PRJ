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
		<link rel="stylesheet" type="text/css" href="css/system/publicuser_add.css" />
			<link rel="stylesheet" type="text/css" href="css/common/middelDialog2.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/system/publicuser.css" />
		

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>		
		<script type="text/javascript" src="js/publicuser/shipuser_update.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
        <script type="text/javascript" src="js/common/CheckLogin.js"></script>
        <script type="text/javascript" src="js/common/jquery.easyui.min.js">
</script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
			<div class="totaldiv">
			<form id='updateAction' action='UpdatePublicInfoByShip' encType="multipart/form-data"
				method="post" >
			<input type="hidden" value="<%=request.getParameter("publicId")%>"
			id="publicId" name="userId"/>
			<table cellspacing="0" cellpadding="0" id="dateList" >
				<col width="80px"/><col width="230px"/><col width="*"/>
				<tr>
						<th colspan="3">
		                   	客户修改	
						</th>
					</tr>
					<tr>
						<td>
							用户名：
						</td>
						<td>
							<input type="hidden" id="publicUserName" name="userName" class="input_box"/>
							<span id="userName1"></span>
							<font color="#FF0000">*</font>
							<font color="#666">手机号码</font>
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
							<input type="password" name="psd" id="password"  class="input_box"/>
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
							<td class="style_top1" colspan="3">
								绑定的船舶
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<img src="image/operation/btn_add-ship_normal.png"
									onclick="addShipFIle()" onMouseOver="SubmitaddInfo_img(this)"
									onmouseout="SubmitaddInfoOut_img(this)" />
									<span id="infoerr" class="promptSpan" ></span>

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
							<img src="image/operation/submit_normal.png" class="bt_submit"
							onclick="UpdatePublicByShip()" onMouseOver="SubmitOver_img(this)"
								onmouseout="SubmitOut_img(this)"/>
							<img src="image/operation/reset_big_normal2.png"
							onclick="javascript:window.location.reload();" id="bt_back"
								onmouseover="ResetBigOver2(this)" onMouseOut="ResetBigOut2(this)"
								title="重置" />
						</td>
					</tr>
				</table>
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
