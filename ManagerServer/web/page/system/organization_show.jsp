<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

     <link rel="stylesheet" type="text/css" href="css/common/style.css" />
     <link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
     <link rel="stylesheet" type="text/css" href="css/update_department.css" />

     <script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
     <script type="text/javascript" src="js/common/inputValidator.js"></script>
     <script type="text/javascript" src="js/system/organizationValidator.js"></script>
     <script type="text/javascript" src="js/system/organization.js"></script>
      <script type="text/javascript" src="js/system/Operation_department.js"></script>
      <script type="text/javascript" src="js/common/Operation.js"></script>
      <script type="text/javascript" src="js/common/CheckLogin.js"></script>
     

  </head>
  
  <body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
    <input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="content">
			<div id="u2_left">
				<p>
				<a	onclick="firstDepartment(this)" id="organization"><b>湖州港航管理局</b></a>
				</p>
				<div id="deparmentTreeDiv">
				<ul id='department_tree'></ul>
				</div>
			</div>
			<div id="u2_right">
				<div class="u3_top">
					<img alt="新建部门" src="image/operation/add_department_normal.png" class="u3_img" 
					onclick="addDepartment()" onMouseOver="AddDepartmentOver(this)" onMouseOut="AddDepartmentOut(this)"/>
					<img alt="撤销部门" src="image/operation/delete_department_normal.png" class="u3_img" 
					onclick="deleteDepartmentById()" onMouseOver="DeleteDepartmentOver(this)" onMouseOut="DeleteDepartmentOut(this)"/>
					<img alt="编辑" src="image/operation/update_normal.png" class="u3_img" 
							onclick="updateDepartment()"  onmouseover="UpdateOver(this)" onMouseOut="UpdateOut(this)"/>
				</div>
				<div class="show_first">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>
									部门名称：
								</td>
								<td>
									湖州港航管理局
								</td>
							</tr>
						</table>
				</div>

				<div class="show_second" style="display:none">
					<table border="0" cellspacing="0" cellpadding="5">
						<tr>
							<td>
								部门名称：
							</td>
							<td>
								<label id="show_departmentname" >湖州港航管理局</label>
								<input type="hidden"  value="-1"  id="show_departmentId">
							</td>
						</tr>
						<tr>
							<td>
								所属部门：
							</td>
							<td>
								<label id="show_partofdepartmentName" ></label>
								<input type="hidden"  id="show_partOfDepartmentId" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div id="add_departmentDiv" style="display:none">
			<div id="layer_short">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						 onclick="hideDepartment()" title="返回" 
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"/>
				</div>
				<div id="open_table_div">
					<table cellspacing="0" cellpadding="0" class="open_table"
						id="departmentInfo">
						<col width="80px"/><col/>
						<tr>
							<td>
								部门名称：
							</td>
							<td>
								<input type="text" value="" id="department_name"
									name="department_name" class="input_box"/>
								<input id="departmentId" type="hidden" />
								<font class="promptSpan">*</font>
								<span id="department_nameerr" class="promptSpan"></span>
							</td>
						</tr>
						<tr>
							<td>
								所属部门：
							</td>
							<td>
								<input type="hidden" value="" id="partOfDepartmentId"
									name="partOfDepartmentId" />
								<input type="text" value="湖州港航管理局" disabled="disabled"
									id="partOfDepartmentName" name="partOfDepartmentName"  class="input_box"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<div id="selectTree">
							<a onClick="chooseDepartment(this,'-1','湖州港航管理局')"><b>湖州港航管理局</b></a>
							<ul id='tree'></ul>
							</div>
								
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<img alt="提交" src="image/operation/submit_normal.png" class="u3_img" 
										onclick="editionDepartmentInfo()"  onmouseover="SubmitOver_img(this)" onMouseOut="SubmitOut_img(this)"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
  </body>
</html>
