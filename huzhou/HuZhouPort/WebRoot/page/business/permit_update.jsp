<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>许可</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/law_see.css" />
	<link rel="stylesheet" type="text/css" href="css/law_update.css" />
	
	<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/business/permit_update.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/common/inputValidator.js"></script>
	<script type="text/javascript" src="js/common/Operation.js"></script>
	<script type="text/javascript" src="js/business/law_back.js"></script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>
  </head>
  
  <body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
 <form action="addInspectionSupplementByInspectionId" encType="multipart/form-data"  
   method="post" onSubmit="return update_submit();" >
  <div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="GotoPermitList()" onMouseOver="BackSmallOver(this)" 
						onmouseout="BackSmallOut(this)" title="返回" />
			</div>
	  <div id="update_container">		
   <input type="hidden" value="<%=basePath %>" id="basePath"/>
  <input type="hidden" value="<%=request.getParameter("sc") %>" id="sc"/>
 
  <input type="hidden" value="<%=request.getParameter("inspectionId") %>" id="inspectionId" name="inspection.inspectionId"/>
  <input type="hidden" value="<%=session.getAttribute("userId")%>" name="inspectionSupplement.userId" id="userId"/>
		<table id="illegalTable">
		<col width="84px"/><col/>
			<tr>
				<td>
					船名/航道：
				</td>
				<td>
				 <label id="inspectionObject"></label>
				</td>
			</tr>
			<tr>
				<td valign="top">
					许可描述：
				</td>
				<td>
					<textarea class="textarea_input" id="inspectionContent" name="inspection.inspectionContent"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td>
					<label id="inspectionContentErr" style="color:red"></label>
				</td>
			</tr>
		</table>
		<div class='content_kind'>附件&nbsp;&nbsp;&nbsp;
		<div id="file_div"><img src="<%=basePath%>image/fileImg/addFile.png" title="添加附件" id="file_img"/>
			 <input type="file"  onchange="addComponent(this)" class="file_button" 
			 id="file1" name='illegalEvidence.ef'  onmouseover="AddFileOver(this)" onMouseOut="AddFileOut(this)" /></div>	
		</div>
		<table id="EvidenceUpload" cellpadding="0" cellspacing="0" width="793px">
		<col width="300px"/><col/>
		</table>
		<div id="submit_div">
		<input type="submit" value="" class="input_submit"
			onmouseover="SubmitOver(this)" onMouseOut="SubmitOut(this)"/>
		<input type="button" value="" class="input_reset" onClick="GotoPermitList()"
			onmouseover="SubmitOver_button(this)" onMouseOut="SubmitOut_button(this)"/>
		
		</div>
		
		</div>
		</form>
  </body>
</html>
