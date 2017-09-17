<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"/></script>
    <script type="text/javascript" src="WebPage/js/common/Operation.js"/></script>
	<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>WebPage/js/carTraining/dynamic_left.js"></script>
	<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/TrainingFunction.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/TrainingFunctionDetail.js"></script>
	

  </head>
  
  <body>
     <div id="NewsList" class="main_right_li">
        <div class="right1"><a class="title" onClick="NewsDetail('1')"></a>
        <p class="date" style="margin-right:30px;"></p>
        <div class="rig_cont"><p></p></div>
        </div>
        
        
        
        
    <!-- <div class="right1">
            <a class="title">驾考新政后很有可能涨价，杭州已经小幅度涨价了</a><p class="date">2014-5-22</p>
            <div class="img"><img src="WebPage/image/carTraining/car1.png"/></div>
            <div class="rig_cont"><p style="margin-top:-10px ">嘉兴学车基本上都参考杭州的模式，杭州作为省会当然是有模范带头作用的，而且嘉兴离杭州也那么近。关于驾考改革后会
                                                                                     会涨价还是引起了很多人的关注，因为现在还没有任何正式的通知说会涨价，也没有明确说不会涨价，这个时候就是大家猜
                                                                                     最多的时候了。</p></div>
         </div>
        
         <div class="right1" style="margin-top: 15px;">
             <a class="title">2013年嘉兴路考线路图(最新)</a><p class="date">2014-5-22</p>
             <div class="img"><img src="WebPage/image/carTraining/car2.png"/></div>
             <div class="rig_cont"><p style="margin-top:-10px ">32013年路考难度有加大，主要是线线路路变得有些复杂，各位仔细看看下面4条最新的考试线路图。 </p></div>
         </div>
         <div class="right2" style="margin-top: 15px;">
             <a class="title">2013年驾考新规嘉兴路考教程</a><p class="date">2014-5-22</p>
             <div class="clear"></div>
             <div class="rig_cont1"><p>2013年1月14日嘉兴首批路考成绩下来了，共有64人参加考试，合格了23人，算一下合格率大概是36%左右。非常多的学员对这次考试评价，总结一
                                                                                                   下大致就是：线路复杂、评判严格、细节突出。 </p></div>
         </div>
         <div class="right2" style="margin-top: 15px;">
             <a class="title">就近学车一定要在选驾校的标准之内</a><p class="date">2014-5-22</p>
             <div class="clear"></div>
             <div class="rig_cont1"><p>有些人比较注重驾校的口碑，在网上也盛传各种各样的广告，但是，选驾校真的就只能看口碑吗？ 口碑固然重要，但毕竟是有点虚无缥缈的，比方
                                                                     说某某驾校口碑好，大家在说，但是你报名进去就会一切如同所说的那样一帆风顺呢？口碑再好的驾校也许离你家很远， </p></div>
         </div>
         <div class="right3" style="margin-top: 15px;">
           
            <div id="pageDiv">
					<p>
					<span class="prevBtnSpan"></span>
					<span class="pageNoSpan"></span>
					<span class="nextBtnSpan"></span>
					<span class="gotoPageSpan"></span>
					</p>
			</div>
            
         </div>-->
         </div>
  </body>
</html>
