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

		<title>违章</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=1.4"></script>
		<script type="text/javascript" src="js/business/illegal_see.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/business/law_back.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="see_container">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:window.history.go(-1);" onMouseOver="BackSmallOver(this)" 
					onMouseOut="BackSmallOut(this)" title="返回" />
			</div>
			<div id="see_content">
				<input type="hidden" value="<%=request.getParameter("illegalId")%>"
					id="illegalId" />
				<input type="hidden" value="<%=session.getAttribute("userId")%>"
					id="userId" />
				<table id="illegalTable">
				<col width="75px"/><col/>
					<tr>
						<td>
							船名：
						</td>
						<td>
							<label id="illegalObject"></label>
						</td>
					</tr>
					<tr>
						<td>
							执法人1：
						</td>
						<td>
							<label id="enforecers1"></label>
						</td>
					</tr>
					<tr>
						<td>
							执法人2：
						</td>
						<td>
							<label id="enforecers2"></label>
						</td>
					</tr>
					<tr>
						<td>
							取证时间：
						</td>
						<td>
							<label id="illegalTime"></label>
						</td>
					</tr>
					<tr>
						<td>
							违章案由：
						</td>
						<td>
							<label id="illegalReason"></label>
						</td>
					</tr>
					<tr>
						<td valign="top">
							违章描述：
						</td>
						<td>
							<div class="table_see_div" id="illegalContent"></div>
						</td>
					</tr>
				</table>
				<div class="content_kind" id="Supplement_title">
								补充情况
							</div>
				<table id="illegalTable_Supplement">
				</table>
				<div class="content_kind" id="evidenceDiv_title">
								附件
							</div>
				<div id="evidenceDiv">					
				</div>				
				<table id="illegalTable_Check" cellpadding="0" cellspacing="0">
				<col width="80px"/><col/>
				<tr>
						<th colspan="2">
						<div class="content_kind">
								审核
							</div>	
						</th>
					</tr>
					<tr>
						<td>
							审核状态：
						</td>
						<td>
							<label id="reviewWether"></label>
						</td>
					</tr>
					<tr>
						<td>
							审核人：
						</td>
						<td>
							<label id="reviewUser"></label>
						</td>
					</tr>
					<tr>
						<td>
							审核时间：
						</td>
						<td>
							<label id="reviewTime"></label>
						</td>
					</tr>
					<tr>
						<td>
							审核结果：
						</td>
						<td>
							<label id="reviewResult"></label>
						</td>
					</tr>
					<tr>
						<td valign="top">
							审核建议：
						</td>
						<td>
							<div class="table_see_div" id="reviewComment"></div>
						</td>
					</tr>
				</table>
				<div class="content_kind">
					执法地点
				</div>
				<div id="locationName"></div>
				<div id="allmap"></div>
			</div>
		</div>
	</body>
</html>
