<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-公交-换乘查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebSit/css/publicTraffic/wisdom.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebSit/css/common/map_bus_popoup.css" />
		<link href="WebSit/js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />
	
		<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>	
		<script type="text/javascript" src="WebSit/js/common/json2.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>
		<script src="WebSit/js/common/jquery-ui-1.8.16.custom.min.js"
			language="javascript"></script>
			
		<script type="text/javascript" src="WebSit/js/publicTraffic/wisdom/bus_transfer.js"></script>
		<script type="text/javascript" src="WebSit/js/common/div_left_handle.js"></script>


	</head>

	<body>
		<div id="page_content">
		
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="1" />
			</jsp:include>
			<!-- 页面内容 -->
		<div id="main_content2">
				<div class="layout1">
					<div class="layout1_left">
						<div class="layout1_left_layout1">
								<a class="layout1_left_layout1_li" href="WebSit/page/publicTraffic/wisdom/road_search.jsp">
									线路查询
								</a>
								<a class="layout1_left_layout1_li" href="WebSit/page/publicTraffic/wisdom/bus_station.jsp">
									站点查询
								</a>
								<a class="layout1_left_layout1_li_yes" href="WebSit/page/publicTraffic/wisdom/bus_transfer.jsp">
									换乘查询
								</a>
							<!-- 	<a class="layout1_left_layout1_li"  href="WebSit/page/publicTraffic/wisdom/favorites.jsp">
									收藏夹
								</a> -->	
						</div>
						<!--起点 终点 搜索 -->
						<div class="layout3_left">
							<div class="layout3_left_layout1">
								<div class="layout3_left_layout1_left">
									<div class="qizhongdian" style="border-bottom: 1px dashed #ccc">起点&nbsp;
									   <input type="text" class="qztext" id="startPoint"  onkeyup="startPointSelect()" onKeyDown="startPointSelect()" onFocus="startPointFocus();"/>
									</div>
									   
								    <div class="qizhongdian">终点
								      <input type="text" style="margin-left: 6px" class="qztext" id="endPoint"  onkeyup="endPointSelect()" onKeyDown="endPointSelect()" onFocus="endPointFocus();"/>    
								    </div>
									<img  src="WebSit/image/graphical/btn_change_normal.png" onMouseOver="ChangeTextOver(this)"
									 onMouseOut="ChangeTextOut(this)" onClick="ChangeTextOnClick()"/>
								</div>
								<img  src="WebSit/image/graphical/btn_search_normal.png" onMouseOver="BarChangeOver(this)"
								 onMouseOut="BarChangeOut(this)" class="layout3_left_layout1_right" onClick="SearchBusTransfer()"/>
								</div>
							</div>
							<div class="layout3_left_layout2">
							<span onClick="TransferOder(this,1)" class="layout3_left_layout2_label_select">少换乘</span><span onClick="TransferOder(this,2)" class="layout3_left_layout2_label">时间短</span>
							<span onClick="TransferOder(this,3)" class="layout3_left_layout2_label">少步行</span>
							<span onClick="TransferOder(this,4)" class="layout3_left_layout2_label">价格少</span><span onClick="TransferOder(this,5)" class="layout3_left_layout2_label">总距离短</span>
							</div>
							<div class="layout1_left_datalist_title" style="visibility: visible;">
								<img src="WebSit/image/graphical/select.png" style="float: left" />
								<span style="float: left;">公交概况</span>
								<span style="float: right;visibility: hidden;">共<label
										id="searchReasultCount"></label>条搜索结果</span>
							</div>
							<div class="layout1_left_trodu" style="text-indent: 2em;padding: 0 10px 10px 10px;line-height: 20px;color: rgb(92, 92, 92);">
  <p>嘉兴公交IC卡（含手机公交卡）可以使用的范围：嘉兴市本级城市公交（100路以内），嘉兴市本级城乡公交【除181路外其余跨县市的城乡公交（跨县市公交指线路编码为151路-192路的公交车）不可以使用】，嘉兴下属的海宁、平湖、桐乡、海盐、嘉善五县市的城市城乡公交，（嘉善县已于近日实现），以及杭州主城区公交（含萧山区、余杭区，出租车不可以使用），嘉兴市区公共自行车（使用公交卡到嘉兴市公共自行车公司自行车办理租借功能时，卡内需要有200元余额押金，使用市民卡需要有100元余额押金）。（09年之前购买的公交IC卡均为非城际通卡，如果需要在杭州等地使用，需要至嘉兴市公交公司IC服务中心免费开通城际通卡功能，09年后购买的公交卡均已经默认开通城际通卡功能。）</p>
  <p>嘉兴公交IC卡（含手机公交卡）及嘉兴市市民卡计费方式：在嘉兴市区乘坐城市公交及城乡公交（181路除外），第一次刷卡为1元，（但如果是在同一辆车上连续刷，譬如带朋友一起乘车，即第二次无优惠为2元），换乘优惠为一小时内（不是半小时，是一小时）第二次刷卡乘坐公交车为5角。（含市本级城市及市本级城乡公交）</p>
  <p>在杭州使用时，除快速公交（B字头）刷卡享受91折优惠外，其他不享受优惠（同理，杭州公交卡在嘉兴使用不享受优惠）</p><p>嘉兴市公交公司IC卡中心（中山西路大桥下，新华书店总店旁，可到达公交站点为：安乐路站），可以提供充值售卡等服务，营业时间8点至18点。联系电话：(0573)82089200。</p>
  
  </div>
								<div class="layout1_left_datalist3">
									
								</div>
								<div id="pageDiv">
									<p>
										<span class="prevBtnSpan"></span>
										<span class="pageNoSpan"></span>
										<span class="nextBtnSpan"></span>
										<span class="gotoPageSpan"></span>
									</p>
								</div>

						</div>
						<div id="div_left_close" onclick="toggleFooter();"></div>
						<div id="div_left_open" onclick="toggleFooter();"></div>
						<div class="layout1_right">
							<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top"> <label class="simpleMap_top_location">嘉兴市</label>
							<div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 页面内容-end -->
				<jsp:include page="../../../../WebSit/page/main/foot.jsp" />
			</div>
			<div id="autocomplete_div1" style="z-index: 1; top: 290px; left: 30px;position: absolute;width: 330px; "></div>
			<div id="autocomplete_div2" style="z-index: 1; top: 324px; left: 30px;position: absolute;width: 330px; "></div>
	</body>
</html>
