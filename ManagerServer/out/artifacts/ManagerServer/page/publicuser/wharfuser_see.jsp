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

		<title>用户管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system/publicuser_add.css" />
		<link rel="stylesheet" type="text/css" href="css/common/middelDialog2.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link rel="stylesheet" type="text/css" href="css/system/publicuser.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>		
		<script type="text/javascript" src="js/publicuser/wharfuser_see.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
         <script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<input type="hidden" value="<%=request.getParameter("publicId")%>"
			id="publicId" />
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div>
			<div class="totaldiv">
			<table cellspacing="0" cellpadding="0" id="dateList1" >
				<col width="65px"/><col width="200px"/><col width="*"/>
					<tr>
						<td>
							用户名称：
						</td>
						<td >
							
							<span id="wharfNum"></span>
						</td>
						<%--<td>
							<img alt="编辑" src="image/operation/update_normal.png"
						class="u3_img" onClick="show_update()"
						onmouseover="UpdateOver(this)" onMouseOut="UpdateOut(this)" />
						</td>
					
					--%></tr>
					<tr>
						<td>
							IMEI号：
						</td>
						<td >
							
							<span id="imei"></span>
						</td>
						<%--<td>
							<img alt="编辑" src="image/operation/update_normal.png"
						class="u3_img" onClick="show_update()"
						onmouseover="UpdateOver(this)" onMouseOut="UpdateOut(this)" />
						</td>
					
					--%></tr>
					<tr>
						<td>
							
						</td>
					
					</tr>
					<tr>
						<td>
							
						</td>
					
					</tr>
					<tr>
						<td class="style_top1" colspan="3">
							绑定的码头
						</td>
					</tr>
					
					<tr>
						<td >
							
						</td>
					</tr>

					<tr>
						<th colspan="3">
							<table border="0" cellspacing="0" cellpadding="0"
								class="listTable" id="wharfTable">
								<col width="10%" />
								<col width="10%" />
								<col width="20%" />
								<tr>
									
									<th>
									码头名称
									</th>
									<th>
										附件
									</th>
								</tr>
							</table>
						</th>
					</tr>
					
				</table>
			</div>
	</body>
</html>
