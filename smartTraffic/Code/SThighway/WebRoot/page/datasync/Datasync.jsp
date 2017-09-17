<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>数据同步</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/datasync/datasync.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/datasync/highcharts.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/datasync/Datasync.js"></script>
	
  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<div style="width:1342px;height:auto;margin:0 auto;" >
    		<div class="direction_div"><img class="direction_image" id="left_image" onmouseover="leftOver(this,'left_')" onmouseout="leftOut(this,'left_')" src="image/datasync/left_normal.png"></div>
		    	<div style="width:1292px;display: inline-block;float:left;" id="out_div">
		    		<div class="data_top">
		    			<div class="data_top_left"><img alt="" src="image/datasync/ic_circleblue.png" class="circle_image"><span class="data_span">正在同步中</span></div>
		    			<div class="data_top_left"><img alt="" src="image/datasync/ic_circlegreen.png" class="circle_image"><span class="data_span">同步成功</span></div>
		    			<div class="data_top_left"><img alt="" src="image/datasync/ic_circlered.png" class="circle_image"><span class="data_span">同步失败</span></div>
		    			<div class="data_top_right"></div>
		    		</div>
		    		<div class="data_middle">
		    			<!-- <div index='1' class="slide_div">
			    			<div class="data_middle_div">
			    				<div class="middle_top4"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">通阻信息</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_insync.gif"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_sync">正在同步中</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
			    			<div class="data_middle_div">
			    				<div class="middle_top3"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">公路站</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_success.png"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_success">同步成功</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
			    			<div class="data_middle_div">
			    				<div class="middle_top4"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">交调信息</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_insync.gif"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_sync">正在同步中</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
			    			<div class="data_middle_div">
			    				<div class="middle_top6"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">治超处罚数据</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_fail.png"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_fail">同步失败</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
			    			<div class="data_middle_div">
			    				<div class="middle_top9"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">智慧交通综合数据库</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_insync.gif"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_sync">正在同步中</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
			    			<div class="data_middle_div" style="border-right:0;">
			    				<div class="middle_top9"><img src="image/datasync/ic_download.png" class="download_image"><span class="data_span datapos">市政府行政处罚平台</span></div>
			    				<div class="data_middle_middle"><img alt="" class="sync_image move" src="image/datasync/ic_insync.gif"></div>
			    				<div class="data_middle_bottom">
			    					<label class="data_label_sync">正在同步中</label><br/><br/>
			    					<label class="data_label_time">上一次同步：2015-05-05 15:00</label>
			    				</div>
			    			</div>
		    			</div> -->
		    			<!-- <div index='2' style='display: none' class="slide_div">
			    			
		    			</div> -->
		    		</div>
		    		<div class="data_bottom">
		    			<div class="data_bottom_top">
		    				<div class="data_bottom_top_left">
		    					<img alt="" src="image/datasync/ic_count.png" class="circle_image" style="margin:16.5px 0 0 15px;">
		    					<span class="data_span">今日数据同步显示</span>
		    				</div>
		    				<div class="data_bottom_top_right">
		    					<a class="Operate move" href="javascript:void(0)" onclick="showMoreInfoDiv()">更多</a>
		    				</div>
		    			</div>
		    			<div class="charts_select">
		    				<ul class="select_ul">
		    					<li class="select_ul_item" style="margin-left:15px;"><a class="choose_radio_select" rel="1"></a>&nbsp;&nbsp;<label>通阻信息</label></li>
		    					<li class="select_ul_item"><a class="choose_radio" rel="2"></a>&nbsp;&nbsp;<label>公路站</label></li>
		    					<li class="select_ul_item"><a class="choose_radio" rel="3"></a>&nbsp;&nbsp;<label>交调信息</label></li>
		    					<li class="select_ul_item"><a class="choose_radio" rel="4"></a>&nbsp;&nbsp;<label>治超处罚数据</label></li>
		    					<li class="select_ul_item"><a class="choose_radio" rel="5"></a>&nbsp;&nbsp;<label>智慧交通综合数据库</label></li>
		    					<li class="select_ul_item"><a class="choose_radio" rel="6"></a>&nbsp;&nbsp;<label>市政府行政处罚平台</label></li>
		    				</ul>
		    			</div>
		    			<div class="data_highcharts">
		    				<div id="linechart" class="linechart">
		    					
		    				</div>
		    				<div id="piechart" class="piechart">
		    					<!-- <center>
		    					<div id="myStat" data-dimension="250" data-text="35%" data-info="New Clients" data-width="30" data-fontsize="38" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd" class="circliful" style="width: 250px;">
			    					<span class="circle-text" style="line-height: 250px; font-size: 38px;">35%</span>
			    					<span class="circle-info" style="line-height: 312.5px;">阿发</span>
		    					</div>
		    					</center> -->
		    							<!-- <div id="diagram"></div>   
											<div class="get">
											  <div class="arc">
											    <input type="hidden" class="percent" value="95" />
											    <input type="hidden" class="color" value="#5ec219" />
											    <input type="hidden" class="text" value="" />
											  </div>
											</div> -->
		    				</div>
		    			</div>
		    		</div>
		    	</div>
	    	<div class="direction_div" ><img class="direction_image" id="right_image" onmouseover="leftOver(this,'right_')" onmouseout="leftOut(this,'right_')" src="image/datasync/right_normal.png"></div>
	   	</div>
    </div>
    
    
    	<div class="dialog" id="DetailDiv" style="display:none;width:900px;margin:-300px 0 0 -450px;background: white;">
    		<div class="User_Top1">
   				<label class="Top_left"></label>
   				<img  class="Top_right" src="image/main/close.png"
   					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
   			</div>
   			<div class="time_search">
   				<div class='search_div' style="width:260px;">
					<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchTimeInfo()">
						<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
						</div><input value='' id='search_time' class="Wdate search_input" style="border-right:0;height:33px;color:black" onFocus="WdatePicker({maxDate:'%y-%M-%d'})" readonly="readonly"  />
				</div>
   			</div>
   			<div class="pop_up_middle" style="height:450px;overflow-y:auto;overflow-x:hidden; ">
   				<div style="margin:20px;">
   					<table class="data_pop_up_middle_table" id="detailTable" cellpadding="0" cellspacing="0" border="0">
   						<col width="200px"/><col width="240px"/><col width="180px"/><col width="240px"/>
	   						<tr>
	   							<th>系统名称</th>
	   							<th>步骤名称</th>
	   							<th>时间</th>
	   							<th>数据插入</th>
	   						</tr>
   					</table>
   				</div>
   			</div>
    	</div>
  </body>
</html>
