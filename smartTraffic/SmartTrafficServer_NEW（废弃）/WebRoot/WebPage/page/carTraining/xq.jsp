<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校详情</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/menu.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/carTraining.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/nav.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/jquery-1.9.1.min.js"></script>
	
	
  </head>
  <body>
		<jsp:include page="../../../WebPage/page/main/top1.jsp" flush="true" >
		<jsp:param name="menu_number" value="6,2"/> 
		</jsp:include>
		<!-- 页面内容 -->
	<div class="xqCont">
		<div class="xq">详情</div>
		<img style="margin: 0 auto;" src="WebPage/image/xq_Int.png"/>
	    <div class="xqCont1">
	       <div class="xqCont1_left">
	          <div class="xqCont1_top1">长运驾校驾校
	             <img style="margin-left:470px;vertical-align: middle" src="WebPage/image/common/collect.png"/>&nbsp;<p>收藏</p>
	          </div>
	          <div class="xqCont1_top2">嘉兴市秀洲区洪高路1289号 <br/>0573-82798888, 0573-82793555</div>
	          <div class="xqCont1_top3">
	              <div class="xqCont1_top3_nav">
	                 <div class="nav">
                        <div class="navmain">
                           <ul class="nav_all">
                              <li style="margin-left:-5px"><a class="nav_select_no">简介</a>
                                <ul style="display: none;"> </ul>
                              </li>
                              <li><a class="nav_select_no" onClick="SelectItem_top(this,'WebPage/page/carTraining/xqCourse.jsp')">课程费用</a>
                                     <ul style="display: none;"></ul>
                              </li>
                              <li><a class="nav_select_no">驾校教练</a>
                                  <ul style="display: none;"></ul>
                              </li>
                              <li><a class="nav_select" onClick="SelectItem_top(this,'WebPage/page/carTraining/xqIntnet.jsp')">网上报名</a>
                                   <ul style="display: none;"></ul>
                              </li>
                            </ul>
                         </div>
                     </div>
	              </div>
	              <div class="xqPublic">
	              <iframe src="WebPage/page/carTraining/xqIntnet.jsp" class="down" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>             
	              </div>
	             
	          </div>
	       </div>
	       <div class="xqCont1_right">
	           <div class="xqCont1_right_top1"><img src="WebPage/image/ditu.png"/></div>
	           <div class="xqCont1_right_top2"><img src="WebPage/image/ditusm.png"/></div>
	       </div>
	    </div>
	</div>
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
  </body>
</html>
