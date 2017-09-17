<%@ page language="java"  contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("UTF-8");
	String poiKind = null;
	if (request.getParameter("poiKind") != null) {
		poiKind = new String(request.getParameter("poiKind").getBytes(
				"ISO-8859-1"), "UTF-8");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-热点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/graphical/pot.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/graphical/parking.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/Graphical/pot.js"></script>
		<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>

	</head>

	<body><input type="hidden" value="<%=request.getParameter("code")%>" id="code"/>
	<input type="hidden" value="<%=poiKind%>" id="poiKind"/>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="1,1" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content3">
				<div class="layout1">
					<div class="left">
						<div class="left_top">
							<a href="WebPage/page/Graphical/pot.jsp" class="left_top_text1">热点搜索</a>
						</div>
						<div class="left_bottom">
							<div class="left_bottom_wb">
								<input type="text" class="input" value="请输入热点类型"
									onBlur="TextBlur(this)" onFocus="TextFocus(this)"
									id="searchContent" />
							<img src="WebPage/image/search_normal.png"  onclick="searchpoibycatByName()"
						 	 onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" id="serarch_img" style="margin: 20px 0 0 12px;cursor:pointer"/>
								
							</div>
							<div class="tubiao">
								<img src="WebPage/image/graphical/select.png"
									style="float: left; margin: 7px auto auto 10px;" />
								首页
							<span style="float: right; display: none"
									id="searchReasultTitle">共<label id="searchReasultCount"></label>条搜索结果</span>
							<span style="float: right;color: red;cursor: pointer;margin-right: 5px; " id="moreOrBack" onclick='showhidediv("msg_2")'>更多&nbsp;&raquo;</span>
							</div>
							<div id="msg_1" class="left_bottom_lb">
								<div class="lb1">
									<img src="WebPage/image/graphical/qc.png" style="float: left" />
									<p>
										交通运输
									</p>
									<div class="lb1_text">
										<a onclick='SearchPOI("0202")'>火车汽车站</a>&nbsp;
										<a onclick='SearchPOI("0203")'>机场</a>&nbsp;
										<a onclick='SearchPOI("0204")'>货运</a>&nbsp;
										<a onclick='SearchPOI("0207")'>港口码头</a>&nbsp;
										<a onclick='SearchPOI("0209")'>服务区</a>&nbsp;
										<a onclick='SearchPOI("0206")'>票务</a>&nbsp;
										<a onclick='SearchPOI("0205")'>仓储</a>&nbsp;
										<a onclick='SearchPOI("0210")'>收费站</a>&nbsp;
										<a onclick='SearchPOI("0211")'>加油站</a>&nbsp;
										<a onclick='SearchPOI("0212")'>汽车租赁</a>&nbsp;
										<a onclick='SearchPOI("0213")'>汽车销售</a>&nbsp;
										<a onclick='SearchPOI("0214")'>汽车服务</a>&nbsp;
										<a onclick='SearchPOI("0215")'>公路桥</a>&nbsp;
										<a onclick='SearchPOI("0216")'>铁路桥</a>&nbsp;
										<a onclick='SearchPOI("0217")'>街道</a>&nbsp;
										<a onclick='SearchPOI("0201")'>其他</a>
									</div>
								</div>
								<div class="lb1">
									<img src="WebPage/image/graphical/ds.png" style="float: left" />
									<p>
										休闲娱乐
									</p>
									<div class="lb1_text">
										<a onclick='SearchPOI("0802")'>酒吧茶楼</a>&nbsp;
										<a style="color: red" onclick='SearchPOI("0803")'>影剧院</a>&nbsp;
										<a style="color: red" onclick='SearchPOI("0804")'>书店音像</a>&nbsp;
										<a onclick='SearchPOI("0805")'>网吧游艺</a>&nbsp;
										<a onclick='SearchPOI("0806")'>公园游乐场</a>&nbsp;
										<a onclick='SearchPOI("0807")'>体育场馆</a>&nbsp;
										<a onclick='SearchPOI("0808")'>健身活动</a>&nbsp;
										<a onclick='SearchPOI("0809")'>娱乐城</a>&nbsp;
										<a onclick='SearchPOI("0810")'>洗浴足道</a>&nbsp;
										<a onclick='SearchPOI("0811")'>动物园</a>&nbsp;
										<a onclick='SearchPOI("0812")'>高尔夫球场</a>&nbsp;
										<a onclick='SearchPOI("0813")'>滑冰场</a>&nbsp;
										<a onclick='SearchPOI("0801")'>其他</a>
									</div>
								</div>
								<div class="lb1">
									<img src="WebPage/image/graphical/cy.png" style="float: left" />
									<p>
										美食城畅饮
									</p>
									<div class="lb1_text">
										<a onclick='SearchPOI("0702")'>快餐</a>&nbsp;
										<a onclick='SearchPOI("0703")'>西餐馆</a>&nbsp;
										<a style="color: red" onclick='SearchPOI("0704")'>中餐馆</a>&nbsp;
										<a onclick='SearchPOI("0705")'>地方口味与名店</a>&nbsp;
										<a onclick='SearchPOI("0701")'>其他</a>
									</div>
								</div>
								<div class="lb1">
									<img src="WebPage/image/graphical/sh.png" style="float: left" />
									<p>
										生活服务
									</p>
									<div class="lb1_text">
										<a onclick='SearchPOI("0502")'>超市商场</a>&nbsp;
										<a onclick='SearchPOI("0503")'>综合市场</a>&nbsp;
										<a style="color: red" onclick='SearchPOI("0504")'>家政维修</a>&nbsp;
										<a onclick='SearchPOI("0505")'>花艺摄影</a>&nbsp;
										<a onclick='SearchPOI("0506")'>水电气</a>&nbsp;
										<a onclick='SearchPOI("0507")'>快递通信</a>&nbsp;
										<a onclick='SearchPOI("0508")'>律师咨询</a>&nbsp;
										<a onclick='SearchPOI("0509")'>公厕</a>&nbsp;
										<a onclick='SearchPOI("0510")'>烟酒日杂</a>&nbsp;
										<a onclick='SearchPOI("0501")'>其他</a>
									</div>
								</div>
								
								<div class="clear"></div>
								<div class="left_bottom_bot">
									<div class="left_bottom_bot_left">
									<img src="WebPage/image/firstpage/download_pic_hexingtong.png" />
										
									</div>
									<div class="left_bottom_bot_rig">
										<p class="moweiwz1">
											下载禾行通APP
										</p>
										<p class="moweiwz2">
											精确定位&nbsp;精确地图&nbsp; 实时路况&nbsp;道路视频
										</p>
										<img src="WebPage/image/firstpage/download_IOS_normal.png"  onclick="DownloadAPK(2)"
						   						onmouseout="IOSOut(this)" onMouseOver="IOSOver(this)" />
										
										<img  src="WebPage/image/firstpage/download_Android_normal.png" onClick="DownloadAPK(1)"
						   						onmouseout="AndroidOut(this)" onMouseOver="AndroidOver(this)" />
									</div>
								</div>
							</div>

							<div id="msg_2" class="return" style="display: none;">
								<div class="lb11">
									<p>
										交通运输
									</p>
									<div class="lb1_text1">
										<a onclick='SearchPOI("0202")'>火车汽车站</a>&nbsp;
										<a onclick='SearchPOI("0203")'>机场</a>&nbsp;
										<a onclick='SearchPOI("0204")'>货运</a>&nbsp;
										<a onclick='SearchPOI("0205")'>仓储</a>&nbsp;
										<a onclick='SearchPOI("0206")'>票务</a>&nbsp;
										<a onclick='SearchPOI("0207")'>港口码头</a>&nbsp;
										<a onclick='SearchPOI("0209")'>服务区</a>
										<br />
										<a onclick='SearchPOI("0210")'>收费站</a>&nbsp;
										<a onclick='SearchPOI("0211")'>加油站</a>&nbsp;
										<a onclick='SearchPOI("0212")'>汽车租赁</a>&nbsp;
										<a onclick='SearchPOI("0213")'>汽车销售</a>&nbsp;
										<a onclick='SearchPOI("0214")'>汽车服务</a>&nbsp;
										<a onclick='SearchPOI("0217")'>街道</a>
										<br />
										<a onclick='SearchPOI("0215")'>公路桥</a>
										<a	onclick='SearchPOI("0216")'>铁路桥</a>&nbsp;
										<a onclick='SearchPOI("0201")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										休闲娱乐
									</p>
									<div class="lb1_text1">
										<a onclick='SearchPOI("0802")'>酒吧茶楼</a>&nbsp;
										<a onclick='SearchPOI("0803")'>影剧院</a>&nbsp;
										<a onclick='SearchPOI("0804")'>书店音像</a>&nbsp;
										<a onclick='SearchPOI("0805")'>网吧游艺</a>&nbsp;
										<a onclick='SearchPOI("0806")'>公园游乐场</a><br />
										<a onclick='SearchPOI("0813")'>滑冰场</a>&nbsp;										
										<a onclick='SearchPOI("0808")'>健身活动</a>&nbsp;
										<a onclick='SearchPOI("0809")'>娱乐城</a>&nbsp;
										<a onclick='SearchPOI("0810")'>洗浴足道</a>&nbsp;
										<a onclick='SearchPOI("0811")'>动物园</a><br />
										<a onclick='SearchPOI("0812")'>高尔夫球场</a>&nbsp;
										<a onclick='SearchPOI("0807")'>体育场馆</a>&nbsp;
										<a onclick='SearchPOI("0801")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										美食城畅饮
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("0702")'>快餐</a>&nbsp;
										<a onclick='SearchPOI("0703")'>西餐馆</a>&nbsp;
										<a onclick='SearchPOI("0704")'>中餐馆</a>&nbsp;
										<a onclick='SearchPOI("0705")'>地方口味与名店</a>&nbsp;
										<a onclick='SearchPOI("0701")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										生活服务
									</p>
									<div class="lb1_text1" style="height: 50px">
										<a onclick='SearchPOI("0502")'>超市商场</a>&nbsp;
										<a onclick='SearchPOI("0503")'>综合市场</a>&nbsp;
										<a onclick='SearchPOI("0504")'>家政维修</a>&nbsp;
										<a onclick='SearchPOI("0505")'>花艺摄影</a>&nbsp;
										<a onclick='SearchPOI("0506")'>水电气</a>&nbsp;
										<a onclick='SearchPOI("0509")'>公厕</a>
										<br />
										<a onclick='SearchPOI("0508")'>律师咨询</a>&nbsp;
										<a onclick='SearchPOI("0507")'>快递通信</a>&nbsp;
										<a onclick='SearchPOI("0510")'>烟酒日杂</a>&nbsp;
										<a onclick='SearchPOI("0501")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										公共管理
									</p>
									<div class="lb1_text1" style="height: 50px">
										<a onclick='SearchPOI("0102")'>省市县政府</a>&nbsp;
										<a onclick='SearchPOI("0103")'>乡镇街道</a>&nbsp;
										<a onclick='SearchPOI("0104")'>部门管理</a>&nbsp;
										<a onclick='SearchPOI("0105")'>办事机构</a>&nbsp;
										<a onclick='SearchPOI("0106")'>行业组织</a>
										<br />
										<a onclick='SearchPOI("0107")'>市政公用</a>&nbsp;
										<a onclick='SearchPOI("0108")'>企事业单位</a>&nbsp;
										<a onclick='SearchPOI("0109")'>村级自治机构</a>&nbsp;
										<a onclick='SearchPOI("0101")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										金融保险
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("0302")'>银行</a>&nbsp;
										<a onclick='SearchPOI("0303")'>自动取款</a>&nbsp;
										<a onclick='SearchPOI("0304")'>保险公司</a>&nbsp;
										<a onclick='SearchPOI("0305")'>投资信托</a>&nbsp;
										<a onclick='SearchPOI("0301")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										房产楼盘
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("0402")'>住宅区</a>&nbsp;
										<a onclick='SearchPOI("0403")'>大楼大厦</a>&nbsp;
										<a onclick='SearchPOI("0404")'>房产物业</a>&nbsp;
										<a onclick='SearchPOI("0405")'>房产公司</a>&nbsp;
										<a onclick='SearchPOI("0405")'>房产中介</a>&nbsp;
										<a onclick='SearchPOI("0401")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										住宿
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a>星级饭店</a>&nbsp;
										<a>一般旅馆</a>&nbsp;
										<a>非星级度假村、疗养院</a>&nbsp;
										<a>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										旅游服务
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("0902")'>旅游景点</a>&nbsp;
										<a onclick='SearchPOI("0903")'>宗教庙宇</a>&nbsp;
										<a onclick='SearchPOI("0904")'>旅行社</a>&nbsp;
										<a onclick='SearchPOI("0901")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										医疗卫生
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("1002")'>医院</a>&nbsp;
										<a onclick='SearchPOI("1003")'>卫生院</a>&nbsp;
										<a onclick='SearchPOI("1004")'>药房保健品</a>&nbsp;
										<a onclick='SearchPOI("1005")'>救护车</a>&nbsp;
										<a onclick='SearchPOI("1006")'>康复中心</a>&nbsp;
										<a onclick='SearchPOI("1001")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										文化媒体
									</p>
									<div class="lb1_text1">
										<a onclick='SearchPOI("1102")'>幼儿园</a>&nbsp;
										<a onclick='SearchPOI("1103")'>小学</a>&nbsp;
										<a onclick='SearchPOI("1104")'>中学</a>&nbsp;
										<a onclick='SearchPOI("1105")'>高等院校</a>&nbsp;
										<a onclick='SearchPOI("1106")'>职业院校</a>&nbsp;
										<a onclick='SearchPOI("1107")'>综合培训</a><br/>
										<a onclick='SearchPOI("1108")'>驾校</a>&nbsp;
										<a onclick='SearchPOI("1109")'>新闻传媒</a>&nbsp;
										<a onclick='SearchPOI("1110")'>研究所</a>&nbsp;
										<a onclick='SearchPOI("1111")'>图书馆</a>&nbsp;
										<a onclick='SearchPOI("1112")'>文工团</a>&nbsp;
										<a onclick='SearchPOI("1113")'>出版社</a>&nbsp;
										<a onclick='SearchPOI("1114")'>电台</a>&nbsp;
										<a onclick='SearchPOI("1115")'>会议中心</a>&nbsp;
										<a onclick='SearchPOI("1101")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										自然要素
									</p>
									<div class="lb1_text1" style="height: 20px">
										<a onclick='SearchPOI("1202")'>自然村</a>&nbsp;
										<a onclick='SearchPOI("1203")'>泉</a>&nbsp;
										<a onclick='SearchPOI("1204")'>瀑布</a>&nbsp;
										<a onclick='SearchPOI("1205")'>水井</a>&nbsp;
										<a onclick='SearchPOI("1206")'>山洞溶洞</a>&nbsp;
										<a onclick='SearchPOI("1207")'>山峰</a>&nbsp;
										<a onclick='SearchPOI("1201")'>其他</a>
									</div>
								</div>
								<div class="lb11">
									<p>
										其他
									</p>
									<div class="lb1_text1">
										<a onclick='SearchPOI("1302")'>装饰纺织品</a>&nbsp;
										<a onclick='SearchPOI("1303")'>五金化工</a>&nbsp;
										<a onclick='SearchPOI("1204")'>文体办公</a>&nbsp;
										<a onclick='SearchPOI("1305")'>通讯电器</a>&nbsp;
										<a onclick='SearchPOI("1306")'>造纸建材</a>
										<br />
										<a onclick='SearchPOI("1307")'>食品加工</a>&nbsp;
										<a onclick='SearchPOI("1308")'>建设工程</a>&nbsp;
										<a onclick='SearchPOI("1309")'>贸易</a>&nbsp;
										<a onclick='SearchPOI("1310")'>技术服务</a>&nbsp;
										<a onclick='SearchPOI("1311")'>进出口</a>&nbsp;
										<a onclick='SearchPOI("1312")'>农林</a>&nbsp;
										<a onclick='SearchPOI("1313")'>印刷</a>
										<a onclick='SearchPOI("1301")'>其他</a>
									</div>
								</div>
								
							</div>

							<div id="msg_3" class="return" style="display: none;">
								
							</div>

							<div class="layout1_left_datalist3 " style="display:none">
								
							</div>
							<div class="layout1_left2" style="display:none">
								<div id="pageDiv">
									<p>
										<span class="prevBtnSpan"></span>
										<span class="pageNoSpan"></span>
										<span class="nextBtnSpan"></span>
										<span class="gotoPageSpan"></span>
									</p>
								</div>
							</div>
						</div>
					</div>
					<div id="div_left_close" onclick="toggleFooter('left')"></div>
						<div id="div_left_open" onclick="toggleFooter('left')"></div>
						<div class="layout1_right" style="position: relative">
						<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top">
						<label class="simpleMap_top_location">
							嘉兴市
						</label>
						
						<!-- <div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div> -->
					</div>

							
						</div>
					</div>
				</div>
				<!-- 页面内容-end -->
				<jsp:include page="../../../WebPage/page/main/foot.jsp" />
			</div>
	</body>
</html>
