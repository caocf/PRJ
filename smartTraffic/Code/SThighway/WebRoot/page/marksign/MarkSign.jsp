<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>标志标线</title>
    
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
	<script type="text/javascript" src="<%=basePath%>js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="js/common/search.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/marksign/MarkSign.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <!-- <body onbeforeunload="checkLeave()"> -->
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
    			<div class="left_I1">
					<div class="left_no_select" id="left_no_select1" onclick="showThePartMark(0,'嘉兴市',this)" style="cursor: pointer;">
						<label class="left_name" id="gljg_bossname">嘉兴市</label>
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
				<input type="hidden" value="<%=request.getParameter("num") %>" id="num"/>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<!-- <span class="wordstyle" id="area_name">嘉兴市桥梁信息</span> -->
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc;background: #f6f6f6;">
							
							<div class="type_select_div" style="float:left;margin-left:15px;">
								<div class="type_select" >
									<div class="type_select_mark"  onclick="showMarkTypeDiv()" ><label style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:5px;" id="bzbxlxmc">请选择标志标线类型</label>
									<img src="image/main/arrow_down.png" style="float:right;margin:13px 10px 0 0;" id="select_image" ></div>
									<ul class="type_down_ul">
										<li onclick="findMarkInfo('','所有标志')">所有标志</li>
									</ul>
								</div>
							</div>
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchMarkSignInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='桩号' id='mark_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
							
						</div>
						<table class="markTable" id="markTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="10%"/><col width="10%"/><col width="10%"/><col width="10%"/>
						<col width="10%"/><col width="18%"/><col width="17%"/>
							<tr>
								<th>内容图案</th>
								<th>标志位置（左）</th>
								<th>标志位置（中）</th>
								<th>标志位置（右）</th>
								<th>标志标线类型</th>
								<th>安装时间</th>
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
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
    			<!-- 新增标志标线 -->
   				 <div class="dialog" id="addMarkDiv" style="display:none;width:800px;margin:-258px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="member_title">新增标志标线</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeaddMarkDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom:0;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">所属行政区划&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="xzqh" style="width:207px;outline: none"></select></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xlmc" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志标线类型&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="bzbxlx" style="width:207px;outline: none"></select></td>
				    						<td class="td_left2">公路等级&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="gldj" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志位置&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="bzwz_position" style="width:80px;outline: none"><option value="1">左</option><option value="2">中</option><option value="3">右</option><option value="4">左中右</option></select>
				    							<input class="mark_input" id="bzwz" type="text" style="width:120px;"/></td>
				    						<td class="td_left2">管理单位&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="gldw" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容图案&nbsp;&nbsp;&nbsp;</td>
				    						<td colspan="3" style="text-align: left;"  class="td_right">
				    							&nbsp;&nbsp;&nbsp;<input type="file" id="pictureName" name="uploadFile">
				    							<!-- <button onclick="openfile()">选择图片</button> -->
				    						</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bmnrsl" type="text"/></td>
				    						<td class="td_left2">版面尺寸&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bmcc" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">支撑形式及规格&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zcxsjgg" type="text" /></td>
				    						<td class="td_left2">安装时间&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input style="height:34px;line-height: 34px;vertical-align: middle;width:195px;cursor: pointer;"	
				    							onFocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly" class="Wdate" id="azsj" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">制作安装单位&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zzazdw" type="text"/></td>
				    						<td class="td_left2">备注&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bz" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border: 0;">&nbsp;</td>
											<td class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border: 0;" colspan="3">
												<div class="c3_button1" onclick="addMarkSign()" style="margin:0 15px 0 10px;">
													<div class="c3_button1_left_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
												</div>
												<div class="c3_button2" onclick="closeaddMarkDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()">
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
		    <!-- 编辑标志标线 -->
   				 <div class="dialog" id="editMarkDiv" style="display:none;width:800px;margin:-258px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left" id="member_title">编辑标志标线</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeeditMarkDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom:0;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">所属行政区划&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="xzqh1" style="width:207px;outline: none"></select></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="xlmc1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志标线类型&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="bzbxlx1" style="width:207px;outline: none"></select></td>
				    						<td class="td_left2">公路等级&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="gldj1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志位置&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="bzwz_position1" style="width:80px;outline: none"><option value="1">左</option><option value="2">中</option><option value="3">右</option><option value="4">左中右</option></select>
				    							<input class="mark_input" id="bzwz1" type="text" style="width:120px;"/></td>
				    						<td class="td_left2">管理单位&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="gldw1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容图案&nbsp;&nbsp;&nbsp;</td>
				    						<td colspan="3" style="text-align: left;" class="td_right">
				    							&nbsp;&nbsp;&nbsp;<input type="file" id="pictureName1" name="uploadFile">
				    							<!-- <button onclick="openfile()">选择图片</button> -->
				    						</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容数量&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bmnrsl1" type="text"/></td>
				    						<td class="td_left2">版面尺寸&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bmcc1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">支撑形式及规格&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zcxsjgg1" type="text" /></td>
				    						<td class="td_left2">安装时间&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input style="height:34px;line-height: 34px;vertical-align: middle;width:195px;cursor: pointer;"	
				    							onFocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly" class="Wdate" id="azsj1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">制作安装单位&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="zzazdw1" type="text"/></td>
				    						<td class="td_left2">备注&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<input class="mark_input" id="bz1" type="text"/></td>
				    					</tr>
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border-right: 0">&nbsp;</td>
											<td class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border-right: 0" colspan="3">
												<div class="c3_button1" onclick="editMarkSign()" style="margin:0 15px 0 10px;" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">
													<div class="c3_button1_left_pop"  ></div>
													<div class="c3_button1_center_pop">确定</div>
													<div class="c3_button1_right_pop" ></div>
												</div>
												<div class="c3_button2" onclick="closeeditMarkDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()" >
													<div class="c3_button2_left_pop" ></div>
													<div class="c3_button2_center_pop"  >取消</div>
													<div class="c3_button2_right_pop" ></div>
												</div>
											</td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
		    		
		    		<!-- 查看标志标线 -->
   				 <div class="dialog" id="MarkDetailDiv" style="display:none;width:800px;margin:-219px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">标志标线详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeMarkDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div style="margin:20px;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">所属行政区划&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xzqh_detail"></label></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xlmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志标线类型&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="bzbxlx_detail"></label></td>
				    						<td class="td_left2">公路等级&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="gldj_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">标志位置&nbsp;&nbsp;&nbsp;</td>
				    						<td  class="td_right">&nbsp;&nbsp;&nbsp;<label id="position_detail"></label>&nbsp;&nbsp;<label id="bzwz_detail"></label></td>
				    						<td class="td_left2">管理单位&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="gldw_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容图案&nbsp;&nbsp;&nbsp;</td>
				    						<td colspan="3" style="text-align: left;"  class="td_right">&nbsp;&nbsp;&nbsp;<img id="bmnrtn_detail" style="max-height: 100px;" ></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">版面内容数量&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="bmnrsl_detail"></label></td>
				    						<td class="td_left2">版面尺寸&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="bmcc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">支撑形式及规格&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="zcxsjgg_detail"></label></td>
				    						<td class="td_left2">安装时间&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="azsj_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">制作安装单位&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="zzazdw_detail"></label></td>
				    						<td class="td_left2">备注&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right">&nbsp;&nbsp;&nbsp;<label id="bz_detail"></label></td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
  </body>
</html>
