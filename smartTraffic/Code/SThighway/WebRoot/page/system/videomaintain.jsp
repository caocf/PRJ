<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>视频维护信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css"> 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/UserManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/videomantain.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/system/setheight.js"></script>
	<script type="text/javascript" src="js/WdatePicker/WdatePicker.js"></script>	
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/button.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/system/VideomantainManage.js"></script>
  </head>
  
 <body>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="1,4" />
	    </jsp:include>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<div class="common_c3" style="height:52px;">
							<div class="c3_button1" onclick="showEditSpsxx(-1)" id="addbutton" style="margin-left:30px;">
								<div class="c3_button1_left" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
								<div class="c3_button1_center" onmouseover="buttonOver()" onmouseout="buttonOut()" style="width:90px;" >新建维护信息</div>
								<div class="c3_button1_right" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
							</div>
							<a onclick="updatesqldate()"
                           class="update_class">更新视频信息</a>
						</div>
						
						<table class="videolistTable" id="videoinfo" cellpadding="0" cellspacing="0" >
						<col width="1%"/><col width="15%"/><col width="15%"/><col width="15%"/><col width="20%"><col width="20%">
						<col width="14%">
							<tr>
								<th></th>
								<th>信息编号</th>
								<th>路线代码</th>
								<th>方向名</th>
								<th>上下行</th>
							    <th>操作</th>
							     <th>&nbsp;</th>
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
   			<!-- 新建或编辑维护信息 -->
		    		<div class="dialog" id="addnewSpsxx" style="display:none;width:380px;margin:-160px 0 0 -190px;background:white;">
		    				<div class="User_Top1">
		    				<label class="Top_left" id="sfz_title"></label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closewhsxxDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table" style="margin-bottom: 0;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="200px"/>
				    					<tr>
				    						<td class="td_left1">路线代码&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="lxdm" class="videoselect"></select></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">方向名&nbsp;&nbsp;&nbsp;</td>
				    						<td class="td_right">&nbsp;&nbsp;&nbsp;<select id="fxm" class="videoselect"></select></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">上下行&nbsp;&nbsp;&nbsp;</td>
				    						<td>&nbsp;&nbsp;&nbsp;<select id="sxx" class="videoselect">
				    						     <option value='-1' selected="true" disabled="true">请选择</option>
				    						     <option value=0>上行</option>
				    						     <option value=1>下行</option>
				    						     </select></td>
				    					</tr>
				    					
				    					<tr>
				    						<td style="height:60px;line-height: 60px;vertical-align: middle;border:0;">&nbsp;</td>
											<td  class="td_right" style="height:60px;line-height: 60px;vertical-align: middle;border:0;" colspan="3">
												<div class="c3_button1" onclick="addsfz()" style="margin:0 15px 0 10px;">
													<div class="c3_button1_left_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
													<div class="c3_button1_center_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()">确定</div>
													<div class="c3_button1_right_pop" onmouseover="buttonOverPop()" onmouseout="buttonOutPop()" ></div>
												</div>
												<div class="c3_button2" onclick="closewhsxxDiv()" style="margin:0" onmouseover="buttonDelOverPop()" onmouseout="buttonDelOutPop()">
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
   		
  </body>
</html>
