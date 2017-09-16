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
<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />


<script src="js/common/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script src="js/officoa/knowledge_treeadd.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
 <input type="hidden" value="<%=basePath1%>" id="basePath"/>
<input id="kindID" type="hidden" value="<%=request.getParameter("kindID") %>" />
   <div id="layer1">
				<img src="<%=basePath1%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div> 

<div id="open_table_div">
					<table cellspacing="0" cellpadding="0" class="seenew"
						id="departmentInfo">
						<col width="100px"/><col/>
						<tr>
							<td>
								分类名称：
							</td>
							<td>
								<input type="text" value="" id="knowledgekind_kindname"
									name="knowledgekind_kindname" class="input_box"/>
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
							<div id="deparmentTreeDiv"  style="border:solid 1px #3d9dd7; width:570px; height:300px"   >
					<ul  style="list-style-type:none ; margin:10px 0 5px 10px;padding: 0" >
					<li class="li-1" > <img src='image/common/minus.png' style='cursor:default'/> <a onClick="firstDepartment(this)" id="bigtree" >通知公告</a>
					<ul id='department_tree' ></ul>
				    </li>
					</ul>
					</div>
									
							</td>
						</tr>
						<tr>
							<td>
								<img alt="提交" src="image/operation/submit_normal.png" class="u3_img" 
										onclick="agree()"  onmouseover="SubmitOver_img(this)" onMouseOut="SubmitOut_img(this)"/>
							</td>
							<td>
							</td>
						</tr>
					</table>
 </div>
  </body>
</html>
