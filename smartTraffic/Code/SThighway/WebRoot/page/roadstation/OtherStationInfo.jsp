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
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/roadstation/RoadStationInfo.js"></script>
  </head>
  
  <body>
    <div class="common_c0" id="allheight">
    <input type="hidden" value="<%=request.getParameter("stationId")%>" id="stationId"/>
    <input type="hidden" value="<%=request.getParameter("typeId")%>" id="typeId"/>
     <input type="hidden" value="<%=request.getParameter("xxxxId")%>" id="xxxxId"/>
    	<jsp:include page="../system/top.jsp" flush="true" />
	    	<div class="roadstation_c1" style="width:100%;background: white;">
		    			<div class="title_info" style="background: #f6f6f6;border-bottom: solid 1px #dcdcdc;">
							<span class="wordstyle" id="station_name" style="float:left;font-size: 18px;"></span>
							<img src="image/main/big_close_normal.png" class="close_image" onclick="closeGoToStation()"
								onmouseover="bigCloseOver(this)" onmouseout="bigCloseOut(this)">
						</div>
						<div class="left_I1" style="height:100%;">
							<!-- <div class="left_no_select" id="left_no_select1" style="">
								<label style="height:52px;line-height: 52px;vertical-align: middle;text-align: center;float:left;margin-left:20px;">基本信息</label>
							</div> -->
							<ul id="left_select_child1" class="left_select_child_station">
								<li class="left_no_select_li" onclick="showRoadStationBy(this,1)">基本信息</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,2)" id="left_no_select_li2">公路站成员</li>
								<!-- <li class="left_no_select_li" onclick="showRoadStationBy(this,3)" id="left_no_select_li3">管养路段</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,4)" id="left_no_select_li4">桥梁</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,5)" id="left_no_select_li5">涵洞</li> -->
								<li class="left_no_select_li" onclick="showRoadStationBy(this,6)" id="left_no_select_li6">月生产统计信息</li>
								<li class="left_no_select_li" onclick="showRoadStationBy(this,7)" id="left_no_select_li7">公路巡查信息</li>
							</ul>
						</div>
						<div id="sss" class="roadstation_c4">
							<div class="station_title">
								<label class="station_title_label" id="station_title_label"></label>
								<!-- <select onchange="showYearStatistic()" id="yearselect"></select> -->
								<div class="year_select_div" style="">
									<div class="year_select">
										<label class="year_select_time" onclick="showYearDiv()" >请选择年份</label>
										<img src="image/main/arrow_down.png" class="year_image" onclick="showYearDiv()" style="float:right;margin:13px 10px 0 0;">
										<ul class="year_down_ul"></ul>
									</div>
								</div>
							</div>
							<div style="width:100%;min-height: 750px;float:left;">
								<table class="listTable" id="stationMember" cellpadding="0" cellspacing="0" style="display:none;float:left;">
									<col width="15%"/><col width="15%"/><col width="15%"/><col width="15%"/>
									<col width="15%"/><col width="25%"/>
									<tr>
										<th>员工姓名</th>
										<th>员工类别</th>
										<th>职务</th>
										<th>手机号</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</table>
								<table class="listTable" id="manageRoad" cellpadding="0" cellspacing="0" style="display:none">
									<col width="15%"/><col width="15%"/><col width="15%"/><col width="15%"/>
									<col width="15%"/><col width="15%"/><col width="10%"/>
									<tr>
										<th>路线编号</th>
										<th>路线名称</th>
										<th>所属行政区域</th>
										<th>管养里程（公里）</th>
										<th>路段起点桩号</th>
										<th>路段终点桩号</th>
										<th>操作</th>
									</tr>
								</table>
								<table class="listTable" id="bridge" cellpadding="0" cellspacing="0" style="display:none">
									<col width="15%"/><col width="15%"/><col width="30%"/><col width="15%"/>
									<col width="10%"/><col width="15%"/>
									<tr>
										<th>桥梁代码</th>
										<th>桥梁名称</th>
										<th>管养路段</th>
										<th>桥梁中心桩号</th>
										<th>桥梁全长（米）</th>
										<th>操作</th>
									</tr>
								</table>
								<table class="listTable" id="culvert" cellpadding="0" cellspacing="0" style="display:none">
									<col width="17%"/><col width="17%"/><col width="17%"/><col width="17%"/>
									<col width="16%"/><col width="16%"/>
									<tr>
										<th>涵洞代码</th>
										<th>涵洞中心桩号</th>
										<!-- <th>管养路段</th> -->
										<th>涵洞类型</th>
										<th>涵洞净高（米）</th>
										<th>涵洞全长（米）</th>
										<th>涵洞全宽（米）</th>
									</tr>
								</table>
								<table class="listTable" id="monthStatistic" cellpadding="0" cellspacing="0" style="display:none;">
									<col width="11%"/><col width="10%"/><col width="13%"/><col width="10%"/>
									<col width="10%"/><col width="10%"/><col width="10%"/><col width="14%"/><col width="12%"/>
									<tr>
										<th>统计年月</th>
										<th>出勤率（%）</th>
										<th>直接生产率（%）</th>
										<th>计划经费（元）</th>
										<th>实际经费（元）</th>
										<th>质量指标</th>
										<th>实际指标</th>
										<th>质量完后率（%）</th>
										<th>计划工程量</th>
									</tr>
								</table>
								<table class="listTable" id="roadInspect" cellpadding="0" cellspacing="0" style="display:none">
									<col width="12%"/><col width="8%"/><col width="30%"/><col width="15%"/>
									<col width="15%"/><col width="12%"/><col width="8%"/>
									<tr>
										<th>巡查日期</th>
										<th>天气状况</th>
										<th>巡查记录</th>
										<th>巡查人</th>
										<th>处理人</th>
										<th>处理日期</th>
										<th>操作</th>
									</tr>
								</table>
								
								<div class="User_S4" id="pageDiv" style="background:#ffffff;display:none;">
									<p>
										共<span id="allTotal"></span>条
										<span class="firstBtnSpan"></span>
										<span class="prevBtnSpan"></span>
										<span class="pageNoSpan"></span>页
										<span class="nextBtnSpan"></span>
										<span class="lastBtnSpan"></span>
										<span class="gotoPageSpan"></span>
									</p>
								</div>
							</div>
							<!-- 员工信息 -->
						<div class="memberInfo" id="memberInfo_div" style="display: none;">
    						<div class="memberInfo_top"><label class="memberInfo_top_title">职员详情</label><img src="image/main/big_close_normal.png" onclick="closememberDetailDiv()"
    									class="div_close_image" onmouseover="bigCloseOver(this)" onmouseout="bigCloseOut(this)" /></div>
    						<table class="member_table" cellpadding="0" cellspacing="0">
    							<col width="150px" /><col width="350px" />
    							<tr><td style="text-align: right;">员工编号：</td><td id="workcode"></td></tr>
    							<tr><td style="text-align: right;">员工姓名：</td><td id="workerName"></td></tr>
    							<tr><td style="text-align: right;">员工类别：</td><td id="workType"></td></tr>
    							<tr><td style="text-align: right;">职务：</td><td id="post"></td></tr>
    							<tr><td style="text-align: right;">性别：</td><td id="sex"></td></tr>
    							<tr><td style="text-align: right;">已婚：</td><td id="marriage"></td></tr>
    							<tr><td style="text-align: right;">民族：</td><td id="people"></td></tr>
    							<tr><td style="text-align: right;">政治面貌：</td><td id="political"></td></tr>
    							<tr><td style="text-align: right;">籍贯：</td><td id="birthplace"></td></tr>
    							<tr><td style="text-align: right;">专业：</td><td id="professional"></td></tr>
    							<tr><td style="text-align: right;">手机号：</td><td  id="telephone"></td></tr>
    							<tr><td style="text-align: right;">身份证号：</td><td  id="cardId"></td></tr>
    							<tr><td style="text-align: right;">学历：</td><td  id="education"></td></tr>
    							<tr><td style="text-align: right;">入职日期：</td><td  id="entryTime"></td></tr>
    							<tr><td style="text-align: right;">在职：</td><td  id="workStatus"></td></tr>
    							<tr><td style="text-align: right;">合同开始日期：</td><td  id="contractBegin"></td></tr>
    							<tr><td style="text-align: right;">合同结束日期：</td><td id="contractEnd"></td></tr>
    							<tr><td style="text-align: right;">住址：</td><td id="address"></td></tr>
    							<tr><td style="text-align: right;">简历：</td><td id="resume"></td></tr>
    							<tr><td style="text-align: right;">参与考勤：</td><td id="attendanceMark"></td></tr>
    							<tr><td style="text-align: right;">备注：</td><td id="remarks"></td></tr>
    						</table>
    					</div>
						</div>
					
    				<!-- 巡查信息 -->
    					<div class="memberInfo" style="display: none;" id="patrol_div">
    						<div class="memberInfo_top"><label class="memberInfo_top_title">巡查记录详情</label><img src="image/main/big_close_normal.png" onclick="closeXCDetailDiv()"
    									class="div_close_image" onmouseover="bigCloseOver(this)" onmouseout="bigCloseOut(this)" /></div>
    						<table class="stationDetailTable" cellpadding="0" cellspacing="0">
    							<col width="150px" /><col width="350px" /><col width="120px" /><col width="180px" />
    							<tr><th colspan="4">巡查记录</th></tr>
    							<tr>
    								<td style="text-align: right;">巡查日期：</td>
    								<td id="inspectDate"></td>
    								<td style="text-align: right;">天气状况：</td>
    								<td id="weather"></td>
    							</tr>
    							<tr>
    								<td style="text-align: right;">巡查记录：</td>
    								<td colspan="3" id="xcjl"></td>
    							</tr>
    							<tr>
    								<td style="text-align: right;">巡查人员：</td>
    								<td colspan="3" id="inspector"></td>
    							</tr>
    							<tr><th colspan="4">处理意见</th></tr>
    							<tr>
    								<td style="text-align: right;">处理意见：</td>
    								<td colspan="3" id="opinion"></td>
    							</tr>
    							<tr>
    								<td style="text-align: right;">签名日期：</td>
    								<td id="signDate"></td>
    								<td style="text-align: right;">站（班长）：</td>
    								<td id="signature"></td>
    							</tr>
    							<tr><th colspan="4">处理结果</th></tr>
    							<tr>
    								<td style="text-align: right;">处理日期：</td>
    								<td id="handleDate"></td>
    								<td style="text-align: right;">处理人：</td>
    								<td id="handlerSignature"></td>
    							</tr>
    							<tr>
    								<td  style="text-align: right;">处理结果：</td>
    								<td colspan="3" id="handleResult"></td>
    							</tr>
    							<tr><th colspan="4">巡查路段</th></tr>
    							<!-- <tr>
    								<td  style="text-align: right;">巡查路段：</td>
    								<td id="sectionName"></td>
    								<td  style="text-align: right;">起止桩号：</td>
    								<td id="stake"></td>
    							</tr> -->
    						</table>
    					</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
    
  </body>
</html>
