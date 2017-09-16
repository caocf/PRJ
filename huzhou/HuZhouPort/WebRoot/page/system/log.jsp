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
 <script src="js/common/jquery-1.5.2.min.js"></script>
 <link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
<link rel="stylesheet" type="text/css" href="css/leave/leave.css" />
<link rel="stylesheet" type="text/css" href="css/common/smallDialog.css" />
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>

<link type="text/css" rel="stylesheet" href="js/calendar_js/calendar.css" >
<script type="text/javascript" src="js/calendar_js/calendar.js" ></script>  
<script type="text/javascript" src="js/calendar_js/calendar-zh.js" ></script>
<script type="text/javascript" src="js/calendar_js/calendar-setup.js"></script>


<script src="js/system/log.js"></script>


</head>
<body>
<input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
<div id="layer4">
				<div id="layerback">	
				<img src="<%=basePath1%>image/operation/delete_log_normal.png"
						onclick="delete213()"
						onmouseover="deletelogOver(this)" onMouseOut="deletelogOut(this)"
						title="删除日志" />
				</div>
				<div id="layer4_right">
				
				<img alt="高级搜索" src="image/operation/log_select_normal.png" class="u3_img" 
					 onclick="deletediv()"  onMouseOver="selectlogOver(this)" onMouseOut="selectlogOut(this)"/>
				</div>	
	</div>	

<!--  
<div id="r1">
<div id="r2">
<p onclick="delete213()" onmousedown="downstyle(this);" onmouseover="overstyle(this);" onmousemove="movestyle(this);" onmouseout="outstyle(this);" onmouseup="upstyle(this);">
		<img src="image/role/delete.png" class="u3_img" style="width:12px"/>&nbsp;删除日志
		</p></div>

	<form action="selectLog"  id="" method="post">
	操作者：<input type="text" id="logUser" name="logbean.LogUser" value="" />
	日志内容：<input type="text"  id="LogContent" name="logbean.LogContent" />
	日志类型：<input type="text" id="StyleName" name="logbean.StyleName"  />
	日志时间：<input type="text" id="LogTime" name="logbean.LogTime"   onclick="return showCalendar('LogTime', 'y-mm-dd');"/>
	<input type="button" value="查询" onclick="select123()" />	
	</form>	-->
	<div style="padding:0 5px 0 5px" >
	<table width="100%" border="0" cellspacing="0" class="listTable"
			id="logTable">
			<col width="7%"/><col width="10%"/><col width="10%"/><col width="25%"/><col width="15%"/><col width="10%"/><col width="23%"/>
			<tr>
			<th><input type="checkbox" name="checkbox1" id="checkbox1" value="checkbox" onClick="chAll()">
				
				</th>
				<th>
					日志编号
				</th>
				<th>
					操作者
				</th>
				<th>
					日志内容
				</th>
				<th>
					日志类型
				</th>
				<th>
					日志来源
				</th>
				<th>
					日志时间
				</th>
			</tr>
		
		
</table>
</div>
<div id="fenye"></div>
<div id="deletelogdiv" style="display:none;height:310px;" icon="icon-search">
<div class="selectlog_content">
    <p>操作者：</p><input type="text" id="logUser" name="logbean.LogUser" value="" /><br/>
	<p>日志内容：</p><input type="text"  id="LogContent" name="logbean.LogContent" /><br/>
	<p>日志类型：</p><input type="text" id="StyleName" name="logbean.StyleName"  /><br/>
	<p>日志时间：</p><input type="text" id="LogTime" name="logbean.LogTime"   /><br/>
</div>
<div class="selectlog_font">
		<img src="image/operation/sure_normal.png" onClick="select123()"
				onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
			<img src="image/operation/reset_small_normal.png" onClick="hiden()"
				onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
		</div>
</div>
</body></html>