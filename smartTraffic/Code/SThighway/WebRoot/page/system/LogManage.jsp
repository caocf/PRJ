<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统日志</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/UserManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/system/setheight.js"></script>
	<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>	
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/system/LogManage.js"></script>

  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="1,3" />
	    </jsp:include>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<div class="common_c3">
								<div class="c3_button1" onclick="loadLogList()" style="margin:9px 15px 0 15px;">
									<div class="c3_button1_left" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
									<div class="c3_button1_center" onmouseover="buttonOver()" onmouseout="buttonOut()">导出</div>
									<div class="c3_button1_right" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
								</div>
								<div class="c3_button2" onclick="deleteLogList()" style="margin-top:9px">
									<div class="c3_button2_left" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
									<div class="c3_button2_center" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >删除</div>
									<div class="c3_button2_right" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
								</div>
								<div style="float:right;">
									<input id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:33px;line-height: 33px;margin-top:8px;" />
									<span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;margin-top:8px;" >至</span>
									<input id="endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:33px;line-height: 33px;margin-top:8px;" />
									<div class='search_div'>
										<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="showLogInfo('czrzmanager/czrzlist',1)">
											<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
											</div><input value='用户名' id='yhm_info' class="search_input" 
										onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
								</div>
						</div>
						<table class="listTable" id="logTable" cellpadding="0" cellspacing="0">
						<col width="3%"/><col width="15%"/><col width="50%"/><col width="32%"/>
							<tr>
								<th><input type="checkbox" id="selectall" onclick="selectAll(this)"/></th>
								<th>用户名</th>
								<th>操作内容</th>
								<th>操作时间</th>
							</tr>
							
						</table> 
						<div class="User_S4" id="pageDiv" style="background:#f3f3f3;display:none;">
							<p>
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
  </body>
</html>
