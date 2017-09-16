<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>违章</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/law_see.css" />
	<link rel="stylesheet" type="text/css" href="css/law_update.css" />
	<link rel="stylesheet" type="text/css" href="css/selectlawuser.css" />
	<link rel="stylesheet" type="text/css" href="css/common/middelDialog.css" />
	
	<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/business/illegal_add.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/business/selectlawuser.js"></script>
	<script type="text/javascript" src="js/common/inputValidator.js"></script>
	<script type="text/javascript" src="js/common/Operation.js"></script>
	<script type="text/javascript" src="js/business/law_back.js"></script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>
  </head>
  
  <body>
 	 <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
  	 <form action="AddIllegal" encType="multipart/form-data" 
   		 method="post" onSubmit="return update_submit();" >
 		 <div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="GotoIllegalList()" onMouseOver="BackSmallOver(this)" 
					onMouseOut="BackSmallOut(this)" title="返回" />
		</div>
		<div id="update_container">
   		<input type="hidden" value="<%=basePath %>" id="basePath"/>
  		<input type="hidden" value="<%=request.getParameter("sc") %>" id="sc"/>
	 	 <input type="hidden" value="<%=session.getAttribute("userId")%>" 
	  		name="illegal.enforecers1" id="userId"/>
		<table id="illegalTable">
		<col width="85px"/><col/>
		<tr>
				<td>
					相关部门：
				</td>
				<td>
					<select name="illegal.illegalCategories">
					<option value="1">港政</option>
					<option value="2">运政</option>
					<option value="3">航政</option>
					<option value="4">海事</option>
					</select>
				</td>
			</tr>
		<tr>
				<td>
					第一执法人：
				</td>
				<td>
				<label><%=session.getAttribute("name")%></label>
				</td>
			</tr>
			<tr>
				<td>
					第二执法人：
				</td>
				<td>
				<input type="text" readonly="readonly"	id="seconduser" class="div_text"/>
				<input type="hidden" id="seconduserId" name="illegal.enforecers2"/>
				<div class="div_choose"><img title="添加用户" src="image/operation/user_add_normal.png" class="u3_img" 
							 onclick="showDialog(1)"  onmouseover="ChooseUserOver(this)" onMouseOut="ChooseUserOut(this)"/>
					<font style="position: relative;top:-10px;color:red">*</font>
					<label id="secondusererr" style="position: relative;top:-10px;color:red"></label>
					</div>
				</td>
			</tr>
				<tr>
				<td>
					审核人：
				</td>
				<td>
				<input type="text" readonly="readonly"	id="threeuser" class="div_text"/>
				<input type="hidden" id="threeuserId" name="illegal.reviewUser"/>
				<div class="div_choose"><img title="添加用户" src="image/operation/user_add_normal.png" class="u3_img" 
							 onclick="showDialog(2)"  onmouseover="ChooseUserOver(this)" onMouseOut="ChooseUserOut(this)"/>
					<font style="position: relative;top:-10px;color:red">*</font>
					<label id="threeusererr" style="position: relative;top:-10px;color:red"></label>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					违章对象：
				</td>
				<td>
				<input type="text" name="illegal.illegalObject"	id="illegalObject" class="input_box"/>
				<label class="promptSpan">*</label>
				<label id="illegalObjecterr" class="promptSpan"></label>
				</td>
			</tr>
			<tr>
				<td>
					违章地点：
				</td>
				<td>
				<input type="text" name="location.locationName" class="input_box" id="location"/>
				<label class="promptSpan">*</label>
				<label id="locationerr" class="promptSpan"></label>
				</td>
			</tr>
			<tr>
				<td>
					违章案由：
				</td>
				<td>
				<select id="illegalReason" name="illegal.illegalReason"></select>
				</td>
			</tr>
			<tr>
				<td valign="top">
					违章描述：
				</td>
				<td>
					<textarea id="illegalContent" name="illegal.illegalContent" class="textarea_input"></textarea>
				</td>
			</tr>
			  <tr><td></td><td><label id="illegalContenterr" style="color:red"></label></td></tr> 
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
		<input type="button" value="" class="input_reset" onClick="GotoIllegalList()"
			onmouseover="SubmitOver_button(this)" onMouseOut="SubmitOut_button(this)"/>
		
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
				<div id="div_tree_operation">
					<img src="image/operation/sure_normal.png" onClick="changeUserOfDepartment()"
						onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
					<img src="image/operation/reset_small_normal.png" onClick="hidenDepartmentTree()"
						onmouseover="ResetSmallOver(this)" onMouseOut="ResetSmallOut(this)" title="取消" />
			   </div>
			</div>
  </body>
</html>
