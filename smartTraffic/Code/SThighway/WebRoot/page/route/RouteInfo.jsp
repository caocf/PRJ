<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>路网信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/system/RoleManage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/newdialog.css">
	<link rel="stylesheet" type="text/css" href="css/system/system_left.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bridge/BridgeInfo.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="js/common/search.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/route/RouteInfo.js"></script>
	<script type="text/javascript" src="js/main/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>

  </head>
  
  <body>
    <div class="common_c0">
    	<jsp:include page="../system/mtop.jsp" flush="true" />
    			<div class="left_I1">
					<div class="left_no_select" id="left_no_select1" onclick="showThePartRoute(0,'嘉兴市',this)" style="cursor: pointer;">
						<label class="left_name" id="gljg_bossname">嘉兴市</label>
					</div>
					<ul id="left_select_child1" class="left_select_child"></ul>
				</div>
				<input type="hidden" value="<%=request.getParameter("num") %>" id="num"/>
	    	<div class="common_c1" id="route_list">
						<div class="right_title">
							<span class="wordstyle" id="area_name">路网信息</span>
							<!-- <div class="c3_button1" onclick="goToStatistic(0)" style="float:right;margin-right:15px;" onmouseover="buttonOver()" onmouseout="buttonOut()">
								<div class="c3_button1_left"></div>
								<div class="c3_button1_center">统计分析</div>
								<div class="c3_button1_right"></div>
							</div> -->
						</div>
						<div class="common_c3" style="border-bottom:solid 1px #dcdcdc">
							<div class='search_div'>
							<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="searchRouteInfo()">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image">
								</div><input value='路线代码、路线简称' id='bridge_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" /></div>
							<!-- <button class="superSearch" onclick="showSearchDiv(1)">高级搜索</button> -->
						</div>
						<table class="listTable" id="roleTable" cellpadding="0" cellspacing="0">
						<col width="10%"/><col width="10%"/><col width="15%"/><col width="8%"/>
						<col width="9%"/><col width="16%"/><col width="11%"/><col width="11%"/><col width="10%"/>
							<tr>
								<th>路线代码</th>
								<th>路段编号</th>
								<th>路线简称</th>
								<!-- <th>路线地方名称</th> -->
								<th>技术等级</th>
								<th>里程（公里）</th>
								<th>管理单位</th>
								<th>路段起点桩号</th>
								<th>路段终点桩号</th>
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
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
   		 <!-- 查看路网 -->
   				 <div class="dialog" id="DetailDiv" style="display:none;width:900px;margin:-300px 0 0 -450px;">
		    			<div class="User_Top1">
		    				<label class="Top_left">路网详情</label>
		    				<img  class="Top_right" src="image/main/close.png"
		    					onmouseover="CloseOver(this)" onMouseOut="CloseOut(this)" onclick="closeDetailDiv()">
		    			</div>
		    			<div class="pop_up_middle" style="height:550px;overflow-y:auto;overflow-x:hidden;">
		    				<div style="margin:20px;">
				    				<table class="see_pop_up_middle_table" cellpadding="0" cellspacing="0" border="0">
				    				<col width="200px"/><col width="230px"/><col width="220px"/><col width="210px"/>
				    					<tr>
											<td class="td_left1" style="background: #f9f9f9;">路线代码&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxdm"></label></td>
											<td class="td_left2" style="background: #f9f9f9;">路线编码&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ldbm"></label></td>
										</tr>
										<tr>
											<td class="td_left1">路线简称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxjc"></label></td>
											<td class="td_left2">路线地方名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxdfmc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">技术等级&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jsdj"></label></td>
											<td class="td_left2">里程&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">管理单位&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gldw"></label></td>
											<td class="td_left2">行政区划（代码）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xzqh"></label></td>
										</tr>
										<tr>
											<td class="td_left1">路段起点名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ldqdmc"></label></td>
											<td class="td_left2">路段止点名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ldzdmc"></label></td>
										</tr>
										<tr>
											<td class="td_left1">路线起点桩号&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxqdzh"></label></td>
											<td class="td_left2">起点是否为分界点&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qdsfwfjd"></label></td>
										</tr>
										<tr>
											<td class="td_left1">路线终点桩号&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lxzdzh"></label></td>
											<td class="td_left2">终点是否为分界点&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdsfwfjd"></label></td>
										</tr>
										<tr>
											<td class="td_left1">断链类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dllx"></label></td>
											<td class="td_left2">路基宽度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ljkd"></label></td>
										</tr>
										<tr>
											<td class="td_left1">车道分类&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cdfl"></label></td>
											<td class="td_left2">设计时速&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjss"></label></td>
										</tr>
										<tr class="aTr">
											<td style="border:0">&nbsp;&nbsp;&nbsp;</td>
											<td style="border:0">&nbsp;</td> 
											<td style="border:0">&nbsp;&nbsp;&nbsp;</td>
											<td style="text-align: right;border:0"><a class="Operate" onclick="seeAllInfo()" style="border-right: 0;font-weight: bold;">查看更多</a></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">起点分界点类别&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="qdfjdlb"></label></td>
											<td class="td_left2">止点分界点类别&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="zdfjdlb"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">路面宽度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="lmkd"></label></td>
											<td class="td_left2">面层类型&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="mclx"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">设计小时交通量&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sjxsjtl"></label></td>
											<td class="td_left2">地貌&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="dm"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">涵洞（米/道）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="hdmd"></label></td>
											<td class="td_left2">是否断头路&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfdtl"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">是否收费路段&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfsfld"></label></td>
											<td class="td_left2">是否晴雨通车&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfqytc"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">是否城管路段&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfcgld"></label></td>
											<td class="td_left2">是否一幅高速&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="sfyfgs"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">重复路段管理单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cfldgldwmc"></label></td>
											<td class="td_left2">重复路线代码&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cflxdm"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">重复路段起点桩号&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cfldqdzh"></label></td>
											<td class="td_left2">重复路段止点桩号&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="cfldzdzh"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">管养单位名称&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gydwmc"></label></td>
											<td class="td_left2">管养单位所属行业类别&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gydwsshylb"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">养护类型（按时间分）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yhlxasjf"></label></td>
											<td class="td_left2">养护类型（按资金来源分）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yhlxazjlyf"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">养护类型（按养护人员分）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yhlx"></label></td>
											<td class="td_left2">养护里程（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yhlcgl"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">可绿化里程（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="klvlcgl"></label></td>
											<td class="td_left2">已绿化里程（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ylvlcgl"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">已实施GBM里程（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="yssgbmlc"></label></td>
											<td class="td_left2">已实施文明样板路的里程（公里）&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ysswmybldlc"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">开工日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="kgrq"></label></td>
											<td class="td_left2">竣工日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="jgrq"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">验收日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="ysrq"></label></td>
											<td class="td_left2">通车日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="tcrq"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">修建年度&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="xjnd"></label></td>
											<td class="td_left2">改建变更日期&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gjbgrq"></label></td>
										</tr>
										<tr class="hideTr">
											<td class="td_left1">改建变更原因&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="gjbgyy"></label></td>
											<td class="td_left2">变更原因说明&nbsp;&nbsp;&nbsp;</td>
											<td id="" class="td_right">&nbsp;&nbsp;&nbsp;<label id="bgyysm"></label></td>
										</tr>
				    				</table>
				    			</div>		    			
		    			</div> 
		    		</div>
		    	
  </body>
</html>
