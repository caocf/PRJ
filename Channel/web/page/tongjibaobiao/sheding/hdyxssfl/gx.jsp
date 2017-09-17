<%@page import="com.channel.model.user.CXtUser"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	CXtUser user = ((CXtUser) session.getAttribute("user"));
	String username = null;
	int userid = -1;
	if (user != null) {
		username = user.getUsername();
		userid = user.getId();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css"
	href="common/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="common/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="page/common/css/common.css">
<link rel="stylesheet"
	href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

<script type="text/javascript" language="javascript"
	src="common/common/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript"
	src="common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript"
	src="common/common/js/common.js"></script>
<script type="text/javascript" language="javascript"
	src="page/common/js/constants.js"></script>
<script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>

</head>

<body style="background: white;overflow: hidden;font-family: 宋体;">
	<input type="hidden" id="username" value="<%=username%>">
	<input type="hidden" id="userid" value="<%=userid%>">
	<input type="hidden" id="basePath" value="<%=basePath%>">
	<div class="container-fluid">

		<c:choose>
			<c:when
				test="${ret.map.columns == null || fn:length(ret.map.columns) <= 0}">
				<div class="col-xs-12">
					<center>
						<h3>暂无报表数据</h3>
					</center>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-xs-12">
						<center>
							<h2 class="bbtitle">${ret.map.xzqhname}航道沿线管线分类统计表<br><small class="bbtitle" style="line-height: 40px;">${ret.map.time}</small></h2>
						</center>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 text-right bbcontent">计量单位：条</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th width="150px" class="text-center" style="padding:0 0 0 0;"><img
										class="img-responsive" src="img/line_italic.png"
										style="width:100%;height:60%;"> <label class="bbcontentbold"
										style="position: absolute;left:30px;top:25px;">行政区划</label>
										<label class="bbcontentbold"
										style="position: absolute;left:100px;top:5px;">管线种类</label></th>

									<c:forEach items="${ret.map.columns}" var="title">
										<th class="text-center bbcontentbold" style="height:30px;line-height:30px;">${title}</th>
									</c:forEach>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${ret.map.data}" var="row">
									<tr>
										<td class="text-center bbcontent">${row.key }</td>
										<c:forEach items="${row.value}" var="col">
											<td class="text-center bbcontent">${col }</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

	</div>
</body>
</html>
