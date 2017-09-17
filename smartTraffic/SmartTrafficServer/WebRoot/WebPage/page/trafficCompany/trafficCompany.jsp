<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>交通企业查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/common/table.css"/>
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/trafficCompany/trafficCompany.css"/>

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/time/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/trafficCompany/trafficCompany.js"></script>

  </head>
  
  <body>
  	<div id="page_content">
  		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="" />
			</jsp:include>
			<%-- 界面内容 --%>
			<div id="main_content">
				<div class='selectCompanyDiv'>
					<span class='selectCompanyWord'>企业查询:</span>
					<input type="text" class='selectCompanyInput' value='请输入企业名称'/>
					<div class='selectCompanyButton'>查询</div>
				</div>
				<div class='tablediv'>
					<table class='companyTable' cellpadding="0" cellspacing="0">
					</table>
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
  	<div class='fugaidiv'>
  	</div>
  	<div class='companyinfodiv'>
  		<div class='infototaldiv'>
  			<div class='totalworddiv'>
  				<span class='totalword' id='info_name'>代价啊可见罚款理发匠罚款斯大林</span>
  				<span class='closeword'>[关闭]</span>
  			</div>
  			<div class='infoaddressdiv'>
  				<span class='infoaddressword' id='info_address'>fsdafasdsdfsfsdfsd</span>
  			</div>
  		</div>
  		<div class='infotablediv'>
  			<table class='infotable'>
  			</table>
  		</div>
  	</div>
  	<div>
  	
  	</div>
  </body>
</html>
