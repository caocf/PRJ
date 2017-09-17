<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <base href="<%=basePath%>">   
    <title>嘉兴智慧交通综合服务平台</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<link rel="stylesheet" type="text/css" href="css/system/top.css">
	<link rel="stylesheet" type="text/css" href="css/system/style.css">
	<link rel="stylesheet" type="text/css" href="css/main/serviceflat.css">
	<link rel="stylesheet" type="text/css" href="css/map/common.css">

	
	<!-- 加载地图设置 
	<script language="javascript" src="js/mywapi.js"></script>
	-->
	<script src="js/common/jquery-1.10.2.min.js"></script> 
	<script type="text/javascript" src="" id="getAspx"></script>
	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
	<!-- <script type="text/javascript" src="js/jquery.jsonp.js"></script> -->
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/zhjtapi_2.0/config.js"></script>
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/js/mapbar/MapbarHelper.js"></script>
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/js/mapbar/Mapbar.js"></script>
	<script type="text/javascript" src="js/map/mapheight1.js"></script>
	<script type="text/javascript" src="js/system/link1.js"></script>
	<script type="text/javascript" src="js/main/serviceFlat.js"></script>
	<script type="text/javascript">
		
	</script>
	<script type="text/javascript">
	
	</script>
	<!-- <script type="text/javascript" src="js/web-t/WebTMap.js"></script>
	<script language="javascript" src="js/web-t/WebTHelper.js"></script>
	<script language="javascript" src="js/web-t/map.js"></script>
	<script language="javascript" src="js/web-t/config.js"></script> -->
	
	<script>
		 
</script>
  </head>
  
  <body style="overflow-x:hidden;overflow-y:hidden">
	<div class="common_c0">
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div class="top_u1">
			<div>
				<span class="top_text1" id="top_text">嘉兴智慧交通综合服务平台</span>
		   </div>
			</div>
		<div id="serviceflat_map1" class="traffic_map_back">
			<div id="serviceflat_map" class="traffic_map"></div>
		</div>

</div>
<!-- 浮动层 -->
   <!-- 热点指数标题 -->
   <div class="hotbet" id="hotbetid" style="display:none;">
   
   <div class="left_3">
				<div id="left_3_1" style="height:266px;  border: 1px solid #3982de;" style="display:none;">
					<ul id="tags">
						<li id="mainRegion" onclick="queryRegion();"><a>区域指数</a></li>
						<li id="mainHot" class="selectTag" onclick="queryHot(1)"><a>热点指数</a></li>
					</ul>
					<div id="traContent" style="position: relative; top: auto; left: auto; right: auto; bottom: auto; width: 200px;"><div id="tagContent" style="position: relative; width: 100%;">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" id="cbdtable" onclick="clickto(this)">
						    <tbody><tr class="titlename">
						      <td id="cloumn_qy">区域</td>
						      <td>指数</td>
						      <td>拥堵等级</td>
						    </tr>
						    </tbody><tbody id="content"></tbody>
						 </table>
					</div><div class="ol_loading_mask null_mask" style="z-index: 800; display: none;"></div><div class="ol_loading" style="z-index: 801; display: none;"></div><div class="ol_loading_text null_text" style="z-index: 801; display: none;">正在加载中...</div></div>
				</div>
			</div>
   
   
	
	
	
	<div class="traffectedroad" id="traffectedroad" style="width:200px;display:none;">
		
		<div class="tip_top">
			<div class="tip_top_textrow">拥挤道路路段</div>
		</div>
    <div class="scrollConRoadsDIV" style="height:150px;">
		<!-- 	<span id="mrgConRoadsTip"></span> -->
			<ul id="conRoadInfo" style="margin-top: 0px;">
