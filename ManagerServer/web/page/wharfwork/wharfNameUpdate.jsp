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

		<title>定期签证</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<link rel="stylesheet" type="text/css" href="css/law_update.css" />
		<link rel="stylesheet" type="text/css" href="css/selectlawuser.css" />
		<link rel="stylesheet" type="text/css"
			href="css/common/middelDialog.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js">
</script>
		<script type="text/javascript" src="js/business/wharfNameUpdate.js">
</script>
	<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		
		<input type="hidden" value="<%=session.getAttribute("username")%>"
			id="LoginUser" />
		<form id="AddFixedTerm_Form">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:window.history.go(-1);" onMouseOver="BackSmallOver(this)"
					onMouseOut="BackSmallOut(this)" title="返回" />
			</div>
			<div id="update_container">
				<input type="hidden" value="<%=basePath%>" id="basePath" />
				<input type="hidden"value="<%=request.getParameter("wharfnameID")%>" name="wharfnameID" id="wharfnameID" />
				<input type="hidden"value="<%=request.getParameter("wharfWorkNorm")%>" name="wharfNorm" id="wharfNorm" />
				<table id="illegalTable">
					<col width="100px" />
					<col />
					<tr>
						<td>
							码头编号：
						</td>
						<td>
							<input id="wharfID" name="wharfID" class="input_box"  disabled="true " />
						</td>
					</tr>
					<tr>
						<td>
							码头定量：
						</td>
						<td>
							<input type="text" name="wharfWorkNorm" id="wharfWorkNorm"
								class="input_box" />
						</td>
					</tr>
					
				
				

				</table>

			
				<div id="submit_div">
					<input type="button" value="" class="input_submit"
						onclick="submitwharfname()" onmouseover="SubmitOver(this)"
						onMouseOut="SubmitOut(this)" />

				</div>
			</div>
		</form>
	</body>
</html>
