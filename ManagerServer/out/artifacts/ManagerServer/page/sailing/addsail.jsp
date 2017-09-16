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
	<script type="text/javascript" src="<%=basePath%>js/sailing/addsail.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/Operation.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/time/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/formatConversion.js"></script>
  </head>
  	
  <body >
    <div class="sail1">
    	<jsp:include page="sailtop.jsp"></jsp:include>
    	<div class="sail2">
    		<input type="hidden" value="<%=request.getParameter("reportId") %>" id="reportId"/>
    		<div class="sail4" style="margin:0 auto;min-height:800px; ">
    			<div class="titlename" id="titlename"></div>
    			<div style="max-height:800px;overflow-y:auto;overflow-x:hidden">
	    			<table id="sailboatTable" class="addTable" cellpadding="0" cellspacing="0">
	    				<col width="16%"/><col width="84%"/>
	    				<tr>
	    					<td>船名号</td>
	    					<td ><span id="boatName"></span></td>
	    				</tr>
	    				<tr>
	    					<td>报告类型</td>
	    					<td><select id="selectsail" onchange="selectSailStyle()"><option class="opti" value="1">重船航行</option><option class="opti" value="2">空船航行</option></select></td>
	    				</tr>
	    				<tr>
	    					<td>始发港</td>
	    					<td><span id="startport"></span><button id="showselectportDiv" style="display:none" class="selectbutton" onClick="showStartPortDiv()">选择港口</button></td>
	    				</tr>
	    				<tr>
	    					<td>目的港</td>
	    					<td><span id="arriveport"></span><button class="selectbutton" onClick="showPortDiv()">选择港口</button></td>
	    				</tr>
	    				<tr id="workwharf" style="display:none;">
	    					<td>本次作业码头</td>
	    					<td><span id="stopWharf"></span><button class="selectbutton" onClick="showWharfDiv()">选择码头</button></td>
	    				</tr>
	    				<tr id="inOrNotPort" style="display:none;">
	    					<td>选择进出港</td>
	    					<td><select id="selectport"><option class="opti" value="1">进港</option><option class="opti" value="2">出港</option></select></td>
	    				</tr>
	    				<tr id="cargoStyle">
	    					<td>载货种类</td>
	    					<td><span id="cargoName"></span><button class="selectbutton" onClick="showCargoDiv()">选择货物</button></td>
	    				</tr>
	    				<tr id="cargoNum">
	    					<td>载货数量</td>
	    					<td><input style="width:50px;height:23px;" id="goodWeight"/>&nbsp;
	    						<select id="unit">
	    							<option>吨</option>
	    							<option>升</option>
	    							<option>包</option>
	    							<option>箱</option>
	    							<option>桶</option>
	    							<option>袋</option>
	    							<option>件</option>
	    							<option>台</option>
	    						</select></td>
	    				</tr>
	    				<tr>
	    					<td>预计进出港时间</td>
	    					<td><input style="width:200px;height:23px;" id="entertime" readonly="readonly"
	    						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate"/></td>
	    				</tr>
	    				<tr>
	    					<td>上一次航次加油量</td>
	    					<td><input style="width:50px;height:23px;" id="addOil"/>&nbsp;<span>升&nbsp;</span></td>
	    				</tr>
	    				<tr>
	    					<td>上一次航次加油时间</td>
	    					<td><input style="width:200px;height:23px;" id="addOiltime" readonly="readonly"
	    						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate"/></td>
	    				</tr>
	    				<tr>
	    					<td><button style="height:27px;width:70px;float:left;line-height: 27px;vertical-align: middle" onclick="selectBoatmanInfo()">选择船员</button></td>
	    					<td>&nbsp;</td>
	    				</tr>
	    				</table>
    			</div>
    			<div style="margin-left:20px;height:50px;width:100%">
    					<img class="submit" src="image/operation/sure_normal.png" onClick="addInfo()" 
    							style="height:27px;margin-top:12px;margin-right:10px;float:left;"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    							
    						<img src="image/operation/back_small_normal.png" onClick="gotoBack()" 
    						 onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"  style="margin-top:12px;float:left;"  title="返回">
    						 
    			</div>
    		</div>
    		
    	</div>
    </div>
    <!-- 始发港口名称 -->
    <div class="dialog" id="selectStartPortDiv" style="display:none;height:350px;width:290px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
    		<span style="margin-left:10px;" >始发港</span>
    	</div>
    	<ul class="portname" id="startportname">
    		
    	</ul>
    	<div style="float:right;margin-right:10px;">
    		<img src="image/operation/sure_normal.png" onClick="selectStartPortToText()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeStartPortDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
    	</div>
    </div>
    <!-- 港口名称 -->
    <div class="dialog" id="selectPortDiv" style="display:none;height:350px;width:290px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
    		<span style="margin-left:10px;" >目的港</span>
    	</div>
    	<ul class="portname" id="portname">
    		
    	</ul>
    	<div style="float:right;margin-right:10px;">
    		<img src="image/operation/sure_normal.png" onClick="selectPortToText()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closePortDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
    	</div>
    </div>
     <!--载货种类 -->
    <div class="dialog" id="selectCargoDiv" style="display:none;height:500px;width:290px;">
   		 <div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
    		<span style="margin-left:10px;">选择货物</span>
    	</div>
    	<ul class="portname" id="cargoname" style="height:420px">	
    	</ul>
    	<div style="float:right;margin-right:10px;">
    		<img src="image/operation/sure_normal.png" onClick="selectCargoToText()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeCargoDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
    	</div>
    </div>
     <!-- 本次作业码头 -->
    <div class="dialog" id="selectWharfDiv" style="display:none;height:420px;width:320px;">
     <div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
    		<span style="margin-left:10px;">选择码头</span>
    	</div>
    		<div style="margin:0 auto;">
    		<img alt="搜索" src="image/operation/search_normal.png" class="searchbutton" onClick="SearchWharfInfo()" 
    		onmouseover="SearchOver(this)" onMouseOut="SearchOut(this)">
	    	<input class="searchinput" style="width:200px;" id="searchwharf"> </div>
	    	<div style="float:left;width:100%;margin-top:10px;height:290px; ">
	    			<!-- 港区 -->
	    			<ul class="portname" style="height:270px;" id="portList">
			    	</ul>
			    	<div onClick="selectWharfDiv(1)" style="height:20px;display:none;cursor: pointer;padding: 0 0 0 20px;font-weight: bold;" id="PieceDiv" >
			    		<img src="image/left/to_right.png"/>
			    		<span id="PieceName"></span>
			    		</div>
			    	<!-- 片区 -->
			    	<ul class="portname" style="height:270px;display:none;" id="areaList">
			    	</ul>
			    	<div onClick="selectWharfDiv(2)" style="height:20px;display:none;cursor: pointer;padding: 0 0 0 20px;font-weight: bold;" id="WharfDiv">
			    		<img src="image/left/to_right.png"/>
			    		<span id="WharfName"></span>
			    	</div>
			    	<!-- 码头 -->
			    	<ul class="portname" style="height:270px;display:none;" id="wharfname">
			    	</ul>
    		</div>
    	<div style="float:right;margin-right:10px;margin-top: 10px;">
    		<img src="image/operation/sure_normal.png" onClick="selectWharfToText()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    		<img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeWharfDialog()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
    	</div>
    </div>
     <!-- 选择船员信息 -->
    <div class="dialog" id="selectBoatManInfoDiv" style="display:none;width:300px;height:400px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			&nbsp;&nbsp;选择船员
		</div>
		<div style="height:300px;margin:20px 0 0 0;">
		  	<ul class="boatmanInfoUl" id="boatmanInfoUl">
		  		
		  	</ul>
		</div>
		<div style="float:right;margin-right:10px;margin-top:10px;">
			<img src="image/operation/sure_normal.png" onClick="determineBoatInfo()" style=''  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    <img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closesboatInfoDiv()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
		</div>
    </div>
    <div class="dialog" id="alarmInfoDiv" style="display:none;width:300px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			&nbsp;&nbsp;提示信息
		</div>
		<div style="margin:5px 5px 0 5px;" id="alarmInfo">
		  	
		</div>
		<div style="float:right;margin:10px 10px 0 10px;">
			<img src="image/operation/sure_normal.png" onClick="determineReport()" style=''  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		</div>
    </div>
    <!-- loading Div -->
    <div class="dialog" id="loadingDiv" style="display:none;width:156px;height:95px;margin:-48px 0 0 -78px;background: gray;opacity:0.5">
		<img alt="" src="image/sailing/loading.gif">
	</div>
  </body>
</html>
