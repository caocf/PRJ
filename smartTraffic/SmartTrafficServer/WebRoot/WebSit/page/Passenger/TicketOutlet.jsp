
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-售票网点</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="<%=basePath%>WebSit/css/taxiInformation/taxiInformation.css" type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>WebSit/css/trafficNews/trafficNews.css" type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>WebSit/css/taxiInformation/taxiOverview.css" type="text/css"/>

		<script type="text/javascript"	src="<%=basePath%>WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/taxi/taxiInfor.js"></script>
		


	</head>
	<body>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="page_content">
			<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="3" />
			</jsp:include>

			<%-- 界面内容 --%>
			<div id="main">
				<div class="top">
					<div class="top_1">
						<a class="top_select"
							onclick="SelectItem_top(this,'WebSit/page/Passenger/TicketOutlet.jsp')">售票网点</a>
					</div>
					<div class="top_1">
						<a class="top_select_no"
							onclick="SelectItem_top(this,'WebSit/page/Passenger/ShuttleBusTime.jsp')">班车时刻</a>
					</div>
					<div class="top_1">
						<a class="top_select_no" href="http://keyun.96520.com/bus/querybus.htm">余票查询</a>
					</div>
				</div>
				<div id="main_content">
					<table cellpadding="0" cellspacing="5" border="0">
						<col width="300px" />
						<col width="*" />
						<TR>
							<TD colspan="2">
								<STRONG><FONT size=4>车站查询、售票、订票渠道介绍</FONT> </STRONG>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >1.汽车客运站售票大厅：客运中心、汽车北站、王江泾汽车站可同步互售八日内（含当日）发往各地的汽车票。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >2.自助售票：通过自助售票机刷卡或现金自助售票，可实现与窗口售票相同的功能。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >3.电信代理售票：通过拨打114，可同步查询班次和票源，有偿上门送票。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >4.全省联网售票：登录省运管<A
									href="http://www.96520.com/"><U><FONT color=#0066cc>http://www.96520.com</FONT>
									</U> </A>，可以购买到省内各车站的电子车票（发车前到车站取票）。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >5.宁波返程车票&lt;仅客运中心&gt;：目前可与宁波南站互售返程车票。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >6.邮政代理售票：通过邮政网络（42个网点），延伸售票空间。</FONT>
							</TD>
						</TR>
						<TR>
							<TD colspan="2">
								<FONT >7.移动订票、购票服务：a
									通过拨打12580，可同步查询班线及票源，对需要的班次进行免费座位预订，预订3天，发车半小时前须到车站购买，否则自动取消；b
									也可拨通电话与移动工作人员说明需要购买目的地车票，然后根据语音信箱提示逐步完成车票的购买。</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >8、可以下载手机客户端软件，注册用户信息后直接在手机上进行购票操作。软件下载地址：<A
									href="http://api.86580.cn/api/getmobile.aspx?version=newest"><U><FONT
											color=#0066cc>http://api.86580.cn/api/getmobile.aspx?version=newest</FONT>
									</U> </A> </FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<FONT >9、站务售票点：新增王店佳辉票务代售点（兴乐路线516号）、嘉兴航空服务公司（中山路71号）、高铁南站汽车票代售点（平湖公交车站旁）</FONT>
							</TD>

						</TR>
						<TR>
							<TD colspan="2">&nbsp;
								
							</TD>

						</TR>
						<TR>
							<TD colspan="2">
								<STRONG><FONT size=4>邮政网点分布</FONT> </STRONG>
							</TD>

						</TR>
						<TR>
							<TD>
								<FONT >1.嘉兴勤俭路邮政营业厅</FONT>
							</TD>
							<TD>
								<FONT >勤俭路711号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >2.嘉兴三水湾邮政所</FONT>
							</TD>
							<TD>
								<FONT >农翔桥13号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >3.嘉兴南杨邮政所</FONT>
							</TD>
							<TD>
								<FONT >斜西街502号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >4.嘉兴文昌路邮政所 </FONT>
							</TD>
							<TD>
								<FONT >吉水路459号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >5.嘉兴中山路邮政所</FONT>
							</TD>
							<TD>
								<FONT >中山东路1457号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >6.嘉兴二环北路邮政所</FONT>
							</TD>
							<TD>
								<FONT >中环北路488号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >7.嘉兴清河邮政所</FONT>
							</TD>
							<TD>
								<FONT >禾兴北路137号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >8.嘉兴邮电路邮政所</FONT>
							</TD>
							<TD>
								<FONT >邮电路11号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >9.嘉兴南门邮政所</FONT>
							</TD>
							<TD>
								<FONT >紫阳街345号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >10.嘉兴南湖邮政所</FONT>
							</TD>
							<TD>
								<FONT >南湖新区南溪西路1999号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >11.嘉兴塘汇邮政支局</FONT>
							</TD>
							<TD>
								<FONT >塘汇街道永政南路100号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >12.嘉兴栖真邮政所</FONT>
							</TD>
							<TD>
								<FONT >油车港镇栖真栖霞路161号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >13.嘉兴七星邮政所</FONT>
							</TD>
							<TD>
								<FONT >七星镇桥埭路670号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >14.嘉兴市马厍邮政所</FONT>
							</TD>
							<TD>
								<FONT >油车港镇兴港西路271号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >15.嘉兴油车港邮政所</FONT>
							</TD>
							<TD>
								<FONT >油车港澄溪南路287号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >16.嘉兴余新邮政支局</FONT>
							</TD>
							<TD>
								<FONT >余新镇余北大街五星路口</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >17.嘉兴凤桥邮政所</FONT>
							</TD>
							<TD>
								<FONT >凤桥镇新康路218号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >18.嘉兴曹庄邮政所</FONT>
							</TD>
							<TD>
								<FONT >曹庄镇民丰路20号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >19.嘉兴洪合邮政支局</FONT>
							</TD>
							<TD>
								<FONT >洪合镇嘉洪大道1705号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >20.嘉兴新丰邮政支局</FONT>
							</TD>
							<TD>
								<FONT >新丰镇双龙路</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >21.嘉兴步云邮政所</FONT>
							</TD>
							<TD>
								<FONT >步云镇人民东路69-71号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >22.嘉兴十八里桥邮政所</FONT>
							</TD>
							<TD>
								<FONT >大桥镇十八里西街488号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >23.嘉兴王江泾邮政支局</FONT>
							</TD>
							<TD>
								<FONT >王江泾镇北虹路363号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >24.嘉兴虹阳邮政所</FONT>
							</TD>
							<TD>
								<FONT >虹阳镇兴虹路55号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >25.嘉兴南汇邮政所</FONT>
							</TD>
							<TD>
								<FONT >南汇蚕溪路66号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >26.嘉兴新塍邮政支局</FONT>
							</TD>
							<TD>
								<FONT >新塍镇新洛东路309号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >27.嘉兴高照邮政所</FONT>
							</TD>
							<TD>
								<FONT >高照街道高桥路313号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >28.嘉兴桃园邮政所</FONT>
							</TD>
							<TD>
								<FONT >桃园集镇</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >29.嘉兴洛东邮政所</FONT>
							</TD>
							<TD>
								<FONT >洛东集镇西文桥路447号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >30.嘉兴象贤邮政所</FONT>
							</TD>
							<TD>
								<FONT >秀州工业区象贤路33号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >31.嘉兴王店邮政支局</FONT>
							</TD>
							<TD>
								<FONT >王店镇广平路18号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >32.嘉兴建设邮政所</FONT>
							</TD>
							<TD>
								<FONT >王店建设龙源路317号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >33.嘉兴蚂桥邮政所</FONT>
							</TD>
							<TD>
								<FONT >蚂桥集镇吉蚂路143号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >34.嘉兴友谊路邮政所</FONT>
							</TD>
							<TD>
								<FONT >友谊街259号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >35.嘉兴新篁邮政所</FONT>
							</TD>
							<TD>
								<FONT >新篁社区青龙街48号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >36.嘉兴田乐邮政所</FONT>
							</TD>
							<TD>
								<FONT >田乐肖家湾８号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >37.嘉兴新洲路邮政所</FONT>
							</TD>
							<TD>
								<FONT >新洲路412号</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >38.华庭街报刊亭</FONT>
							</TD>
							<TD>
								<FONT ></FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >39.嘉兴学院报刊亭</FONT>
							</TD>
							<TD>
								<FONT ></FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >40.徒门村油站</FONT>
							</TD>
							<TD>
								<FONT ></FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >41.同济大学报刊亭</FONT>
							</TD>
							<TD>
								<FONT ></FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >42.宇泗浜村油站</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >42.石堰社区</FONT>
							</TD>
							<TD>
								<FONT >石堰北区门口</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >43.东洋浜村邮站</FONT>
							</TD>
							<TD>
								<FONT >东洋浜村委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >44.江南村邮站</FONT>
							</TD>
							<TD>
								<FONT >江南村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >45.云东村邮站</FONT>
							</TD>
							<TD>
								<FONT >云东村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >46.建国村邮站</FONT>
							</TD>
							<TD>
								<FONT >建国村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >47.新民村邮站</FONT>
							</TD>
							<TD>
								<FONT >新民村</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >48.庄史村邮站</FONT>
							</TD>
							<TD>
								<FONT >庄史村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >49.联丰村村邮站</FONT>
							</TD>
							<TD>
								<FONT >联丰村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >50.永红村邮站</FONT>
							</TD>
							<TD>
								<FONT >永红村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >51.禾源社区服务站</FONT>
							</TD>
							<TD>
								<FONT >南大门口</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >52.良三村邮站</FONT>
							</TD>
							<TD>
								<FONT >良三村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >53.凤桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >凤桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >54.建北村邮站</FONT>
							</TD>
							<TD>
								<FONT >建北村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >55.泾桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >泾桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >56.阳海社区</FONT>
							</TD>
							<TD>
								<FONT >阳海社区警务站</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >57.清泰桥村村邮站</FONT>
							</TD>
							<TD>
								<FONT >清泰桥村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >58.江南新家园服务站</FONT>
							</TD>
							<TD>
								<FONT >江南新家园社区</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >59.华玉社区(永政）服务站</FONT>
							</TD>
							<TD>
								<FONT >和风丽苑门口桥下</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >60.茶香坊社区服务站</FONT>
							</TD>
							<TD>
								<FONT >茶香坊东区桥下</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >61.八联村邮站</FONT>
							</TD>
							<TD>
								<FONT >八联村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >62.镇西村邮站</FONT>
							</TD>
							<TD>
								<FONT >镇西村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >63.先锋村邮站</FONT>
							</TD>
							<TD>
								<FONT >先锋村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >64.南星桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >南星桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >65.建林村邮站</FONT>
							</TD>
							<TD>
								<FONT >建林村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >66.建农村邮站</FONT>
							</TD>
							<TD>
								<FONT >建农村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >67.宝华村村邮站</FONT>
							</TD>
							<TD>
								<FONT >宝华村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >68.范滩村邮站</FONT>
							</TD>
							<TD>
								<FONT >范滩村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >69.沈家桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >沈家桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >70.民主村邮站</FONT>
							</TD>
							<TD>
								<FONT >民主村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >71.洪典村邮站</FONT>
							</TD>
							<TD>
								<FONT >洪典村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >72.收藏村邮站</FONT>
							</TD>
							<TD>
								<FONT >收藏村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >73.双桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >双桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >74.市泾村邮站</FONT>
							</TD>
							<TD>
								<FONT >市泾村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >75.荷花村邮站</FONT>
							</TD>
							<TD>
								<FONT >荷花村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >76.北荷村邮站</FONT>
							</TD>
							<TD>
								<FONT >北荷村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >77.湘城社区</FONT>
							</TD>
							<TD>
								<FONT >湘城社区村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >78.观音桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >观音桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >79.天福村村邮站</FONT>
							</TD>
							<TD>
								<FONT >天福村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >80.潘家浜村村邮站</FONT>
							</TD>
							<TD>
								<FONT >潘家浜村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >81.万民村村邮站</FONT>
							</TD>
							<TD>
								<FONT >万民村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >82.大通村村邮站</FONT>
							</TD>
							<TD>
								<FONT >大通村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >83.竹林村邮站</FONT>
							</TD>
							<TD>
								<FONT >竹林村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >84.栖凰埭村邮站</FONT>
							</TD>
							<TD>
								<FONT >栖凰埭村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >85.乌桥村邮站</FONT>
							</TD>
							<TD>
								<FONT >乌桥村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >86.永丰村村邮站</FONT>
							</TD>
							<TD>
								<FONT >永丰村村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >87.濮家湾村邮站</FONT>
							</TD>
							<TD>
								<FONT >濮家湾村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >88.百花庄村邮站</FONT>
							</TD>
							<TD>
								<FONT >百花庄村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >89.马厍村邮站</FONT>
							</TD>
							<TD>
								<FONT >马厍村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >90.陈家坝村邮站</FONT>
							</TD>
							<TD>
								<FONT >陈家坝村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >91.古窦泾村邮站</FONT>
							</TD>
							<TD>
								<FONT >古窦泾村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >92.金星村邮站</FONT>
							</TD>
							<TD>
								<FONT >金星村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >93.农庄村邮站</FONT>
							</TD>
							<TD>
								<FONT >农庄村民委</FONT>
							</TD>
						</TR>
						<TR>
							<TD>
								<FONT >94.普光村邮站</FONT>
							</TD>
							<TD>
								<FONT >普光村民委</FONT>
							</TD>
						</TR>

					</table>

				</div>
			</div>
			<%-- 界面内容 --%>
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />

		</div>
	</body>
</html>
