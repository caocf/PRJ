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
	<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/sailing/saildetail.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/Operation.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/time/WdatePicker.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
  </head>
  	
  <body >
    <div class="sail1">
    	<jsp:include page="sailtop.jsp"></jsp:include>
    	<div class="sail2">
    		<input type="hidden" value="<%=request.getParameter("reportId") %>" id="seereportId"/>
    		<div class="sail4" style="margin:0 auto;min-height:800px;">
    			<div class="titlename" >查看航行电子报告</div>
    				<div style="max-height:800px;overflow-y:auto;overflow-x:hidden">
		    			<table id="boatmanInfoTable" class="addTable" cellpadding="0" cellspacing="0">
		    				<col width="16%"/><col width="84%"/>
		    				<tr>
		    					<td>报告状态(<label id="report_sta"></label>)</td>
		    					<td id="reportstatus" style="white-space: pre-wrap;line-height: 20px;"></td>
		    				</tr>
		    				<tr>
		    					<td>船名号</td>
		    					<td id="boatName"></td>
		    				</tr>
		    				<tr>
		    					<td>报告类型</td>
		    					<td><span id="selectsail"></span></td>
		    				</tr>
		    				<tr>
		    					<td>始发港</td>
		    					<td><span id="startport"></span></td>
		    				</tr>
		    				<tr>
		    					<td>目的港</td>
		    					<td><span id="arriveport"></span></td>
		    				</tr>
		    				<tr id="workwharf" style="display:none">
		    					<td>本次作业码头</td>
		    					<td><span id="stopWharf"></span></td>
		    				</tr>
		    				<tr id="inOrNotPort" style="display:none;">
		    					<td>选择进出港</td>
		    					<td><span id="selectport"></span></td>
		    				</tr>
		    				<tr id="cargoStyle">
		    					<td>载货种类</td>
		    					<td><span id="cargoName"></span></td>
		    				</tr>
		    				<tr id="cargoNum">
		    					<td>载货数量</td>
		    					<td><span id="cargoquantity"></span>&nbsp;<span>吨</span></td>
		    				</tr>
		    				<tr>
		    					<td>预计进出港时间</td>
		    					<td id="entertime">&nbsp;</td>
		    				</tr>
		    				<tr>
		    					<td>上一次航次加油量</td>
		    					<td><span id="oilquantity"></span>&nbsp;<span>升</span></td>
		    				</tr>
		    				<tr>
		    					<td>上一次航次加油时间</td>
		    					<td id="addOiltime">&nbsp;</td>
		    				</tr>
		    			</table>
	    			</div>
    			<div style="margin-left:20px;height:50px;width:100%">
    					<img src="image/operation/back_small_normal.png" onClick="gotoBack()" 
    						 onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"  style="margin-top:12px;"  title="返回">
    						 
    			</div>
    		</div>
    	</div>
    </div>
  </body>
</html>
