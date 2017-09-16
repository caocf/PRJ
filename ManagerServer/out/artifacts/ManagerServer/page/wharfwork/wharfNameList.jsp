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
		
		<script type="text/javascript" src="js/business/wharfnamelist.js">
</script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		
			<div id="layer4">
				<div id="layer4_left">
				 	<img src="image/operation/update_big_hover.png" style="margin-top: 7px" onmouseover="UpdateOver(this)" onmouseout="UpdateOut(this)" 
				 	onClick="updatewharfname()" />
				 </div>
				 	
				<div id="layer4_right">
					<input type="text" value="" name="content"
						id="content"class="input_box" />
					<img alt="搜索" src="image/operation/search_normal.png"
						class="u3_img" onClick="selectwharfname()"
						onMouseOver="(this)" onMouseOut="SearchOut(this)" />
				</div>
			</div>
			<div id="showFixedTermReport"></div>
			<div id="fenye"></div>
	</body>
</html>
