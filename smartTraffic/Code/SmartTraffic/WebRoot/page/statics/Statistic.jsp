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
	<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
		<link rel="stylesheet" type="text/css" href="css/system/top.css">
	<link rel="stylesheet" type="text/css" href="css/system/style.css">
	<link rel="stylesheet" type="text/css" href="css/main/traffic.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/system/link.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/statistic/statistic.js"></script>

  </head>
  
  <body style="min-height: 0;overflow-x:hidden;">
    <div class="common_c0" style="min-height: 0;min-width:1200px;">
    <div class="top_u1">
			<div>
				<img class="top_u2" id="trafficStatics_back"
					src="image/system/back_normal.png" /> <span class="top_text1"
					id="trfffic_top">综合交通报表分析</span>
					<!-- <a><span class="top_text1" style="font-size:15px;color:white;">报表系统</span></a> -->
						<span  style="font-size:18px;padding-top:30px;color:white; float:right;padding-right:60px;cursor:pointer;" id="link_tb">行业业务分析系统</span>
			</div>
		</div>
    	<input type="hidden" value="<%=basePath%>" id="basePath"/>
    	<input type="hidden" value="<%=request.getParameter("num") %>" id="num"/>
    			<div class="left_I1" style="min-height: 0;overflow-y:auto;overflow-x:auto;width:260px;height:100%;">
					<!-- <div class="left_no_select" id="left_no_select1">
						<label class="left_name" id="gljg_bossname">统计分析</label>
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul> -->
				</div>
            <div style='float:left;' id='iframeId'>
	    	<iframe name="statistic" class="common_iframe" style="display:block;float:left;overflow-y:auto;overflow-x:auto;width:100%;height:100%;" frameBorder=0  id="statistic"></iframe>
            </div>
    	<!-- <iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe1"></iframe>
    	<iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe2"></iframe>
    	<iframe name="statistic" class="common_iframe1" style="display:block;" frameBorder=0 style="overflow-y:auto;overflow-x:hidden; " id="iframe3"></iframe> -->
    </div>
  </body>
</html>
