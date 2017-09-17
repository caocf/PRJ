<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人资料</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/personal/PersonalData.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<script src="js/common/jquery-1.10.2.min.js"></script>
		<script  src="js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/system/setheight.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/cookie.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/personal/PersonalData.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/personal/HeadPicture.js"></script>
  </head>
  
  <body>
  <input type="hidden" id="rybh"/>
  <input type="hidden" id="xmpyszm" /> 
  <input type="hidden" value="<%=basePath%>" id="basePath"/>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="2,1" />
	    </jsp:include>
	    	<div class="common_c1" style="background:#ffffff;">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
		            
		            <div style="width:100%;">
						<span class="wordstyle" style="*float:left;">个人资料</span>
						<button  class="changeBt" id="change" style="float:right;outline: none;">修改</button>
						</div>
						
						
		<!-- 			    <div style="margin:0px 0px 0px 20px;">
						 <div id="preview" style="margin:18px 0px 18px 10px;float:left;"> 
                           <img id="imghead"   border=0 src=''> 
                            </div> 
                            <button class="personal_button" style="margin-left:16px;margin-top:88px;"  
                            onclick="document.getElementById('imgFile').click()">更换头像</button> 
                            
                           <input id="imgFile" name="uploadFile" type="file" 
                            onchange="PreviewImage(this,'imghead','preview')" style="visible:hidden;"/>
                          <div class="file-box" id="changeHeadPc" style="display:none;">
                            <input type='text' name='textfield' id='textfield' class='txt' />  
                            <div  class='btn' id="btn" onmouseover="changebuttonover()" 
                            onmouseout="changebuttonout()">更换头像</div>
                            <input type="file" name="uploadFile" class="file" id="imgFile" size="28"
                            onchange="PreviewImage(this,'imghead','preview')"
                            onmouseover="changebuttonover()" 
                            onmouseout="changebuttonout()" />
                            </div>
                          </div>
                        -->
						
						</div>
						<div style="height:0px;*height:18px;"></div>
						<table class="personalTable" id="userTable" cellpadding="0" cellspacing="0" style="border-top: solid 1px #CCCCCC;">
						<col width="10%"/><col width="40%"/><col width="10%"/><col width="40%"/>
						
							<tr>	
								<td style="text-align: right">姓名:</td>
								<td style="width:280px;"><input type="text" value="" id="name" class="personaldata_input" disabled="disabled" />
								<input type="hidden" id="headpath" value=""/></td>
								
								<td style="text-align: right">姓名全拼:</td>
								<td style="width:280px;"><input type="text" value="" id="xmqp" class="personaldata_input" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="text-align: right">长号:</td>
								<td><input type="text" value="" id="sjch" class="personaldata_input" disabled="disabled"/></td>
								<td style="text-align: right">短号:</td>
								<td><input type="text" value="" id="sjdh" class="personaldata_input" disabled="disabled"/></td>
							</tr>
						<!-- 	<tr>
								<td style="text-align: right">所属部门:</td>
								<td><input type="text" value="" id="department" class="personaldata_input" disabled="disabled"/></td>
								<td style="text-align: right">职务:</td>
								<td><input type="text" value="" id="post" class="personaldata_input" disabled="disabled"/></td>
							</tr>
							<tr>
								<td style="text-align: right">办公室电话:</td>
								<td><input type="text" value="" id="phone" class="personaldata_input" disabled="disabled"/></td>
								<td style="text-align: right">状态:</td>
								<td id="status">在职</td>
							</tr> -->
						</table> 
						<!-- <table class="personalTable" id="rybmlistTable" cellpadding="0" cellspacing="0" style="border-top: solid 1px #CCCCCC;">
						<col width="10%"/><col width="40%"/><col width="10%"/><col width="40%"/>
						
						</table> -->
						
						<div style="padding-left:20px;padding-top:20px;display:none;" id="updatediv">
							<button class="personal_button" style="margin-right:10px;" id="update">保存</button>
							
							<button class="cancle_button" style="" id="cancle">取消</button>
							</div>
                             
					
					
					</div>	
				</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
 
   
   			
  </body>
</html>
