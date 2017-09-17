<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公路站信息详情</title>
    
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
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/zhjtapi_2.0/config.js"></script>
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/js/mapbar/MapbarHelper.js"></script>
	<script type="text/javascript" src="http://172.20.8.46:8080/zhjtapi/js/mapbar/Mapbar.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/roadstation/RoadStationDetail.js"></script>
	
	
	

  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    <input type="hidden" value="<%=request.getParameter("xxxxId")%>" id="xxxxId"/>
    <input type="hidden" value="<%=request.getParameter("stationId")%>" id="stationId"/>
    	<jsp:include page="../system/top.jsp" flush="true" />
	    	<div class="roadstation_c1" style="width:100%;background: white;">
		    	<div class="common_c2">
		    		<div class="style">
		    			<div class="title_info" style="background: #f6f6f6;border-bottom: solid 1px #dcdcdc;">
							<span class="wordstyle" id="station_name" style="float:left;font-size: 18px;"></span>
							<img src="image/main/big_close_normal.png" class="close_image" onclick="closeGoToStation()"
								onmouseover="bigCloseOver(this)" onmouseout="bigCloseOut(this)">
						</div>
						<div class="left_I1" style="height:100%;min-height: 1970px;">
							<!-- <div class="left_no_select" id="left_no_select1" style="">
								<label style="height:52px;line-height: 52px;vertical-align: middle;text-align: center;float:left;margin-left:20px;">基本信息</label>
							</div> -->
							<ul id="left_select_child1" class="left_select_child_station">
								<li class="left_select_li" onclick="showRoadStationBy(this,1)">基本信息</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,2)">公路站成员</li>
								<!-- <li class="left_no_select_li" onclick="showRoadStationBy(this,3)">管养路段</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,4)">桥梁</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,5)">涵洞</li> -->
								<li class="left_no_select_li" onclick="showRoadStationBy(this,6)">月生产统计信息</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,7)">公路巡查信息</li>
							</ul>
						</div>
						<div id="sss" class="roadstation_c4">
							<div class="station_title"><label class="station_title_label" id="station_title_label"></label></div>
							<div style="width:100%;min-height: 750px;">
								<table class="stationDetailTable" cellpadding="0" cellspacing="0">
									<col width="20%"/><col width="30%"/><col width="20%"/><col width="30%"/>
									<tr><th colspan="4">单位信息</th></tr>
									<tr><td style="text-align: right">公路站代码：</td><td id="glzdm"></td><td style="text-align: right">公路站名称：</td><td id="glzmc"></td></tr>
									<tr><td style="text-align: right">所属管理机构：</td><td id="gljg"></td><td style="text-align: right">登记年份：</td><td id="year"></td></tr>
									<tr><td style="text-align: right">绿化面积（平方米）：</td><td id="greenArea"></td><td style="text-align: right">生产用房产地（平方米）：</td><td id="workArea"></td></tr>
									<tr><td style="text-align: right">生活用房（平方米）：</td><td id="liveArea"></td><td style="text-align: right">占地面积（亩）：</td><td id="totalArea"></td></tr>
									<tr><th colspan="4">指标信息</th></tr>
									<tr><td style="text-align: right">下达优良里程指标（公里）：</td><td id="issuedTarget"></td><td style="text-align: right">下达优良里程优良率（%）：</td><td id="issuedPercent"></td></tr>
									<tr><td style="text-align: right">力争优良里程指标（公里）：</td><td id="fightTarget"></td><td style="text-align: right">力争优良里程优良率（%）：</td><td id="fightPercent"></td></tr>
									<tr><td style="text-align: right">国省道优良里程（公里）：</td><td id="provinceTarget"></td><td style="text-align: right">县乡道优良里程（公里）：</td><td id="countryTarget"></td></tr>
									<tr><td style="text-align: right">一季度指标（公里）：</td><td id="firstSeasonTarget"></td><td style="text-align: right">二季度指标（公里）：</td><td id="secondSeasonTarget"></td></tr>
									<tr><td style="text-align: right">三季度指标（公里）：</td><td id="thirdSeasonTarget"></td><td style="text-align: right">四季度指标（公里）：</td><td id="fourthSeasonTarget"></td></tr>
									<tr><th colspan="4">本站管养线路及桩号</th></tr>
									<tr><td style="text-align: right">共养护里程（公里）：</td><td id="maintainTotal"></td><td style="text-align: right">桥梁座数（座）：</td><td id="bridgeCount"></td></tr>
									<tr><td style="text-align: right">桥梁长度（延米）：</td><td id="bridgeLong"></td><td style="text-align: right">隧道座数（座）：</td><td id="tunnelCount"></td></tr>
									<tr><td style="text-align: right">隧道长度（延米））：</td><td id="tunnelLong"></td><td style="text-align: right">绿化里程（公里）：</td><td id="greenLong"></td></tr>
									<tr><td style="text-align: right">绿化率（%）：</td><td id="greenPercent"></td><td style="text-align: right">国道（公里）：</td><td id="countryRoad"></td></tr>
									<tr><td style="text-align: right">省道（公里）：</td><td id="provinceRoad"></td><td style="text-align: right">县乡道（公里）：</td><td id="cityRoad"></td></tr>
									<tr><th colspan="4">公路指标</th></tr>
									<tr><td style="text-align: right">干线优良指标（公里）：</td><td id="mainGoodIndex"></td><td style="text-align: right">干线优良率（%）：</td><td id="mainGoodRate"></td></tr>
									<tr><td style="text-align: right">支线优良指标（公里）：</td><td id="subGoodIndex"></td><td style="text-align: right">支线优良率（%）：</td><td id="subGoodRate"></td></tr>
									<tr><th colspan="4">本站主要机具（20辆/台）</th></tr>
									<tr><td style="text-align: right">站内共有机具（台/辆）：</td><td id="machineTotal"></td><td style="text-align: right">机具原值（万元）：</td><td id="machineMoney"></td></tr>
									<tr><td style="text-align: right">拖拉机（台）：</td><td id="tractorCar"></td><td style="text-align: right">压路机（台）：</td><td id="rollerCar"></td></tr>
									<tr><td style="text-align: right">小翻斗车（台）：</td><td id="smallTruckCar"></td><td style="text-align: right">沥青洒布车（台）：</td><td id="asphaltCar"></td></tr>
									<tr><td style="text-align: right">装载机（台）：</td><td id="loadCar"></td><td style="text-align: right">洒水车（台）：</td><td id="waterCar"></td></tr>
									<tr><td style="text-align: right">清扫车（台）：</td><td id="sweepCar"></td><td style="text-align: right">除草机（台）：</td><td id="weedCar"></td></tr>
									<tr><td style="text-align: right">粒料拌和机（台）：</td><td id="mixerCar"></td><td style="text-align: right">灌缝机（台）：</td><td id="fillSewCar"></td></tr>
									<tr><td style="text-align: right">巡查车（台）：</td><td id="inspectCar"></td><td style="text-align: right">&nbsp;</td><td>&nbsp;</td></tr>
									<tr><th colspan="4">人员构成及经费</th></tr>
									<tr><td style="text-align: right">站内共有职工（人）：</td><td id="totalWorker"></td><td style="text-align: right">定额（人）：</td><td id="fixedMoney"></td></tr>
									<tr><td style="text-align: right">固定工（人）：</td><td id="fixedWorker"></td><td style="text-align: right">临时工（人）：</td><td id="tempWorker"></td></tr>
									<tr><td style="text-align: right">男职工（人）：</td><td id="manWorker"></td><td style="text-align: right">女职工（人）：</td><td id="womanWorker"></td></tr>
									<tr><td style="text-align: right">35岁以下（人）：</td><td id="thirtyFiveDown"></td><td style="text-align: right">35至40岁（人）：</td><td id="thirtyFiveToFortyFive"></td></tr>
									<tr><td style="text-align: right">45岁以上（人）：</td><td id="fortyFiveUp"></td><td style="text-align: right">大中专（人）：</td><td id="college"></td></tr>
									<tr><td style="text-align: right">高中（人）：</td><td id="highSchool"></td><td style="text-align: right">初中（人）：</td><td id="middleSchool"></td></tr>
									<tr><td style="text-align: right">党员（人）：</td><td id="partyCount"></td><td style="text-align: right">团员（人）：</td><td id="memberCount"></td></tr>
									<tr><th colspan="4">线路管养示意图</th></tr>
									<tr><td colspan="4" id="picture"></td></tr>
									<tr><th colspan="4">备注</th></tr>
									<tr><td style="text-align: right">备注：</td><td id="remark"></td><td style="text-align: right">&nbsp;</td><td>&nbsp;</td></tr>
								</table>
								<!-- <div style="width:100%;height:300px;margin-top:30px;">
									<div class="bridge_map" id="bridge_map_div">
										<div class="bridge_map_div" id="station_map"></div>
										<div class="map_text">&nbsp;&nbsp;&nbsp;&nbsp;<label style="cursor: pointer;" onclick="seeAllMap()">点击查看完整地图</label></div>
									</div>
								</div> -->
							</div>
						</div>
					</div>	
				</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
  </body>
</html>
