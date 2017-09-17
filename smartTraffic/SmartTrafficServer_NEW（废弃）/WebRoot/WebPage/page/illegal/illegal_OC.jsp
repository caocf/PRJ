<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>智慧出行-营运证违章查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/illegal/illegal.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/table.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
		
		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
		<script type="text/javascript" src="WebPage/js/illegal/illegal_OC.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		
	</head>

	<body>
		<div id="page_content">
	<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="6,2"/> 
		</jsp:include>
		<!-- 页面内容 -->
		<div id="main_content">
				<div class="layout1">
					<div class="layout1_left"><label style="display:none" id="loginerr"></label>
						<table cellpadding="0" cellspacing="0" border="0">
						<col width="80px"/><col width="110px"/><col width="*"/>
						<col width="80px"/><col width="90px"/><col width="*"/>
							<tr>
								<td valign="middle">
									查询城市：
								</td>
								<td valign="middle">
									<div class="CRselectBox" id="layout1_select01">
										<input type="hidden" value="1"  class="abc" />
										<input type="hidden" value="浙江" class="abc_CRtext" />
										<a class="CRselectValue" href="#">浙江</a>
										<ul class="CRselectBoxOptions">
											<li class="CRselectBoxItem">
												<a  class="selected" rel="1">浙江</a>
											</li>
										</ul>
									</div>
								</td>
								<td valign="middle">
									<div  class="CRselectBox" id="layout1_select02" style="margin-right: 35px;">
										<input type="hidden" value="1" class="abc" />
										<input type="hidden" value="嘉兴" 	class="abc_CRtext" />
										<a class="CRselectValue" >嘉兴</a>
										<ul class="CRselectBoxOptions">
											<li class="CRselectBoxItem">
												<a  class="selected" rel="1">嘉兴</a>
											</li>
										</ul>
									</div>
								</td>
								<td valign="middle">
									营运车号：
								</td>
								<td valign="middle">
								
								<div  class="CRselectBox" id="layout1_select03">
										<input type="hidden" value="1"  class="abc" />
										<input type="hidden" value="浙" 	class="abc_CRtext" />
										<a class="CRselectValue" >浙</a>
										<ul class="CRselectBoxOptions">
											<li class="CRselectBoxItem">
												<a  class="selected" rel="1">浙</a>
											</li>
											
										</ul>
									</div>
									
								</td>
								<td valign="middle">
								<input type="text" class="input_text2" id="CarNumber"/>
								</td>
							</tr>
							<tr>
								<td valign="middle">
									营运证号：
								</td>
								<td colspan="2" valign="middle">
								<div class="div_input">
									<input type="text" class="input_text_illegal"  id="EngineNumber"/>
								  </div>
								</td>
								<td valign="middle">
									营运公司：
								</td>
								<td colspan="2" valign="middle">
								<div class="div_input">
									<input type="text" class="input_text_illegal" id="FrameNumber"/>
								</div>
								</td>
								
							</tr>
							
						</table>

					</div>
					<div class="layout1_right">
					
					<img src="WebPage/image/query_normal.png"
					onclick="ShowIllegalByPage('GetBreakRulesRecord', 1)"
					 onmouseout="QueryOut(this)" onMouseOver="QueryOver(this)"/>
					
					</div>
				</div>
					<div class="layout2">
					<table cellpadding="0" cellspacing="0" border="0" class="listTable">
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="20%" />
						<col width="8%" />
						<col width="6%" />
						<col width="6%" />
						<tr>
							<th class="listTable_th01">
								车牌号码
							</th>
							<th>
								车辆类型
							</th>
							<th>
								违章时间
							</th>
							<th>
								违章地点
							</th>
							<th>
								违章行为
							</th>
							<th>
								记分（分）
							</th>
							<th class="listTable_th02">
								罚金（元）
							</th>
						</tr>
					</table>
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
		<!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
