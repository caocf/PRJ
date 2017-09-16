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
 <title>船舶日志</title>
<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/ais/ais.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
 <script type="text/javascript" src="js/common/paging.js"></script>
</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" name="userId"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />

<div style="height:56px;border-bottom: 1px solid #c9d9e9;background-color:#f5f5f5">
<label style="font-size:17px;line-height: 56px;vertical-align: middle;margin-left:10px">九位码核查</label>
		<img src="<%=basePath1%>image/operation/back_small_normal.png" style="float:right; line-height: 56px"
				onclick="javascript:window.history.go(-1);"
				onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
				title="返回" />
	</div>

	<div style="padding:2px 5px 0 5px" >
	<table class="listTable" width="100%" border="0" cellspacing="0" 
			id="LeaveAndOvertimeTable">
			<col width="20%"/>
			<col width="20%"/>
			<col width="15%"/>
			<col width="20%"/>
			<col width="10%"/>
			<col width="15%"/>
			<tr>
				<th >
					船舶名称
				</th>
				<th >
					九位码名
				</th>
				<th >
					核查人
				</th>
				<th >
					补充时间
				</th>
					<th >
					审核情况
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