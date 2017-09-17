<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>通阻信息</title>
    
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
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/tzinfo/TzInfo.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0" style="min-height:730px;">
    	<jsp:include page="../system/top.jsp" flush="true" />
	    	<div style="width:100%;" id="tzHeight">
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc;">
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchTzInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='标题、类别' id='tz_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="10%"/><col width="25%"/><col width="5%"/><col width="20%"/><col width="20%"/><col width="20%"/>
							<tr>
								<th>&nbsp;</th>
								<th style="text-align: left;">标题</th>
								<th>&nbsp;</th>
								<th style="text-align: center;">发布时间</th>
								<th style="text-align: center;">信息类别</th>
								<th style="text-align: center;">操作</th>
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
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
    
    <div class="dialog" id="tzdetailDiv" style="display:none;width:800px;margin:-160px 0 0 -400px;background: white;">
    	<div class="User_Top1">
 				<label class="Top_left">通阻信息详情</label>
 				<img  class="Top_right" src="image/main/close.png"
 					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closetzdetailDiv()">
 			</div>
 			<div class="pop_up_middle">
   				<div id="main_title" class="tz_detail_title"></div>
   				<div class="tz_detail_time">
   					<label>发布时间：</label>
					<label id="push_time"></label>
					<label>&nbsp;&nbsp;发布人：</label>
					<label id="push_person"></label>&nbsp;&nbsp;
   				</div> 
   				<div id="text_content" class="tz_text">
   					
   				</div> 
   				<div class="tz_detail_time" style="border-top:solid 1px #cccccc;border-bottom:0;">
   					<label>创建时间：</label>
					<label id="create_time"></label>
					<label>&nbsp;&nbsp;更新时间：</label>
					<label id="update_time"></label>&nbsp;&nbsp;
   				</div>  			
 			</div> 
 			
    </div>
  </body>
</html>
