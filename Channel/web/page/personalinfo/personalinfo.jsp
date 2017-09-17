<%@page import="com.channel.model.user.CXtUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	CXtUser user = ((CXtUser) session.getAttribute("user"));
	String username = null;
	int userid = -1;
	if (user != null) {
		username = user.getUsername();
		userid = user.getId();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>个人设置</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css"
	href="common/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="common/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="page/common/css/common.css">
<link rel="stylesheet" type="text/css"
	href="page/personalinfo/personalinfo.css">

<script type="text/javascript" language="javascript"
	src="common/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript"
	src="common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/common.js"></script>
<script type="text/javascript" language="javascript"
	src="page/common/js/constants.js"></script>
<script type="text/javascript" language="javascript"
	src="page/personalinfo/personalinfo.js"></script>

</head>
<body>
	<input type="hidden" id="username" value="<%=username%>">
	<input type="hidden" id="userid" value="<%=userid%>">
	<input type="hidden" id="basePath" value="<%=basePath%>">

	<div class="container-fluid">
		<div class="row navrow">
			<div class="col-xs-2">
				<i onclick="window.close();" class="icon-circle-arrow-left icon-2x pull-left"></i> <label
					class="pull-left">个人设置</label>
			</div>
		</div>
		<div class="row navline"></div>

		<div class="row navcontent shadow">
			<table class="table table-bordered table-striped">
				<tr style="height:40px;">
					<td class="text-left" colspan="2">
							<strong><p style="font-size: 14px; color:#333333;">用户资料</p></strong>
						</td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" width="15%" style="line-height: 29px;">用户名</td>
					<td class="text-left" width="100%" id="tdUsername" style="line-height: 29px;"></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">密码</td>
					<td class="text-left" style="line-height: 29px;"><label style="float:left;line-height:29px;">******&nbsp;&nbsp;</label>
						<button class="btn btn-default" data-toggle="modal"
							data-target="#myModal" style="float:left;margin-top:6px;font-size: 14px;color:#337ab7;">修改密码</button></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right"  style="line-height: 29px;">姓名</td>
					<td class="text-left" id="divName"><input style="line-height: 29px;float:left"
						class="editInput" readonly="readonly" type="text" id="tdName"
						value=""><label id="tdNameErr" class="hide"
						style="float:left;margin-left:20px;color:red;font-size:12px;"></label></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right"  style="line-height: 29px;">所属部门</td>
					<td class="text-left" id="tdDpt" style="line-height: 29px;"></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">职务</td>
					<td class="text-left" id="tdTitle" style="line-height: 29px;"></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">在职状态</td>
					<td class="text-left" id="tdJstatus" style="line-height: 29px;"></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">联系电话</td>
					<td class="text-left" id="divPhone"><input style="line-height: 29px;float:left"
						class="editInput" readonly="readonly" type="text" id="tdPhone"
						value=""><label id="tdPhoneErr" class="hide"
						style="float:left;margin-left:20px;color:red;font-size:12px;"></label></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">电子邮件</td>
					<td class="text-left" id="divEmail"><input style="line-height: 29px;float:left"
						class="editInput" readonly="readonly" type="email" id="tdEmail"
						value=""><label id="tdEmailErr" class="hide"
						style="float:left;margin-left:20px;color:red;font-size:12px;"></label></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">执法证编号</td>
					<td class="text-left" id="divLawid"><input style="line-height: 29px;float:left"
						class="editInput" readonly="readonly" type="text" id="tdLawid"
						value=""><label id="tdLawidErr" class="hide"
						style="float:left;margin-left:20px;color:red;font-size:12px;"></label></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-right" style="line-height: 29px;">角色</td>
					<td class="text-left" id="divRoles"><input style="line-height: 29px;float:left;width: 80%"
															   class="editInput" readonly="readonly" type="text" id="tdRoles"
															   value=""><label id="tdRolesErr" class="hide"
																			   style="float:left;margin-left:20px;color:red;font-size:12px;"></label></td>
				</tr>
				<tr style="height:46px;">
					<td class="text-left" colspan="2">
						<div class="row ">
							<div class="col-xs-2" id="divEditBtn">
								<input type="button" class="btn btn-primary" id="btnEdit"
									value="修改用户资料">
							</div>
							<div class="col-xs-1  hide" id="divEditSave">
								<input type="button" class="btn btn-primary btn-block"
									id="btnSaveEdit" value="保存">
							</div>
							<div class="col-xs-1  hide" id="divEditCancel">
								<input type="button" class="btn btn-primary btn-block"
									id="btnCancel" value="取消" onclick="window.location.reload();">
							</div>

						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row" style="padding-top:15px;">
							<div class="col-xs-1 text-right">
								<p style="margin-top:7px;">原密码</p>
							</div>
							<div id="divOriPwd" class="col-xs-3 text-left">
								<input id="inputOriPwd" type="password" class="form-control">
							</div>
							<div class="col-xs-2 text-left">
								<label class="hide" id="inputOriPwdErr"></label>
							</div>
						</div>
						<div class="row" style="padding-top:15px;">
							<div class="col-xs-1 text-right">
								<p style="margin-top:7px;">新密码</p>
							</div>
							<div id="divNewPwd" class="col-xs-3 text-left">
								<input id="inputNewPwd" type="password" class="form-control">
							</div>
							<div class="col-xs-2 text-left">
								<label class="hide" id="inputNewPwdErr"></label>
							</div>
						</div>
						<div class="row" style="padding-top:15px;">
							<div class="col-xs-1 text-right">
								<p style="margin-top:7px;">确认密码</p>
							</div>
							<div id="divANewPwd" class="col-xs-3 text-left">
								<input id="inputANewPwd" type="password" class="form-control">
							</div>
							<div class="col-xs-2 text-left">
								<label class="hide" id="inputANewPwdErr"></label>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="btnSavePwd" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
