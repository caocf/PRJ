<%@page import="java.util.*"  pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath1%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/leave/see.css"/>
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/Equipment/EquipmentApproval.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
  <input id="ID" name="ID" type="hidden" value="<%=request.getParameter("equipmentID") %>" />
   <input id="kind" name="kind" type="hidden" value="<%=request.getParameter("kind") %>" />
   <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
    <input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId"/>
       <div id="layer1">
				<img src="<%=basePath1%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
</div> 
    <table width="100%" border="0" cellspacing="0" class="see"
	   id="EquipmentApproval">
	<col width="100px" /><col />
  
  </table>
    <div id="approvaldiv" class="seedivapproval"><p>审批</p></div>
<div id="EquipmentApprovalDiv">
     <table width="100%" border="0" cellspacing="0" class="see1"
	   id="EquipmentApproval1">
	<tr>
         <td><input type="radio" value="4" name="reviewResult" checked="checked" id="reviewResult" />通过    <input type="radio" value="3" id="reviewResult" name="reviewResult" />驳回 </td>
    </tr>
     <tr>
	<td class="see1td"><textarea  onfocus="textareabeage(this)"
    onblur="textareaend(this)" name="reviewOpinion" id="reviewOpinion" cols="70" rows="5"  style="color:#ccc">请输入审批意见</textarea> 
	   </td>
</tr>
 <tr>
	
</tr>
	
</table>	
<img class="submit" src="image/operation/sure_normal.png" onClick="agree()"
	onmouseover="SureOver(this)" onMouseOut="SureOut(this)"/>
</div>


   
  </body>
</html>
