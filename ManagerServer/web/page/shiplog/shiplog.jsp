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
<script src="js/shiplog/shiplog.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
 <script type="text/javascript" src="js/common/paging.js"></script>
 <script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>

</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId" name="userId"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />

<div id="layer4" style='width:100%;'>
				<div id="layer4_left" style='width:100px;'>
					船舶日志		
				</div>
				<div id="layer4_right" style='margin-right:20px;width:600px;'>
					时间：<input onclick="WdatePicker()" type="text" value="" id="starttime" name="Content" class="input_box timeinput"/>&nbsp;至
					<input onclick="WdatePicker()" type="text" value="" id="endtime" name="Content" class="input_box timeinput"/>
					<img alt="搜索" src="image/operation/search_normal.png" class="u3_img" 
					 onclick="showLeaveAndOvertime('queryShipQueryLogs',1)"  onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)" style='cursor:pointer;'/>
				</div>	
	</div>
	<div style="padding:0 5px 0 5px" >
	<table class="listTable" width="100%" border="0" cellspacing="0" 
			id="LeaveAndOvertimeTable">
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="15%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<tr>
				<th >
					查询人
				</th>
				<th >
					查询人地点（经纬度）
				</th>
				<th >
					船舶名称
				</th>
				<th >
					船舶九位码
				</th>
				<th >
					查询时间
				</th>
				<th >
					签证是否异常
				</th>
				<th >
					缴费是否异常
				</th>
				<th >
					证书是否异常
				</th>
				<th >
					违章是否异常
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