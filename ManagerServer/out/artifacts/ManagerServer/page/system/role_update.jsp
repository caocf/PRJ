<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>角色管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/role.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/system/role_update.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" id="roleId" value="<%=request.getParameter("roleId") %>"/>
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:window.history.go(-1);" title="返回"
					onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)" />
			</div>
			<div id="show_addDIV">				
			<div class="nameDiv">
				角色名称：
				<input type="text"  id="roleName" name="roleName"
					class="input_box" />
				<font color="red">*</font>
			</div>
			<form>
			<div class="post_right">
				<div id="permission_list">
				</div>
				<div id="submit_div">
					<input type="checkbox" id="choose_Allcheckbox" class="choose_All"
						onclick="chooseAll()" />
					<label>
						全选
					</label>
					<img alt="提交" src="image/operation/submit_normal.png"
						class="input_submit" onClick="updata_role()"
						onmouseover="SubmitOver_img(this)"
						onMouseOut="SubmitOut_img(this)" />

					<!--<input type="reset" value="" class="input_reset"
						onmouseover="SubmitOver_button(this)"
						onMouseOut="SubmitOut_button(this)" />
				--></div>
			</div>
		</form>
		</div>
		
	</body>
</html>
