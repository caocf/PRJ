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
						<li class='left_no_select_li' onclick="goToService()">服务区</li>
						<li class='left_select_li' onclick="goTofeeStation()">收费站</li>
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
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<!-- <div class="c3_button1" onclick="addfeeStation()" style="margin:10px 15px 0 10px;float:right;" onmouseover="addbuttonover()" onmouseout="addbuttonout()">
								<div class="c3_button1_left" id="add_button_left" ></div>
								<div class="c3_button1_center" id="add_button_center">新增</div>
								<div class="c3_button1_right" id="add_button_right" ></div>
							</div> -->
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchFeeStationInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='收费站名称' id='service_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="12%"/><col width="15%"/><col width="15%"/>
						<col width="23%"/><col width="20%"/>
							<tr>
								<th>收费站名称</th>
								<th>收费站类型</th>
								<th>出口指向</th>
								<th>线路名称</th>
								<th>所属公司</th>
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
   	 	<!-- 新增收费站 -->
   				 <div class="dialog" id="addsfzDiv" style="display:none;width:800px;margin:-258px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="sfz_title"></label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddsfzDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom:0">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">收费站名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" type="text" id="sfzmc"></td>
				    						<td class="td_left2">收费站票据站名&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="sfzpjzm" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属公司&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" type="text" id="ssgs"></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xlmc" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">收费站类型&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="sfzlx"><option value="1">主线收费站</option><option value="2">匝道收费站</option></select></td>
				    						<td class="td_left2">出口指向&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="ckzx" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">进口ETC车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="jketccdsl" type="text"/></td>
				    						<td class="td_left2">出口ETC车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="cketccdsl" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">主线上行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zxsxjkzh" type="text"/></td>
				    						<td class="td_left2">主线上行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zxsxckzh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">进口人工车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="jkrgcdsl" type="text"/></td>
				    						<td class="td_left2">出口人工车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="ckrgcdsl" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">主线下行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zxxxjkzh" type="text"/></td>
				    						<td class="td_left2">主线下行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zxxxckzh" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">&nbsp;</td>
											<td class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border: 0;" colspan="3">
												<div class="c3_button1" onclick="addsfz()" style="margin:0 15px 0 10px;">
													<div class="c3_button1_left_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddsfzDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()">
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
		    		<!-- 查看收费站 -->
   				 <div class="dialog" id="DetailDiv" style="display:none;width:800px;margin:-212px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">收费站详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">收费站名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfzmc_detail"></label></td>
				    						<td class="td_left2">收费站票据站名&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfzpjzm_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">所属公司&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ssgs_detail"></label></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xlmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">收费站类型&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfzlx_detail"></label></td>
				    						<td class="td_left2">出口指向&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ckzx_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">进口ETC车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jketccdsl_detail"></label></td>
				    						<td class="td_left2">出口ETC车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cketccdsl_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">主线上行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxsxjkzh_detail"></label></td>
				    						<td class="td_left2">主线上行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxsxckzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">进口人工车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jkrgcdsl_detail"></label></td>
				    						<td class="td_left2">出口人工车道数量&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ckrgcdsl_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">主线下行进口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxxxjkzh_detail"></label></td>
				    						<td class="td_left2">主线下行出口桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxxxckzh_detail"></label></td>
				    					</tr>
				    				</table>	
				    			</div>	    			
		    			</div> 
		    		</div>
		    		 <script type="text/javascript" src="<%=basePath%>js/services/FeeStation.js"></script>
  </body>
 
</html>
