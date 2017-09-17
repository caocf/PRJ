<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>服务设施</title>
    
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
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/services/Services.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
  			<div class="left_I1">
				<div class="left_no_select" id="left_no_select1">
					<label class="left_name" id="gljg_bossname">服务设施</label>
				</div>
				<ul id="left_select_child1" class="left_select_child">
					<li class='left_select_li' onclick="goToService()">服务区</li>
					<li class='left_no_select_li' onclick="goTofeeStation()">收费站</li>
				</ul>
			</div>
	    	<div class="common_c1">
		    	<div class="common_c2">
		    		<div class="right_title">
						<span class="wordstyle" id="area_name">服务设施</span>
						<!-- <div class="c3_button1" onclick="goToStatistic(2)" style="float:right;margin-right:15px;" onmouseover="buttonOver()" onmouseout="buttonOut()">
							<div class="c3_button1_left"></div>
							<div class="c3_button1_center">统计分析</div>
							<div class="c3_button1_right"></div>
						</div> -->
					</div>
						 <div class="common_c3" style="border-bottom:solid 1px #dcdcdc;">
						<!--	<div class="c3_button1" onclick="addfwqService()" style="margin:10px 15px 0 10px;float:right;" onmouseover="addbuttonover()" onmouseout="addbuttonout()">
								<div class="c3_button1_left" id="add_button_left"></div>
								<div class="c3_button1_center" id="add_button_center">新增</div>
								<div class="c3_button1_right" id="add_button_right"></div>
							</div> -->
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="showServicesInfo('fwssmanager/fwqlist',1)">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='设施名称' id='service_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="25%"/><col width="25%"/><col width="25%"/><col width="25%"/>
							<tr>
								<th>服务设施名称</th>
								<th>所属公司</th>
								<th>线路名称</th>
								<th>操作</th>
							</tr>
							
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
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
   	 	<!-- 新增服务区 -->
   				 <div class="dialog" id="addfwqDiv" style="display:none;width:800px;margin:-288px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="fwq_title"></label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddfwqDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom:0;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">服务区名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" type="text" id="fwqmc"></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xlmc" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属公司&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" type="text" id="ssgs"></td>
				    						<td class="td_left2">加油设施&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input type="radio" name="jyss" value="0" />无&nbsp;&nbsp;&nbsp;<input type="radio" name="jyss" value="1" />有</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">餐饮设施&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input type="radio" name="cyss" value="0" />无&nbsp;&nbsp;&nbsp;<input type="radio" name="cyss" value="1" />有</td>
				    						<td class="td_left2">住宿设施&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input type="radio" name="zsss" value="0" />无&nbsp;&nbsp;&nbsp;<input type="radio" name="zsss" value="1" />有</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">购物设施&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input type="radio" name="gwss" value="0" />无&nbsp;&nbsp;&nbsp;<input type="radio" name="gwss" value="1" />有</td>
				    						<td class="td_left2">车辆维修设施&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input type="radio" name="clwxss" value="0" />无&nbsp;&nbsp;&nbsp;<input type="radio" name="clwxss" value="1" />有</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">上行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="sxjkzh" type="text"/></td>
				    						<td class="td_left2">下行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xxjkzh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">上行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="sxckzh" type="text"/></td>
				    						<td class="td_left2">下行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xxckzh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">管理中队桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="glzdzh" type="text"/></td>
				    						<td class="td_left2">治超站桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zczzh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">管理交警桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="gljjzh" type="text"/></td>
				    						<td class="td_left2">&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;</td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border-right:0;">&nbsp;</td>
											<td class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border-right:0;" colspan="3">
												<div class="c3_button1" onclick="addfwq()" style="margin:0 15px 0 10px;">
													<div class="c3_button1_left_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddfwqDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()">
													<div class="c3_button2_left_pop"></div>
													<div class="c3_button2_center_pop">取消</div>
													<div class="c3_button2_right_pop"></div>
												</div>
											</td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
		    	<!-- 查看服务区详情 -->
		    		 <div class="dialog" id="fwqdetailDiv" style="display:none;width:800px;margin:-246px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">服务区详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closefwqdetailDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">服务区名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="fwqmc_detail"></label></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xlmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属公司&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ssgs_detail"></label></td>
				    						<td class="td_left2">加油设施&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jyss_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">餐饮设施&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cyss_detail"></label></td>
				    						<td class="td_left2">住宿设施&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zsss_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">购物设施&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gwss_detail"></label></td>
				    						<td class="td_left2">车辆维修设施&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="clwxss_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">上行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sxjkzh_detail"></label></td>
				    						<td class="td_left2">下行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xxjkzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">上行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sxckzh_detail"></label></td>
				    						<td class="td_left2">下行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xxckzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">管理中队桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="glzdzh_detail"></label></td>
				    						<td class="td_left2">治超站桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zczzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">管理交警桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td  style="border-right:0;" id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gljjzh_detail"></label></td>
				    						<td style="border-right:0;">&nbsp;&nbsp;&nbsp;</td>
				    						<td>&nbsp;</td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
  </body>
</html>
