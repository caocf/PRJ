<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

	<link rel="stylesheet" type="text/css" href="WebPage/css/register/register.css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
 	<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
	<script type="text/javascript" src="WebPage/js/time/birthdaySelect.js"></script>
	<script type="text/javascript" src="WebPage/js/personalcenter/birthplace.js"></script>
	<script type="text/javascript" src="WebPage/js/personalcenter/birthplace_min.js"></script>
 	<script type="text/javascript" src="WebPage/js/register/register.js"></script>

  </head>
  
  <body>
   <div id="page_content">
  <jsp:include page="../../../WebPage/page/main/top.jsp" />
     <div id="reg_content"><p class="cont_title">欢迎注册</p>
		<table cellpadding="0" cellspacing="0" border="0" width="100%" class="regtable">
		<col width="90px"/><col width="700px"/><col width="10px"/><col width="*"/>
			<tr>
				<th>手机号码</th>
				<td>
				 <input type="text" class="input_text" id="txttelephone" />
            	<font color="red">*</font>
            	<input type="button" class="sendMsgBt2" id="bt_getcode" onClick="GetVerifyCodeForWeb()"  value="获取验证码"/>&nbsp;
            	<span id="txttelephoneerr"></span>
				</td>
				<td rowspan="8" valign="middle">
				 <img  src="WebPage/image/register/reg.png"/>
				</td>
				<td rowspan="8" valign="middle" align="center">
				<p style="color: #5c5c5c;line-height: 30px;">已有账号？</p>
				<p style="color:#2546a3;line-height: 30px;cursor:pointer;" onClick="javascript:parent.LoginWeb();">立即登录&raquo; </p>
				</td>
			</tr>
			<tr>
				<th>
				验证码
				</th>
				<td>
				  <input type="text" class="input_text" style="width:110px;" id="codetext"/>
		            <font color="red">*</font>
		            <label  class="text_wz">请输入手机收到的验证码，如果没有收到，请重新获取</label> 
		            <span id="codetexterr"></span>
				</td>
			</tr>
			<tr>
				<th>
				用户名
				</th>
				<td>
				  <input type="text" class="input_text"  id="username"  />
				   <span id="usernameerr"></span>
				</td>
			</tr>
			<tr>
				<th>
				设置密码
				</th>
				<td>
				<input type="password" id="txtuserpassword"  class="input_text" />
	            <font color="red">*</font>
	            <span id="userpassword"></span>
	            <span id="txtuserpassworderr"></span>
				</td>
			</tr>
			<tr>
				<th>
				确认密码
				</th>
				<td>
				 <input type="password"  id="txtpwdagin" class="input_text"  />
            <font color="red">*</font>
            <span id="pwdagin"></span>
				</td>
			</tr>
			<tr>
				<th>
					性&nbsp;&nbsp;别：
				</th>
				<td >
					<label style="margin-left: 16px;">
						<input id="sextext1" type="radio" name="sex" value="1" checked="checked" />
						男 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="sextext2" type="radio" name="sex" value="2" />
						女 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="sextext3" type="radio" name="sex" value="0" />
						不详
					</label>
				</td>
			</tr>
			
			<tr>
				<th>
					所在地：
				</th>
				<td valign="middle">
	
					<select id="seachprov" name="seachprov"
						onchange="changeComplexProvince(this.value,sub_array,'seachcity','seachdistrict');"></select>
					<select id="seachcity" name="homecity"
						onchange="changeCity(this.value,'seachdistrict');"></select>
					<span id="seachdistrict_div"> <select
							id="seachdistrict" name="seachdistrict"></select> </span>
				</td>
			</tr>
			<tr>
				<th>
					生 日：
				</th>
				<td valign="middle">
					<form name=form1>
						<select  onchange="YYYYMM(this.value)" id="seachyear">
						<option value="">年</option>
						</select>
						<select onChange="MMDD(this.value)" id="seachmonth">
						<option value="">月</option>
						</select>
						<select   id="seachday">
						<option value="">日</option>
						</select>
					</form>
				</td>
				
			</tr>
			<tr>
				<th>
				电子邮箱
				</th>
				<td>
				 <input type="text" class="input_text"  id="txtemail"  />
				 <span id="emailerr"></span>
				</td>
			</tr>
			<tr>
				<th>
				
				</th>
				<td>
				 <img onClick="Register()" style="margin-top:30px;cursor:pointer;" src="WebPage/image/register/reg_sub.png" />
				</td>
			</tr>
			
		</table>
			

     </div>
 
  <jsp:include page="../../../WebPage/page/main/foot.jsp" /> 
  </div>
  </body>
</html>
