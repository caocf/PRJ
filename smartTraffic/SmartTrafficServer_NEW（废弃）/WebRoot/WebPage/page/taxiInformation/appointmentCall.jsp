<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入预约电召服务</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="WebPage/css/taxiInformation/appointmentCall.css" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" type="text/css"/>
		<link rel="stylesheet" href="WebPage/css/taxiInformation/taxiInformation.css" type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css" type="text/css"></link> 
			
		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/time/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/taxiInformation/appointmentCall.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/taxi/taxiInfor.js"></script>
	</head>
   <body>
  	<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
			<jsp:param name="menu_number" value="2,1,4"/> 
		</jsp:include>
		
		
	<%-- 界面内容 --%>
	<div id="main_content">
	<div class="top">
	<div class="top_1"><a class="top_select_no" onclick="SelectItem_top(this,'WebPage/page/taxiInformation/TaxiInfor.jsp')">出租车概况</a></div>
	<div class="top_1"><a class="top_select" onclick="SelectItem_top(this,'WebPage/page/taxiInformation/appointmentCall.jsp')">预约电招</a></div>
	<!-- <div class="top_2"><a class="top_select_no" onclick="SelectItem_top(this,'#')">预约拼车电招</a></div>
	 --></div>
     <div class="page_dowm">
					<table cellpadding="0" cellspacing="10" border="0">
						<tr>
							<td>
								出发
							</td>
							<td>
								<input class="input_text2" type="text"  id="startCity" />
							</td>
							<td>
								<img src="WebPage/image/publicTraffic/chang_normal.png"
									onClick="ChangeText()" onMouseOut="ChangeOut(this)"
									onMouseOver="ChangeOver(this)" />
							</td>
							<td>
								到达
							</td>
							<td>
								<input class="input_text2" type="text"  id="endCity"/>


							</td>
						</tr>
						<tr>
							<td>
								时间
							</td>
							<td>
								<input  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate: getMaxDate()})" 
					  				class="Wdate"  readonly="readonly"  id="Time"/>
							</td>
							<td>
							</td>
							
							<td>
								手机号码
							</td>
							<td>
								<input type="text"  class="input_text2" id="phone"/>
							</td>
							<td>
								短信提醒
							</td>
							<td>
								<div class="CRselectBox" id="tixing">
									<input type="hidden" value="1" class="abc" />
									<input type="hidden" value="是" class="abc_CRtext" />
									<a class="CRselectValue">是</a>
									<ul class="CRselectBoxOptions">
										<li class="CRselectBoxItem">
											<a class="selected" rel="1">是</a>
										</li>
										<li class="CRselectBoxItem">
											<a rel="2">否</a>
										</li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
					<div>
			<img alt="" src="WebPage/image/publicTraffic/taxiSubmit.png"
				style="float: right; margin: -65px 15px 0 0;" onclick="orderTaxi()"/>
		</div>

		<div class="layout3">
			<table cellpadding="0" cellspacing="0" border="0" class="listTable">
				<col width="2%" />
				<col width="20%" />
				<col width="20%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="*" />
				<tr>
					<th>&nbsp;
					</th>
					<th>
						出发
					</th>
					<th>
						到达
					</th>
					<th>
						时间
					</th>
					<th>
						手机号码
					</th>
					<th>
						短信提示
					</th>
					<th>
						状态
					</th>

				</tr>
			</table>
			<label class="loadingData">
				<span
					style="font-size: 16px; font-weight: bold; color: #666; float: left; margin-top: 20px; margin-left: 46%">暂无任何历史订单</span>
			</label>
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
   </div>
   <%-- 界面内容 --%>
	<jsp:include page="../../../WebPage/page/main/foot.jsp" />

</div>
  </body>
</html>



