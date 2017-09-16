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
	<script type="text/javascript" src="<%=basePath%>js/sailing/sailseaman.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/Operation.js"></script>

  </head>
  
  <body>
    <div class="sail1">
    	<jsp:include page="sailtop.jsp"></jsp:include>
    	<div class="sail2">
    		<input type="hidden" value="<%=request.getParameter("reportId") %>" id="reportId"/>
    		<div class="sail4" style="margin:0 auto;min-height:800px;">
    			<table id="logTable" class="addTable" cellpadding="0" cellspacing="0">
    				<col width="20%"/><col width="80%"/>
    		
    				<tr>
    					<td>船名号</td>
    					<td ><span id="boatName"></span><select id="selectboatname"></select></td>
    				</tr>
    				<tr>
    					<td>报告类型</td>
    					<td><select id="selectsail"><option value="1">重船航行</option><option value="2">空船航行</option></select></td>
    				</tr>
    				<tr>
    					<td>选择进港</td>
    					<td><select id="selectport"><option value="1">进港</option><option value="2">出港</option></select></td>
    				</tr>
    				<tr>
    					<td>始发港</td>
    					<td><span id="startport"></span><button onClick="showPortDiv()">选择港口</button></td>
    				</tr>
    				<tr>
    					<td>目的港</td>
    					<td><span id="arriveport"></span><button onClick="showPortDiv()">选择港口</button></td>
    				</tr>
    				<tr>
    					<td>载货种类</td>
    					<td><span id="cargoName"></span><button onClick="showCargoDiv()">选择货物</button></td>
    				</tr>
    				<tr>
    					<td>载货数量</td>
    					<td><input style="width:50px;height:23px;" id="goodWeight"/>&nbsp;<span>吨</span></td>
    				</tr>
    				<tr>
    					<td>本次作业码头</td>
    					<td><span id="stopWharf"></span><button onClick="showWharfDiv()">选择码头</button></td>
    				</tr>
    				<tr>
    					<td>预计进出港时间</td>
    					<td><input style="width:200px;height:23px;" id="entertime" readonly="readonly"
    						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate"/></td>
    				</tr>
    				<tr>
    					<td>上一航次加油量</td>
    					<td><input style="width:50px;height:23px;" id="addOil"/>&nbsp;<span>升</span></td>
    				</tr>
    				<tr>
    					<td>上一航次加油时间</td>
    					<td><input style="width:200px;height:23px;" id="addOiltime" readonly="readonly"
    						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate"/></td>
    				</tr>
    			</table>
    			<div style="float:left;margin:10px 20px 0 20px;">
    				<img class="submit" src="image/operation/sure_normal.png" onClick="addInfo()" style="height:27px;"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    				<img src="image/operation/back_small_normal.png" onClick="javascript:window.history.go(-1);" 
    				 onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)" title="返回">	
    			</div>
    		</div>
    	</div>
    </div>
  </body>
</html>
