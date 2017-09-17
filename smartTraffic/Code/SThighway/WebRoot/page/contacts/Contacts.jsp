<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>通讯录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/manageins/Contacts.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/CRselectBox.css" />
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>	
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/contacts/Contacts.js"></script>
	
  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<%-- <jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="1,1" />
	    </jsp:include> --%>
	    <input type="hidden" value="<%=request.getParameter("bmdm")%>" id="bmdm" />
	    <input type="hidden" value="<%=request.getParameter("gljgdm")%>" id="gljgdm" />
		<div class="manage_c1" >
				<div class="left_I1">
					<div class="left_select" id="left_no_select1">
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
					<div class="dpt_left_select" id="dpt_left_no_select2">
						
					</div>
					<ul id="department_left_select_child2" class="department_left_select_child">
					</ul>
				</div>
		    	<div class="manage_c2" id="manage_c2">
		    		<div style="float:left;width:100%;">
		    			<div style="width:100%;height:50px;">
							<span class="manage_wordstyle" style="float:left;" id="departmentry_title"></span>
							<div class="c3_button1" id="order_button" onclick="showSortDiv()" style="float:right;margin-left:0;display:none;" onmouseover="orderbuttonover()" onmouseout="orderbuttonout()">
								<div class="c3_button1_left" id="order_button_left" ></div>
								<div class="c3_button1_center" style="width:76px;" id="order_button_center" >排序</div>
								<div class="c3_button1_right" id="order_button_right" ></div>
							</div>
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
							<div class="letter" >全部</div><div class="letter" >A</div><div class="letter" >B</div><div class="letter" >C</div><div class="letter" >D</div><div class="letter" >E</div><div class="letter" >F</div><div class="letter">G</div>
							<div class="letter" >H</div><div class="letter" >I</div><div class="letter" >J</div><div class="letter" >K</div><div class="letter" >L</div><div class="letter" >M</div><div class="letter" >N</div>
							<div class="letter" >O</div><div class="letter" >P</div><div class="letter" >Q</div><div class="letter" >R</div><div class="letter" >S</div><div class="letter" >T</div><div class="letter">U</div>
							<div class="letter">V</div><div class="letter" >W</div><div class="letter">X</div><div class="letter" >Y</div><div class="letter" >Z</div>
						</div>
						<div style="width:100%;">
							<table class="listTable" id="userTable" cellpadding="0" cellspacing="0">
							<col width="15%"/><col width="20%"/><col width="20%"/><col width="13%"/><col width="12%"/>
							<col width="10%"/><col width="10%"/>
								<tr>
									<th>姓名</th>
									<th>部门</th>
									<th>职务</th>
									<th>办公室电话</th>
									<th>手机长号</th>
									<th>手机短号</th>
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
							<div class="User_S4" id="pageDiv" style="background:#f6f6f6;display:none;">
								<p>
									共<span id="allTotal"></span>条
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
			
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
	   
    </div>
    
    	<!-- 查看人员信息 -->
   			<div class="dialog" id="member_info" style="display:none;width:600px;margin:-251px 0 0 -300px;">
					<div class="member_info_top"><label style="margin-left:20px;float:left;">职员详情</label>
						<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closememberInfoDiv()"></div>
					<div class="pop_up_middle" style="max-height:450px;overflow-y:auto;overflow-x:hidden;">
							<div class="outer_table">
				    				<table class="see_pop_up_middle_table" id="ryinfolist" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="430px"/>
				    					<!-- <tr>
				    						<td class="td_left1">姓名&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_name"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">部门&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_dpt"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">职务&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_position"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">手机长号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_sjch"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">手机短号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_sjdh"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">办公室电话&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_bgsdh"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">角色&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="member_role"></label></td>
				    					</tr> -->
				    				</table>
				    			</div>		    			
		    			</div> 
				</div>
    	
		 <!-- 重置密码 -->		
		   <div class="dialog" id="ResetPwdDiv" style="display:none;width:600px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">重&nbsp;置&nbsp;密&nbsp;码</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeResetPwdDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom: 0"></div>
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="430px"/>
				    					<tr>
				    						<td class="td_left1">密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="td_input1" style="width:370px" id="reset_newmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">确认密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="td_input1" style="width:370px" id="reset_checkmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">&nbsp;</td>
											<td  class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">
												<div class="c3_button1" onclick="resetPwd()" style="margin:0 15px 0 10px;">
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
		    	<!-- 人员排序 -->
		    		<div class="dialog"  id="sortDiv" style="display:none;width:300px;background: white;">
		    			<div class="User_Top1">
		    				<label class="Top_left">人员排序</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closesortDiv()">
		    			</div>
		    			<div class="sort_div" id="sort_div">
		    			
		    				<ul id="outer_wrap" class="sort_ul">
		    				</ul>
		    			</div>
		    			<div style="width:100%;height:40px;border-top:solid 1px #d8d8d8;">
			    			<div class="c3_button1" onclick="suresort()" style="margin:4px 15px 0 10px;">
								<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
								<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
								<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
							</div>
							<div class="c3_button2" onclick="closesortDiv()" style="margin-top:4px">
								<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
								<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
								<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
							</div>
						</div>
		    		</div>
		    		
		    			<!-- 新成员入职 -->
		    		<div class="dialog" id="addnewMemberDiv" style="display:none;width:845px;margin:-300px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="member_title">新成员入职</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddnewMemberDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" id="addscroolDiv" style="margin-bottom: 0;max-height:359px;overflow-y:auto;overflow-x:hidden; ">
				    				<table class="see_pop_up_middle_table" id="addnewMemberTable"  cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="250px"/><col width="130px"/><col width="250px"/>
				    					<tr>
				    						<td class="td_left1">姓名&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="name" type="text"/></td>
				    						<td class="td_left2">姓名全拼&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xmqp" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">姓名首字母&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xmszm" type="text"/></td>
				    						<td class="td_left2">所属角色&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="ssjs" style="width:207px;"  ></select></td>
				    					</tr>
				    					<tr class="hideMmTr">
				    						<td class="td_left1">设置密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="mm" type="password"/></td>
				    						<td class="td_left2">确认密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="checkmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">手机长号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="lxdh" type="text" /></td>
				    						<td class="td_left2">短号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right" style="border-right:0;">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="sjdh" type="text" /></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属机构&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="ssjg0" style="width:207px;" onchange="selectaddDpt(0)"></select></td>
				    						<td class="td_left2">所属部门&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right"><label style="float:left;">&nbsp;&nbsp;&nbsp;</label><select id="ssbm0" name="ssbm" style="width:207px;float:left;"></select>
				    							<img class="add_image" onmouseover="addImageOver(this)" onmouseout="addImageOut(this)"
				    								src="image/main/ic_add_normal.png" onclick="addDepartment()" /></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">职务&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">
				    							<div class="type_select_div" style="margin-left:12px;">
													<div class="type_select" >
														<div class="type_select_mark" id="divposition0" onclick="showZWDiv(0)">
															<label class="zw_label" id="zw0">选择职务</label><input type="hidden" name="zwbh" id="zwid0" value="" /> <img src="image/main/arrow_down.png"
																style="float:right;margin:13px 10px 0 0;" id="selectbm_image">
														</div>
														<div class="zw_down_div" id="zw_down_div_add0">
															<ul class="zw_down_ul" id="zwselect0">
																
															</ul>
															<button class="zw_button" onclick="selectZW(0)">确定</button><button class="zw_button" onclick="hideZWDiv(0)">取消</button>
														</div>
													</div>
												</div>
				    						</td>
				    						<td class="td_left2">办公室电话&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" name="bgsdh" id="bgsdh0" type="text"/>
				    							<!-- <img class="add_image" src="image/main/ic_delete_normal.png" onclick="delDepartment(1)" onmouseover="delImageOver(this)" onmouseout="delImageOut(this)" /> --></td>
				    					</tr>
				    				</table>
				    				<div >
				    					<div style="width:134px;height:52px;float:left;"></div>
										<div class="c3_button1" onclick="addNewMember()" style="margin:10px 15px 0 10px;">
											<div class="c3_button1_left_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
											<div class="c3_button1_center_pop" onmouseover="buttonOver()" onmouseout="buttonOut()">确定</div>
											<div class="c3_button1_right_pop" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
										</div>
										<div class="c3_button2" onclick="closeaddnewMemberDiv()" style="margin:10px 0 0 0;">
											<div class="c3_button2_left_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" ></div>
											<div class="c3_button2_center_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()" >取消</div>
											<div class="c3_button2_right_pop" onmouseover="buttonDelOver()" onmouseout="buttonDelOut()"></div>
										</div>
				    				</div>
				    			</div>		    			
		    			</div> 
		    		</div>
		    		
		    		
		    	<!-- 新成员入职 -->
		    		<!-- <div class="dialog" id="addnewMemberDiv" style="display:none;width:800px;margin:-231px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="member_title">新成员入职</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddnewMemberDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom: 0;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="130px"/><col width="270px"/><col width="130px"/><col width="250px"/>
				    					<tr>
				    						<td class="td_left1">姓名&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="name" type="text"/></td>
				    						<td class="td_left2">姓名全拼&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xmqp" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">姓名首字母&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xmszm" type="text"/></td>
				    						<td class="td_left2">职务&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">
				    							<div class="type_select_div" style="margin-left:12px;">
													<div class="type_select" >
														<div class="type_select_mark" onclick="showZWDiv()">
															<label class="zw_label" id="zw">选择职务</label> <img src="image/main/arrow_down.png"
																style="float:right;margin:13px 10px 0 0;" id="selectbm_image">
														</div>
														<div class="zw_down_div" id="zw_down_div_add">
															<ul class="zw_down_ul" id="zwselect">
																
															</ul>
															<button class="zw_button" onclick="selectZW(1)">确定</button><button class="zw_button" onclick="hideZWDiv(1)">取消</button>
														</div>
													</div>
												</div>
				    						</td>
				    					</tr>
				    					<tr class="hideMmTr">
				    						<td class="td_left1">设置密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="mm" type="password"/></td>
				    						<td class="td_left2">确认密码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="checkmm" type="password"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属部门&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right"><select id="ssbm" style="width:277px;"></select>
					    						<div class="type_select_div" style="margin-left:12px;">
													<div class="type_select" >
														<div class="type_select_mark" onclick="showDepartmentDiv()">
															<label
																style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:5px;cursor: pointer;"
																id="ssbm">选择部门</label> <img src="image/main/arrow_down.png"
																style="float:right;margin:13px 10px 0 0;" id="selectbm_image">
														</div>
														<div class="type_down_ul" id="addselect">
															
														</div>
													</div>
												</div>
				    						</td>
				    						<td class="td_left2">所属角色&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="ssjs" style="width:207px;" ></select></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">办公室电话&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bgsdh" type="text"/></td>
				    						<td class="td_left2">手机长号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="lxdh" type="text" /></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">短号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right" style="border-right:0;">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="sjdh" type="text" /></td>
				    						<td style="border-right:0;">&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;</td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">&nbsp;</td>
											<td  class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">
												<div class="c3_button1" onclick="addNewMember()" style="margin:0 15px 0 10px;">
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
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div> -->
  </body>
</html>
