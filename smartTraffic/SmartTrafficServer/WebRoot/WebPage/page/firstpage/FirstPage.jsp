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

		<title>智慧出行-首页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/firstpage/first.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/map.css" />
		<link href="WebPage/js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/ImageDiv.css" />

		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/WebTHelper.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
			<script type="text/javascript"
			src="<%=basePath%>WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/common/div_left_handle.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/firstpage/firstPage.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="null" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="main_layout1">
					<div class="main_layout1_left">
						<div class="main_layout1_left_layout1">
							<div class="main_layout1_left_layout1_title">
								智慧公交
							</div>
							<div class="main_layout1_left_layout1_content">
								<ul class="main_layout1_left_ul">
									<li class="main_layout1_left_layout1_li"
										onclick="ChangeDiv('01',this)">
										线路查询
									</li>
									<li class="main_layout1_left_layout1_li_yes"
										onclick="ChangeDiv('02',this)">
										站点查询
									</li>
									<li class="main_layout1_left_layout1_li"
										onclick="ChangeDiv('03',this)">
										换乘查询
									</li>
								</ul>
								<div class="main_layout1_left_layout1_item" id="mllli_01">
									<input type="text" class="input_text" id="SearchBusContent1" />
									<img src="WebPage/image/search_normal.png" onClick="SearchBus(1)"
										onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />
								</div>


								<div class="main_layout1_left_layout1_item_yes" id="mllli_02">
									<input type="text" class="input_text"  id="SearchBusContent2"/>
									<img src="WebPage/image/search_normal.png" onClick="SearchBus(2)"
										onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />
								</div>
								
								<div class="main_layout1_left_layout1_item" id="mllli_03">
									<div class="layout3_left">
							      		<div class="layout3_left_layout1">
											<div class="layout3_left_layout1_left_01">
												<div class="qizhongdian_01" style="border-bottom: 1px dashed #ccc">起点&nbsp;
												      <input type="text" class="qztext_01" id="startPoint" />
												</div>
											    <div class="qizhongdian_01">终点
											           <input type="text" style="margin-left: 6px" class="qztext_01" id="endPoint" />
											         
											    </div>
											<img  src="WebPage/image/graphical/btn_change_normal.png" onMouseOver="ChangeTextOver(this)"
											 onMouseOut="ChangeTextOut(this)" onClick="ChangeTextOnClick()"/>
										</div>
										<img  style="margin-left: 10px" src="WebPage/image/graphical/btn_search_normal.png" onMouseOver="BarChangeOver(this)"
										 onMouseOut="BarChangeOut(this)" class="layout3_left_layout1_right" onClick="SearchBus(3)"/>
									</div>
								</div>
							</div>
						</div>
						</div>
						<div class="main_layout1_left_layout3">
							

						</div>
					</div>
					<div class="main_layout1_right">
						<div id="simpleMap" class="simpleMap_notop"></div>
					</div>

				</div>
				<div class="main_layout2">
					<div class="main_layout2_left">
						<div class="main_layout1_left_layout2_left">
								<img src="WebPage/image/firstpage/download_pic_hexingtong.png" />
							</div>
							<div class="main_layout1_left_layout2_right">
								<label class="main_layout1_left_layout2_right_01">
									下载禾行通APP
								</label>
								<label class="main_layout1_left_layout2_right_02">
									精确定位&nbsp;精准地图 &nbsp;实时路况
								</label>
								<img class="main_layout1_left_layout2_right_03"
									src="WebPage/image/firstpage/download_IOS_normal.png"
									onmouseout="IOSOut(this)" onMouseOver="IOSOver(this)"
									onclick="DownloadAPK(2)" />
								<img class="main_layout1_left_layout2_right_04"
									src="WebPage/image/firstpage/download_Android_normal.png"
									onmouseout="AndroidOut(this)" onMouseOver="AndroidOver(this)"
									onclick="DownloadAPK(1)" />
							</div>

						
					</div>
					<div class="main_layout2_right">
						
						
					</div>
				</div>
				<div id="main_layout3">
					<div class="main_layout1_left_layout1_title">
						城市特色
					</div>
					<div id="main_layout3_content" onMouseOver="clearInterval(MyMar)"
							onmouseout="MyMarOut()">
						<table cellpadding=0 cellspacing="0" border=0>
							<tr>
								<td>
									<div id="main_layout3_img1">
										<img src="<%=basePath%>WebPage/image/firstpage/0000.jpg" onClick="openToLargeImage('E0000.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0001.jpg" onClick="openToLargeImage('E0001.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0002.jpg" onClick="openToLargeImage('E0002.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0003.jpg" onClick="openToLargeImage('E0003.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0004.jpg" onClick="openToLargeImage('E0004.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0005.jpg" onClick="openToLargeImage('E0005.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0006.jpg" onClick="openToLargeImage('E0006.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0007.jpg" onClick="openToLargeImage('E0007.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0008.jpg" onClick="openToLargeImage('E0008.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0009.jpg" onClick="openToLargeImage('E0009.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0010.jpg" onClick="openToLargeImage('E0010.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0011.jpg" onClick="openToLargeImage('E0011.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0012.jpg" onClick="openToLargeImage('E0012.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0013.jpg" onClick="openToLargeImage('E0013.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0014.jpg" onClick="openToLargeImage('E0014.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0015.jpg" onClick="openToLargeImage('E0015.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0016.jpg" onClick="openToLargeImage('E0016.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0017.jpg" onClick="openToLargeImage('E0017.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0018.jpg" onClick="openToLargeImage('E0018.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0019.jpg" onClick="openToLargeImage('E0019.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0020.jpg" onClick="openToLargeImage('E0020.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0022.jpg" onClick="openToLargeImage('E0022.jpg')"/>
										<img src="<%=basePath%>WebPage/image/firstpage/0023.jpg" onClick="openToLargeImage('E0023.jpg')"/>
									</div>
								</td>
								<td>
									<div id="main_layout3_img2"></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<!-- 页面内容-end -->
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
			<!-- 正在加载中 -->
			<div id="ShowPopUpDiv">
				<p onclick="CloseLargeImage()">关闭</p>
				<div id="img_ShowPopUpDiv"></div> 
			</div> 
	</body>
</html>
