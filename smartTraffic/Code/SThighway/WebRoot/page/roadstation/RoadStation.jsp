<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公路站</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/RoleManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
    			<div class="left_I1">
    				<div class="left_no_select" id="left_no_select1" style="cursor: pointer;">
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchRoadStationInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='公路站名称、代码' id='bridge_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="18%"/><col width="18%"/><col width="10%"/><col width="10%"/>
						<col width="10%"/><col width="10%"/><col width="10%"/><col width="14%"/>
							<tr>
								<th>公路站名称</th>
								<th>所属管理机构</th>
								<th>桥梁座数</th>
								<th>隧道座数</th>
								<th>国道（公里）</th>
								<th>省道（公里）</th>
								<th>县乡道（公里）</th>
								<th>操作</th>
							</tr>
							
						</table> 
						<div class="User_S4" id="pageDiv" style="background:#f6f6f6;display:none;">
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
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
   		<script type="text/javascript" src="<%=basePath%>js/roadstation/RoadStation.js"></script>	
  </body>
</html>
