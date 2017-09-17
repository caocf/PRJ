
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
		<link rel="stylesheet"
			href="<%=basePath%>WebSit/css/taxiInformation/taxiInformation.css"
			type="text/css"></link>

		<script type="text/javascript"
			src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>



	</head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="8" />
			</jsp:include>

			<div id="main_content">
				<div class="top">
					<div class="top_1">
						<a class="top_select"
							href="<%=basePath%>WebSit/page/taxiInformation/TaxiInfor.jsp">出租车概况</a>
					</div>
					<div class="top_1">
						<a class="top_select_no"
							href="<%=basePath%>WebSit/page/taxiInformation/TaxiCompany.jsp">出租车企业信息</a>
					</div>
					
				</div>
				<div class="page_dowm">
					<h1 align="center" style="margin-top: 30px; margin-bottom: 10px;font: 16px;">
						一、电召运营基本情况
					</h1>
					<div
						style="font-size: 14px; font-weight: normal; letter-spacing: 2px; color: rgb(51, 51, 51); line-height: 18px; text-indent: 2em; margin-left: 20px; margin-right: 20px;">
						目前嘉兴市共有出租汽车企业37家，出租汽车2324辆，所有均安装了GPRS车载终端，除嘉善县240辆出租汽车未统一平台调度外，市本级及其他县市出租汽车均纳入了嘉兴市神州长城通讯有限公司调度平台实施统一调度，电召调度热线为13905730000和95838，2014年10月日均接入叫车电话约<font color="red">5900</font>个，日均下发电召调度成功的叫车电话约<font color="red">2000</font>个，叫车成功率约为<font color="red">33.9%</font>。‍
						目前市区共有出租汽车1073辆，从业人员2500余人，日运送旅客已超过15万人次。
					</div>
					<h1 align="center" style="margin-top: 30px; margin-bottom: 10px;font: 16px;">
						二、市本级出租汽车企业情况
					</h1>
					<div
						style="font-size: 14px; font-weight: normal; letter-spacing: 2px; color: rgb(51, 51, 51); line-height: 18px; text-indent: 2em; margin-left: 20px; margin-right: 20px;">
						市区共有12家出租汽车公司，
					</div>


					<p align="center">
						<img src="<%=basePath%>WebSit/image/taxi/taxiInfo.jpg" />
					</p>

				</div>

			</div>
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />

		</div>
	</body>
</html>
