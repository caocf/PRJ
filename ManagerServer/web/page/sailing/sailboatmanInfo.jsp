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
	<link rel="stylesheet" type="text/css" href="css/sailing/sailboatmanInfo.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/table.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
	<link rel="stylesheet" type="text/css" href="css/sailing/style.css" />
	<script src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/sailing/sailboatmanInfo.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/Operation.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/time/WdatePicker.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
  </head>
  	
  <body >
    <div class="sail1">
    	<jsp:include page="sailtop.jsp"></jsp:include>
    	<div class="sail2">
    		<input type="hidden" value="<%=request.getParameter("shipName") %>" id="shipName"/>
    		<div class="sail4" style="margin:0 auto;min-height:800px;">
    			<div class="titlename" >管理船员信息</div>
    			<div><img src="image/operation/add_know_normal.png" class="addbutton" onMouseOver="addkonwOver(this)" onMouseOut="addkonwOut(this)" onClick="addBoaManInfo()">
    						<img class="submit" src="image/operation/sure_normal.png" onClick="determinemanInfo()" 
    						 	style="margin-top:10px;margin-left:10px;"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
    						<img src="image/operation/back_small_normal.png" onClick="gotoBack()" 
    						 onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"  style="margin-top:11px;"  title="返回"></div>
    						 
    			<div style="max-height:739px;overflow-y:auto;overflow-x:hidden">
	    			<table id="boatmanTable" class="boatmanTable" cellpadding="0" cellspacing="0">
	    				<col width="20%"/><col width="20%"/><col width="60%"/>
	    			</table>
    			</div>
    			
    		</div>
    	</div>
    </div>
    <!-- 选择证书类型 -->
    <div class="dialog" id="certificateDiv" style="display:none;width:300px;height:300px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold">
		  			&nbsp;&nbsp;选择证书
		</div>
		<div style="height:200px;margin:20px 0 0 0;">
		  	<ul class="certificateUl" id="certificateUl">
		  		
		  	</ul>
		</div>
		<div style="float:right;margin-right:10px;margin-top:10px;">
			<img src="image/operation/sure_normal.png" onClick="selectCFNamecl()" style='display:none' id="selectCFName" onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
			<img src="image/operation/sure_normal.png" onClick="selectaddCFNamecl()" style='display:none' id="selectaddCFName" onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    <img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closecertificateDiv()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
		</div>
    </div>
    <!-- 新增/修改船员信息 -->
    <div class="dialog" id="boatmanInfoDiv" style="display:none;width:370px;height:200px;">
    	<div style="background: #eaeaea;height:30px;line-height: 30px;vertical-align: middle;color:#333;font-weight: bold" >
    		<label id="boatmanInfoTitle" style="margin-left:10px;"></label>
    	</div>
		<div style="height:100px;margin:20px 0 0 0;">
		  <table class="boatmanlistTable" style="" cellpadding="0" cellspacing="0">
		  	<col width="33%"><col width="67%">
		  		<tr>
		  			<td ><select id="boatmanKind" style="width:100%"></select></td>
		  			<td><input type="text" class="input_info" id="input_boatmanKind"/></td>
		  		</tr>
		  		<tr style="border-bottom:solid 1px #e8e9ed;">
		  			<td ><select id="certificateID" style="width:100%"></select></td>
		  			<td><input type="text" class="input_info" id="input_certificateID"/></td>
		  		</tr>
		  </table>
		</div>
		<div style="float:right;margin-right:10px;margin-top:10px;">
			<img src="image/operation/sure_normal.png" onClick="addboatmanInfo()"  onmouseover="SureOver(this)" onMouseOut="SureOut(this)">
		    <img src="image/operation/reset_big_normal.png" style="width:70px;height:28px;" onClick="closeboatmanInfoDiv()" id="bt_back" onMouseOver="ResetBigOver(this)" onMouseOut="ResetBigOut(this)" title="取消">
		</div>
    </div>
  </body>
</html>
