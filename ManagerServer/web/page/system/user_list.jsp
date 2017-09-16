<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
		<link rel="stylesheet" type="text/css" href="css/common/smallDialog.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/system/user_list.js"></script>
		 <script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		  <script type="text/javascript" src="js/system/Operation_user.js"></script>
		   <script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="u2_right_content">
	    <input type="hidden" value="<%=basePath%>" id="basePath"/>
		<input type="hidden" value="<%=request.getParameter("departmentID") %>" id="departmentID" />
		<div class="u2_top">
				<div id="u2_top_left">
					<img alt="新增用户" src="image/operation/add_user_normal.png" class="u3_img" 
						onclick="addUser()" onMouseOver="AddUserOver(this)" onMouseOut="AddUserOut(this)"/>
					<img alt="调整用户" src="image/operation/update_user_normal.png" class="u3_img" 
						onclick="ChangeUserStatusByList()" onMouseOver="AdjustStateOver(this)" onMouseOut="AdjustStateOut(this)"/>
				</div>
				<div id="u2_top_right">
					<select onChange="changeList('UserList',1)" id="List_select" class="search_select_box">
						<option value="all">全部</option>
						<option selected="selected" value="inPost">在职</option>
						<optgroup id="optgroup1">
						</optgroup>
						<option value="outPost">离职</option>
						<optgroup id="optgroup2">
						</optgroup>
					</select>
					<input type="text" id="search_content" class="search_input_box"/><img alt="查询" 
					src="image/operation/query_normal.png" class="u3_img" 
					onclick="searchUser('UserList',1)" onMouseOver="QueryOver(this)" onMouseOut="QueryOut(this)"/>
				</div>
			</div>
		
		
		<table border="0" cellspacing="0" cellpadding="0" class="listTable" id="usertable">
			<col width="5%"/><col width="7%"/><col width="10%"/><col width="13%"/>
			<col width="10%"/><col width="15%"/><col width="15%"/><col width="7%"/><col width="18%"/>
			<tr>
				<th>
					<input type="checkbox" id="chooseAll" name="chooseAll"
						onclick="chooseAllInfo()" />
				</th>
				<th>
					序号

				</th>
				<th>
					姓名
				</th>
				<th>
					部门
				</th>
				<th>
					职务
				</th>
				<th>
					电话
				</th>
				<th>
					邮箱
				</th>
				<th>
					状态
				</th>
				<th style='width:15%;'>
					<label class="operation">操作</label>
				</th>
			</tr>

		</table>
       <label class="loadingData" style="display:none">数据加载中·····</label>
       <!-- 用于显示页码的div -->
	<div id="pageDiv">
		<p>
			共<span id="allTotal"></span>条
			<span class="firstBtnSpan"></span>
			<span class="prevBtnSpan"></span>
			<span class="pageNoSpan"></span>页
			<span class="nextBtnSpan"></span>
			<span class="lastBtnSpan"></span>
			<span class="gotoPageSpan"></span>
		</p>
	</div>
	
	<!-- 调整用户状态 -->
		<div id="changeUserStatus" style="display:none;height:160px;">
		
		<div class="UserStatusDIV"></div>
		<div class="changeUserStatus_font">
		<img src="image/operation/sure_normal.png" onClick="changeUserStatus()"
				onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
		<img src="image/operation/reset_small_normal.png" onClick="hiden()"
				onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
		<input type="hidden" id="hideStatusId" />
		<input type="hidden" id="hideUserId" />
		</div>
		
		</div>
	
</div>
	</body>
</html>
