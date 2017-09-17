<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>交通量</title>
    
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
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/traffic/RealTraffic.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/top.jsp" flush="true" />
    			<div class="left_I1">
					<div class="left_no_select" id="left_no_select1">
						<label class="left_name" id="gljg_bossname">交通量</label>
					</div>
					<ul id="left_select_child1" class="left_select_child">
						<li class='left_no_select_li' onclick="goToTraffic()">交调信息</li>
						<li class='left_select_li' onclick="goToRealTraffic()">统计数据</li>
					</ul>
				</div>
	    	<div class="common_c1">
		    	<div class="common_c2">
		    		<div class="right_title" >
						<span class="wordstyle" id="area_name">交通量</span>
					<!-- 	<div class="c3_button1" onclick="goToStatistic(3)" style="float:right;margin-right:15px;" onmouseover="trabuttonOver()" onmouseout="trabuttonOut()">
							<div class="c3_button1_left" id="tra_button1_left"></div>
							<div class="c3_button1_center" id="tra_button1_center">统计分析</div>
							<div class="c3_button1_right" id="tra_button1_right"></div>
						</div> -->
					</div>
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<select id="selectway" onchange="showInput()" style="float:left;margin:12px 15px 0 15px;">
								<option value="1">年</option>
								<option value="2">月</option>
								<option value="3">日</option>
								<option value="4">季度</option>
								<option value="5">半年</option>
							</select>
							<input type="text" style="margin-top:11px;float:left;" id="year" readonly="readonly" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy'})" />
							<input type="text" style="display: none;float:left;margin-top:11px;" id="month" readonly="readonly" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM'})" />
							<input type="text" style="display: none;float:left;margin-top:11px;" id="day" readonly="readonly" class="Wdate" onFocus="WdatePicker({maxDate:'%y-%M-%d'})" />
							<!-- <input type="text" style="display: none;float:left;margin-top:11px;" id="quarter" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM季度',disabledDates:['....-0[5-9]-..','....-1[0-2]-..','%y-01-01']})" /> -->
							<div id="quarter" style="display: none;float:left;margin-top:11px;">
								<input type="text"  id="quarter_year" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy'})" />
								<select id="quarter_four" style="margin-left:5px;"><option value="1">第一季度</option><option value="2">第二季度</option><option value="3">第三季度</option><option value="4">第四季度</option></select>
							</div>
							<div id="halfyear" style="display: none;float:left;margin-top:11px;">
								<input type="text"  id="halfyear_year" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy'})" />
								<select id="halfyear_half" style="margin-left:5px;"><option value="1">上半年</option><option value="2">下半年</option></select>
							</div>
							<div class="c3_button1" onclick="showRealTrafficInfo('jxdczxx/transdatalist',1);" style="margin:10px 15px 0 10px;" onmouseover="addbuttonover()" onmouseout="addbuttonout()">
								<div class="c3_button1_left" id="add_button_left"></div>
								<div class="c3_button1_center" id="add_button_center">查询</div>
								<div class="c3_button1_right" id="add_button_right"></div>
							</div>
						</div>
						<table class="listTable" id="infoTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="11%"/><col width="11%"/><col width="11%"/>
						<col width="10%"/><col width="10%"/><col width="13%"/><col width="8%"/><col width="11%"/>
							<tr>
								<th>路线名称</th>
								<th>观测里程（公里）</th>
								<th>机动车当量数合计</th>
								<th>机动车自然数合计</th>
								<th>汽车当量数合计</th>
								<th>汽车自然数合计</th>
								<th>适应交通量（辆/日）</th>
								<th>交通拥挤度</th>
								<th>操作</th>
							</tr>
							
						</table> 
						<table class="listTable" id="totalTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="11%"/><col width="11%"/><col width="11%"/>
						<col width="10%"/><col width="10%"/><col width="13%"/><col width="8%"/><col width="11%"/>
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
		    	<!-- 查看实时交通量详情 -->
		    		 <div class="dialog" id="DetailDiv" style="display:none;width:800px;margin:-300px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">实时信息详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle" style="height:500px;overflow-y:auto;overflow-x:hidden;">
		    				<div class="outer_table">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="190px"/><col width="190px"/><col width="190px"/><col width="190px"/>
				    					<tr>
				    						<td class="td_left1">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxmc"></label></td>
				    						<td class="td_left2">观测里程（公里）&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gclc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">机动车当量数合计&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jdcdlshj"></label></td>
				    						<td class="td_left2">机动车自然数合计&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jdczrshj"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">汽车当量数合计&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qcdlshj"></label></td>
				    						<td class="td_left2">汽车自然色合计&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qczrshj"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">小型货车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xxhc"></label></td>
				    						<td class="td_left2">中型货车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxhc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">大型货车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dxhc"></label></td>
				    						<td class="td_left2">特大型货车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="tdxhc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">集装箱车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jzxc"></label></td>
				    						<td class="td_left2">中小客车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxkc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">大客车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dkc"></label></td>
				    						<td class="td_left2">摩托车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="mtc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">拖拉机&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="tlj"></label></td>
				    						<td class="td_left2">人力车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="rlc"></label>&nbsp;</td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">蓄力车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="clc"></label></td>
				    						<td class="td_left2">自行车&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zxc"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">行驶量（万车公里/日）&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xsl"></label></td>
				    						<td class="td_left2">适应交通量（辆/日）&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="syjtl"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">交通拥挤度&nbsp;&nbsp;&nbsp;</td>
				    						<td id="jtyjd" class="td_right" style="border-right:0;"></td>
				    						<td style="border-right:0;">&nbsp;&nbsp;&nbsp;</td>
				    						<td >&nbsp;</td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
  </body>
</html>
