<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>交通量</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/CRselectBox.css" />	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/RoleManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/traffic/Traffic.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/top.jsp" flush="true" />
    			<div class="left_I1">
					<div class="left_no_select" id="left_no_select1">
						<label class="left_name" id="gljg_bossname">交通量</label>
					</div>
					<ul id="left_select_child1" class="left_select_child">
						<li class='left_select_li' onclick="goToTraffic()">交调信息</li>
						<li class='left_no_select_li' onclick="goToRealTraffic()">统计数据</li>
					</ul>
				</div>
	    	<div class="common_c1">
		    	<div class="common_c2">
		    		<div class="right_title" style="border-bottom: solid 1px #cccccc;">
						<span class="wordstyle" id="area_name">交通量</span>
						<!-- <div class="c3_button1" onclick="goToStatistic(3)" style="float:right;margin-right:15px;" onmouseover="trabuttonOver()" onmouseout="trabuttonOut()">
							<div class="c3_button1_left" id="tra_button1_left"></div>
							<div class="c3_button1_center" id="tra_button1_center">统计分析</div>
							<div class="c3_button1_right" id="tra_button1_right"></div>
						</div> -->
					</div>
						<div class="common_traffic">
							<table class="pop_up_middle_table" cellpadding="0" cellspacing="0" >
								<col width="10%"/><col width="20%"/><col width="10%"/><col width="20%"/>
								<col width="10%"/><col width="20%"/><col width="10%"/>
								<tr>
									<td style="text-align: right;">行政区划&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="xzqh" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择行政区划" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择行政区划</a>
													  	<ul class="CRselectBoxOptions" id="xzqh_ul">
														</ul>
										</div>
									</td>
									<td style="text-align: right;">调查站类型&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="dczlx" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择调查站类型" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择调查站类型</a>
													  	<ul class="CRselectBoxOptions" id="dczlx_ul">
													  		<li class="CRselectBoxItem"><a rel="0">全部</a></li>
														</ul>
										</div>
									</td>
									<td style="text-align: right;">调查方法&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="dcff" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择调查方法" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择调查方法</a>
													  	<ul class="CRselectBoxOptions" id="dcff_ul">
													  		<li class="CRselectBoxItem"><a rel="0">全部</a></li>
														</ul>
										</div>
									</td>
									<td rowspan="2">
										<div class="c3_button1" onclick="showTrafficInfo('jxdczxx/dczxxlist',1)" style="margin:10px 15px 0 10px;float:right;" onmouseover="addbuttonover()" onmouseout="addbuttonout()">
											<div class="c3_button1_left" id="add_button_left"></div>
											<div class="c3_button1_center" id="add_button_center">查询</div>
											<div class="c3_button1_right" id="add_button_right"></div>
										</div>
									<td>
								</tr>
								<tr>
									<td style="text-align: right;">供电方式&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="gdfs" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择供电方式" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择供电方式</a>
													  	<ul class="CRselectBoxOptions" id="gdfs_ul">
													  		<li class="CRselectBoxItem"><a rel="0">全部</a></li>
														</ul>
										</div>
									</td>
									<td style="text-align: right;">通讯方式&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="txfs" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择通讯方式" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择通讯方式</a>
													  	<ul class="CRselectBoxOptions" id="txfs_ul">
													  		<li class="CRselectBoxItem"><a rel="0">全部</a></li>
														</ul>
										</div>
									</td>
									<td style="text-align: right;">公路功能&nbsp;&nbsp;&nbsp;</td>
									<td>
										<div class="CRselectBox" id="glgn" style="margin-top:5px;">
											<input type="hidden" value="0" class="abc" />
												<input type="hidden" value="选择公路功能" class="abc_CRtext"/>
													<a class="CRselectValue" style="color:black">选择公路功能</a>
													  	<ul class="CRselectBoxOptions" id="glgn_ul">
													  		<li class="CRselectBoxItem"><a rel="0">全部</a></li>
														</ul>
										</div>
									</td>
								</tr>
							</table>
						</div >
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="15%"/><col width="15%"/><col width="15%"/><col width="15%"/>
						<col width="10%"/><col width="10%"/><col width="10%"/><col width="10%"/>
							<tr>
								<th>调查站名称</th>
								<th>行政区划</th>
								<th>调查站类型</th>
								<th>调查方法</th>
								<th>供电方式</th>
								<th>通讯方式</th>
								<th>公路功能</th>
								<th>操作</th>
							</tr>
							
						</table> 
						<div class="User_S4" id="pageDiv" style="background:#f6f6f6;display:none;">
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
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
   	 	
		    	<!-- 查看交调信息详情 -->
		    		 <div class="dialog" id="DetailDiv" style="display:none;width:800px;margin:-246px 0 0 -400px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">交调信息详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle">
		    				<div class="outer_table">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="140px"/><col width="240px"/><col width="140px"/><col width="240px"/>
				    					<tr>
				    						<td class="td_left1">调查站名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dczmc_detail"></label></td>
				    						<td class="td_left2">线路名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xlmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">调查方法&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dcffmc_detail"></label></td>
				    						<td class="td_left2">调查站类型&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dczlxmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">调查站状态&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dczzt_detail"></label></td>
				    						<td class="td_left2">供电方式&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gdfsmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">通讯方式&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="txfsmc_detail"></label></td>
				    						<td class="td_left2">行政区划&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xzqhmc_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">公路功能&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="glgnmc_detail"></label></td>
				    						<td class="td_left2">管理机构&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gljg_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">起点名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qdmc_detail"></label></td>
				    						<td class="td_left2">起点桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qdzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">止点名称&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdmc_detail"></label></td>
				    						<td class="td_left2">止点桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdzh_detail"></label></td>
				    					</tr>
				    					<tr>
				    						<td class="td_left1">桩号&nbsp;&nbsp;&nbsp;</td>
				    						<td id=""  class="td_right" style="border-right:0">&nbsp;&nbsp;&nbsp;<label id="zh_detail"></label></td>
				    						<td style="border-right:0">&nbsp;&nbsp;&nbsp;</td>
				    						<td>&nbsp;&nbsp;&nbsp;</td>
				    					</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
  </body>
</html>
