<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'top.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/common/style.css">
	<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	<link rel="stylesheet" type="text/css" href="css/system/mtop.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/common/cookie.js"></script>
	<script type="text/javascript" src="js/main/mtop.js"></script>
	<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>
  </head>
  
  <body>
    <div id="fullbg"></div>
    <input type="hidden" value="<%=basePath%>" id="basePath"/>
     <!-- token -->
    <input type="hidden" value="<%=session.getAttribute("token")%>" id="roadtoken"/>
	<div class="mtop_u1">
		<div style="width:100%;height:50px;">
			<!-- <img class="top_u2" src="image/main/back_normal.png" onmouseover="hoverOver(this)" 
				onmouseout="hoverOut(this)"/> -->
			<span class="top_text" id="top_text" onclick="showLinkDiv()">公路基础信息</span>
		</div>
		<div style="height:32px;">
				<a class="mtop_no_select" id="first_select" href="page/main/firstpage.jsp">公路概况</a>
				<a class="mtop_no_select" id="route_select" href="page/route/RouteInfo.jsp">路网信息</a>
				<a class="mtop_no_select" id="bridge_select" href="page/bridge/BridgeInfo.jsp">桥梁信息</a>
				<a class="mtop_no_select" id="service_select" href="page/services/Services.jsp">服务设施</a>
				<a class="mtop_no_select" id="mark_select" href="page/marksign/MarkSign.jsp">标志标线</a>
				<a class="mtop_no_select" id="manage_select" href="page/manageins/Agency.jsp">管理机构</a>
				<a class="mtop_no_select" id="station_select" href="page/roadstation/RoadStation.jsp">公路站</a>
		</div>
	</div>
	<div class="loadingDiv">
		<img alt="" src="image/common/loading.gif">
	</div>
	<!-- 高级搜索 -->
		    		 <div class="dialog" id="SearchDiv" style="display:none;width:510px;margin:-200px 0 0 -255px;">
		    			<div class="User_Top1">
		    				<!-- <label class="Top_left">高级搜索</label> -->
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeSearchDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="250px"/><col width="210px"/><col width="50px"/>
				    					<tr>
											<td><select id="search1" class="search_select" onchange="selectSearch(1)"><option value="null">请选择搜索条件</option></select></td>
											<td><input id="search_input1" type="text" class="mark_input"/><select id="search_select1" class="second_search_select" ></select>
												<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy'})" readonly="readonly" style="display: none;width:195px;height:34px;" id="search_year1"></td>
											<td><label id="search_label1">精确<input type="checkbox" id="search_checkbox1"></label></td>
										</tr>
										<tr id="search_tr2" style="display:none;">
											<td style="width:250px;"><select id="search2" class="search_select" onchange="selectSearch(2)"><option value="null">请选择搜索条件</option></select></td>
											<td><input id="search_input2" type="text" class="mark_input" /><select id="search_select2" class="second_search_select" ></select>
												<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy'})" readonly="readonly" style="display: none;width:195px;height:34px;" id="search_year2"></td>
											<td>精确<input type="checkbox" id="search_checkbox2"></td>
										</tr>
										<tr id="search_tr3" style="display:none;">
											<td><select id="search3" class="search_select" onchange="selectSearch(3)"><option value="null">请选择搜索条件</option></select></td>
											<td><input id="search_input3" type="text" class="mark_input" /><select id="search_select3" class="second_search_select" ></select>
												<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy'})" readonly="readonly" style="display: none;width:195px;height:34px;" id="search_year3"></td>
											<td>精确<input type="checkbox" id="search_checkbox3"></td>
										</tr>
										<tr>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;" colspan="3">
												<div class="c3_button1" onclick="searchListInfo()" style="margin:0 15px 0 10px;" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">
													<div class="c3_button1_left_pop"  ></div>
													<div class="c3_button1_center_pop">搜索</div>
													<div class="c3_button1_right_pop" ></div>
												</div>
												<div class="c3_button2" onclick="closeSearchDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()" >
													<div class="c3_button2_left_pop" ></div>
													<div class="c3_button2_center_pop"  >取消</div>
													<div class="c3_button2_right_pop" ></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
</body>
</html>
