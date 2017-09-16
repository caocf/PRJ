<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath1%>">
 <title>考勤管理</title>
<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/LeaveAndOvertime/LeaveAndOverTimeList.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
 <script type="text/javascript" src="js/common/paging.js"></script>

</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" name="userId"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />

<div id="layer4">
				<div id="layer4_left">
				<a onclick="ListKind(this,1)" style="color:#0260a6">由我审批</a>
				|
				<a onclick="ListKind(this,2)">我的申请</a>
				|
				<a onclick="showLeaveAndOvertime('FindAttedanceByPermission', 1)">全部</a>		
				</div>
				<div id="layer4_right">
					<input type="text" value="" id="Content" name="Content" class="input_box"/><img alt="搜索" src="image/operation/search_normal.png" class="u3_img" 
					 onclick="SearchContent('FindAttedanceByPermission',1)"  onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
				</div>	
	</div>
	<div style="padding:0 5px 0 5px" >
	<table class="listTable" width="100%" border="0" cellspacing="0" 
			id="LeaveAndOvertimeTable">
			<col width="15%"/><col width="15%"/><col width="20%"/><col width="15%"/>
			<col width="15%"/><col width="20%"/>
			<tr>
				<th >
					申请人
				</th>
				<th >
					加班/请假/出差
				</th>
				<th >
					申请时间
				</th>
				<th >
					审批人
				</th>
				<th >
					状态
				</th>
				<th >
					操作
				</th>
			</tr>

		
</table>
</div>
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
</body>
</html>