<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-个人资料</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/CRselectBox.css" />
		<link rel="stylesheet"
			href="<%=basePath%>WebPage/css/main/selfInfo.css" type="text/css"></link>

		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/time/birthdaySelect.js"></script>
		<script type="text/javascript" src="WebPage/js/personalcenter/birthplace.js"></script>
		<script type="text/javascript" src="WebPage/js/personalcenter/birthplace_min.js"></script>
		<script type="text/javascript" src="WebPage/js/personalcenter/personal.js"></script>
		
		

	</head>


	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="0" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div id="main">
					<div>
						<img alt="" src="WebPage/image/toptoptop.png" />
					</div>
					<div class="main_left">
						<img alt=""
							src="WebPage/image/person_info.png" />
					</div>
					<div class="main_right">
						<div class="main_right_up">
							<table border="0">
								<tr height="45px">
									<td width="65px">
										用户名：
									</td>
									<td>
										<span id="nametext" style="margin-left: 16px;"></span>
									</td>
									
								</tr>
								<tr height="45px">
									<td width="65px">
										性&nbsp;&nbsp;别：
									</td>
									<td >
										<label style="margin-left: 16px;">
											<input id="sextext1" type="radio" name="sex" value="1" />
											男 &nbsp;&nbsp;&nbsp;&nbsp;
											<input id="sextext2" type="radio" name="sex" value="2" />
											女 &nbsp;&nbsp;&nbsp;&nbsp;
											<input id="sextext3" type="radio" name="sex" value="0" />
											不详
										</label>
									</td>
								</tr>
								<tr height="45px">
									<td width="65px">
										密 码：
									</td>
									<td>
										<a href="WebPage/page/register/modify.jsp"
											style="color: #2746a2; text-decoration: none; margin-left: 16px;">修改密码</a>
									</td>
									
								</tr>
								<tr height="45px">
									<td width="65px">
										所在地：
									</td>
									<td valign="middle">

										<select id="seachprov" name="seachprov"
											onchange="changeComplexProvince(this.value,sub_array,'seachcity','seachdistrict');"></select>
										<select id="seachcity" name="homecity"
											onchange="changeCity(this.value,'seachdistrict');"></select>
										<span id="seachdistrict_div"> <select
												id="seachdistrict" name="seachdistrict"></select> </span>
									</td>
								</tr>
								<tr height="45px">
									<td width="65px">
										生 日：
									</td>
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
								<tr height="45px">
									<td width="65px">
										手机号：
									</td>
									<td>
										<span id="phonetext" style="margin-left: 16px;"></span>
									</td>
									
								</tr>
								<tr height="45px">
									<td width="65px">
										邮箱：
									</td>
									<td>
										<span id="emailtext" style="margin-left: 16px;"></span>
									</td>
									
								</tr>

								<tr height="45px">
									<td></td>
									<td>
										<a><img
												src="WebPage/image/update_data_normal.png"
												onclick="ModifyUserInfoForWeb()"
												onmouseout="javascript:this.src='WebPage/image/update_data_normal.png'"
												onmouseover="javascript:this.src='WebPage/image/update_data_hover.png'" />
										</a>

									</td>
								</tr>
							</table>
						</div>
						<div class="main_right_down">
							<table>
								<tr height="106px">

									<td width="48px">
										<div>
											<img alt=""
												src="WebPage/image/person_phone.png"
												style="margin-bottom: 15px;" />
										</div>
									</td>

									<td width="640px" align="left" style="line-height: 30px;">
										<span style="color: #333333; font-weight: bolder">绑定手机</span>
										<br>
										<span style="color: gray; font-size: 12px">
											绑定手机之后，您可以用使用手机找回密码</span>
									</td>
									<td align="center">
										<a style="color: #2746a2" onClick="bandphone()">绑定手机</a>
									</td>
								</tr>
								<tr height="106px" style="border-top: 1px dashed #333;">
									<td width="48px">
										<div>
											<img alt=""
												src="WebPage/image/person_email.png "
												style="margin-bottom: 20px;" />
										</div>
									</td>
									<td width="640px" style="line-height: 30px">
										<span style="color: #333333; font-weight: bolder">绑定邮箱</span>
										<br>
										<span style="color: gray; font-size: 12px">绑定邮箱之后，您可以用邮箱手机找回密码</span>
									</td>
									<td align="center">
										<a style="color: #2746a2" onClick="bandemail()"> 绑定邮箱</a>

									</td>
								</tr>
								<tr height="106px" style="border-top: 1px dashed #333;">
									<td width="48px">
										<div>
											<img alt=""
												src="WebPage/image/person_idcard.png"
												style="margin-bottom: 30px;" />
										</div>
									</td>
									<td style="line-height: 30px">
										<span style="color: #333333; font-weight: bolder">实名认证</span>
										<br>
										<span style="color: gray; font-size: 12px">
											实名认证后，您可以享受到更多的服务</span>
									</td>
									<td align="center">
										<a style="color: #2746a2" onClick="recognizenow()">立即认证</a>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>

