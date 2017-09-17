<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/main/top.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/loadingDiv.css" />

		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top_common.js"></script>
		<script type="text/javascript" src="WebPage/js/common/cookie.js"></script>
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
						<a href="http://www.jxjtj.gov.cn/page-446066119604.aspx">意见建议</a>|<a href="<%=basePath%>WebPage/page/help/help.jsp">帮助</a>
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
				<img alt="嘉兴智慧交通出行网" src="<%=basePath%>WebPage/image/main/top_title.png" style="float: left;cursor: pointer;" onClick="OpenTrafficMain()"/>
				<!--<img title="返回嘉兴交通运输局" src="<%=basePath%>WebPage/image/main/traffice_web_normal.png" onClick="OpenTrafficMain()"
				onmouseout="TrafficeWebOut(this)" onMouseOver="TrafficeWebOver(this)" id="top_layout2_traffice_web"/>
				--></div>
			</div>
			<div id="top_layout3">
				<div id="main_menu">
					<ul id="top_menu">
						<li style="background: url(<%=basePath%>WebPage/image/ic_home.png) no-repeat 9px 15px;">
							<a onClick="CreatePage1_1_1()" class="m_1" title="网站首页">网站首页</a>
						</li>
						<li>
							<a onClick="CreatePage2_3_1()" class="m_2" title="图行嘉兴">图行嘉兴</a>
							<ul>
								<li>
									<a onClick="CreatePage2_3_1()" title="出行规划" id="s_3_3">出行规划</a>
									<ul>
										<li>
											<a onClick="CreatePage2_3_1()" title="自驾出行" id="s_2_3_1">自驾出行</a>
										</li>
										<!--<li>
											<a onClick="CreatePage2_3_2()" title="综合出行" id="s_2_3_2">综合出行</a>
										</li>
										<li>
											<a onClick="CreatePage2_3_3()" title="货运出行" id="s_2_3_3">货运出行</a>
										</li>
									--></ul>
								</li>
								<li>
									<a onClick="CreatePage2_1_1()" title="公共交通" id="s_2_1">公共交通</a>
									<ul>
										<li>
											<a onClick="CreatePage2_1_1()" title="公交" id="s_2_1_1">公交</a>
										</li>
										<li>
											<a onClick="CreatePage2_1_2()" title="公共自行车" id="s_2_1_2">公共自行车</a>
										</li>
										<li>
											<a onClick="CreatePage2_1_3()" title="长途客运" id="s_2_1_3">长途客运</a>
										</li>
										<li>
											<a onClick="CreatePage2_1_4()" title="出租车" id="s_2_1_4">出租车</a>
										</li>
										<li>
											<a onClick="CreatePage2_1_5()" title="铁路" id="s_2_1_5">铁路</a>
										</li>
										<li>
											<a onClick="CreatePage2_1_6()" title="民航" id="s_2_1_6">民航</a>
										</li>
									</ul>
								</li>
								<li>
									<a onClick="CreatePage2_2_1()" title="实时路况" id="s_2_2">实时路况</a>
									<ul>
										<li>
											<a onClick="CreatePage2_2_1()"  title="城市交通" id="s_2_2_1">城市交通</a>
										</li>
										<li>
											<a onClick="CreatePage2_2_2()" title="公路交通" id="s_2_2_2">公路交通</a>
										</li>
										<li>
											<a style="color:#666;cursor: not-allowed;" title="水路交通" id="s_2_2_3">水路交通</a>
										</li>
									</ul>
								</li>
								
								<li>
									<a onClick="CreatePage2_4_2()" title="行车诱导" id="s_2_4">行车诱导</a>
								</li>
								<li>
									<a onClick="CreatePage2_4_1()" title="停车诱导" id="s_2_5">停车诱导</a>
								</li>
							</ul>
						</li>
						<li>
							<a onClick="CreatePage3_1_1()" class="m_3">汽车维修</a>
							<ul>
								<li>
									<a onClick="CreatePage3_1_1()"  title="维修网点查询" id="s_3_1">维修网点查询</a>
								</li>
							</ul>
						</li>

						<li>
							<a onClick="CreatePage4_2_1()" class="m_4">驾校培训</a>
							<ul>
								<li>
									<a style="color:#666;cursor: not-allowed;"  title="驾培动态" id="s_4_1">驾培动态</a>
								</li>
								<li>
									<a onClick="CreatePage4_2_1()"  title="驾培网点查询" id="s_4_2">驾培网点查询</a>
								</li>
							</ul>
						</li>
						<li>
							<a onClick="CreatePage5_1_1()" class="m_5">交通快讯</a>
								<ul>
										<li>
											<a onClick="CreatePage5_1_1()" title="交通快讯" id="s_5_1">交通快讯</a>
										</li>
								</ul>
						</li>
						<li>
							<a style="color:#666;cursor: not-allowed;" class="m_6" >综合信息</a>
							<ul>
								<li>
									<a style="color:#666;cursor: not-allowed;" id="s_6_1" title="交通违章查询" id="s_6_1">交通违章查询</a>
								</li>
								<li>
									<a style="color:#666;cursor: not-allowed;" id="s_6_2" title="营运证违章查询" id="s_6_2">营运证违章查询</a>
								</li>
							</ul>
						</li>
						<li>
							<a onClick="CreatePage7_1_1()" class="m_7">意见建议</a>
							<ul>
								<li>
									<a onClick="CreatePage8_1_1()" id="s_7_1" title="投诉受理" id="s_7_1">投诉受理</a>
								</li>
								<li>
									<a onClick="CreatePage8_2_1()" id="s_7_2" title="意见建议受理" id="s_7_2">意见建议受理</a>
								</li>
							</ul>
						</li>
						<li>
							<a onClick="CreatePage8_1_1()" class="m_8">关于我们</a>
						</li>
					</ul>
				</div>
			</div>
		
			
			</div>
			
	
		
		<div id="openNew" style="display:none;">
			<iframe src="" id="LoginWindow" width="100%" height="402px" frameborder="0" scrolling="no" ></iframe>
		</div>
		<!-- 正在加载中 -->
			<div id="ShowLoadingDiv">
				<div id="img_ShowLoadingDiv"><img src='WebPage/image/main/loading.gif'/></div> 
			</div> 
	</body>
</html>
