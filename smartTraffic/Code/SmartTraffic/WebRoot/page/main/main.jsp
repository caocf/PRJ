<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>     
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/main.css">

	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/main/main.js"></script>	
	
  </head>
  
<body>

<div class="mainall">
<div class="mainstyle">
<input type="hidden" value="<%=basePath%>" id="basePath"/>
 
 <div class="logintop">
  <div class="p1">
  <%-- <div style="float:left">
  <img  src="<%=basePath%>image/login/logo1.png" /> </div> --%>
  <div style="margin-top:9px;"></div>
 <img  src="image/newmain/main_title.png" >


    
		<div class="maindiv">
		<div class="main_level1">
		<img  src="image/newmain/module_run_normal.png" style="margin-left:120px;" id="module_run" onClick="linkToRun()">
		<img  src="image/newmain/module_video_normal.png" style="margin-left:17px;" id="module_video"  onClick="linkToVideo()">
		<img  src="image/newmain/module_analyse_normal.png" style="margin-left:17px;" id="module_analyse"  onClick="linkToAnalyse()">
		</div>
		
		<div class="main_level2">
		<img  src="image/newmain/module_assist_normal.png" style="margin-left:12px;" id="module_assist" >
		<img  src="image/newmain/module_science_normal.png" style="margin-left:17px;" id="module_science" onClick="setFullBGShow1()">
		<img  src="image/newmain/module_service_normal.png" style="margin-left:17px;" id="module_service" onClick="setFullBGShow()">
		<img  src="image/newmain/module_direct_normal.png" style="margin-left:17px;" id="module_direct">
		</div>
		   </div>
    </div>
   </div>
   </div>
 	   <div class="footer" id="footer">
		 <div class="versiondiv">
                  <div style="padding-top:10px;">嘉兴市交通局2006版权所有 &nbsp;浙ICP备05001821 &nbsp;建议使用IE8.0，分辨率1024x768以上浏览本站</div> 
                   <div style="padding-top:10px;">嘉兴市交通局主办  &nbsp;嘉兴市交通局办公室管理  &nbsp; 地址：嘉兴市中山东路1005号 &nbsp;联系电话：0573-82085014</div> 
          </div>
         
        </div>
        </div>


<!-- 弹出层 -->
<div id="popBack" >


</div>
  <div class="popWin">
    <img  src="image/newmain/module_walk_normal.png" style="margin-left:94px;margin-top:67px;"  id="module_walk" onClick="linkToWalk()">
    <img  src="image/newmain/module_transport_normal.png" style="margin-left:17px;margin-top:67px;"  id="module_transport"> 
    <img src="image/newmain/module_close_normal.png" style="float:right;" id="module_close" onClick="setFullBGHide()">
</div> 
 <div class="popWin1">
    <img  src="image/newmain/module_jtzs_normal.png" style="margin-left:94px;margin-top:67px;"  id="module_jtzs" onClick="linkToScience()">
    <img  src="image/newmain/module_xcyd_normal.png" style="margin-left:17px;margin-top:67px;"  id="module_xcyd" onClick="linkToXcyd()"> 
    <img src="image/newmain/module_close_normal.png" style="float:right;" id="module_close" onClick="setFullBGHide1()">
</div> 
  </body>
</html>
