<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>行政执法</title>
    
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
	<script type="text/javascript" src="js/common/search.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/law/enforcelaw.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/top.jsp" flush="true" />
    			<div class="left_I1" id="left_I1">
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
	    	<div class="common_c1" id="common_c1">
		    	<div class="common_c2" id="sss">
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<div class="select_outer_div">
									<div class="select_text_div" >
										<div class="select_label_div"  onclick="showSelectUl()" ><label class="select_label" id="select_label">筛选</label>
										<img src="image/main/arrow_down.png" style="float:right;margin:13px 10px 0 0;" id="select_image" ></div>
										<ul class="select_down_ul">
											<li class="select_down_li" id="xzcfjdwh">行政处罚决定书文号</li>
											<li class="select_down_li" id="ajmc">案件名称</li>
										</ul>
									</div>
								</div>
							<div class='search_div' style="float:left;width:auto;">
								<input id='search_input' class="search_input" style="float:left;"
											onfocus="TextFocus(this)" onblur="TextBlur(this)" />
								<div class="image_div" style="float:left;" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchInfoList()">
									<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image"></div>
							</div>
							
							<button class="add_button" onclick="goToaddpage()">新增</button>
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="25%"/><col width="25%"/>
						<col width="20%"/><col width="15%"/><col width="15%"/>
							<tr>
								<th>行政处罚决定书文号</th>
								<th>案件名称</th>
								<th>被处罚人姓名或者名称</th>
								<th>处罚日期</th>								
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作</th>
								
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
   				 <div class="dialog" id="DetailDiv" style="display:none;width:950px;margin:-300px 0 0 -450px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle" style="height:550px;overflow-y:auto;overflow-x:hidden; ">
		    				<div class="outer_table" style="width:910px;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="220px"/><col width="240px"/><col width="220px"/><col width="230px"/>
				    					<tr>
											<td class="td_left1">行政处罚决定书文号：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfjdwh_detail" class="td_right" style="padding-left:5px;"></td>
											<td class="td_left2">案件名称：&nbsp;&nbsp;&nbsp;</td>
											<td id="ajmc_detail" class="td_right" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">违法企业组织机构代码：&nbsp;&nbsp;&nbsp;</td>
											<td id="wfqyzjjgdm" class="td_right" colspan="3" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">被处罚对象类别：&nbsp;&nbsp;&nbsp;</td>
											<td id="bcfdxlb" class="td_right" style="padding-left:5px;"></td>
											<td class="td_left2">被处罚人姓名或者名称：&nbsp;&nbsp;&nbsp;</td>
											<td id="cfrxm" class="td_right" style="padding-left:5px;"></td>
										</tr>
										<tr class="frdbTr" style="display: none;">
											<td class="td_left1">被处罚单位法定代表人姓名：&nbsp;&nbsp;&nbsp;</td>
											<td id="fddbrxm" class="td_right" style="padding-left:5px;"></td>
											<td class="td_left2">法定代表人身份证号码：&nbsp;&nbsp;&nbsp;</td>
											<td class="td_right" id="fddbrcard" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">被处罚对象证件类型：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfcardname" class="td_right" style="padding-left:5px;"></td>
											<td class="td_left2">被处罚对象证件号码：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfcardnumber" class="td_right" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">权力事项：&nbsp;&nbsp;&nbsp;</td>
											<td id="taskname" class="td_right" colspan="3" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">主要违法事实：&nbsp;&nbsp;&nbsp;</td>
											<td colspan="3"  class="td_right" id="zywfss" style="padding-left:5px;line-height: 30px;"></td>
										</tr>
										<tr>
											<td class="td_left1">行政处罚的履行方式和期限：&nbsp;&nbsp;&nbsp;</td>
											<td colspan="3" class="td_right" style="padding-left:5px;"><div id="xzcf"></div></td>
										</tr>
										<tr>
											<td class="td_left1">行政处罚的种类和依据：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfzl" class="td_right" style="padding-left:5px;"></td>
											<td class="td_left2">做出行政处罚机关名称：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfjg" class="td_right" style="padding-left:5px;"></td>
										</tr>
										<tr>
											<td class="td_left1">行政处罚日期：&nbsp;&nbsp;&nbsp;</td>
											<td id="xzcfrq" class="td_right" style="padding-left:5px;"></td>
											<td style="border:0">&nbsp;&nbsp;</td>
											<td style="border:0">&nbsp;</td>
										</tr>
										<tr>
											<td class="td_left1">备注：&nbsp;&nbsp;&nbsp;</td>
											<td colspan="3" class="td_right" style="padding-left:5px;"><div id="bz"></div></td>
										</tr>
										<tr>
											<td class="td_left1">数据来源：&nbsp;&nbsp;&nbsp;</td>
											<td colspan="3" class="td_right" style="padding-left:5px;"><div id="xzcfly"></div></td>
										</tr>
				    				</table>
				    			</div>	    			
		    			</div> 
		    		</div>
		    	
  </body>
</html>
