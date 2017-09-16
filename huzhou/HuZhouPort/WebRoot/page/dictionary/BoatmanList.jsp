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

		<title>船员配置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/profession/profession_information.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
		<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/dictionary/boatmanList.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="<%=basePath%>js/common/paging.js"></script>




	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div class="profession_top">
					<img alt="新增" src="image/operation/add_know_normal.png" class="profession_addbutton" 
						onclick="OpenAddDiv()" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)"/>
					<img alt="搜索" src="image/operation/search_normal.png" class="profession_searchbutton" 
					onclick="SearchBoatmanKind('ShowBoatmanKindListByPage',1)" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
					<input type="text" id="content_search"  class="profession_search_input"/>
				</div>
				<table class=listTable cellpadding="0" cellspacing="0">
					<col width="20%" /><col width="40%" /><col width="40%" />
					<tr>
						<th>编号</th>
						<th>职位</th>
						<th>操作</th>
					</tr>
				</table>
				<div class="User_S4" id="pageDiv">
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
		</div>
		 <!-- 新增或编辑 -->
		  <div class="dialog" id="BoatmanKindDiv" style="display:none;width:360px;height:150px;">
		  		<div style="padding:0 0 0 10px;background: #eaeaea;height:30px;line-height: 30px;color:#333;font-weight: bold" id="dialogtitle">
		  			
		  		</div>
		  		<div style="height:30px;margin:20px 0 0 20px;">
		  			<input type="hidden" class="input_txt" id="boatmanKindID" />
		  			<span style="width: 80px;display:inline-block;">职位</span>
		  			<input type="text" class="input_txt" id="boatmanKindName" />
		  		</div>
		  		
		  		<div style="margin-right:10px;margin-top: 16px;text-align: center;">
		  			<img src="image/operation/sure_normal.png" onClick="UpdateOrSaveBoatmanKind()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    		<img src="image/operation/reset_small_normal.png" style="width:70px;height:28px;" onClick="closeDialogDiv()" id="bt_back" onMouseOver="ResetSmallOver(this)" onMouseOut="ResetSmallOver(this)" title="取消"/>
		  		</div>
		  </div>
	</body>
</html>
