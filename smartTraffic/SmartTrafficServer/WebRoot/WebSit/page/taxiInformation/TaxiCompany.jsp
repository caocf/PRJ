
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

		<title>智慧出行-出租车企业信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet"
			href="<%=basePath%>WebSit/css/taxiInformation/taxiInformation.css"
			type="text/css"></link>

		<script type="text/javascript"
			src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
<style type="text/css">
.com_div{margin-top: 10px; margin-bottom: 10px;border-bottom: dashed 1px blue;}
.com_title{
cursor: pointer;
margin-top: 10px; margin-bottom: 10px;font-size: 16px;
font-weight: bold;

}
.com_title:hover{
color:#FF9900
}
.com_title img{
vertical-align: bottom;}
.com_time{
float: right;
font-size: 12px;
line-height: 20px;
font-weight: normal;}
.com_content{
font-size: 14px; font-weight: normal; letter-spacing: 2px; color: rgb(51, 51, 51); line-height: 20px; text-indent: 2em;  margin-bottom: 20px;display:none}</style>
<script type="text/javascript">
function ToggleP(id){
$$("#"+id).toggle();
}</script>

	</head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="8" />
			</jsp:include>

			<div id="main_content">
				<div class="top">
					<div class="top_1">
						<a class="top_select_no"
							href="<%=basePath%>WebSit/page/taxiInformation/TaxiInfor.jsp">出租车概况</a>
					</div>
					<div class="top_1">
						<a class="top_select"
							href="<%=basePath%>WebSit/page/taxiInformation/TaxiCompany.jsp">出租车企业信息</a>
					</div>
					
				</div>
				<div class="page_dowm">
				<div class="com_div">
					<p onclick="ToggleP('company1')" class="com_title">
						<img src="WebSit/image/common/arrow.gif">
						嘉兴客运出租公司简介
						<font class="com_time" color="#999">发布日期：2008-12-11</font>
					<p>
					<p id="company1" class="com_content">
						嘉兴客运出租公司组建于一九九六年四月，是嘉兴市客运出租行业的骨干企业，隶属于国鸿（集团）有限责任公司，公司具有独立法人资格，注册资金84．5万，拥有出租汽车147辆，驾驶员350余人，企业规模较大，车辆保有量占市本级出租汽车辆数的17.2%。公司于2002年3月被交通部授予“2000-2001年度全国道路运输系统文明单位”；2002年被嘉兴市运输管理处授予“二星级企业”和企业管理先进单位；2005年被省交通厅评为年度“十佳出租汽车企业”；2004年被嘉兴市财政局评为“财务会计信用等级A类单位”； 2007年被嘉兴市公路运输管理处授予“市本级出租汽车行业管理先进企业”和“信用考核AAA级运输企业”。
						地址：嘉兴市汽车北站内    联系电话：82222112
					</p>
				</div>
				<div class="com_div">
					<p onclick="ToggleP('company2')" class="com_title">
						<img src="WebSit/image/common/arrow.gif">
						嘉兴市南海巴士出租汽车有限公司简介
						<font class="com_time" color="#999">发布日期：2008-12-11</font>
					<p>
					<p id="company2" class="com_content">
							  嘉兴市南海巴士出租汽车有限公司是上海巴士出租汽车公司下属股份制企业，成立于2004年9月，现有出租汽车135辆，从业人员305人，管理人员10人，固定资产1500万元，公司经营管理体制设董事会、总经理室、业务部、车队、财务中心和车管部。2005年获市运管处“管理星级单位”称号；2006年实现了全市唯一一家浙江省出租汽车行业首届“优秀品牌”企业； 2008年获得省运管局“文明出租汽车企业”。
							地址：嘉兴市二环西路2518号    联系电话：82714708
					</p>
				</div>
				<div class="com_div">
					<p onclick="ToggleP('company3')" class="com_title">
						<img src="WebSit/image/common/arrow.gif">
						嘉兴市南海巴士出租汽车有限公司简介
						<font class="com_time" color="#999">发布日期：2008-12-11</font>
					<p>
					<p id="company3" class="com_content">
     嘉兴市禾城出租汽车有限公司成立于1997年5月1日，规模由组建时挂靠的73辆出租汽车，通过收购、招标，逐步发展到现有的165辆出租汽车，占现有嘉兴出租汽车总数的20.4%，现有管理人员7人，大专学历以上5人。1999年被评为先进私营企业，2004年被评为出租汽车经营文明单位，2007年被评为本级出租汽车行业管理先进企业等。
地址：嘉兴市时代广场D-601    联系电话：82212115
					</p>
				</div>
					

				</div>

			</div>
			<jsp:include page="../../../WebSit/page/main/foot.jsp" />

		</div>
	</body>
</html>
