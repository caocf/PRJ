<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManageSelect.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/system/system_left.js"></script>
  </head>
  
  <body>
    <input type="hidden" value="<%=request.getParameter("menu_number") %>" id="menu_number" />
	<div class="left_I1" id="left_I1">
		<div class="left_no_select" id="left_no_select1" >
			<label class="left_name">系统设置</label>
		</div>
			<ul id="left_select_child1" class="system_left_select_child" style="display:none;">
				<li class="li_select2" id="roleselect" onclick="RoleManage()">角色管理</li>
				<li class="li_select1" id="rolegroupselect" onclick="RoleGroupManage()">权限组管理</li>
				<li class="li_select3" id="logselect" onclick="LogManage()">系統日志</li>
				<li class="li_select4" id="videomantain" onclick="VideomantainManager()">视频维护</li>
				<li class="li_select5" id="postmantain" onclick="PostmantainManager()">职位维护</li>
			</ul>
		<div class="left_no_select" id="left_no_select2" >
			<label class="left_name">个人设置</label>
		</div>
			<ul id="left_select_child2" class="system_left_select_child" style="display:none;">
				<li class="li_select1" id="personalselect" onclick="PersonalData()">个人资料</li>
				<!-- <li class="li_select2" id="headselect" onclick="HeadPicture()">头像设置</li> -->
				<li class="li_select3" id="passwordselect" onclick="ModifyPassword()">修改密码</li>
			</ul>
	</div>
</body>
</html>