<!-- 			 <li>[次干道]&nbsp;&nbsp;海盐塘路：凌公塘路→烟雨路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;海盐塘路：烟雨路→凌公塘路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;环城西路：安乐路→杨柳湾路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;环城西路：吉杨路→杨柳湾路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[主干道]&nbsp;&nbsp;禾兴北路：云海路→三环北路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;中环北路→同心路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;秀州路：勤俭路→中山东路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;紫阳街：斜西街→杨柳湾路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;三环东路：南溪东路→周安路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;三环南路→广益路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;三环东路：凌公塘路→广益路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;三环南路：嘉南线→嘉余线&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;中环东路：锦带河路→创业路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;城东路→周安路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;中环北路：东方路→正原路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;中环西路：东升东路→振兴路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;亚厦路：新秀路→新洲路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;凯旋路：越秀北路→中环西路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;勤俭路：解放路→城东路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;勤俭路：解放路→秀州路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;建国南路→少年路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;华云路：昌盛中路→云海路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[快速路]&nbsp;&nbsp;南湖大道：三环南路→长水路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;南溪西路：中环东路→景宜路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;吉杨路：环城西路→吉水路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;吴越路：中山西路→亚厦路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[主干道]&nbsp;&nbsp;城北路：栅堰路→环城西路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;安乐路：杨柳湾路→中山东路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;庆丰路：南溪西路→望湖路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;文昌路：城南路→吉水路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[主干道]&nbsp;&nbsp;昌盛东路：三环北路→鸣羊路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[主干道]&nbsp;&nbsp;昌盛东路：茶园路→鸣羊路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[次干道]&nbsp;&nbsp;望湖路：南江路→庆丰路&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;杨柳湾路：禾兴南路→紫阳街&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li><li>[支路]&nbsp;&nbsp;洪殷路：昌盛中路→常秀街&nbsp;比较拥堵&nbsp;&nbsp;&nbsp;&nbsp;<br><br></li>
 -->
</ul>
		</div>
    </div>
	</div>
   <!-- 左部浮动层 -->
    <!-- 拥挤道路路段标题 -->
	
    <!-- 底部导航栏 -->
    <img  style='height:68px;position:absolute;cursor:pointer;border:1px solid #ccc;' id="link_Left" src="image/map/linkleft_on.png"> 
	<div class="linkline" id="linklineid">
		

	
		<div class="linkline_img" id="mainguid_traff"
			style="background:#1f5aa7;">
			<img src="image/link_icon/icon_zhjt.png"> <label>综合交通</label>
		</div>
		<div class="linkline_img" id="mainguid_road"
			style="background:#008181;">
			<img src="image/link_icon/icon_zhgl.png"> <label>智慧公路</label>
		</div>
		<div class="linkline_img" id="mainguid_transport"
			style="background:#2abb5d;">
			<img src="image/link_icon/icon_zhyg.png"> <label>智慧运管</label>
		</div>
		<div class="linkline_img" id="mainguid_outgo"
			style="background:#2a8eff;">
			<img src="image/link_icon/icon_zhgh.png"> <label>智慧港航</label>
		</div>
		<div class="linkline_img" id="mainguid_quality"
			style="background:#ff8034;">
			<img src="image/link_icon/icon_zhzj.png"> <label>智慧质监</label>
		</div>
		<div class="linkline_img" id="mainguid_portoffice"
			style="background:#6073ff;">
			<img src="image/link_icon/icon_zhgw.png"> <label>智慧港务</label>
		</div>
		<div class="linkline_img" id="mainguid_sea"
			style="background:#1446aa;
		-moz-border-radius:0 4px 4px 0 ;-webkit-border-radius:0 4px 4px 0 ;
		border-radius:0 4px 4px 0;behavior: url(ie-css3.htc);">
			<img src="image/link_icon/icon_zhhs.png"> <label>智慧海事</label>
		</div>
		
	</div>

	<!-- 右部浮动层 -->
    <div class="degreeguid"  style="display:none;">
    <span>拥堵</span><img src="image/main/guid_color.png"/><span>畅通</span>
    <img  src="image/main/icon_nodata.png"><span>无数据</span>
    </div>
    
   <!-- <select class="regionselect">
     <option value="请选择">请选择</option>        
      <option value="1" selected="selected">南湖区  </option> 
      <option value="2" selected="selected">秀洲区  </option> 
      </select>
       --> 
    <div class="sizeguid" >
    <div id="zoomLarge"  style="CURSOR:pointer;" onclick="zoomIn();">
    <img class="sizeguid_img1" src="image/main/icon_add.png" /></div>
    <div class="sizeguid_line"></div>
    <div onclick="zoomOut();"  style="CURSOR:pointer;">
    <img class="sizeguid_img2" src="image/main/icon_reduce.png" style="CURSOR:pointer;" /></div>
    </div>
    
</body>
</html>
