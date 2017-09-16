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
<link rel="stylesheet" type="text/css" href="css/common/smallDialog.css" />
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/officoa/knowledge.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
</head>
<body><input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
<div id="layer4">
					<div id="layerback">	
				<img src="<%=basePath1%>image/operation/delete_log_normal.png"
						onclick="addknowledgediv()"
						onmouseover="deletelogOver(this)" onMouseOut="deletelogOut(this)"
						title="添加知识库类别" />
				</div>
				<div id="layer4_right">
			<input type="text" value="" id="Content" name="Content" class="input_box"/><img alt="搜索" src="image/operation/search_normal.png" class="u3_img" 
					 onclick="select123()"  onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
				</div>	
	</div>
	<table width="100%" border="0" cellspacing="0" class="listTable"
			id="Knowledge">
			<col width="60%"/><col width="40%"/>
			<tr>
				<th> 
					通知公告类别 
				</th>
				<th>
					操作
				</th>
			</tr>
   </table>

<div id="fenye">
</div>
<div id="deletelogdiv" style="display:none;height:165px;" icon="icon-search">
<div class="selectlog_content">
    <p>知识库类别：</p><input type="text" id="kindname" name="kindname" value="" /><br/>
	
</div>
<div class="selectlog_font">
		<img src="image/operation/sure_normal.png" onClick="addknowledge()"
				onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
			<img src="image/operation/reset_small_normal.png" onClick="hiden()"
				onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
		</div>
</div>

</body></html>