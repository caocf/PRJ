<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="utf-8"%>
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

		<title>My JSP 'schedule.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/schedule_see.css" />
		<link rel="stylesheet" type="text/css" href="css/common/bigDialog.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/time/WdatePicker.js"></script>
		<script type="text/javascript" src="js/officoa/schedule.js"></script>
		<script type="text/javascript" src="js/officoa/schedule_add.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>
		<script type="text/javascript" src="js/officoa/scheduleValidator.js"></script>
		<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/officoa/selectUser.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>

	</head>

	<body>
	<div id="totaldiv">
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<form action="addEvent" encType="multipart/form-data"  method="post"  onsubmit="return add_submit();">
	<input type="hidden" value="<%=request.getParameter("sc") %>" id="sc"/>
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
	<div id="layer1">
		<img src="<%=basePath%>image/operation/back_small_normal.png"
			 onclick="goBackSchedule()" onMouseOver="BackSmallOver(this)" 
			 onmouseout="BackSmallOut(this)" title="返回日程页面"/>
	</div>		
	<div id="showEvent">
			<input type="hidden" value="<%=session.getAttribute("userId")%>" name="scheduleEventUser.userId" id="userId"/>
			<table id="EventList" cellpadding="0" cellspacing="0" >
			<col width="80px"/><col/>
				<tr>
					<td>
						日程名称：
					</td>
					<td>
						<input type="text" name="scheduleEvent.eventName" id="sName" class="input_box"/>
						<font color="red">*</font><label id="eventNameerr"  class="promptSpan"></label>
					</td>
				</tr>
				<tr>
					<td>
						日程类型：
					</td>
					<td>
						<select name="scheduleEvent.eventKind" id="scheduleKind" class="select_box"></select>
					</td>
				</tr>
				<tr>
					<td>
						开始时间：

					</td>
					<td>
						
					 <input type="text" name="scheduleEvent.eventTime"  id="sTime" value=""  onchange="sTimechange()"
					 readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate: getMaxDate(),minDate:getMinDate()})" class="Wdate" />
					<font color="red">*</font><label id="eventTimeerr" class="promptSpan"></label>
					</td>
				</tr>
				<tr>
					<td>
						提醒时间：
					</td>
					<td>
					<select name="scheduleEventUser.eventRemind" class="select_box">
					<option value="0">无提醒</option>
					<option value="5">提前5分钟提醒</option>
					<option value="10">提前10分钟提醒</option>
					<option value="15">提前15分钟提醒</option>
					<option value="30">提前30分钟提醒</option>
					<option value="60">提前1个小时提醒</option>
					<option value="120">提前2个小时提醒</option>
					<option value="240">提前4个小时提醒</option>
					<option value="720">提前12个小时提醒</option>
					<option value="1440">提前24个小时提醒</option>
					</select>
					<font color="#a2a2a2">只在手机端提醒</font>
					</td>
				</tr>
				<tr>
					<td>
						提醒方式：
					</td>
					<td>
					<select name="scheduleEventUser.eventRemindType" class="select_box">
					<option value="1">铃声</option>
					<option value="2">震动</option>
					<option value="3">铃声和震动</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						地点：
					</td>
					<td>
					<input type="text" name="scheduleEvent.eventLocation" id="sLocation" class="input_text"/>
					</td>
				</tr>
				<tr>
					<td>
						参加人：
					</td>
					<td>
					<input type="hidden" value="" name='scheduleEventUser.eventUserList' id="listOfUser"/>
					<div id="listOfUserName" class="div_text"><%=(String)session.getAttribute("name")%></div>
					<div class="div_choose"><img title="添加用户" src="image/operation/user_add_normal.png" class="u3_img" 
							 onclick="showDialog()"  onmouseover="ChooseUserOver(this)" onMouseOut="ChooseUserOut(this)"/>
					<font style="position: relative;top:-10px;color:red">*</font>
					</div>
					</td>
				</tr>
				<tr>
					<td valign="top">
						内容：
					</td>
					<td>
					<textarea name='scheduleEvent.eventContent' id="sContent" class="textarea_schedule"></textarea>
					<label id="eventContenterr" class="promptSpan"></label>
					</td>
				</tr>
				<tr>
					<td>
						附件
					</td>
					<td>
					<div id="file_div"><img src="image/operation/add_attachments_normal.png" class="file_img" title="添加附件"/>
					 <input type="file"  onchange="addComponent(this)" class="file_button"  id="file1" name='scheduleAttachment.af'
					  onmouseover="AttachmentsOver()" onMouseOut="AttachmentsOut()"  /></div>
					</td>
				</tr>
			</table>
			<table id="EvidenceUpload" cellpadding="0" cellspacing="0" width="723px">
				<col width="300px"/><col/>
			</table>
			<div class="submit_div">	
				<input type="submit" value="" class="input_pulish"
				onmouseover="PublishOver(this)" onMouseOut="PublishOut(this)"/>
				<input type="reset" value="" class="input_reset2"  title="重置" 
					onmouseover="ResetBigOver2_BT(this)" onMouseOut="ResetBigOver2_BT(this)"/>
			</div>
		</div>
	</form> 

			<div id="cresteTree"  style="display:none;">
				<div id="dialog01">
				所有用户及用户组
					<div id="dialog02">
						<input type="text" id="searchName" class="dialog_input_box"/><img src="image/schedule/searchuser_normal.png"
						 onclick="FindByName()" onMouseOver="DialogSearchOver(this)" onMouseOut="DialogSearchOut(this)" title="搜索"
						class="img_search"/>
					</div>
					<div class="div_tree">
						<ul id='tree'></ul>
					</div><div class="dialog_alluser">
						</div>
				</div>
				<div id="dialog03">
					<img src="image/schedule/addto.png" onClick="AddToClick()"
					onmouseover="AddToOver(this)" onMouseOut="AddToOut(this)"/>
					<img src="image/schedule/alladdto.png" onClick="AllAddToClick()"
					onmouseover="AllAddToOver(this)" onMouseOut="AllAddToOut(this)"/>
					<img src="image/schedule/moveto.png" onClick="MoveToClick()"
					onmouseover="MoveToOver(this)" onMouseOut="MoveToOut(this)"/>
					<img src="image/schedule/allmoveto.png" onClick="AllMoveToClick()"
					onmouseover="AllMoveToOver(this)" onMouseOut="AllMoveToOut(this)"/>
				</div>
				<div id="dialog04">
				选择对象
					<div class="dialog_userList">
					</div>
				</div>
				<div id="div_tree_operation">
					<img src="image/operation/sure_normal.png" onClick="changeUserOfDepartment()"
						onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
					<img src="image/operation/reset_small_normal.png" onClick="hidenDepartmentTree()"
						onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
			   </div>
			</div>
     	</div>
	</body>
</html>
