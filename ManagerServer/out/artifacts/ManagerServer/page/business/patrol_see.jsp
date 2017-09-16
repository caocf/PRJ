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

		<title>码头巡查</title>

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
		<script type="text/javascript" src="js/business/patrol_see.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/business/law_back.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="see_container">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="GotoPatrolList()" onMouseOver="BackSmallOver(this)"
					 onmouseOut="BackSmallOut(this)" title="返回" />
			</div>
			<div id="see_content">
				<input type="hidden" value="<%=request.getParameter("patrolId")%>"
					id="patrolId" />
				<input type="hidden" value="<%=session.getAttribute("userId")%>"
					id="userId" />
				<table id="illegalTable">
				<col width="80px"/><col/>
					<tr>
						<td>
							码头：
						</td>
						<td>
							<label id="patrolObject"></label>
						</td>
					</tr>
					<tr>
						<td>
							巡查人员：
						</td>
						<td>
							<label id="patrolUser"></label>
						</td>
					</tr>
					<tr>
						<td>
							巡查时间：
						</td>
						<td>
							<label id="patrolTime"></label>
						</td>
					</tr>
					<tr>
						<td>
							巡查描述：
						</td>
						<td>

								<div class="table_see_div" id="patrolContent"></div>
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
				<table cellpadding="0" cellspacing="0">
				<tr><td>
				<div class="content_kind">
					执法地点
				</div>
				<div id="locationName"></div>
				</td></tr>
				<tr><td>
				<div id="allmap"></div>
				</td></tr>
				</table>
				
				
				
			</div>
		</div>
	</body>
</html>
