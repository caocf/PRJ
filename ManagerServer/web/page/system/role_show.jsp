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

		<title>角色管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/role.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/system/role_show.js"></script>
		<script type="text/javascript" src="js/system/role_list.js"></script>
		<script type="text/javascript" src="js/system/Operation_role.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>


	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div id="u2_left">
					<p>
						<b>角色列表</b>
					</p>
					<div id="roleListDiv">
						<ul id="role_menu"></ul>
					</div>
				</div>
				<div id="u2_right">
					<div id="u2_right_content">
						<input type="hidden" id="roleId" value="1" />
						<div id="u2_right">
							<div class="u2_top">
								<a href="page/system/role_add.jsp"> <img alt="增加角色"
										src="image/operation/add_role_normal.png" class="u3_img"
										onclick="addRole()" onMouseOver="AddRoleOver(this)"
										onMouseOut="AddRoleOut(this)" /> </a>
								<img alt="删除角色" src="image/operation/delete_role_normal.png"
									class="u3_img" onClick="del_role();"
									onMouseOver="DeleteRoleOver(this)"
									onMouseOut="DeleteRoleOut(this)" />
								<img alt="编辑" src="image/operation/update_normal.png"
									class="u3_img" onClick="show_update()"
									onmouseover="UpdateOver(this)" onMouseOut="UpdateOut(this)" />
							</div>
							<div class="RoleNameDiv">
								角色名称：
								<input type="text" id="roleName" disabled="disabled" class="input_box"/>
							</div>
							<div class="post_right">
								<div id="permission_list">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
