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
		<link rel="stylesheet" type="text/css"
			href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css">
		<script src="js/common/jquery-1.5.2.min.js">
</script>
		<script src="js/common/paging.js">
</script>
		<script type="text/javascript" src="js/attendace/sinList.js">
</script>

		<script type="text/javascript" src="js/common/inputValidator.js">
</script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<div id="layer3">
			<div id="layer4">
				<div id="layer4_left">
					<a onclick="changSinState(0)" id='signInfo_0'/> 签到 </a>|
					<a onclick="changSinState(1)" id='signInfo_1'/> 签退 </a>
				</div>
				<div id="layer4_right">
					<input type="text" value="" class="input_box" name="conditionName"
						id="conditionId" /><img alt="搜索" src="image/operation/search_normal.png"
						class="u3_img" onclick="getConditionSignsInfo('showSignInfo',1)"
						onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)" />
					
				</div>
			</div>
			<div id="layer5">
				

				<table width="100%" border="0" cellspacing="0" class="listTable"
					id="signTable">
					<tr>
						<th width="15%" id="pmark">
							签到人
						</th>
						<th width="30%" id="tmark">
							签到时间
						</th>
						<th width="35%" id="lmark">
							签到地点
						</th>
						<th width="10%">
							状态
						</th>
						<th width="10%">
							操作
						</th>
						
					</tr>
				</table>
				<div id="NoDataTitle"></div>
			</div>
			<!-- 用于显示页码的div -->
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
	</body>
</html>
