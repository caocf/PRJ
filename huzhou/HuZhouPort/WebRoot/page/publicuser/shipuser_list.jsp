<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>公众用户管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/system/publicuser.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/publicuser/shipuser_list.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		  <script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="p1">
	    <input type="hidden" value="<%=basePath%>" id="basePath"/>
		<input type="hidden" value="<%=request.getParameter("departmentID") %>" id="departmentID" />
		<div class="u2_top">
				<div id="u2_top_left">
					<img alt="新增客户" src="image/operation/add_custom_normal.png" class="u3_img" 
						onclick="AddPublicUser()" onMouseOver="AddCustomOver(this)" onMouseOut="AddCustomOut(this)"/>
					<img alt="删除客户" src="image/operation/delete_custom_normal.png" class="u3_img" 
						onclick="DeletePublicUser()" onMouseOver="DeleteCustomOver(this)" onMouseOut="DeleteCustomOut(this)"/>
					<img alt="船舶资料管理" src="image/system/shipmanage_normal.png" class="u3_img" 
						onclick="GoShipManage()" onMouseOver="ShipManageOver(this)" onMouseOut="ShipManageOut(this)"/>
				</div>
				<div id="u2_top_right">
					<input type="text" id="search_content" class="search_input_box"/><img alt="查询" 
					src="image/operation/query_normal.png" class="u3_img" 
					onclick="SearchPublicUser('PublicUserListByShip',1)" onMouseOver="QueryOver(this)" onMouseOut="QueryOut(this)"/>
				</div>
			</div>
		
		
		<table border="0" cellspacing="0" cellpadding="0" class="listTable" id="usertable">
			<col width="10%"/><col width="10%"/><col width="20%"/><col width="30%"/><col width="30%"/>
			<tr>
				<th>
					<input type="checkbox" id="chooseAll" name="chooseAll"
						onclick="SelectAll()" />
				</th>
				<th>
					序号
				</th>
				<th>
					用户名
				</th>
				<th>
					绑定的船舶
				</th>
				<th>
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
	
</div>
	</body>
</html>
