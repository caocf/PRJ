<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/register/logindailog.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/menu.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/main/top.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/loadingDiv.css" />
	
		<script type="text/javascript"
			src="WebPage/js/common/inputValidator.js"></script>
		<script type="text/javascript" src="WebPage/js/main/Login.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top_common.js"></script>
		<script type="text/javascript" src="WebPage/js/common/cookie.js"></script>
		<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>
	</head>

	<body>
		<div id="top_container"> 
		<input type="hidden" value="<%=session.getAttribute("user_session_phone")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<input type="hidden" value="<%=request.getParameter("menu_number")%>" id="menu_number" />
		<div id="fullbg"></div>
			<div id="top_layout1">
				<div id="top_layout1_content">
					<div id="top_layout1_left">
						<a  onclick="SetHome()">设为首页</a>|<a onClick="AddFavorite()">加入收藏</a>|<a id="weather"></a>
					</div>
					<div id="top_layout1_right_nologin">
						<a onClick="LoginWeb()">登录</a>|<a href="<%=basePath%>WebPage/page/register/register.jsp">注册</a>|
						<a href="<%=basePath%>WebPage/page/help/help.jsp">帮助</a>
					</div>
					<div id="top_layout1_right_login">
						<a onClick="ShowExitAndPwdDialog()"><b class="top_logionname"><%=session.getAttribute("user_session_name")%></b>
						<img src="<%=basePath%>WebPage/image/main/top_arrow_down_blue.png" id="top_layout1_right_login_img1"/>
						</a>|<a onClick="ShowAPKDowloadDialog()"><img src="<%=basePath%>WebPage/image/main/top_icon_hexingtong.png"/>嘉兴交通禾行通<img 
						 src="<%=basePath%>WebPage/image/main/top_arrow_down_black.png" id="top_layout1_right_login_img2"/></a>|
						 <a href="<%=basePath%>WebPage/page/help/help.jsp">帮助</a>
					</div>
					<div id="personDiv"> 
						<div><label onClick="PersonCenter()">个人资料</label></div>
						<div><label onClick="prExit()" >退出登录</label></div>		
					</div>
					<div id="APKDIV"> 
						<div><label onClick="DownloadAPK(1)">Android版</label></div>
						<div><label onClick="DownloadAPK(2)">Iphone版</label></div>		
					</div>
				</div>
			</div>
			<div id="top_layout2">
				<div id="top_layout2_content">
				<img alt="嘉兴智慧交通出行网" src="<%=basePath%>WebPage/image/main/top_title.png" style="float: left;cursor: pointer;" />
				<!--<img title="返回嘉兴交通运输局" src="WebPage/image/main/traffice_web_normal.png" onClick="OpenTrafficMain()"
				onmouseout="TrafficeWebOut(this)" onMouseOver="TrafficeWebOver(this)" id="top_layout2_traffice_web"/>
				--></div>
			</div>
			<div id="top_layout3">
				<div id="main_menu">
					<ul id="top_menu">
						<li style="background: url(<%=basePath%>WebPage/image/ic_home.png) no-repeat 2px;">
							<a href="http://www.jxjtj.gov.cn/" class="m_1" title="网站首页">网站首页</a>
						</li>
						<!-- <li>
							<a href="WebPage/page/Graphical/driving.jsp" class="m_2"  title="图行嘉兴">图行嘉兴</a>
							<ul>
								<li>
									<a href="WebPage/page/Graphical/self_driving.jsp" title="出行规划" id="s_2_3">出行规划</a>
									<ul>
										<li>
											<a href="WebPage/page/Graphical/self_driving.jsp" title="自驾出行" id="s_2_3_1">自驾出行</a>
										</li>
										<li>
											<a  href="WebPage/page/Graphical/multiple.jsp" title="综合出行" id="s_2_3_2">综合出行</a>
										</li>
										<li>
											<a href="WebPage/page/Graphical/FreightTraffic.jsp" title="货运出行" id="s_2_3_3">货运出行</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="WebPage/page/publicTraffic/wisdom/businfo.jsp" title="公共交通" id="s_2_1">公共交通</a>
									<ul>
										<li>
											<a href="WebPage/page/publicTraffic/wisdom/businfo.jsp" title="公交" id="s_2_1_1">公交</a>
										</li>
										<li>
											<a href="WebPage/page/publicTraffic/bicycle/ConstructionSurvey.jsp" title="公共自行车" id="s_2_1_2">公共自行车</a>
										</li>
										<li>
											<a href="WebPage/page/Passenger/TicketOutlet.jsp" title="长途客运" id="s_2_1_3">长途客运</a>
										</li>
										<li>
											<a href="WebPage/page/taxiInformation/TaxiInfor.jsp" title="出租车" id="s_2_1_4">出租车</a>
										</li>
										<li>
											<a href="WebPage/page/publicTraffic/railway/railwayinfo.jsp" title="铁路" id="s_2_1_5">铁路</a>
										</li>
										<li>
											<a href="WebPage/page/publicTraffic/airline/airlineinfo.jsp" title="民航" id="s_2_1_6">民航</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="WebPage/page/main/LiveTraffic.jsp" title="实时路况" id="s_2_2">实时路况</a>
									<ul>
										<li>
											<a href="WebPage/page/main/LiveTraffic.jsp"  title="城市交通" id="s_2_2_1">城市交通</a>
										</li>
										<li>
											<a href="http://61.130.7.232:7002/gis3/" title="公路交通" id="s_2_2_2">公路交通</a>
										</li>
										<li>
											<a style="color:#666;cursor: not-allowed;" title="水路交通" id="s_2_2_3">水路交通</a>
										</li>
									</ul>
								</li>
								
								<li>
									<a href="WebPage/page/Graphical/driving.jsp" title="行车诱导" id="s_2_4">行车诱导</a>
								</li>
								<li>
									<a href="WebPage/page/Graphical/parking.jsp" title="停车诱导" id="s_2_5">停车诱导</a>
								</li>
							</ul>
						</li> -->
						<li>
							<a href="WebPage/page/Graphical/self_driving.jsp" title="出行规划" class="m_8">出行规划</a>
							<ul>
								<li>
									<a href="WebPage/page/Graphical/self_driving.jsp" title="自驾出行" id="s_8_1">自驾出行</a>
								</li>
								<!--<li>
									<a  href="WebPage/page/Graphical/multiple.jsp" title="综合出行" id="s_2_3_2">综合出行</a>
								</li>
								<li>
									<a href="WebPage/page/Graphical/FreightTraffic.jsp" title="货运出行" id="s_2_3_3">货运出行</a>
								</li>
							--></ul>
						</li>
						<li>
							<a href="WebPage/page/publicTraffic/wisdom/road_search.jsp" title="公共交通" class="m_9">公共交通</a>
							<ul>
								<li>
									<a href="WebPage/page/publicTraffic/wisdom/road_search.jsp" title="公交" id="s_9_1">公交</a>
								</li>
								<li>
									<a href="WebPage/page/publicTraffic/bicycle/ConstructionSurvey.jsp" title="公共自行车" id="s_9_2">公共自行车</a>
								</li>
								<li>
									<a href="WebPage/page/Passenger/TicketOutlet.jsp" title="长途客运" id="s_9_3">长途客运</a>
								</li>
								<li>
									<a href="WebPage/page/taxiInformation/TaxiInfor.jsp" title="出租车" id="s_9_4">出租车</a>
								</li>
								<li>
									<a href="WebPage/page/publicTraffic/railway/railwayinfo.jsp" title="铁路" id="s_9_5">铁路</a>
								</li>
								<li>
									<a href="WebPage/page/publicTraffic/airline/airlineinfo.jsp" title="民航" id="s_9_6">民航</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="WebPage/page/main/LiveTraffic.jsp" title="实时路况" class="m_10">实时路况</a>
							<ul>
								<li>
									<a href="WebPage/page/main/LiveTraffic.jsp"  title="城市交通" id="s_10_1">城市交通</a>
								</li>
								<li>
									<a href="http://61.130.7.232:7002/gis3/" title="公路交通" id="s_10_2">公路交通</a>
								</li>
								<li>
									<a style="color:#666;cursor: not-allowed;" title="水路交通" id="s_10_3">水路交通</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="WebPage/page/Graphical/parking.jsp" title="停车诱导" class="m_11">停车诱导</a>
						</li>
						<!-- <li>
							<a href="WebPage/page/Graphical/driving.jsp" title="行车诱导" class="m_12">行车诱导</a>
						</li> -->
						<li>
							<a href="WebPage/page/carRepair/dot.jsp" class="m_3">维修驾培</a>
							<ul>
								<li>
									<a href="WebPage/page/carRepair/dot.jsp"  title="维修网点查询" id="s_3_1">维修网点查询</a>
								</li>
								<!-- <li>
									<a style="color:#666;cursor: not-allowed;"  title="驾培动态" id="s_3_2">驾培动态</a>
								</li> -->
								<li>
									<a href="WebPage/page/carTraining/drivingIntSec.jsp"  title="驾培网点查询" id="s_3_3">驾培网点查询</a>
								</li>
							</ul>
						</li>

						<!-- <li>
							<a href="WebPage/page/carTraining/drivingIntSec.jsp" class="m_4">驾校培训</a>
							<ul>
								<li>
									<a style="color:#666;cursor: not-allowed;"  title="驾培动态" id="s_4_1">驾培动态</a>
								</li>
								<li>
									<a href="WebPage/page/carTraining/drivingIntSec.jsp"  title="驾培网点查询" id="s_4_2">驾培网点查询</a>
								</li>
							</ul>
						</li> -->
						<li>
							<a href="WebPage/page/trafficNews/trafficNews.jsp" class="m_5">交通快讯</a>
								<ul>
										<li>
											<a href="WebPage/page/trafficNews/trafficNews.jsp" title="交通快讯" id="s_5_1">交通快讯</a>
										</li>
								</ul>
						</li>
						<li>
							<a  class="m_6" >综合信息</a>
							<ul>
								<li>
									<a href="http://60.190.151.158:8080/queryVehVio.do"  title="交通违章查询" id="s_6_1">交通违章查询</a>
								</li>
								<li>
									<a href="WebPage/page/trafficCompany/trafficCompany.jsp"  title="交通运输企业查询" id="s_6_2">交通企业查询</a>
								</li>
								<!-- <li>
									<a style="color:#666;cursor: not-allowed;" id="s_6_2" title="营运证违章查询" id="s_6_2">营运证违章查询</a>
								</li> -->
							</ul>
						</li>
						
						<li>
							<a href="http://115.231.73.253/jxtpi/public_index" class="m_7">交通指数</a>
						</li>
						
						<!-- 
						<li>
							<a href="http://www.jxjtj.gov.cn/page-446066119604.aspx" class="m_7">意见建议</a>
							<ul>
								<li>
									<a href="http://www.jxjtj.gov.cn/page-446066119604.aspx" id="s_7_1" title="投诉受理" id="s_7_1">投诉受理</a>
								</li>
								<li>
									<a href="http://www.jxjtj.gov.cn/page-446066119604.aspx" id="s_7_2" title="意见建议受理" id="s_7_2">意见建议受理</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="WebPage/page/about/about.jsp" class="m_8">关于我们</a>
						</li>
						
						 -->
					</ul>
				</div>
			</div>
		
			
			</div>
			
	
		
		<div id="openNew" style="display:none;">
			<iframe src="" id="LoginWindow" width="100%" height="402px" frameborder="0" scrolling="no" ></iframe>
		</div>
		<!-- 正在加载中 -->
			<div id="ShowLoadingDiv">
				<div id="img_ShowLoadingDiv"><img src='WebPage/image/main/loading.gif'/>
				<label onclick="CloseLoadingDiv()" >取消</label></div> 
			</div> 
			
			<div id="dialog" style="display:none;"><a  onClick="closeLoginDiv()" class="log_close_img">关闭</a>
			<div class="log_top">
				登录智慧交通出行网
			</div>
			<label
				style="color: red; display: block; height: 20px; text-align: center; margin-top: 10px;"
				id="loginerr"></label>
			<div style="margin-top: -38px">
				<div class="log_txt1">
					&nbsp;
					<img style="margin-top: 10px"
						src="WebPage/image/register/text_bg1.png" />
					<input type="text" class="log_txt11" id="userName"
						value="请输入用户名" onBlur="TextBlur(this)"
						onFocus="TextFocus(this)" />
				</div>
				<div class="log_txt2">
					&nbsp;
					<img src="WebPage/image/register/text_bg2.png" />
						<input type="text"  id="passText" value="请输入密码" 
						onfocus="changeTip(this)" class="password_input" style="color: #a3a3a3;"/>
						<input type="password" id="password" class="password_input" style="display:none" onBlur="changeBack(this)"/>
				</div>
			</div>
			<div>
				<input type="checkbox" class="log_che" id="CookieSaveOrDelete"/>
				
				<p
					style="float: left; margin: 20px auto auto 10px; font-size: 12px; ">
					两周内自动登录
				</p>
				<a href="WebPage/page/register/find1.jsp" target="_parent"
					style="display: block; float: left; margin: 20px auto auto 150px; color: #2547a0; font-size: 12px;">忘记密码？</a>
			</div>
			<div class="clear"></div>
			<div>
				<img src="WebPage/image/register/login_sub.png" class="text2"
					style="cursor: auto; width: 320px; margin: 20px auto auto 50px; font-weight: normal;"
					onClick="Login()"/>
			</div>
			<a href="WebPage/page/register/register.jsp" target="_parent"
				style="display: block; float: right; margin: 25px 50px auto auto; color: #2547a1;">立即注册</a>
			<p style="float: right; margin: 25px 10px auto auto; color: #5c5c5c;">
				没有账号？
			</p>
			<div class="clear"></div>
		</div>
	</body>
</html>
