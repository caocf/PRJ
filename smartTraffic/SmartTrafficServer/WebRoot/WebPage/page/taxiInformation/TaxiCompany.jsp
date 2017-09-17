
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-出租车概况</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/table.css" />
			
		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/taxiInformation/taxicompany.js"></script>



	</head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="9,4" />
			</jsp:include>

			<div id="main_content">
				<div class="menu_div">
						<a class="layout1_a"
							href="<%=basePath%>WebPage/page/taxiInformation/TaxiInfor.jsp">出租车概况</a>
						<a class="a_select"
							href="<%=basePath%>WebPage/page/taxiInformation/TaxiCompany.jsp">出租车企业</a>
						<a class="layout1_a"
							href="<%=basePath%>WebPage/page/taxiInformation/appointmentCall.jsp">预约电召</a>
						<label class="pagepath"><script>document.write(PATH_GRAPHICAL_PUBLICTRAFFIC_TAXI_COMPANY)</script></label>
				</div>
				<div class="layout">
					
				</div>
				<div id="pageDiv">
							<p>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>
								<span class="nextBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>

			</div>
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />

		</div>
	</body>
</html>
