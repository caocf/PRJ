<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理机构</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/manageins/ManageInstitutions.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/CRselectBox.css" />
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>	
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/manageins/ManageInstitutions.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>

  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<%-- <jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="1,1" />
	    </jsp:include> --%>
		<div class="manage_c1" >
				<div class="left_I1">
					<div class="left_select" id="left_no_select1" onmouseover="showgljgedit()" onmouseout="hidegljgedit()">
						<!-- <label class="left_name" id="gljg_bossname"></label>
						<div class="gljg_edit">
							<div class="gljg_edit_link" onclick="showGljgEditDiv()">编辑</div>
							<div class="gljg_edit_link" onclick="showGljgAddDiv()">新增</div>
							<div class="gljg_edit_link" onclick="showGljgDeleteDiv()">删除</div>
						</div> -->
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
				<div class="department_I1">
					<div class="department_left_no_select" id="department_left_no_select1">
						<label class="left_name">部门</label>
					</div>
					<div class="dpt_left_select" id="dpt_left_no_select2" onmouseover="showgljgeditDepartment()" onmouseout="hidegljgeditDepartment()">
						
					</div>
					<ul id="department_left_select_child2" class="department_left_select_child">
					</ul>
				</div>
		    	<div class="manage_c2" id="manage_width">
		    		<div style="margin:0 223px 0 0;float:left;">
		    			<div style="width:100%;height:50px;">
							<span class="manage_wordstyle" style="float:left;" id="departmentry_title"></span>
							<div class="c3_button1" onclick="showinductionDiv()" style="float:right;width:80px;" onmouseover="addbuttonover()" onmouseout="addbuttonout()">
								<div class="c3_button1_left" id="add_button_left" ></div>
								<div class="c3_button1_center" style="width:76px;" id="add_button_center" >新成员入职</div>
								<div class="c3_button1_right" id="add_button_right" ></div>
							</div>
							<div class="c3_button1" onclick="resetPassword()" style="float:right;margin-right:0;" onmouseover="resetbuttonover()" onmouseout="resetbuttonout()">
								<div class="c3_button1_left" id="reset_button_left" ></div>
								<div class="c3_button1_center" id="reset_button_center" >重置密码</div>
								<div class="c3_button1_right" id="reset_button_right" ></div>
							</div>
							<div class="c3_button1" onclick="editRyInfo()" style="float:right;margin-right:0;" onmouseover="editbuttonover()" onmouseout="editbuttonout()">
								<div class="c3_button1_left" id="edit_button_left" ></div>
								<div class="c3_button1_center" id="edit_button_center" >编辑</div>
								<div class="c3_button1_right" id="edit_button_right" ></div>
							</div>
							<div class="c3_button1" onclick="deleteMember()" style="float:right;margin-right:0;" onmouseover="delbuttonover()" onmouseout="delbuttonout()">
								<div class="c3_button1_left" id="del_button_left" ></div>
								<div class="c3_button1_center" id="del_button_center" >删除</div>
								<div class="c3_button1_right" id="del_button_right" ></div>
							</div>
							<div class='search_div'>
								<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchMemberInfo('gljglist/glrylistbybm',1)">
									<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
									</div><input value='姓名、手机长短号' id='search_info' class="search_input" 
								onfocus="TextFocus(this)" onblur="TextBlur(this)" />
							</div>
						</div>
						<div class="manage_c3" >
							<div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">全部</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">A</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">B</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">C</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">D</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">E</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">F</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">G</div>
							<div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">H</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">I</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">J</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">K</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">L</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">M</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">N</div>
							<div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">O</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">P</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">Q</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">R</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">S</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">T</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">U</div>
							<div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">V</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">W</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">X</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">Y</div><div class="letter" onclick="searchMemberList(this,1,'gljglist/glrylistbybm')">Z</div><div style="float:right;">&nbsp;</div>
						</div>
						<div style="width:100%;height:697px;">
							<table class="listTable" id="userTable" cellpadding="0" cellspacing="0">
							<col width="15%"/><col width="17%"/><col width="17%"/><col width="15%"/><col width="13%"/>
							<col width="13%"/><col width="10%"/>
								<tr>
									<th>姓名</th>
									<th>角色</th>
									<th>职务</th>
									<th>手机长号</th>
									<th>手机短号</th>
									<th>办公室电话</th>
									<th>操作</th>
								</tr>
								<!-- <tr>
									<td>admin</td>
									<td>四四四十四</td>
									<td>员工</td>
									<td>11111111111</td>
									<td>在职</td>
									<td><a class="Operate">查看</a><a class="Operate">编辑</a><a class="Operate">重置密码</a></td>
								</tr> -->
								
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
						<div class="manage_info">
							<div class="manage_info_top"><label style="margin-left:20px;float:left;">基本信息</label>
								<label style="margin-right:20px;float:right;color:#006993;cursor: pointer;" onclick="showEditBasicInfo()">编辑</label></div>
							<div class="manage_info_middle">
								<div class="manage_info_middle_name"><label style="margin-left:20px;" id="gljg_name"></label></div>
								<div class="manage_info_middle_address"><div id="gljg_address" style="word-wrap:break-word;
									overflow:hidden;margin:0 10px 0 20px"></div></div>
								<div class="manage_info_middle_phone"><label style="margin-left:20px;" id="gljg_phone"></label></div>
							</div>
							<div class="manage_info_middle" style="height:auto;">
								<!-- <div class="manage_info_middle_name"><label style="margin-left:20px;">下属部门</label></div>
								<div class="manage_info_middle_address"><div id="gljg_department" style="word-wrap:break-word;
									overflow:hidden;margin:0 10px 0 20px"></div></div> -->
								<div class="manage_info_middle_phone"><a style="margin-left:20px; color:#006993"  onclick="seeInMap()">点击查看地图所在位置</a></div>
							</div>
						</div>
				</div>
				<!-- 查看人员信息 -->
   			<div class="member_info">
					<div class="member_info_top"><label style="margin-left:20px;float:left;">职员详情</label>
						<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closememberInfoDiv()"></div>
					<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="470px"/>
				    					<tr>
				    						<td class="td_left">姓名：&nbsp;&nbsp;&nbsp;</td><td id="member_name"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">部门：&nbsp;&nbsp;&nbsp;</td><td id="member_dpt"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">角色：&nbsp;&nbsp;&nbsp;</td><td id="member_role"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">职务：&nbsp;&nbsp;&nbsp;</td><td id="member_position"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">手机长号：&nbsp;&nbsp;&nbsp;</td><td id="member_sjch"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">手机短号：&nbsp;&nbsp;&nbsp;</td><td id="member_sjdh"></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">办公室电话：&nbsp;&nbsp;&nbsp;</td><td id="member_bgsdh"></td>
				    					</tr>
				    					<!-- <tr>
				    						<td class="td_left">状态：&nbsp;&nbsp;&nbsp;</td><td id="member_status"></td>
				    					</tr> -->
				    				</table>		    			
		    			</div> 
				</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
	   
    </div>
    	
   			<!-- 编辑管理机构信息 -->
		    		<div class="dialog" id="editGljgDiv" style="display:none;width:680px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="editGljgDiv_text">编辑</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeEditGljgDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">名称：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="gljg_edit_name" type="text"/></td>
				    					</tr>
				    					<!-- <tr>
				    						<td class="td_left">确认密码&nbsp;&nbsp;&nbsp;</td>
				    						<td><input class="td_input1" id="reset_checkmm" type="password"/></td>
				    					</tr> -->
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="editGljg()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeEditGljgDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		    	<!-- 新增子管理机构下的机构 -->
		    		<div class="dialog" id="addGljgChildDiv" style="display:none;width:680px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">新增子管理机构</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeEditGljgDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">名称：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="gljg_edit_name" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;"><div class="c3_button1" onclick="editGljg()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeEditGljgDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		    	<!-- 新增部门-->
		    		<div class="dialog" id="addGljgDepartmentDiv" style="display:none;width:680px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="department_title">新增部门</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddGljgDepartmentDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">名称：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="add_department_name" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="addFirstDepartment()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddGljgDepartmentDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		    	<!-- 新增子部门-->
		    		<div class="dialog" id="addDepartmentChildDiv" style="display:none;width:680px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="add_child_department_title"></label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddDepartmentChildDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">名称：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="add_department_child_name" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="addChildDepartment()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddDepartmentChildDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>	
		    	<!-- 新成员入职 -->
		    		<div class="dialog" id="addnewMemberDiv" style="display:none;width:800px;margin:-231px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="member_title">新成员入职</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddnewMemberDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="270px"/><col width="130px"/><col width="250px"/>
				    					<tr>
				    						<td class="td_left">姓名：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="name" type="text"/></td>
				    						<td class="td_left">姓名全拼：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="xmqp" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">姓名首字母：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="xmszm" type="text"/></td>
				    						<td class="td_left">职务：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="zw" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">设置密码：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="mm" type="password"/></td>
				    						<td class="td_left">确认密码：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="checkmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">管理机构：&nbsp;&nbsp;&nbsp;</td>
				    						<td><select id="gljg" style="width:207px;outline: none;" onchange="selectaddGljg()"></select></td>
				    						<td class="td_left">所属部门：&nbsp;&nbsp;&nbsp;</td>
				    						<td><!-- <select id="ssbm" style="width:277px;"></select> -->
				    						<div class="type_select_div">
												<div class="type_select" >
													<div class="type_select_mark" onclick="showDepartmentDiv()">
														<label
															style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:5px;cursor: pointer;"
															id="ssbm">选择部门</label> <img src="image/main/arrow_down.png"
															style="float:right;margin:13px 10px 0 0;" id="selectbm_image">
													</div>
													<ul class="type_down_ul" id="addselect">
														
													</ul>
												</div>
											</div>
				    						</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">所属角色：&nbsp;&nbsp;&nbsp;</td>
				    						<td><select id="ssjs" style="width:207px;" ></select></td>
				    						<!-- <div class="CRselectBox" id="ssjs" style="margin-top:5px;">
													<input type="hidden" value="1" class="abc" />
													<input type="hidden" value="选择角色" class="abc_CRtext" id="bm"/>
														<a class="CRselectValue" style="color:black" id="BM">选择角色</a>
														  	<ul class="CRselectBoxOptions" id="addjs">
															</ul>
												</div> -->
				    						<td class="td_left">办公室电话：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="bgsdh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">手机长号：&nbsp;&nbsp;&nbsp;</td> <td><input class="mark_input" id="lxdh" type="text" /></td>
				    						<td class="td_left">短号：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="sjdh" type="text" /></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="addNewMember()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddnewMemberDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		    	<!-- 编辑成员 -->
		    		<div class="dialog" id="editMemberDiv" style="display:none;width:800px;margin:-204px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">编辑成员</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeeditMemberDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="270px"/><col width="130px"/><col width="250px"/>
				    					<tr>
				    						<td class="td_left">姓名：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="name1" type="text"/></td>
				    						<td class="td_left">姓名全拼：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="xmqp1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">姓名首字母：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="xmszm1" type="text"/></td>
				    						<td class="td_left">职务：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="zw1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">管理机构：&nbsp;&nbsp;&nbsp;</td>
				    						<td><select id="gljg1" style="width:207px;" onchange="selecteditGljg(0)"></select></td>
				    						<td class="td_left">所属部门：&nbsp;&nbsp;&nbsp;</td>
				    						<td><!-- <select id="ssbm" style="width:277px;"></select> -->
				    						<div class="type_select_div">
												<div class="type_select" >
													<div class="type_select_mark" onclick="showDepartmentDiv()">
														<label
															style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:5px;cursor: pointer;"
															id="ssbm1">选择部门</label> <img src="image/main/arrow_down.png"
															style="float:right;margin:13px 10px 0 0;" id="selectbm_image">
													</div>
													<ul class="type_down_ul" id="editselect">
														
													</ul>
												</div>
											</div>
				    						</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">所属角色：&nbsp;&nbsp;&nbsp;</td>
				    						<td><select id="ssjs1" style="width:207px;" ></select></td>
				    						<!-- <div class="CRselectBox" id="ssjs" style="margin-top:5px;">
													<input type="hidden" value="1" class="abc" />
													<input type="hidden" value="选择角色" class="abc_CRtext" id="bm"/>
														<a class="CRselectValue" style="color:black" id="BM">选择角色</a>
														  	<ul class="CRselectBoxOptions" id="addjs">
															</ul>
												</div> -->
				    						<td class="td_left">办公室电话：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="bgsdh1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">手机长号：&nbsp;&nbsp;&nbsp;</td> <td><input class="mark_input" id="lxdh1" type="text" /></td>
				    						<td class="td_left">短号：&nbsp;&nbsp;&nbsp;</td><td><input class="mark_input" id="sjdh1" type="text" /></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="editMember()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeeditMemberDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		    	<!-- 编辑基本信息 -->
		    		<div class="dialog" id="editBasicInfoDiv" style="display:none;width:680px;margin:-285px 0 0 -340px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">编辑基本信息</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeeditBasicInfoDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">名称：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="Infoname" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">地址：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="Infoaddress" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">电话：&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="Infophone" type="text"/></td>
				    					</tr>
				    					<!-- <tr>
				    						<td class="td_left" style="height:180px;">下属部门：&nbsp;&nbsp;&nbsp;</td>
				    						<td style="height:180px;"><textarea style="width:277px;height:150px;outline: none; border: solid 1px #ececec;" id="Infodpt"></textarea></td>
				    					</tr> -->
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="editBasicInfo()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeeditBasicInfoDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
		 <!-- 重置密码 -->		
		   <div class="dialog" id="ResetPwdDiv" style="display:none;">
		    			<div class="User_Top1">
		    				<label class="Top_left">重&nbsp;置&nbsp;密&nbsp;码</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeResetPwdDiv()">
		    			</div>
		    			<div class="pop_up_middle">
				    				<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="550px"/>
				    					<tr>
				    						<td class="td_left">密码&nbsp;&nbsp;&nbsp;</td><td><input class="td_input1" id="reset_newmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left">确认密码&nbsp;&nbsp;&nbsp;</td>
				    						<td><input class="td_input1" id="reset_checkmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">&nbsp;</td>
											<td style="height:80px;line-height: 80px;vertical-align: middle;border-top: solid 1px #dcdcdc;">
												<div class="c3_button1" onclick="resetPwd()" style="margin:0 15px 0 0;">
													<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
												</div>
												<div class="c3_button2" onclick="closeResetPwdDiv()" style="margin:0">
													<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
													<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
													<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
												</div>
											</td>
				    					</tr>
				    				</table>		    			
		    			</div> 
		    		</div>
  </body>
</html>
