<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>统计分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/statistic/statistic.js"></script>

  </head>
  
  <body>
    <div class="common_c0" style="min-height: 0;">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<input type="hidden" value="<%=request.getParameter("num") %>" id="num"/>
    			<div class="left_I1" style="min-height: 0;overflow-y:auto;overflow-x:hidden;width:260px;">
					<!-- <div class="left_no_select" id="left_no_select1">
						<label class="left_name" id="gljg_bossname">统计分析</label>
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul> -->
				</div>
	    	<iframe name="statistic" class="common_iframe" style="display:none;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="statistic"></iframe>
    
    	<!-- <iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe1"></iframe>
    	<iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe2"></iframe>
    	<iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe3"></iframe> -->
    </div>
  </body>
</html>
