<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.huzhouport.portDynmicNews.action.ModelActionPortDynmicNews"%>
<%@page import="com.huzhouport.portDynmicNews.model.ModelPortDynmicNews"%>
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

		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="JSP,keyword2,keyword3">
		<meta http-equiv="description" content="">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css"/>
		<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
		
		
		<script src="js/common/jquery-1.5.2.min.js"></script>

		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>
		<script type="text/javascript" src="js/common/dialog_paging.js"></script>
		<script type="text/javascript" src="js/electricReport/electricReport.js"></script>
		</head>
<style>
#boatmanInfoUl{
padding:0 10px;
}
#boatmanInfoUl li{
	width:100%;
	height:30px;
	overflow-x:hidden;
	padding-left:20px;
	line-height: 30px;
	vertical-align: middle;
	cursor: pointer;
}
#boatmanInfoUl li:HOVER,.newreport:HOVER {
	color: #2290e5;
	text-decoration: underline;
}
#dialog_pageDiv {
text-align: right;
margin: 0;
padding: 6px 15px 6px 0;
background: #fff;
border: none;
}

</style>
	<body><div id="fullbg"></div>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="layer3">
			<div id="layer4">
				<div id="layer4_left">
					<a onclick="selectBoatmanInfo()" class="newreport">新增船户航行电子报告</a>
				</div>
				<div id="layer4_right">
					<input type="text" value="" class="input_box" name="Content"
						id="Content" /><img alt="搜索" src="image/operation/search_normal.png"
						class="u3_img" onClick="selectContent('ElectricReportListByInfo', 1)" 
						onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)" />
				</div>
			</div>
			<div id="layer5">
				

				<table width="100%" border="0" cellspacing="0" class="listTable"
					id="electricReportTable">
					<tr>
				<th width="20%">
					船舶名称
				</th>
				<th width="15%">
					报告类型
				</th>
				<th width="25%">
					本次作业码头
				</th>
				<th width="15%">
					预计进出港时间
				</th>
				<th width="15%">
					报告时间
				</th>
				<th width="10%">
					操作
				</th>
			</tr>

		</table>
				<div id="NoDataTitle"></div>
			</div>

			
			<div id="pageDiv">
				<p>
					共
					<span id="allTotal"></span>条
					<span class="firstBtnSpan"></span>
					<span class="prevBtnSpan"></span>
					<span class="pageNoSpan"></span>页
					<span class="nextBtnSpan"></span>
					<span class="lastBtnSpan"></span>
					<span class="gotoPageSpan"></span>
				</p>
			</div>
		</div>
	<!-- 选择船户信息 -->
    <div class="dialog" id="selectBoatManInfoDiv" style="display:none;width:300px;height:400px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			<label style="float: left;display: block;width: 100px;margin-left: 16px;">选择船户</label><label  class="newreport" onclick="closesboatInfoDiv()" style="float: right;margin-right: 10px;">关闭</label>
		</div>
		<div style="margin:5px 10px;">
    		<input class="input_box" id="SearchBoatman"/><img alt="搜索" src="image/operation/search_normal.png" class="u3_img" onclick="SearchBoatMan('PublicUserListByShip', 1)" onmouseover="SearchOver(this)" onmouseout="SearchOut(this)"/>
	    </div>
		<div style="height:300px;margin:0;">
		  	<ul class="boatmanInfoUl" id="boatmanInfoUl">
		  		
		  	</ul>
		</div>
		<div id="dialog_pageDiv">
				<p>
					共
					<span id="dialog_allTotal"></span>条
					<span class="dialog_firstBtnSpan"></span>
					<span class="dialog_prevBtnSpan"></span>
					<span class="dialog_pageNoSpan"></span>页
					<span class="dialog_nextBtnSpan"></span>
					<span class="dialog_lastBtnSpan"></span>
					<span class="dialog_gotoPageSpan"></span>
				</p>
		</div>
    </div>
		
	</body>
</html>
