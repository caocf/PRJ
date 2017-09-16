<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>湖州港航公众服务系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="css/sailing/sailreport.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/table.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/style.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/sailing/sailreport.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/Operation.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/time/WdatePicker.js"></script>
	
  </head>
  	
  <body >
    <div class="sail1">
    	<jsp:include page="sailtop.jsp"></jsp:include>
    	<div class="sail2">
    	<input type="hidden" value="<%=basePath%>" id="basePath" />
	    	<div class="position1">
	    		<div class="sail3">
	    			<img src="image/operation/add_know_normal.png" class="addbutton" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)" onClick="addReport()">
	    			<img src="image/sailing/manageboatmaninfo_normal.png" style="float:left;margin-top:10px;margin-left:10px;" onClick="goToSailor()" onMouseOver="manageboatmanOver(this)" onMouseOut="manageboatmanOut(this)"/>
	    			<select style="float:left;margin-top:15px;margin-left:10px;" id="selectBindBoatInfo" onChange="selectBindBoat()"></select>
	    			<span style="font-size: 14px;margin-left:10px;float:left;" id="selectBindBoatspan"></span>
	    			<img alt="搜索" src="image/operation/search_normal.png" class="searchbutton" onClick="showSailReportList('ElectricReportListByShip',1)" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)">
	    			<input class="searchinput" id="searchinput">
	    		</div>
	    		<div class="sail4">
	    			<table id="reportTable" class="listTable" cellpadding="0" cellspacing="0">
	    				<col width="7%"/><col width="7%"/><col width="7%"/><col width="24%"/>
	    				<col width="12%"/><col width="12%"/><col width="7%"/><col width="24%"/>
	    				<tr>
	    					<th>报告类型</th>
	    					<th>始发港</th>
	    					<th>目的港</th>
	    					<th>本次作业码头</th>
	    					<th>预计进出港时间</th>
	    					<th>报告时间</th>
	    					<th>是否异常</th>
	    					<th>操作</th>
	    				</tr>
	    				</table>
	    			<div class="User_S4" id="pageDiv">
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
     <!-- 调整航线 -->
		  <div class="dialog" id="sailLineDiv" style="display:none;width:360px;height:400px;">
		  		<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			&nbsp;&nbsp;调整航线
		  		</div>
		  		<div style="height:30px;margin:20px 0 0 20px;">
		  			<span style="width: 80px;display:inline-block;">预计进出港时间</span><input type="text" style="width:180px;" id="entertime" readonly="readonly"
		    						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate"/>
		  		</div>
		  		<div style="height:240px;margin:20px 0 0 20px;">
		  			<span style="width: 80px; display:inline-block;">目的港</span><input type="text" 
		  				style="width:188px;height:25px;line-height:25px;vertical-align:middle;border: solid 1px #d8d8d8;" readonly="readonly" id="PortName"/>
		  			<div style="border:solid 1px #e8e9ed;width:95%;margin-top:5px;">
		  				<ul class="portname" style="height:200px" id="portname">	
		  				</ul>
		  			</div>
		  		</div>
		  		<div style="float:right;margin-right:10px;margin-top:10px;">
		  			<img src="image/operation/sure_normal.png" onClick="adjustPortAndTime()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeLineDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
		  		</div>
		  </div>
		 <!-- 管理船员信息 -->
		 <div class="dialog" id="boatmanInfoDiv" style="display:none;width:360px;height:350px;">
		  		<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			&nbsp;&nbsp;管理船员信息
		  		</div>
		  		<div style="height:250px;margin:20px 0 0 20px;">
		  			<table class="boatmanlistTable" style="" cellpadding="0" cellspacing="0">
		  			<col width="20%"><col width="80%">
		  				<tr>
		  					<td>船长</td>
		  					<td><input type="text" class="input_info" id="captain"/></td>
		  				</tr>
		  				<tr style="border-bottom:solid 1px #e8e9ed;">
		  					<td>证件号</td>
		  					<td><input type="text" class="input_info" id="captainID"/></td>
		  				</tr>
		  				<tr>
		  					<td>大副</td>
		  					<td><input type="text" class="input_info" id="mate"/></td>
		  				</tr>
		  				<tr style="border-bottom:solid 1px #e8e9ed;">
		  					<td>证件号</td>
		  					<td><input type="text" class="input_info" id="mateID"/></td>
		  				</tr>
		  				<tr>
		  					<td>水手</td>
		  					<td><input type="text" class="input_info" id="sailor"/></td>
		  				</tr>
		  				<tr style="border-bottom:solid 1px #e8e9ed;">
		  					<td>证件号</td>
		  					<td><input type="text" class="input_info" id="sailorID"/></td>
		  				</tr>
		  			</table>
		  		</div>
		  		<div style="margin-left:100px;margin-top:10px;">
		  			<img src="image/operation/sure_normal.png" onClick="manageBoatInfo()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeBoatmanDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
		  		</div>
		  </div>
  </body>
 
  </html>
