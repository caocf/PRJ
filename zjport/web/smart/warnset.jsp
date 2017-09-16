<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>报警设置</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
	<!-- jQuery 2.1.4 -->
	<script src="../js/common/jQuery-2.1.4.min.js"></script>
	<script src="../js/common/bootpaging.js"></script>
	<script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
	<!-- 页面js -->
	<script src="../js/smart/warnset.js" type="text/javascript"></script>
	<style type="text/css">
		.clickword{
			color:rgb(31,116,180);
			cursor: pointer;
		}
	</style>
  </head>
  
  <body >
  <input type="hidden" value="<%=basePath%>" id="basePath" />
  <section class="content-header">
	  <h1>
		  报警设置
		  <!-- <small>advanced tables</small> -->
	  </h1>
	  <ol class="breadcrumb">
		  <li><a href="#"><i class="fa fa-cog"></i> 智慧监管</a></li>
		  <li><a href="#">监测预警</a></li>
		  <li><a href="#">报警设置</a></li>
	  </ol>
  </section>
  <section class="content">
	  <div class="box">
		  <div id="generalTabContent" class="t  ab-content no-margin" style="margin-top:10px;margin-left:8px;height:600px;">
			  <!-- 角色列表 -->
			  <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
				  <table class="table table-bordered table-hover col-xs-12" id="loglist-info" style="border-top:none">
				  </table>
			  </div>

			  <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
				  <nav style='float:right;display:none' id='pageshow'>
					  <ul class="pagination" id='pagination'>
					  </ul>
				  </nav>
			  </div>
			  <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
				  <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
			  </div>
		  </div>
	  </div>
  </section>
  </body>
</html>
