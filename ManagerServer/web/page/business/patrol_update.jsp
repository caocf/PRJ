<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>码头巡查</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/law_see.css" />
	<link rel="stylesheet" type="text/css" href="css/law_update.css" />
	
	<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/business/patrol_update.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/common/inputValidator.js"></script>
	<script type="text/javascript" src="js/common/Operation.js"></script>
	<script type="text/javascript" src="js/business/law_back.js"></script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>
  </head>
  
  <body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
   <form action="addPatrolSupplementByServer" encType="multipart/form-data"  method="post" onSubmit="return update_submit();" >
  <div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="GotoPatrolList()" onMouseOver="BackSmallOver(this)"
					 onmouseOut="BackSmallOut(this)" title="返回" />
			</div>
			  <div id="update_container">
   <input type="hidden" value="<%=basePath %>" id="basePath"/>
  <input type="hidden" value="<%=request.getParameter("sc") %>" id="sc"/>
 
  <input type="hidden" value="<%=request.getParameter("patrolId") %>" id="patrolId" name="patrol.patrolId"/>
  <input type="hidden" value="<%=session.getAttribute("userId")%>" name="patrolSupplement.userId" id="userId"/>
		<table id="illegalTable">
		<col width="75px"/><col/>
			<tr>
				<td>
					码头：
				</td>
				<td>
				 <label id="patrolObject"></label>
				</td>
			</tr>
			<tr>
				<td valign="top">
					内容描述：
				</td>
				<td>
					<textarea class="textarea_input" id="patrolContent" name="patrol.patrolContent"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td >
					<label id="patrolContentErr" style="color:red"></label>
				</td>
			</tr>
		</table>
		<div class='content_kind'>附件&nbsp;&nbsp;&nbsp;
		<div id="file_div"><img src="<%=basePath%>image/fileImg/addFile.png" title="添加附件" id="file_img"/>
			 <input type="file"  onchange="addComponent(this)" class="file_button" 
			 id="file1" name='illegalEvidence.ef'  onmouseover="AddFileOver(this)" onMouseOut="AddFileOut(this)" /></div>	
		</div>
		<table id="EvidenceUpload" cellpadding="0" cellspacing="0" width="793px">
		<col width="300px"/><col/>
		</table>
		<div id="submit_div">
		<input type="submit" value="" class="input_submit"
			onmouseover="SubmitOver(this)" onMouseOut="SubmitOut(this)"/>
		<input type="button" value="" class="input_reset" onClick="GotoPatrolList()"
			onmouseover="SubmitOver_button(this)" onMouseOut="SubmitOut_button(this)"/>
		
		</div>
		
		</div>
		</form>
  </body>
</html>
