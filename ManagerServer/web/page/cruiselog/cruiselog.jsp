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

<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/cruiselog/cruiselog.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>

</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />

	
	<div id="layer4">
				<div id="layer4_right">
				<input type="text" value="" id="Content" name="Content" class="input_box"/><img alt="搜索" src="image/operation/search_normal.png" class="u3_img" 
					 onclick="selectContent()"  onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
				</div>	
	</div>
	<div style="padding:0 5px 0 5px" >
	<table width="100%" border="0" cellspacing="0" class="listTable"
			id="cruiselogTable">
			<tr>
				<th>
					巡航区域
				</th>
				<th>
					巡航人员
				</th>
				<th>
				        起始时间
				</th>
				<th>
					结束时间
				</th>
				<th>操作</th>
			</tr>
		
		
</table>
</div>
<div id="fenye">


</div>
</body></html>