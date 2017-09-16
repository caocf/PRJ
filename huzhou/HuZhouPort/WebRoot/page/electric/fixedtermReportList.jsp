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

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css"
			href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css">
		<link rel="stylesheet" type="text/css" href="css/electric/fixedtermReprt.css"/>
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js">
</script>
		<script type="text/javascript" src="js/electric/fixedtermReortList.js">
</script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=session.getAttribute("username")%>"
			id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="layer3">
		<div id="layer_top"><div>定期签证</div></div>
			<div id="layer4">
				<div id="layer4_left">
				<img src="image/operation/add_know_hover.png" style="margin-top: 7px" onmouseover="addkonwOver(this)" onmouseout="addkonwOut(this)" 
				 	onClick="javascript:location.href='<%=basePath%>page/electric/fixedtermReportAdd.jsp'" />
				 	<img src="image/operation/update_big_hover.png" style="margin-top: 7px" onmouseover="addkonwOver(this)" onmouseout="addkonwOut(this)" 
				 	onClick="updateFixedTermReport('<%=basePath%>page/electric/fixedtermReportUpdate.jsp')" />
				 	<img src="image/operation/delete_big_hover.png" style="margin-top: 7px" onmouseover="addkonwOver(this)" onmouseout="addkonwOut(this)" 
				 	onClick="deleteInfo()" /></div>
				 	
				<div id="layer4_right">
					<input type="text" value="" name="conditionName"
						id="conditionId"class="input_box" />
					<img alt="搜索" src="image/operation/search_normal.png"
						class="u3_img" onClick="getConditionFixedTermReportInfo('findFixedTermReportInfo',1)"
						onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)" />
				</div>
			</div>
			<div id="showFixedTermReport"></div>
		</div>
	</body>
</html>
