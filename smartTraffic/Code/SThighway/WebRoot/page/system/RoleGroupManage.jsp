<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限组管理</title>
    
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
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/system/setheight.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/system/RoleGroupManage.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="1,1" />
	    </jsp:include>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<div class="common_c3" style="height:52px;">
							<div class="c3_button1" onclick="ShowAddRoleGroup()" id="addbutton">
								<div class="c3_button1_left" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
								<div class="c3_button1_center" onmouseover="buttonOver()" onmouseout="buttonOut()" style="width:78px;" >新建权限组</div>
								<div class="c3_button1_right" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
							</div>
							<!-- <div class="c3_button2"  id="delbutton">onclick="deleteRole()"
								<div class="c3_button2_left" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
								<div class="c3_button2_center" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >删除</div>
								<div class="c3_button2_right" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
							</div> -->
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="3%"/><col width="15%"/><col width="62%"/><col width="20%"/>
							<tr>
								<th>&nbsp;</th>
								<th>权限组名称</th>
								<th>拥有权限</th>
								<th>操作</th>
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
   			<!-- 新建权限组 -->
		    		<div class="dialog" id="addnewRoleGrouDiv" style="display:none;height:360px;width:840px;margin:-300px 0 0 -400px;background:white;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id='add_rolegroup_title' style="color:#333333;margin-left:20px;">新建权限组</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddnewRoleGroupDiv()">
		    			</div>
		    			<div style="height:55px;width:100%;background: #ffffff;">
		    				<div style="float:left;height:55px;line-height: 55px;vertical-align: middle;margin-left:20px;color:#666666;">权限组名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		    				<div style="float:left;height:55px;width:550px;"><input class="td_input1" style="margin-top:10px;width:260px;" id="rolegroup_name" type="text"/></div>
		    			</div>
		    			<div style="height:154px;width:100%;background: #ffffff;margin-top:15px;">
		    				<div style="float:left;height:55px;vertical-align: middle;margin-left:20px;color:#666666;">可配置权限：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		    				  <div class="perssiongropu_main" id="perssiongroup_main"></div>
		    				 
		    				</div>
		    			
		    			<div style="height:77px;width:100%;background:white;margin-top:14px;border-top:1px solid #dcdcdc;">
		    			    <input  type='checkbox' id="qxallcheck" style="float:left;margin:34px 0 0 20px;"  onclick="qxallcheckRoleGroup('rolegroup_',this)"/>
		    			    <label style="float:left;margin:30px 0 0 0;"> &nbsp;&nbsp;全选</label>
			    			<div class="c3_button1" onclick="savePermission()" style="margin:25px 10px 0 48px;">
								<div class="c3_button1_left_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
								<div class="c3_button1_center_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">确定</div>
								<div class="c3_button1_right_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
							</div>
							<div class="c3_button2" onclick="closeaddnewRoleGroupDiv()" style="margin:25px 0 0 0">
								<div class="c3_button2_left_pop" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()" ></div>
								<div class="c3_button2_center_pop" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()" >取消</div>
								<div class="c3_button2_right_pop" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()"></div>
							</div>
						</div>
		    		</div>
   			<!-- 删除角色信息 -->
		    		<div class="dialog" id="DeleteDiv" style="display:none;height:206px;width:472px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">删&nbsp;&nbsp;除</label>
		    				<label  class="Top_right"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDialog2()">关闭</label>
		    			</div>
		    			<div class="User_DeleteMiddle">
		    				<div class="Middle_p1"></div>
		    				<div class="Middle_p2">您确定要删除这些角色吗？</div>
		    			</div>
		    			<div class="User_Deletebottom">
		    					<div class="bottom_button1"  onclick="closeDialog2()">取消</div>
								<div class="bottom_button2"  onclick="Delete()">确定</div>	
		    			</div>
		    		</div>
  </body>
</html>
