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

		<title>智慧出行-民航-班线查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebSit/css/publicTraffic/ticket2.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebSit/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/TiShiInfo.css" />

		<script type="text/javascript"
			src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebSit/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebSit/js/publicTraffic/airline/ticket.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/time/WdatePicker.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="4" />
			</jsp:include>
		<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
					<a class="a_select">班线查询</a>
					<img src="WebSit/image/common/phone.png" style="height: 30px;margin-left: 20px;">
  
                    <label style="display: inline-block;line-height: 33px;color: #E7761D;vertical-align: top;">订票电话：0573-82096268 0573-82076665</label>
					</div>
				<div class="layout2">
					<label class="layout2_label1">
						出发：
					</label>
					<input class="input_text3" onFocus="TextFocus1_ticket('tishi_startcity')"  id="startCity"/>
					<img src="WebSit/image/publicTraffic/chang_normal.png" onClick="ChangeText()"
					 onMouseOut="ChangeOut(this)" onMouseOver="ChangeOver(this)"/>
					<label class="layout2_label1">
						到达：
					</label>
					<input class="input_text3" onFocus="TextFocus1_ticket('tishi_endcity')" id="endCity"/>
					
					<label class="layout2_label3">
						出发时间：
					</label> 
					<input  class="Wdate" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate: getMaxDate(),minDate:getMinDate()})" 
					  onBlur="TextBlur(this)" readonly="readonly" onFocus="TextFocus(this)" id="leaveTime"/>
					<img src="WebSit/image/firstpage/search_normal.png"
						onclick="ShowDataBySearch('GetAirSchedule', 1)"
						onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />

				</div>
				<div class="layout3" id="TimeItem">
				</div>
				<div class="layout4">
				<label class="layout4_label1"></label><label class="layout4_label2">(共<a id="alltotal"></a>个航班)</label>
				</div>
			<div class="layout6">
					<table cellpadding="0" cellspacing="0" border="0"
						class="listTable2">
						<col width="5%" />
						<col width="10%" />
						<col width="16%" />
						<col width="16%" />
						<col width="16%" />
						<col width="14%" />
						<col width="13%" />
						<col width="10%" />
						<tr>
							<th>&nbsp;
								
							</th>
							<th>
								航班号
							</th>
							<th>
								起抵机场
							</th>
							<th>
								起抵时间
							</th>
							<th>
								航空公司
							</th>
							<th>
								历时
							</th>
							<th>
								参考票价
							</th>
							<th>
								余票
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
				<div class="tishi_startcity">
					<a onClick="SelectCityItem('tishi_startcity','startCity',this)" >上海</a>
					<a onClick="SelectCityItem('tishi_startcity','startCity',this)">杭州</a>
					<a onClick="SelectCityItem('tishi_startcity','startCity',this)">宁波</a></div>
				<div class="tishi_endcity">
					<a  onclick="SelectCityItem('tishi_endcity','endCity',this)" >上海</a>
					<a  onclick="SelectCityItem('tishi_endcity','endCity',this)">杭州</a>
					<a  onclick="SelectCityItem('tishi_endcity','endCity',this)">宁波</a></div>
			</div>
		
			<!-- 页面内容-end -->
			<jsp:include page="../../../../WebSit/page/main/foot.jsp" />
		</div>
	</body>
</html>
