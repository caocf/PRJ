<%@page import="java.util.*"  pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath1%>">
    
    <title>通知公告</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/leave/see.css"/>
	<link rel="stylesheet" type="text/css" href="css/common/bigDialog.css" />
		<link rel="stylesheet" type="text/css" href="css/schedule_see.css" />

	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/common/Operation.js"></script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/officoa/schedule.js"></script>
	<script type="text/javascript" src="js/officoa/selectUserForKnowledge.js"></script>
	<script src="js/officoa/knowledge_add.js"></script>
	  <script src="js/officoa/knowledge_treeadd1.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
   <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
   <input id="kindID1" name="kindID1" type="hidden" value="" disabled="disabled"/>
 
   <div id="layer1">
		<img src="<%=basePath1%>image/operation/back_small_normal.png"
				onclick="javascript:window.history.go(-1);"
				onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
				title="返回" />
	</div> 
    
    <table width="100%" border="0" cellspacing="0" class="seenew"
	   id="knowledgeadd">
	<col width="100px" /><col width="*"/>
			<tr>
				<td>
					通知名称:
				</td>
				<td>
					<input id="knowledgeName" name="knowledgeName" type="text" />
					<font style="color:red;">*</font>
				</td>

			</tr>

			<tr>
				<td>
					通知摘要:
				</td>
				<td>
					<textarea name="knowledgeIndex" id="knowledgeIndex" cols="50"
						rows="3"></textarea>
				</td>
			</tr>

			<tr>
				<td>
					是否是链接:
				</td>
				<td>
					<input type="radio" value="1" name="isLink" id="isLink" />
					是
					<input type="radio" value="2" id="isLink" checked="checked"
						name="isLink" />
					否
				</td>
			</tr>


		<tr>
			<td>
				所属分类：
			</td>
			<td>
				<input type="hidden" value="<%=request.getParameter("parentId") %>" id="parentId" />
				<input type="text" value="" disabled="disabled"
					   id="knowledgekind_kindindexname" name="knowledgekind_kindindexname"  class="input_box"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="deparmentTreeDiv"  style="border:solid 1px #3d9dd7; width:400px; height:100px"   >
					<ul  style="list-style-type:none ; margin:10px 0 5px 10px;padding: 0" >
						<li class="li-1" > <img src='image/common/minus.png' style='cursor:default'/> <a onClick="firstDepartment1(this)" id="bigtree" >通知公告</a>
							<ul id='department_tree' ></ul>
						</li>
					</ul>
				</div>

			</td>
		</tr>



			<tr>
				<td>
					通知对象：
				</td>
				<td>
					<select id="isLinkInfo" name="isLinkInfo" onchange="SelectObject()">
						<option value="0">
							全部
						</option>
						<option value="1">
							内部用户
						</option>
						<option value="2">
							公众用户
						</option>
					</select>
				</td>
			</tr>
	
			<tr style="display: none" id="tr_user">
				<td>
					具体对象：
				</td>
				<td>
				<input type="hidden"  id="listOfUser"/>
					<div id="listOfUserName" class="div_text">港航内部全部用户</div>
					<div class="div_choose"><img title="添加用户" src="image/operation/user_add_normal.png" class="u3_img" 
							 onclick="showDialog()"  onmouseover="ChooseUserOver(this)" onMouseOut="ChooseUserOut(this)"/>
					<font style="position: relative;top:-10px;color:red">*</font>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					具体内容:
				</td>
				<td>
					<textarea name="knowledgeContent" id="knowledgeContent" cols="50"
						rows="18"></textarea>
					<font style="position: relative;top: -130px;color:red;">*</font>
				</td>
			</tr>

			<tr>
				<td>
					&nbsp;

				</td>
				<td>
					<img  src="image/operation/sure_normal.png" onClick="knowledgeAdd()"
						onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
				</td>
			</tr>

		</table>
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
     
  </body>
</html>
